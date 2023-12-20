(ns advent-of-code-2023.day-8
  (:require
   [advent-of-code-2023.utils :refer [sum map-vals def- take-until]]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-8-input.txt"))))

(defn- parse-nodes [lines]
  (into {} (for [s (string/split-lines lines)]
                       (let [[_ a b c] (re-find #"(\S+) = \((\S+), (\S+)\)" s)]
                         [a {\L b \R c}]))))

(defn- parse-input [input]
  (let [[instr-str nodes-str] (string/split input #"\n\n")
        instr                 (into [] instr-str)
        nodes                 (parse-nodes nodes-str)]
    {:instr instr
     :nodes nodes}))

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

;; Part two

(def- example-input-three
  (string/join "\n" ["LR"
                     ""
                     "11A = (11B, XXX)"
                     "11B = (XXX, 11Z)"
                     "11Z = (11B, XXX)"
                     "22A = (22B, XXX)"
                     "22B = (22C, 22C)"
                     "22C = (22Z, 22Z)"
                     "22Z = (22B, 22B)"
                     "XXX = (XXX, XXX)"]))


(defn- create-state [{:keys [nodes] :as input}]
  (assoc input
         :ghosts (into []
                       (for [[k v] nodes :when (string/ends-with? k "A")]
                         {:curr   k
                          :path   []
                          :len    nil
                          :offset nil}))))

(defn- next-ghost-state [nodes dir {:keys [curr path len] :as state}]
  (if (nil? len)
    (let [new-curr (get-in nodes [curr dir])
          idx      (.indexOf path new-curr)]
      (let [next-state {:curr   new-curr
                        :path   (conj path curr)
                        :len    (if (> idx 0) (- (inc (count path)) idx) len)
                        :offset (if (> idx 0) idx)}]
        (do (comment println next-state)
            next-state)))
    state))

(defn- next-state [{:keys [instr nodes curr ghosts] :as state}]
  (let [[dir & dirs] instr]
    (do
      (comment println "---------------------------------------------------------------------")
      (assoc state
             :instr  (lazy-cat dirs [dir])
             :ghosts (map (partial next-ghost-state nodes dir) ghosts)
             :curr   (get-in nodes [curr dir])))))

(defn- gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn- lcm [a b]
  (/ (* a b) (gcd a b)))

(let [input problem-input]
  (let [state       (create-state (parse-input input))
        stream      (iterate next-state state)
        final-state (first (drop-while (fn [{:keys [ghosts]}] (some (comp nil? :len) ghosts)) stream))]
    (->> final-state
         (:ghosts)
         (map :len)
         (reduce lcm)
         (println))))

(let [input problem-input]
  (let [state       (create-state (parse-input input))
        stream      (iterate next-state state)
        final-state (first (drop-while (fn [{:keys [ghosts]}] (some (comp nil? :len) ghosts)) stream))]
    (->> final-state
         (:ghosts)
         (clojure.pprint/pprint))
    ))

;; TODO: End of loops may not be ZZZ
