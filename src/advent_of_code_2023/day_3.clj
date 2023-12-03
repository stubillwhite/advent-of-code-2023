(ns advent-of-code-2023.day-3
  (:require [advent-of-code-2023.utils :refer [sum def- re-seq-matches]]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :refer [intersection]]))

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

(defn- neighbour-locations [[x y]]
  (let [deltas (for [dx (range -1 2)
                     dy (range -1 2)
                     :when (not (= [dx dy] [0 0]))]
                 [dx dy])]
    (for [[dx dy] deltas] [(+ x dx) (+ y dy)])))

(defn- numbers-neighbouring-symbols [numbers symbols]
  (let [coords-of-chars (fn [[x y] s] (for [dx (range (count s))] [(+ x dx) y]))]
    (filter-map
     (fn [[coords number]]
       (not-empty
        (let [num-neighbour-coords (set (mapcat neighbour-locations (coords-of-chars coords number)))
              sym-coords           (set (keys symbols))]
          (intersection num-neighbour-coords sym-coords))))
     numbers)))

(defn solution-part-one [input]
  (let [{:keys [numbers symbols]} (parse-input input)]
    (->> (numbers-neighbouring-symbols numbers symbols)
         (vals)
         (map parse-long)
         (sum))))



