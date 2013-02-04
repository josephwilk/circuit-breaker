(ns circuit-breaker.core
  (:require
    [clj-time.core :as time]
    [clojure.tools.logging :as logger]))

(def _circuit-breakers-counters (atom {}))
(def _circuit-breakers-config   (atom {}))
(def _circuit-breakers-open     (atom {}))

(defn- failure-threshold [circuit-name]
  (:threshold @(circuit-name @_circuit-breakers-config)))

(defn- timeout-in-seconds [circuit-name]
  (:timeout @(circuit-name @_circuit-breakers-config)))

(defn- time-since-broken [circuit-name]
  @(circuit-name @_circuit-breakers-open))

(defn- exception-counter [circuit-name]
  @(circuit-name @_circuit-breakers-counters))

(defn- update-circuit [breakers circuit-name]
  (let [counter @(circuit-name breakers)]
    (assoc breakers circuit-name (atom (inc counter)))))

(defn- inc-counter [circuit-name]
  (swap! _circuit-breakers-counters update-circuit circuit-name))

(defn- failure-count [circuit-name]
  (exception-counter circuit-name))

(defn- record-opening! [circuit-name]
  (swap! _circuit-breakers-open assoc circuit-name (atom(time/now))))

(defn record-failure [circuit-name]
  (inc-counter circuit-name)
  (when (> (failure-count circuit-name) (failure-threshold circuit-name))
    (record-opening! circuit-name)))

(defn record-success [circuit-name]
  (swap! _circuit-breakers-open assoc circuit-name (atom nil))
  (swap! _circuit-breakers-counters assoc circuit-name (atom 0)))

(defn- breaker-open? [circuit-name]
  (not (not (time-since-broken circuit-name))))

(defn- timeout-exceeded? [circuit-name]
  (> (time/in-secs (time/interval (time-since-broken circuit-name) (time/now))) (timeout-in-seconds circuit-name)))

(defn tripped? [circuit-name]
  (and (breaker-open? circuit-name) (not (timeout-exceeded? circuit-name))))

(defn defncircuitbreaker [circuit-name settings]
  (swap! _circuit-breakers-counters merge {circuit-name (atom 0)})
  (swap! _circuit-breakers-config   merge {circuit-name (atom settings)})
  (swap! _circuit-breakers-open     merge {circuit-name (atom nil)}))

(defn wrap-with-circuit-breaker [circuit-name method-that-might-error]
  (if-not (tripped? circuit-name)
    (try
      (let [result (method-that-might-error)]
        (record-success circuit-name)
        result)
    (catch Exception e
      (logger/error e)
      (record-failure circuit-name)
      e))))