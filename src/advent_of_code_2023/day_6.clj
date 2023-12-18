(ns advent-of-code-2023.day-6
  (:require
   [advent-of-code-2023.utils :refer [def- product]]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-6-input.txt"))))

(defn- parse-input [input]
  (let [[times records] (string/split-lines input)
        extract-numbers (fn [s] (->> s (re-seq #"\d+") (map parse-long)))]
    (->> (interleave (extract-numbers times) (extract-numbers records))
         (partition 2)
         (map (fn [[time record]] {:time time :record record})))))

(defn- distance-travelled [{:keys [time record]} button-time]
  (let [remaining-time (- time button-time)]
    (* remaining-time button-time)))

(defn- error-margin [{:keys [time record] :as race} button-time]
  (- (distance-travelled race button-time) record))

(defn- button-timings [{:keys [time]}]
  (range 0 (inc time)))

(defn- number-of-ways-to-beat-record [race]
  (->> (map (partial error-margin race) (button-timings race))
       (filter pos?)
       (count)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map number-of-ways-to-beat-record)
       (product)))

