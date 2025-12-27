(ns regexx.helpers
  (:require [regexx.core :as core]))

(defn email []
  [:seq
   [:one-or-more [:not "@" [:whitespace]]]
   "@"
   [:one-or-more [:not "@" [:whitespace]]]
   "."
   [:one-or-more [:alpha]]])

(defn uuid []
  [:seq
   [:repeat 8 8 :hex-digit]
   "-"
   [:repeat 4 4 :hex-digit]
   "-"
   [:repeat 4 4 :hex-digit]
   "-"
   [:repeat 4 4 :hex-digit]
   "-"
   [:repeat 12 12 :hex-digit]])

(defn iban []
  [:seq
   [:repeat 2 2 :alpha]
   [:repeat 2 2 :digit]
   [:repeat 1 30 :alphanumeric]])

(defn url []
  [:seq
   [:optional [:seq "http" [:optional "s"] "://"]]
   [:one-or-more [:not "/" [:whitespace]]]
   [:optional [:seq "/" [:zero-or-more [:not [:whitespace]]]]]])

(defn ipv4 []
  (let [octet [:seq [:repeat 1 3 :digit]]]
    [:seq octet "." octet "." octet "." octet]))

(defn ipv6 []
  ;; Simplified IPv6
  [:seq [:repeat 1 4 :hex-digit]
   [:repeat 7 7 [:seq ":" [:repeat 1 4 :hex-digit]]]])

(defn date []
  ;; ISO 8601 YYYY-MM-DD
  [:seq
   [:repeat 4 4 :digit] "-" [:repeat 2 2 :digit] "-" [:repeat 2 2 :digit]])

(defn time []
  ;; ISO 8601 HH:MM:SS
  [:seq
   [:repeat 2 2 :digit] ":" [:repeat 2 2 :digit] ":" [:repeat 2 2 :digit]])

(defn hex-color []
  [:seq
   [:optional "#"]
   [:or
    [:repeat 6 6 :hex-digit]
    [:repeat 3 3 :hex-digit]]])
