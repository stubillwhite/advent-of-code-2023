(ns advent-of-code-2023.day-5
  (:require
   [advent-of-code-2023.utils :refer [def- product re-seq-matches sum]]
   [clojure.java.io :as io]
   [clojure.set :refer [intersection]]
   [clojure.spec.alpha :as spec]
   [clojure.string :as string]
   [expound.alpha :as expound]))

;; Data model

(spec/def ::start int?)
(spec/def ::end int?)
(spec/def ::offset int?)

(spec/def ::closed-range
  (spec/keys :req-un [::start ::end]))

(spec/def ::mapping
  (spec/keys :req-un [::start ::end ::offset]))

(spec/def ::category
  (spec/coll-of ::mapping))

(spec/def ::categories
  (spec/coll-of ::category))

(spec/def ::seeds
  (spec/coll-of int?))

(spec/def ::almanac
  (spec/keys :req-un [::seeds ::categories]))

;; Parsing

(def problem-input
  (string/trim (slurp (io/resource "day-5-input.txt"))))

(defn- parse-seeds [s]
  (->> (re-seq #"\d+" s)
       (map parse-long)
       (into [])))

(defn- parse-mappings [m]
  (let [[name & mappings] (string/split-lines m)
        parse-mapping     (fn [s] (let [[dst src len] (map parse-long (re-seq #"\d+" s))]
                                   {:start  src
                                    :end    (dec (+ src len))
                                    :offset (- dst src)}))]
    (into [] (sort-by :start (map parse-mapping mappings)))))

(defn- parse-categories [ms]
  (into [] (map parse-mappings ms)))

(defn fill-gaps
  [mappings]
  (let [contiguous-mappings (reduce
                             (fn [{:keys [end-of-last processed]} m]
                               {:end-of-last (:end m)
                                :processed   (concat processed
                                                     (when (not= (inc end-of-last) (:start m))
                                                       [{:start (inc end-of-last) :end (dec (:start m)) :offset 0}])
                                                     [m])})
                             {:end-of-last -1 :processed []}
                             mappings)
        end-of-all       (:end (last mappings))
        end-padding      (when (not= Long/MAX_VALUE end-of-all)
                           [{:start (inc end-of-all) :end Long/MAX_VALUE :offset 0}])]
    (concat (:processed contiguous-mappings) end-padding)))

(defn parse-input
  [input]
  {:post [(spec/assert ::almanac %)]}
  (let [[seeds & categories] (string/split input #"\n\n")]
    {:seeds      (parse-seeds seeds)
     :categories (->> categories
                      (parse-categories)
                      (map fill-gaps)
                      (into []))}))

(defn- translate-point [mappings x]
  (let [in-range? (fn [{:keys [start end]}] (and (<= start x end)))]
    (if-let [{:keys [start end offset]} (first (filter in-range? mappings))]
      (+ x offset)
      x)))

(defn- final-location [categories seed]
  (reduce (fn [loc m] (translate-point m loc)) seed categories))

(defn solution-part-one [input]
  (let [{:keys [seeds categories]} (parse-input input)]
    (->> seeds
         (map (partial final-location categories))
         (apply min))))

;; Part two

;; This really hurts my head, need to write it out. Turn the input into intervals and step through the stages, splitting
;; the intervals if required at each stage, then take the final minimum value. See tests for TDD at each stage.

(defn- overlap? [r1 r2]
  (if (< (:start r2) (:start r1))
    (overlap? r2 r1)
    (<= (:start r1) (:start r2) (:end r1))))

(defn to-seed-ranges [seeds]
  (->> seeds
       (partition 2 2)
       (map (fn [[start len]] {:start start :end (dec (+ start len))}))
       (sort-by :start)))

(defn- translate-range [mappings range]
  (let [iter (fn [src translated mappings]
               (let [[m & ms] mappings]
                 (cond
                   (nil? m)         translated
                   (overlap? src m) (let [new-start (translate-point mappings (:start src))
                                          new-end   (translate-point mappings (Math/min (:end src) (:end m)))
                                          dst       {:start new-start :end new-end}
                                          new-src   (if (> (:end src) (:end m)) (assoc src :start (inc (:end m))) src)]
                                      (recur new-src (concat translated [dst]) ms))
                   :else
                   (recur src translated ms))))]

    (iter range [] mappings)))

(defn translate-ranges [{:keys [seeds categories]}]
  (let [seed-ranges    (to-seed-ranges seeds)
        apply-mappings (fn [ranges mappings]
                         (->> ranges
                              (mapcat (partial translate-range mappings))
                              (sort-by :start)))]

    (reduce (fn [ranges mappings] (apply-mappings ranges mappings))
            seed-ranges
            categories)))

(defn solution-part-two [input]
  (->> (parse-input input)
       (translate-ranges)
       (map :start)
       (apply min)))
