(ns advent-of-code-2023.day-3
  (:require [advent-of-code-2023.utils :refer [def- product re-seq-matches sum]]
            [clojure.java.io :as io]
            [clojure.set :refer [intersection]]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-3-input.txt"))))

(defn- extract-words-matching [re input]
  (let [lines (string/split-lines input)]
    (into {}
          (apply concat
                 (map-indexed (fn [y line]
                                (map
                                 (fn [{:keys [start end group]}]
                                   [[start y] group])
                                 (re-seq-matches re line)))
                              lines)))))

(defn- parse-input [input]
  {:numbers (extract-words-matching #"\d+" input)
   :symbols (extract-words-matching #"[^\d.]" input)})

(defn- filter-map [f m]
  (into {} (filter f) m))

(defn- adjacent-locations [[x y]]
  (let [deltas (for [dx (range -1 2)
                     dy (range -1 2)
                     :when (not (= [dx dy] [0 0]))]
                 [dx dy])]
    (for [[dx dy] deltas] [(+ x dx) (+ y dy)])))

(defn- adjacent-symbol [symbols number]
  (let [[[x y] word]    number
        coords-of-chars (for [dx (range (count word))] [(+ x dx) y])
        adjacent-coords (set (mapcat adjacent-locations coords-of-chars))]
    (first (filter-map (fn [[coords s]] (contains? adjacent-coords coords)) symbols))))

(defn solution-part-one [input]
  (let [{:keys [numbers symbols]} (parse-input input)
        adjacent-to-symbol?       (fn [[[coords sym] nums]] (and (not= sym nil) (not-empty nums)))]
    (->> (group-by (partial adjacent-symbol symbols) (seq numbers))
         (filter-map adjacent-to-symbol?)
         (vals)
         (mapcat (fn [nums] (map parse-long (vals nums))))
         (sum))))

;; Part two

(defn solution-part-two [input]
  (let [{:keys [numbers symbols]} (parse-input input)
        gear?                     (fn [[[coords sym] nums]] (and (= sym "*") (= (count nums) 2)))]
    (->> (group-by (partial adjacent-symbol symbols) (seq numbers))
         (filter-map gear?)
         (vals)
         (map (fn [nums] (product (map parse-long (vals nums)))))
         (sum))))
