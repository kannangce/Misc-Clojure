(ns logic-test.n-queens
  (:refer-clojure :exclude [==]) ;; Exclude clojure default == so that we'll use it from core.logic
  (:use clojure.core.logic)
  (:require [clojure.core.logic.fd :as fd]))

(defn down
 [[x y]]
 [(inc x) y])

(defn up
 [[x y]]
 [(dec x) y])

(defn right
 [[x y]]
 [x (inc y)])

(defn left
 [[x y]]
 [x (dec y)])

(defn down-right
 [[x y]]
 (down (right [x y])))

(defn down-left
 [[x y]]
 (down (left [x y])))

(defn up-left
 [[x y]]
 (up (left [x y])))

(defn up-right
 [[x y]]
 (up (right [x y])))


(defn in-limit?
 "Checks if the given point is withing the limit of the given collection coll"
 [coll [x y]]
 (if (or (< x 0) (< y 0))
 	false
 	(if (or
 		 (>= x (count coll))
 		 (>= y (count (nth coll 0))))
 		 false
 		 true)))
     

(defn  get-elem-along
 "Gets the elements from the collection coll,
  starting from the given point,
  where each point is specified by the given function step"
 [coll [x y] step]
 (loop [curr-pos [x y]
 		step step
 		collected [(get-in coll curr-pos)]]
 	(if-not (in-limit? coll curr-pos)
 		[]
 		(let [next-pos (step curr-pos)
 			  next-elem (get-in coll next-pos)]
 			(if-not (in-limit? coll next-pos)
 				collected
 				(recur (step curr-pos) step (conj collected next-elem)))))))


(defn get-all-diagonals
	"Gets all the possible diagnols in the given collection"
	[coll]
	(let [point-fns []
		  x-max (count coll)
		  y-max (count (nth coll 0))]

		(println x-max)
		(println y-max)
		(println point-fns)

		(->> point-fns
			(cons {:points (for [y (range y-max)] [0 y])
			 		:fn down-left})
			(cons {:points (for [x (range x-max)] [x (dec y-max)])
			 		:fn down-left})
			(cons {:points (for [y (range y-max)] [(dec x-max) y])
			 		:fn up-left})
			(cons {:points (for [x (range x-max)] [x (dec y-max)])
			 		:fn up-left})
			(reduce 
				(fn[accum param]
				 (do (println accum)
				 				(println param)
									(into accum 
										(map #(get-elem-along coll %1 (:fn param))
											(:points param)))))
									#{})
			(filter not-empty))))

(defn count-queens [coll]
  "Counts the queens in the given collection coll"
  (count (filter #(= :Q %) coll)))
  
(defn solve-n-queens
 [n]
 (let [sqrs (repeatedly (* n n) lvar)
   				rows (mapv #(identity (vec %)) (partition n sqrs))
   				cols (apply mapv vector rows)
   				diagnols (get-all-diagonals rows)]

				(println rows)
				(println cols)
				(println diagnols)
    (run 1 [q]
        (== q rows)
        (everyg #(membero % [:Q :_]) sqrs)
        (project [diagnols]
            (conde
            	[(== 1 (count-queens diagnols))]
            	[(== 0 (count-queens diagnols))]))
        (project [rows]
            (everyg #(== 1 (count-queens %)) rows))
        (project [cols]
            (everyg #(== 1 (count-queens %)) cols)))))
