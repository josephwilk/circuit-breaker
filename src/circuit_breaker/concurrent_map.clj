(ns circuit-breaker.concurrent-map
  (:refer-clojure :exclude [keys get remove])
  (:import java.util.concurrent.ConcurrentHashMap))

(defn new []
  (ConcurrentHashMap.))

(defn put [map key value]
  (.put map key value))

(defn get [map key]
  (.get map key))

(defn clear [map]
  (.clear map))

(defn keys [map]
  (.keys map))

(defn remove [map key]
  (.remove map key))