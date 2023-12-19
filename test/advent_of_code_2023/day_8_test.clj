(ns advent-of-code-2023.day-8-test
  (:require [advent-of-code-2023.day-8 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input-one
  (string/join "\n" ["RL"
                     ""
                     "AAA = (BBB, CCC)"
                     "BBB = (DDD, EEE)"
                     "CCC = (ZZZ, GGG)"
                     "DDD = (DDD, DDD)"
                     "EEE = (EEE, EEE)"
                     "GGG = (GGG, GGG)"
                     "ZZZ = (ZZZ, ZZZ)"]))

(def- example-input-two
  (string/join "\n" ["LLR"
                     ""
                     "AAA = (BBB, BBB)"
                     "BBB = (AAA, ZZZ)"
                     "ZZZ = (ZZZ, ZZZ)"]))

(deftest solution-part-one-given-example-input-one-then-example-result
  (is (= 2 (solution-part-one example-input-one))))

(deftest solution-part-one-given-example-input-two-then-example-result
  (is (= 6 (solution-part-one example-input-two))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 20659 (solution-part-one problem-input))))




