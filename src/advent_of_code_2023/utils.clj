(ns advent-of-code-2023.utils
  (:require [clojure.string :as string]))

(defmacro def-
  ([name & decls]
    (list* `def (with-meta name (assoc (meta name) :private true)) decls)))

(defmacro defmulti-
  [name & decls]
  (list* `defmulti (with-meta name (assoc (meta name) :private true)) decls))

(defmacro defmethod-
  [name & decls]
  (list* `defmethod (with-meta name (assoc (meta name) :private true)) decls))

(defn sum [coll]
  (apply + coll))

(defn product [coll]
  (apply * coll))

(defn re-seq-matches [re s]
  (let [m (re-matcher re s)]
    ((fn step []
       (when (. m (find))
         (cons {:start (. m start) :end (. m end) :group (. m group)}
               (lazy-seq (step))))))))

(defn map-vals [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn take-until [pred coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (if (pred (first s))
        (cons (first s) nil)
        (cons (first s) (take-until pred (rest s)))))))
