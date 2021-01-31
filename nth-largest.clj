(defn insert [sorted-seq n]
  "inserts the n to the right position in the given sorted seq"
      (loop [s sorted-seq a []]
            (cond
              (empty? s) (conj a n)
              (> (first s) n) (recur (rest s) (conj a (first s)))
              :else (apply conj a n s))))

(defn nth-largest
"Find the nth largest number from the given seq"
      [arr n]
      (loop [ip-arr (drop n arr)
             n-sorted (vec (sort > (take n arr)))]
            (do
              (if
               (empty? ip-arr)
               (nth n-sorted (dec n))                       ; n to be converted to zero index
               (recur (rest ip-arr) (take n (insert n-sorted (first ip-arr))))))))
