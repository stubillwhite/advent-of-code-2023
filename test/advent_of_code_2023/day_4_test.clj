(ns advent-of-code-2023.day-4-test
  (:require [advent-of-code-2023.day-4 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n" ["Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
                     "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
                     "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
                     "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
                     "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
                     "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 13 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 24706 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 30 (solution-part-two example-input))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 13114317 (solution-part-two problem-input))))
