(ns advent-of-code-2023.day-5
  (:require [advent-of-code-2023.utils :refer [def- product re-seq-matches sum]]
            [clojure.java.io :as io]
            [clojure.set :refer [intersection]]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-5-input.txt"))))

(defn- parse-seeds [s]
  (->> (re-seq #"\d+" s)
       (map parse-long)
       (into [])))

(defn- parse-map [m]
  (let [[name & entries] (string/split-lines m)
        parse-entry   (fn [s] (let [[dst src len] (map parse-long (re-seq #"\d+" s))]
                               {:src src :dst dst :len len}))]
    {:name    (re-find #"\S+" name)
     :entries (into [] (sort-by (juxt :src :dst) (map parse-entry entries)))}))

(defn- parse-maps [ms]
  (into [] (map parse-map ms)))

(defn- parse-input [input]
  (let [[seeds & maps] (string/split input #"\n\n")]
    {:seeds (parse-seeds seeds)
     :maps  (into [] (parse-maps maps))}))

(defn- new-location [{:keys [entries]} loc]
  (let [loc-in-range? (fn [{:keys [src len]}] (and (<= src loc) (< loc  (+ src len))))]
    (if-let [{:keys [src dst len]} (first (filter loc-in-range? entries))]
      (+ dst (- loc src))
      loc)))

(defn- final-location [maps seed]
  (reduce (fn [loc m] (new-location m loc)) seed maps))

(defn solution-part-one [input]
  (let [{:keys [seeds maps]} (parse-input input)]
    (->> seeds
         (map (partial final-location maps))
         (apply min))))



