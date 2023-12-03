(ns advent-of-code-2023.day-3-test
  (:require [advent-of-code-2023.day-3 :refer :all]
            [advent-of-code-2023.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
  (string/join "\n" ["467..114.."
                     "...*......"
                     "..35..633."
                     "......#..."
                     "617*......"
                     ".....+.58."
                     "..592....."
                     "......755."
                     "...$.*...."
                     ".664.598.."
                     ]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 4361 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-correct-result
  (is (= 519444 (solution-part-one problem-input))))

