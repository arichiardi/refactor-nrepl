(ns find.symbols-in-file-test.clj-example
  (:require
   [clojure.set :as set])
  (:import
   (java.io File)
   (java.sql Connection)))

(def thing 42)

::foo

(def ^File file nil)

(defn ^::defn-kw-meta defn1 [])

(defn defn2 ^Connection [])

(defn defn3 []
  (let [a (set/difference #{} #{})]))
