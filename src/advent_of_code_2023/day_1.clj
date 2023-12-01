(ns advent-of-code-2023.day-1
  (:require [advent-of-code-2023.utils :refer [parse-long sum]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-1-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (into [])))

(defn- extract-digits [s]
  (re-seq #"\d" s))

(defn- calibration-value [coll]
  (parse-long (apply str (first coll) (last coll))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map extract-digits)
       (map calibration-value)
       (sum)))
