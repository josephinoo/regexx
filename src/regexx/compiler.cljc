(ns regexx.compiler
  (:require [clojure.string :as str]
            [regexx.validation :as v]))

(defn escape-pattern [s]
  #?(:clj (java.util.regex.Pattern/quote s)
     :cljs (str/replace s #"([.*+?^=!:${}()|\[\]\/\\])" "\\$1")))

(defn compile-ast [ast]
  (cond
    (keyword? ast)
    (case ast
      :digit "\\d"
      :non-digit "\\D"
      :word "\\w"
      :non-word "\\W"
      :whitespace "\\s"
      :non-whitespace "\\S"
      :any "."
      :start "^"
      :end "$"
      :boundary "\\b"
      :non-boundary "\\B"
      :alpha "[a-zA-Z]"
      :alphanumeric "[a-zA-Z0-9]"
      :ascii "\\p{ASCII}"
      :hex-digit "[0-9a-fA-F]"
      (throw (ex-info (str "Unknown primitive: " ast) {:ast ast})))

    (string? ast)
    (escape-pattern ast)

    (vector? ast)
    (let [[op & args] ast]
      (if (v/valid-primitive? op)
        (compile-ast op)
        (case op
          :seq (str/join (map compile-ast args))
          :or (str "(?:" (str/join "|" (map compile-ast args)) ")")
          :optional (str "(?:" (compile-ast (first args)) ")?")
          :one-or-more (str "(?:" (compile-ast (first args)) ")+")
          :zero-or-more (str "(?:" (compile-ast (first args)) ")*")
          :repeat (let [[n m child] args]
                    (str "(?:" (compile-ast child) "){" n "," (or m "") "}"))
          :range (str "[" (str/join args) "]") ; TODO: Handle range properly
          :not (str "[^" (str/join (map compile-ast args)) "]")
          :group (str "(" (compile-ast (first args)) ")")
          :named-group (let [[group-name child] args]
                         (str "(?<" (name group-name) ">" (compile-ast child) ")"))
          :lookahead (str "(?=" (compile-ast (first args)) ")")
          :lookbehind (str "(?<=" (compile-ast (first args)) ")")
          :neg-lookahead (str "(?!" (compile-ast (first args)) ")")
          :neg-lookbehind (str "(?<!" (compile-ast (first args)) ")")
          :at-least (let [[n child] args]
                      (str "(?:" (compile-ast child) "){" n ",}"))
          :at-most (let [[n child] args]
                     (str "(?:" (compile-ast child) "){0," n "}"))
          :one-of (str "[" (str/join args) "]")
          :none-of (str "[^" (str/join args) "]")
          :back-ref (let [[ref] args]
                      (if (number? ref)
                        (str "\\" ref)
                        (str "\\k<" (name ref) ">")))
          :atomic-group (str "(?>" (compile-ast (first args)) ")")
          (throw (ex-info (str "Unknown combinator: " op) {:ast ast})))))

    :else
    (throw (ex-info (str "Invalid AST node: " ast) {:ast ast}))))

(defn to-pattern [ast]
  (v/validate ast)
  (let [pattern-str (compile-ast ast)]
    #?(:clj (java.util.regex.Pattern/compile pattern-str)
       :cljs (js/RegExp. pattern-str))))
