(ns advent-of-code-2023.day-5-test
  (:require [advent-of-code-2023.day-5 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n" ["seeds: 79 14 55 13"
                     ""
                     "seed-to-soil map:"
                     "50 98 2"
                     "52 50 48"
                     ""
                     "soil-to-fertilizer map:"
                     "0 15 37"
                     "37 52 2"
                     "39 0 15"
                     ""
                     "fertilizer-to-water map:"
                     "49 53 8"
                     "0 11 42"
                     "42 0 7"
                     "57 7 4"
                     ""
                     "water-to-light map:"
                     "88 18 7"
                     "18 25 70"
                     ""
                     "light-to-temperature map:"
                     "45 77 23"
                     "81 45 19"
                     "68 64 13"
                     ""
                     "temperature-to-humidity map:"
                     "0 69 1"
                     "1 0 69"
                     ""
                     "humidity-to-location map:"
                     "60 56 37"
                     "56 93 4"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 35 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 174137457 (solution-part-one problem-input))))
