(ns regexx.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [regexx.core :as sut]))

(deftest compile-primitives-test
  (testing "Primitives compilation"
    (is (= "\\d" (str (sut/regex :digit))))
    (is (= "\\w" (str (sut/regex :word))))
    (is (= "\\s" (str (sut/regex :whitespace))))
    (is (= "." (str (sut/regex :any))))))

(deftest compile-combinators-test
  (testing "Combinators compilation"
    (is (= "\\d\\w" (str (sut/regex [:seq :digit :word]))))
    (is (= "(?:\\d|\\w)" (str (sut/regex [:or :digit :word]))))
    (is (= "(?:\\d)?" (str (sut/regex [:optional :digit]))))
    (is (= "(?:\\d)+" (str (sut/regex [:one-or-more :digit]))))
    (is (= "(?:\\d)*" (str (sut/regex [:zero-or-more :digit]))))
    (is (= "(?:\\d){3,}" (str (sut/regex [:repeat 3 nil :digit]))))
    (is (= "(\\d)" (str (sut/regex [:group :digit]))))))

(deftest validation-test
  (testing "Invalid AST"
    (is (thrown? clojure.lang.ExceptionInfo (sut/regex :invalid-keyword)))
    (is (thrown? clojure.lang.ExceptionInfo (sut/regex [:invalid-combinator])))))

(deftest matching-test
  (testing "Matching against strings"
    (let [pattern (sut/regex [:seq :digit :word])]
      (is (re-matches pattern "1a"))
      (is (not (re-matches pattern "aa"))))))
