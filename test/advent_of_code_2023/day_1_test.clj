(ns advent-of-code-2023.day-1-test
  (:require [advent-of-code-2023.day-1 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input-one
  (string/join "\n" ["1abc2"
                     "pqr3stu8vwx"
                     "a1b2c3d4e5f"
                     "treb7uchet"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 142 (solution-part-one example-input-one))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 53651 (solution-part-one problem-input))))

(def- example-input-two
  (string/join "\n" ["two1nine"
                     "eightwothree"
                     "abcone2threexyz"
                     "xtwone3four"
                     "4nineeightseven2"
                     "zoneight234"
                     "7pqrstsixteen"]))

(deftest solution-part-two-given-example-input-then-example-result
  (is (= 281 (solution-part-two example-input-two))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 53894 (solution-part-two problem-input))))

