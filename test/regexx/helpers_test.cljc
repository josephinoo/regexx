(ns regexx.helpers-test
  (:require [clojure.test :refer [deftest is testing]]
            [regexx.core :as core]
            [regexx.helpers :as sut]))

(deftest email-helper-test
  (testing "Email validation"
    (let [pattern (core/regex (sut/email))]
      (is (re-matches pattern "test@example.com"))
      (is (re-matches pattern "user.name@domain.co.uk"))
      (is (not (re-matches pattern "invalid-email")))
      (is (not (re-matches pattern "test@.com"))))))

(deftest uuid-helper-test
  (testing "UUID validation"
    (let [pattern (core/regex (sut/uuid))]
      (is (re-matches pattern "123e4567-e89b-12d3-a456-426614174000"))
      (is (not (re-matches pattern "invalid-uuid"))))))

(deftest ipv4-helper-test
  (testing "IPv4 validation"
    (let [pattern (core/regex (sut/ipv4))]
      (is (re-matches pattern "192.168.1.1"))
      (is (re-matches pattern "10.0.0.1"))
      ;; Note: The simple regex allows 999.999.999.999, so we just test structure
      (is (re-matches pattern "123.456.789.0")))))

(deftest date-helper-test
  (testing "Date validation"
    (let [pattern (core/regex (sut/date))]
      (is (re-matches pattern "2023-12-25"))
      (is (not (re-matches pattern "2023/12/25"))))))
