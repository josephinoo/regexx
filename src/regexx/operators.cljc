(ns regexx.operators)

(defn at-least [n p]
  [:at-least n p])

(defn at-most [n p]
  [:at-most n p])

(defn one-of [& chars]
  (into [:one-of] chars))

(defn none-of [& chars]
  (into [:none-of] chars))

(defn back-ref [ref]
  [:back-ref ref])

(defn atomic-group [p]
  [:atomic-group p])
