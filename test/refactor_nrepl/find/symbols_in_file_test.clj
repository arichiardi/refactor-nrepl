(ns refactor-nrepl.find.symbols-in-file-test
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]
   [clojure.test :refer [deftest is]]
   [refactor-nrepl.find.symbols-in-file :as sut]
   [refactor-nrepl.ns.ns-parser :as ns-parser]))

(deftest symbols-in-file
  (let [ns-sym 'find.symbols-in-file-test.clj-example]

    ;; Fully remove the ns prior to running the deftest, because `sut/symbols-in-file`
    ;; performs a `require` and we don't want to cache that:
    (remove-ns ns-sym)
    (dosync (alter @#'clojure.core/*loaded-libs* disj ns-sym))

    (let [path-string (-> ns-sym
                          str
                          (string/replace "-" "_")
                          (string/replace "." "/")
                          (str ".clj"))
          path (-> path-string io/resource io/as-file str)
          parsed-ns (ns-parser/parse-ns path)
          found (sut/symbols-in-file path parsed-ns :clj)]
      (doseq [f found]
        (is (symbol? f)
            (-> f class pr-str)))
      (is (contains? found 'Connection)
          "Returns `Connection`, because there's a reference to that literal, unqualified symbol")
      (is (contains? found 'java.sql.Connection)
          "Also returns `java.sql.Connection`, because that's the fully-qualified name of the used symbol,
which can provide consumers with additional insights"))))
