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
     :winning-numbers (parse-numbers winning-numbers)}))

(defn- parse-input [input]
  (->> (string/split-lines input)
       (map parse-card)))

(defn- pow [x y]
  (long (Math/pow x y)))

(defn- score-card [{:keys [n card-numbers winning-numbers]}]
  (let [matches (intersection (set card-numbers) (set winning-numbers))]
    (pow 2 (dec (count matches)))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map score-card)
       (sum)))
