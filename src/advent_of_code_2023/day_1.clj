(ns advent-of-code-2023.day-1
  (:require [advent-of-code-2023.utils :refer [sum]]
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

;; Part two

(defn- first-word [s words]
  (->> (for [w words] [w (.indexOf s w)])
       (filter (fn [[wrd idx]] (>= idx 0)))
       (sort-by second)
       (map first)
       (first)))

(defn- replace-words-with-digits [s]
  (let [words ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]
        wrd   (first-word s words)]
    (if (nil? wrd)
      s
      (recur (string/replace-first s wrd (str (inc (.indexOf words wrd))
                                              (apply str (rest wrd))) )))))

(defn solution-part-two [input]
  (->> (parse-input input)
       (map replace-words-with-digits)
       (map extract-digits)
       (map calibration-value)
       (sum)))
