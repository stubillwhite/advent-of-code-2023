(ns advent-of-code-2023.day-2
  (:require [advent-of-code-2023.utils :refer [sum def- product]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-2-input.txt"))))

(def- parse-game
  (comp parse-long second (partial re-find #"Game (\d+)")))

(defn- parse-handful [s]
  (let [matches (re-seq #"(\d+) (red|blue|green)(, )?+" s)]
    (into {} (for [[_ n color] matches] [(keyword color) (parse-long n)]))))

(defn- parse-line [s]
  (let [[game-str handfuls-str] (string/split s #": ")]
    {:game     (parse-game game-str)
     :handfuls (->> (string/split handfuls-str #"; ")
                     (map parse-handful)
                     (into []))}))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-line)))

(defn- meets-bag-limits? [handful]
  (let [limits {:red 12 :green 13 :blue 14}]
    (every? (fn [k] (<= (handful k) (limits k))) (keys handful))))

(defn- every-handful? [f {:keys [handfuls]}]
  (every? f handfuls))

(defn solution-part-one [input]
  (->> (parse-input input)
       (filter (partial every-handful? meets-bag-limits?))
       (map :game)
       (sum)))

;; Part two

(defn- minimum-cube-numbers [{:keys [handfuls] :as game}]
  (assoc game :min-numbers (apply merge-with max handfuls)))

(defn- powerset [{:keys [min-numbers]}]
  (product (vals min-numbers)))

(defn solution-part-two [input]
  (->> (parse-input input)
       (map minimum-cube-numbers)
       (map powerset)
       (sum)))
