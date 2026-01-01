# regexx

[![Clojars Project](https://img.shields.io/clojars/v/net.clojars.josephinoo/regexx.svg)](https://clojars.org/net.clojars.josephinoo/regexx)
[![Clojure](https://img.shields.io/badge/clojure-1.11.1-blue.svg)](https://clojure.org)
[![License](https://img.shields.io/badge/License-EPL%201.0-red.svg)](https://opensource.org/licenses/EPL-1.0)

A Clojure library for generating regular expressions using a composable, data-driven DSL (AST).
Treat regex as data, not strings!

Inspired by [pregex](https://github.com/manoss96/pregex).

## Features

*   **Data-Driven**: Define regex patterns using Clojure vectors and keywords.
*   **Cross-Platform**: Compiles to `java.util.regex.Pattern` (JVM) and `RegExp` (JavaScript/CLJS).
*   **Composable**: Easily combine patterns and create reusable abstractions.
*   **Semantic Helpers**: Built-in helpers for common patterns like Email, UUID, URL, etc.
*   **Readable**: No more "write-only" regex strings.

## Installation

Add the following to your `deps.edn`:

```clojure
{:deps {net.clojars.josephinoo/regexx {:mvn/version "0.1.0"}}}
```

Or in Leiningen (`project.clj`):

```clojure
[net.clojars.josephinoo/regexx "0.1.0"]
```

## Usage

```clojure
(require '[regexx.core :as r]
         '[regexx.helpers :as h]
         '[regexx.operators :as op])

;; Basic Primitives
(r/regex [:seq :digit :word])
;; => #"\d\w"

;; Combinators
(r/regex [:or "cat" "dog"])
;; => #"(?:cat|dog)"

;; Quantifiers
(r/regex [:one-or-more :digit])
;; => #"(?:\d)+"

;; Semantic Helpers
(r/regex (h/email))
;; => Matches emails

(r/regex (h/uuid))
;; => Matches UUIDs

;; Enhanced Operators
(r/regex (op/at-least 3 :digit))
;; => #"(?:\d){3,}"

(r/regex (op/one-of "a" "b" "c"))
;; => #"[abc]"

;; Named Groups & Backreferences
(r/regex [:seq
          [:named-group :my-group :digit]
          (op/back-ref :my-group)])
;; => #"(?<my-group>\d)\k<my-group>"
```

## Supported Primitives

*   `:digit` (`\d`), `:non-digit` (`\D`)
*   `:word` (`\w`), `:non-word` (`\W`)
*   `:whitespace` (`\s`), `:non-whitespace` (`\S`)
*   `:any` (`.`)
*   `:start` (`^`), `:end` (`$`)
*   `:boundary` (`\b`), `:non-boundary` (`\B`)
*   `:alpha` (`[a-zA-Z]`), `:alphanumeric` (`[a-zA-Z0-9]`)
*   `:hex-digit` (`[0-9a-fA-F]`)

## Supported Combinators

*   `[:seq ...]`
*   `[:or ...]`
*   `[:optional ...]`
*   `[:one-or-more ...]`
*   `[:zero-or-more ...]`
*   `[:repeat n m ...]`
*   `[:not ...]`
*   `[:group ...]`, `[:named-group name ...]`
*   `[:lookahead ...]`, `[:lookbehind ...]`, `[:neg-lookahead ...]`, `[:neg-lookbehind ...]`
*   `[:at-least n ...]`, `[:at-most n ...]`
*   `[:one-of ...]`, `[:none-of ...]`
*   `[:back-ref ...]`
*   `[:atomic-group ...]`

## Testing

Run tests with:

```bash
clj -M:test
```
