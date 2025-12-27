(ns regexx.primitives)

(def primitives
  "Set of valid regex primitives."
  #{:digit       ; \d
    :non-digit   ; \D
    :word        ; \w
    :non-word    ; \W
    :whitespace  ; \s
    :non-whitespace ; \S
    :any         ; .
    :start       ; ^
    :end         ; $
    :boundary    ; \b
    :non-boundary ; \B
    :alpha       ; [a-zA-Z]
    :alphanumeric ; [a-zA-Z0-9]
    :ascii       ; \p{ASCII}
    :hex-digit   ; [0-9a-fA-F]
    })

(def combinators
  "Set of valid regex combinators."
  #{:seq
    :or
    :optional    ; ?
    :one-or-more ; +
    :zero-or-more ; *
    :repeat      ; {n,m}
    :range       ; [a-z]
    :not         ; [^...]
    :group       ; (...)
    :named-group ; (?<name>...)
    :lookahead
    :lookbehind
    :neg-lookahead
    :neg-lookbehind
    :at-least
    :at-most
    :one-of
    :none-of
    :back-ref
    :atomic-group
    })
