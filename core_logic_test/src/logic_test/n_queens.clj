(ns logic-test.n-queens
  (:refer-clojure :exclude [==]) ;; Exclude clojure default == so that we'll use it from core.logic
  (:use clojure.core.logic)
  (:require [clojure.core.logic.fd :as fd]))


;; NOTE: FOR SOME REASON THIS DOESN'T WORK. 
;; I'M ABLE TO GET THE ROWS AND COLS CONDITION WORKING.
;; AND THE DIAGONAL CONDITION WORKING SEPARATELY.
;; BUT NOT BOTH.

;; All the below functions is meant to get the diagonals assuming the input is
;; a n x n nested seq. Where each inner seq is a row.
;; Hece for the second element from the first nested seq, you would denote
;; it by [x y] position [1 2]

;; Navigation functions
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
	"Gets all the possible diagonals in the given collection"
	[coll]
	(let [point-fns []
		  x-max (count coll)
		  y-max (count (nth coll 0))]

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
			(filter #(> (count %1) 1)))))

(defn count-queens [coll]
  "Counts the queens in the given collection coll"
  (count (filter #(= :Q %) coll)))
  
(defn solve-n-queens
 "Solve the n-queens for the given number n and optional cnt.
 If cnt not given it will taken as 1"
 ([n cnt]
 	(let [sqrs (repeatedly (* n n) lvar) ;; Create a lvars for n x n grid
   				rows (mapv #(identity (vec %)) (partition n sqrs)) ;; Create rows out of it
   				cols (apply mapv vector rows) ;; Create cols out of it
   				diagonals (get-all-diagonals rows)] ;; Get all the possible diagonals in the given nxn grid
    (run cnt [q]
        (== q rows)
        (everyg #(membero % [:Q :_]) sqrs) ;; Each lvar in must be either :Q (queen) or :_ (nothing)
        (project [rows cols diagonals]
        				(everyg #(== 1 (count-queens %)) rows) ;; Every row must have atleast one queen
        				(everyg #(== 1 (count-queens %)) cols) ;; Every column must have atleast one queen
            (conde  ;; Every diagnol must have 0 or 1 Queen not more than that.
            	[(everyg #(== 1 (count-queens %)) diagonals)]
            	[(everyg #(== 0 (count-queens %)) diagonals)])
            ))))
 		([n]
 				solve-n-queens[n 1]))
