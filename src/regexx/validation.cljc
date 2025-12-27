(ns regexx.validation
  (:require [regexx.primitives :as p]))

(defn valid-primitive? [x]
  (contains? p/primitives x))

(defn valid-combinator? [x]
  (contains? p/combinators x))

(defn validate
  "Validates the AST. Returns nil if valid, or throws an exception with details."
  [ast]
  (cond
    (keyword? ast)
    (if (valid-primitive? ast)
      nil
      (throw (ex-info (str "Invalid primitive: " ast) {:ast ast})))

    (string? ast)
    nil ; Strings are valid literals

    (number? ast)
    nil ; Numbers are valid (e.g. for repeat)

    (nil? ast)
    nil ; Nil is valid (e.g. for repeat)

    (vector? ast)
    (let [[op & args] ast]
      (cond
        (valid-combinator? op)
        (if (= op :back-ref)
          nil ; Arguments to back-ref are not AST nodes
          (if (= op :named-group)
            (validate (second args)) ; Only validate the child (second arg)
            (doseq [arg args]
              (validate arg))))

        (valid-primitive? op)
        (if (empty? args)
          nil
          (throw (ex-info (str "Primitive " op " cannot have arguments") {:ast ast})))

        :else
        (throw (ex-info (str "Invalid combinator: " op) {:ast ast}))))

    :else
    (throw (ex-info (str "Invalid AST node: " ast) {:ast ast}))))
