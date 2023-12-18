(ns user
  (:require [clojure.java.javadoc :refer [javadoc]]
            [clojure.pprint :refer [pprint print-table]]
            [clojure.reflect :refer [reflect]]
            [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.stacktrace :refer [print-stack-trace]]
            [taoensso.timbre :as timbre]
            [clojure.test :as test]
            [clojure.edn :as edn]
            [clojure.spec.alpha :as spec]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [clojure.tools.trace :refer [trace-ns untrace-ns]]
            [expound.alpha :as expound]
            [mount.core :as mount]))

(timbre/refer-timbre)
(timbre/set-level! (keyword :warn))


(defn- enforce-spec [x]
  (do
    (spec/check-asserts x)
    (set! spec/*explain-out* expound/printer)))

(defn print-methods [x]
  (->> x
       reflect
       :members 
       (filter #(contains? (:flags %) :public))
       (sort-by :name)
       print-table))

(defn write-object [fnam obj]
  (spit fnam (prn-str obj)))

(defn read-object [fnam]
  (edn/read-string (slurp fnam)))

(defn start []
  (do
    (enforce-spec true)
    (mount/start)))

(defn stop []
  (mount/stop))

(defn reset []
  (stop)
  (refresh :after 'user/start))
