(ns advent-of-code-2023.day-1
  (:require [advent-of-code-2023.utils :refer [sum def- re-seq-matches]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-1-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (into [])))

(defn- indices-of [s words]
  (->> words
       (mapcat (fn [w] (re-seq-matches (re-pattern w) s)))
       (sort-by :start)))

(def- numeral-mapper
  (into {} (for [x (range 1 10)] [(str x) (str x)])))

(defn extract-digits [mapper s]
  (->> (indices-of s (keys mapper))
       (map :group)
       (map mapper)))

(defn- calibration-value [coll]
  (parse-long (str (first coll) (last coll))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map (partial extract-digits numeral-mapper))
       (map calibration-value)
       (sum)))

;; Part two

(def numeral-and-word-mapper
  (let [words ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]]
    (merge numeral-mapper
           (into {} (map-indexed (fn [idx x] [x (str (inc idx))]) words)))))

(defn solution-part-two [input]
  (->> (parse-input input)
       (map (partial extract-digits numeral-and-word-mapper))
       (map calibration-value)
       (sum)))
