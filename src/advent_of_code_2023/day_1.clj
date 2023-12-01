(ns advent-of-code-2023.day-1
  (:require [advent-of-code-2023.utils :refer [sum def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-1-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (into [])))

(defn- re-seq-matches [re s]
  (let [m (re-matcher re s)]
    ((fn step []
       (when (. m (find))
         (cons {:start (. m start) :end (. m end) :group (. m group)}
               (lazy-seq (step))))))))

(defn- indices-of [s words]
  (->> words
       (mapcat (fn [w] (re-seq-matches (re-pattern w) s)))
       (sort-by :start)))

(def- num-to-digit {"1" 1
                    "2" 2
                    "3" 3
                    "4" 4
                    "5" 5
                    "6" 6
                    "7" 7
                    "8" 8
                    "9" 9})

(defn extract-digits [decoder s]
  (->> (indices-of s (keys decoder))
       (map :group)
       (map decoder)))

(defn- calibration-value [coll]
  (parse-long (str (first coll) (last coll))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map (partial extract-digits num-to-digit))
       (map calibration-value)
       (sum)))

;; Part two

(def word-to-digit {"one"   1
                    "two"   2
                    "three" 3
                    "four"  4
                    "five"  5
                    "six"   6
                    "seven" 7
                    "eight" 8
                    "nine"  9
                    "1"     1
                    "2"     2
                    "3"     3
                    "4"     4
                    "5"     5
                    "6"     6
                    "7"     7
                    "8"     8
                    "9"     9})

(defn solution-part-two [input]
  (->> (parse-input input)
       (map (partial extract-digits word-to-digit))
       (map calibration-value)
       (sum)))
