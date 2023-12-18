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

;; Solving for button-time x
;; d = (t - x) * x
;; 0 = -x^2 + tx -d
;;
;; (-b ± sqrc(b^2 - 4ac)) / 2a
;; a = -1, b = race-time, c = -dist

(defn- quadratic-roots [a b c]
  (let [d  (Math/sqrt (- (* b b) (* 4 a c)))
        r1 (/ (+ (- b) d) (* 2 a))
        r2 (/ (- (- b) d) (* 2 a))]
    (sort [r1 r2])))

(defn- floor [x]
  (long (Math/floor x)))

(defn- ceil [x]
  (long (Math/ceil x)))

(defn- limits-of-button-time-to-beat-record [{:keys [time record]}]
  (let [[r1 r2] (quadratic-roots -1 time (- record))]
    [(inc (floor r1)) (dec (ceil r2))]))

(defn- count-of-button-times [[a b]]
  (inc (- b a)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map limits-of-button-time-to-beat-record)
       (map count-of-button-times)
       (product)))

;; Part two

(defn- parse-input-without-spaces [input]
  (let [[times records] (string/split-lines input)
        extract-numbers (fn [s] (->> (string/replace s " " "") (re-seq #"\d+") (map parse-long) (first)))]
  {:time   (extract-numbers times)
   :record (extract-numbers records)}))

(defn solution-part-two [input]
  (let [race (parse-input-without-spaces input)]
    (count-of-button-times (limits-of-button-time-to-beat-record race))))
