(defproject advent-of-code-2023 "0.1.0-SNAPSHOT"

  :description "TODO"

  :url "TODO"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repl-options {:port 4555}

  :plugins [[lein-eftest "0.5.9"]]

  :test-selectors {:quick (complement :slow)
                   :slow  :slow}

  :eftest {:multithread? :vars
           :thread-count 4
           :test-warn-time 250}
  
  :dependencies [;; core
                 [org.clojure/tools.nrepl "0.2.13"]
                 [org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.trace "0.7.11"]

                 ;; Logging
                 [com.taoensso/timbre "6.3.1"]

                 ;; Spec helpers
                 [expound "0.9.0"]

                 ;; DI
                 [mount "0.1.17"]

                 ;; Profiling
                 [com.taoensso/tufte "2.6.3"]]
  
  :profiles {:dev     {:dependencies [[org.clojure/tools.namespace "1.4.4"]]
                       :source-paths ["dev"]}})
