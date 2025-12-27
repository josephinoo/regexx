(ns demo
  (:require [regexx.core :as r]
            [regexx.helpers :as h]
            [regexx.operators :as op]))

(defn -main []
  (println "--- Regexx Demo ---")

  ;; 1. Define a regex for an email
  (let [email-pattern (r/regex (h/email))]
    (println "\nEmail Pattern:" email-pattern)
    (println "Match 'test@example.com':" (re-matches email-pattern "test@example.com"))
    (println "Match 'invalid':" (re-matches email-pattern "invalid")))

  ;; 2. Define a custom regex using primitives and combinators
  ;; Matches a US phone number: (123) 456-7890
  (let [phone-pattern (r/regex [:seq
                                [:optional [:seq "(" [:repeat 3 3 :digit] ") "]]
                                [:repeat 3 3 :digit]
                                "-"
                                [:repeat 4 4 :digit]])]
    (println "\nPhone Pattern:" phone-pattern)
    (println "Match '123-4567':" (re-matches phone-pattern "123-4567"))
    (println "Match '(123) 456-7890':" (re-matches phone-pattern "(123) 456-7890")))

  ;; 3. Using Enhanced Operators
  ;; Match a string with at least 3 digits
  (let [at-least-3-digits (r/regex (op/at-least 3 :digit))]
    (println "\nAt least 3 digits pattern:" at-least-3-digits)
    (println "Match '12':" (re-matches at-least-3-digits "12"))
    (println "Match '12345':" (re-matches at-least-3-digits "12345"))))

;; Run the main function if this file is executed directly
(when (= *file* (System/getProperty "clojure.core.scriptfile"))
  (-main))
