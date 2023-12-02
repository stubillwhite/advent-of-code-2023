(ns advent-of-code-2023.day-2-test
  (:require [advent-of-code-2023.day-2 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def example-input
  (string/join "\n" ["Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
                     "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
                     "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
                     "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
                     "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 8 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 2716 (solution-part-one problem-input))))

