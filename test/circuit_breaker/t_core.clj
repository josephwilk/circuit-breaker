(ns circuit-breaker.t-core
  (:require
    [midje.sweet :refer :all]
    [circuit-breaker.core :refer :all])
  (:import java.util.UUID))

(defn guid []
  (str (UUID/randomUUID)))

(facts "defncircuitbreaker"
  (fact "with no errors wrapped method should be called"
    (defncircuitbreaker :service-x {:timeout 30 :threshold 1})

    (let [random-guid (guid)]
      (wrap-with-circuit-breaker :service-x (fn [] random-guid)) => random-guid))

  (fact "it should not run the wrapped method when the number of errors is greater than the threshold and the timeout has not expired"
    (defncircuitbreaker :service-x {:timeout 30 :threshold 1})

    (let [random-guid (guid)]
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))))
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))))

      (wrap-with-circuit-breaker :service-x (fn [] random-guid)) => nil)))