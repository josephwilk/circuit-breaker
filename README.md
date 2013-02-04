# Circuit Breaker

A Clojure library for the circuit breaker pattern:

http://en.wikipedia.org/wiki/Circuit_breaker_design_pattern

## Usage

```clojure
(defncircuitbreaker :memcache {:timeout 30 :threshold 1})

(wrap-with-circuit-breaker :memcache (fn [] do-something-that-might-exception))
```
