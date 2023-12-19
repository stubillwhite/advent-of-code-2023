(ns advent-of-code-2023.day-7
  (:require
   [advent-of-code-2023.utils :refer [sum map-vals def-]]
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

(defn- best-hand [{:keys [cards] :as hand}]
  (let [non-joker-cards (filter (fn [c] (not= c \J)) cards)
        by-type         (->> non-joker-cards (group-by identity) (map-vals count))
        most-frequent   (if (empty? non-joker-cards) "K" (->> (apply max-key fnext by-type) (first) (str)))]
    (assoc hand :cards
           (-> (apply str cards)
               (string/replace #"J" most-frequent)
               (vec)))))

(defn- hand-rank [hand jokers?]
  (let [ranks [:high-card :one-pair :two-pair :three-of-a-kind :full-house :four-of-a-kind :five-of-a-kind]]
    (.indexOf ranks
              (if jokers? (hand-type (best-hand hand)) (hand-type hand)))))

(defn- card-ranks [{:keys [cards]} jokers?]
  (let [ranks (if jokers? (vec "J23456789TQKA") (vec "23456789TJQKA"))]
    (map (fn [c] (.indexOf ranks c)) cards)))

(defn- hand-strength [hand & {:keys [jokers?] :or {jokers? false}}]
  (into [] (concat [(hand-rank hand jokers?)] (card-ranks hand jokers?))))

(defn solution-part-one [input]
  (let [hands  (->> (string/split-lines input) (map parse-hand))]
    (->> hands
         (sort-by hand-strength)
         (map-indexed (fn [rank {:keys [bid]}] (* (inc rank) bid)))
         (sum))))

;; Part two

(defn solution-part-two [input]
  (let [hands  (->> (string/split-lines input) (map parse-hand))]
    (->> hands
         (sort-by (fn [h] (hand-strength h :jokers? true)))
         (map-indexed (fn [rank {:keys [bid]}] (* (inc rank) bid)))
         (sum))))
