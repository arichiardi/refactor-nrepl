(ns com.example.one
  (:require [com.example.two :as two :refer [foo]]
            [com.example.four :as four]))

;; Tries reproducing https://github.com/clojure-emacs/clj-refactor.el/issues/485
(set! *warn-on-reflection* true)

(defn bar []
  (str "bar" (two/foo)))

(defn from-registry [k]
  (k four/registry))
