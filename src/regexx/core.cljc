(ns regexx.core
  (:require [regexx.compiler :as compiler]))

(defn compile-regex
  "Compiles the given AST into a platform-specific Regex object.
   JVM: java.util.regex.Pattern
   JS: RegExp"
  [ast]
  (compiler/to-pattern ast))

(defn regex
  "Alias for compile-regex."
  [ast]
  (compile-regex ast))
