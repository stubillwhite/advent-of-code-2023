(ns advent-of-code-2023.day-1-test
  (:require [advent-of-code-2023.day-1 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n" ["1abc2"
                     "pqr3stu8vwx"
                     "a1b2c3d4e5f"
                     "treb7uchet"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 142 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 53651 (solution-part-one problem-input))))
