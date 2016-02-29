(defproject circuit-breaker "0.1.8-SNAPSHOT"
  :description "Circuit breaker for Clojure"
  :url "https://github.com/josephwilk/circuit-breaker"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-time "0.4.4"]
                 [slingshot "0.10.3"]]

  :aliases {"compatibility" ["with-profile" "1.4:1.5" "test"]}

  :profiles {:dev {:dependencies [[midje "1.4.0"]
                                  [bultitude "0.1.7"]]
                   :plugins      [[lein-midje "2.0.4"]
                                  [lein-kibit "0.0.7"]
                                  [jonase/eastwood "0.0.2"]
                                  [lein-cloverage "1.0.2"]]}

             :1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.0"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}})
