(ns advent-of-code-2023.day-7-test
  (:require [advent-of-code-2023.day-7 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n" ["32T3K 765"
                     "T55J5 684"
                     "KK677 28"
                     "KTJJT 220"
                     "QQQJA 483"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 6440 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 250898830 (solution-part-one problem-input))))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 5905 (solution-part-two example-input))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 252127335 (solution-part-two problem-input))))


