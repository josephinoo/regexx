(ns regexx.operators-test
  (:require [clojure.test :refer [deftest is testing]]
            [regexx.core :as core]
            [regexx.operators :as op]))

(deftest quantifiers-test
  (testing "at-least"
    (is (= "(?:\\d){3,}" (str (core/regex (op/at-least 3 :digit))))))
  (testing "at-most"
    (is (= "(?:\\d){0,5}" (str (core/regex (op/at-most 5 :digit)))))))

(deftest character-classes-test
  (testing "one-of"
    (is (= "[abc]" (str (core/regex (op/one-of "a" "b" "c"))))))
  (testing "none-of"
    (is (= "[^abc]" (str (core/regex (op/none-of "a" "b" "c")))))))

(deftest back-ref-test
  (testing "back-ref"
    (is (= "(\\d)\\1" (str (core/regex [:seq [:group :digit] (op/back-ref 1)]))))
    (is (= "(?<foo>\\d)\\k<foo>" (str (core/regex [:seq [:named-group :foo :digit] (op/back-ref :foo)]))))))

(deftest atomic-group-test
  (testing "atomic-group"
    (is (= "(?>\\d)" (str (core/regex (op/atomic-group :digit)))))))
