(defproject circuit-breaker "0.1.1-SNAPSHOT"
  :description "Circuit breaker for Clojure"
  :url "https://github.com/josephwilk/circuit-breaker"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-time "0.4.4"]
                 [org.clojure/tools.logging "0.2.3"]]
  :profiles {:dev {:dependencies [[midje "1.4.0"]
                                  [bultitude "0.1.7"]]
                   :plugins      [[lein-midje "2.0.4"]
                                  [lein-kibit "0.0.7"]]}})
