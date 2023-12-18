(ns advent-of-code-2023.day-7
  (:require
   [advent-of-code-2023.utils :refer [sum]]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-7-input.txt"))))

(defn- parse-hand [s]
  (let [[cards bid] (string/split s #" ")]
    {:cards (vec cards)
     :bid   (parse-long bid)}))

(defn- hand-type [{:keys [cards]}]
  (let [counts    (->> (group-by identity cards) (vals) (map count) (sort) (reverse))
        [a b & _] counts]
    (cond
      (= 5 a)               :five-of-a-kind
      (= 4 a)               :four-of-a-kind
      (and (= 3 a) (= 2 b)) :full-house
      (= 3 a)               :three-of-a-kind
      (and (= 2 a) (= 2 b)) :two-pair
      (= 2 a)               :one-pair
      :else                 :high-card)))

(defn- hand-rank [hand]
  (let [ranks [:high-card :one-pair :two-pair :three-of-a-kind :full-house :four-of-a-kind :five-of-a-kind]]
    (.indexOf ranks (hand-type hand))))

(defn- card-ranks [{:keys [cards]}]
  (let [ranks (vec "23456789TJQKA")]
    (map (fn [c] (.indexOf ranks c)) cards)))

(defn- hand-strength [hand]
  (into [] (concat [(hand-rank hand)] (card-ranks hand))))

(defn solution-part-one [input]
  (let [hands  (->> (string/split-lines input) (map parse-hand))]
    (->> hands
         (sort-by hand-strength)
         (map-indexed (fn [rank {:keys [bid]}] (* (inc rank) bid)))
         (sum))))
