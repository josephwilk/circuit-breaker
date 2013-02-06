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

      (wrap-with-circuit-breaker :service-x (fn [] random-guid)) => nil))

  (fact "it should run the wrapped method when the timeout has expired"
    (defncircuitbreaker :service-x {:timeout 1 :threshold 1})

    (let [random-guid (guid)]
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))))
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))))

      (wrap-with-circuit-breaker :service-x (fn [] random-guid)) => nil

      (Thread/sleep 2000)

      (wrap-with-circuit-breaker :service-x (fn [] random-guid)) => random-guid)))

(facts "defncircuitbreaker with multiple circuits"
  (fact "it should keep different circuits seperate"
    (defncircuitbreaker :service-y {:timeout 20 :threshold 2})
    (defncircuitbreaker :service-x {:timeout 30 :threshold 1})

    (let [random-guid (guid)]
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))))
      (wrap-with-circuit-breaker :service-y (fn [] (throw (Exception. "Oh crap"))))
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))))

      (wrap-with-circuit-breaker :service-y (fn [] random-guid)) => random-guid

      (wrap-with-circuit-breaker :service-x (fn [] random-guid)) => nil)))

(facts "wrap-with-circuit-breaker with a default"
  (fact "it should run the default if the method errors"
    (defncircuitbreaker :service-x {:timeout 1 :threshold 1})

    (let [random-guid (guid)]
      (wrap-with-circuit-breaker :service-x (fn [] :success) (fn [] :default)) =not=> :default
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))) (fn [] :default)) => :default
      (wrap-with-circuit-breaker :service-x (fn [] (throw (Exception. "Oh crap"))) (fn [] :default)) => :default

      (wrap-with-circuit-breaker :service-x (fn [] random-guid) (fn [] :default)) => :default)))