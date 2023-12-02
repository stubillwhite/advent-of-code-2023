(ns advent-of-code-2023.day-2
  (:require [advent-of-code-2023.utils :refer [sum def-]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-2-input.txt"))))

(def- parse-game
  (comp parse-long second (partial re-find #"Game (\d+)")))

(defn- parse-handfull [s]
  (let [matches (re-seq #"(\d+) (red|blue|green)(, )?+" s)]
    (into {} (for [[_ n color] matches] [(keyword color) (parse-long n)]))))

(defn- parse-line [s]
  (let [[game-str handfulls-str] (string/split s #": ")]
    {:game      (parse-game game-str)
     :handfulls (->> (string/split handfulls-str #"; ")
                     (map parse-handfull)
                     (into []))}))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-line)))

(defn- meets-bag-limits? [handfull]
  (let [limits {:red 12 :green 13 :blue 14}]
    (every? (fn [k] (<= (handfull k) (limits k))) (keys handfull))))

(defn- possible? [f {:keys [handfulls]}]
  (let [_ (print (map f handfulls))]
    (every? f handfulls)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (filter (partial possible? meets-bag-limits?))
       (map :game)
       (sum)))

