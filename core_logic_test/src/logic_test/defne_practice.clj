(ns logic-test.defne_practice)
(use 'clojure.core.logic)

(defn lefto1
  [x y l]
  (conde
    [(fresh [d]
       (conso x d l) (membero y d))]
    [(fresh [a b]
       (conso a b l) (lefto1 x y b))]))
