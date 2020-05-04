;; The snippet is convert a alphabet representation of index into respective number.
;; #chennai-clojure #practice-program

;; Generates a map of capital characters to their respective index
(def alpha->num
		(into {}
			(map #(vector (char %1) %2)
				(range 65 92)
				(range 1 27))))

(defn convert
 [str-index]
 (let [str-len (count str-index)]
 	(->> (range (dec str-len) -1 -1)
 		 (map #(* (pow 26 %2) (alpha->num %1)) str-index)
 		 (reduce +))))

(defn pow
[x y]
	((memoize #(Math/pow %1 %2)) x y))
