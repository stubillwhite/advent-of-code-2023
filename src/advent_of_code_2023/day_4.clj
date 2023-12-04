(ns advent-of-code-2023.day-4
  (:require [advent-of-code-2023.utils :refer [def- product re-seq-matches sum]]
            [clojure.java.io :as io]
            [clojure.set :refer [intersection]]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-4-input.txt"))))

(defn- parse-card [s]
  (let [[_ n card-numbers winning-numbers] (re-matches #".* (\d+): (.*) \| (.*)" s)
        parse-numbers                      (fn [s] (->> (re-seq #"\d+" s) (map parse-long) (vec)))]
    {:n               (parse-long n)
     :card-numbers    (parse-numbers card-numbers)
     :winning-numbers (parse-numbers winning-numbers)
     :copies          1}))

(defn- parse-input [input]
  (->> (string/split-lines input)
       (map parse-card)))

(defn- pow [x y]
  (long (Math/pow x y)))

(defn- matches [{:keys [card-numbers winning-numbers]}]
  (count (intersection (set card-numbers) (set winning-numbers))))

(defn- score-card-naively [card]
  (pow 2 (dec (matches card))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map score-card-naively)
       (sum)))

;; Part two

(defn- copy-next-n-cards [cards n times]
  (lazy-cat (map (fn [c] (update c :copies (partial + times))) (take n cards))
            (drop n cards)))

(defn- score-cards [cs]
  (loop [cards cs
         n     0]
    (if (empty? cards)
      n
      (let [[card & remaining] cards]
        (recur (copy-next-n-cards remaining (matches card) (:copies card))
               (+ n (:copies card)))))))

(defn solution-part-two [input]
  (score-cards (parse-input input)))
