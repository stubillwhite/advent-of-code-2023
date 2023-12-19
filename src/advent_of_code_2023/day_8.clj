(ns advent-of-code-2023.day-8
  (:require
   [advent-of-code-2023.utils :refer [sum map-vals def- take-until]]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-8-input.txt"))))

(defn- parse-input [input]
  (let [[instr nodes] (string/split input #"\n\n")]
    {:instr (into [] instr)
     :nodes (into {} (for [s (string/split-lines nodes)]
                       (let [[_ a b c] (re-find #"(\S+) = \((\S+), (\S+)\)" s)]
                         [a {\L b \R c}])))}))

(defn solution-part-one [input]
    (let [state  (assoc (parse-input input) :curr "AAA")
          stream (iterate (fn [{:keys [instr nodes curr] :as state}]
                          (let [[dir & dirs] instr]
                            (assoc state
                                   :instr (lazy-cat dirs [dir])
                                   :curr  (get-in nodes [curr dir]))))
                          state)]
    (->> stream
         (take-until (fn [{:keys [curr]}] (= curr "ZZZ")))
         (count)
         (dec))))


