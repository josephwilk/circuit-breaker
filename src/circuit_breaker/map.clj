(ns circuit-breaker.map
  (:refer-clojure :exclude [keys get])
  (:import java.util.concurrent.ConcurrentHashMap))

(defn new-map []
  (ConcurrentHashMap.))

(defn put [map key value]
  (.put map key value))

(defn get [map key]
  (.get map key))

(defn clear [map]
  (.clear map))

(defn keys [map]
  (.keys map))