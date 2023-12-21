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
                         {:curr       k
                          :path       []
                          :loop-start nil
                          :exit       nil
                          :loop-end   nil}))))

(defn- next-ghost-state [nodes dir {:keys [curr path loop-start exit loop-end] :as state}]
  (if (nil? loop-end)
    (let [new-curr (get-in nodes [curr dir])
          idx      (.indexOf path [new-curr dir])
          new-path (conj path [curr dir])]
      (let [next-state {:curr       new-curr
                        :path       new-path
                        :loop-start (if (> idx 0) idx loop-start)
                        :exit       (if (string/ends-with? new-curr "Z") (count new-path) exit)
                        :loop-end   (if (> idx 0) (count new-path) exit)}]
        (do (println next-state)
            next-state)))
    state))


(defn- next-state [{:keys [instr nodes curr ghosts] :as state}]
  (let [[dir & dirs] instr]
    (do
      (println "---------------------------------------------------------------------")
      (assoc state
             :instr  (lazy-cat dirs [dir])
             :ghosts (map (partial next-ghost-state nodes dir) ghosts)
             :curr   (get-in nodes [curr dir])))))

(let [input example-input-three]
  (let [state       (create-state (parse-input input))
        stream      (iterate next-state state)
        final-state (first (drop-while (fn [{:keys [ghosts]}] (some (comp nil? :loop-start) ghosts)) stream))]
    (->> final-state
         (:ghosts)
         (clojure.pprint/pprint))
    ))

;; TODO: End of loops may not be ZZZ

;; (let [input example-input]
;;   (let [state       (create-state (parse-input input))
;;         stream      (iterate next-state state)
;;         final-state (first (drop-while (fn [{:keys [ghosts]}] (some (comp nil? :len) ghosts)) stream))]
;;     (->> final-state
;;          (:ghosts)
;;          (map :len)
;;          (reduce lcm)
;;          (println))))

;; clicks-to-exit
;; exit, loop-length, loop-length, loop-length
;; 2, 4, 6
;; 3,    6

(defn- coincident-points [xs ys]
  (let [x (first xs)
        y (first ys)]
    (cond
      (= x y) (cons x (lazy-seq (coincident-points (rest xs) (rest ys))))
      (< x y) (coincident-points (rest xs) ys)
      (> x y) (coincident-points xs (rest ys)))))

(defn exit-points [{:keys [loop-start exit loop-end]}]
  (let [start  exit
        period (- loop-end loop-start)]
    (iterate (fn [x] (+ x period)) start)))

(let [a-exit 2
      a-len  2
      b-exit 3
      b-len  3
      stream (fn [start period] (iterate (fn [x] (+ x period)) start))]
  (take 5 (drop 1000 (coincident-points (stream a-exit a-len)
                             (stream b-exit b-len)))))


(comment let [input example-input-three]
  (let [state       (create-state (parse-input input))
        stream      (iterate next-state state)
        final-state (first (drop-while (fn [{:keys [ghosts]}] (some (comp nil? :loop-start) ghosts)) stream))]
    (->> final-state
         (:ghosts)
         (clojure.pprint/pprint)
         
         )
    ))



(let [input example-input-three]
  (let [state       (create-state (parse-input input))
        stream      (iterate next-state state)
        final-state (last (take 5 stream))]
    (->> final-state
         (clojure.pprint/pprint))))
