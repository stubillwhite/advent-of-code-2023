(ns advent-of-code-2023.day-6-test
  (:require [advent-of-code-2023.day-6 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n" ["Time:      7  15   30"
                     "Distance:  9  40  200"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 288 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 275724,tn (solution-part-one problem-input))))
