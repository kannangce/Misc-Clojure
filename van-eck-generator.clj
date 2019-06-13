(defn van-eck
  "Generates a lazy sequence of van eck's sequence"
  ([]
   (van-eck []))
  ([curr-seq]
   (let [next-num (if (empty? curr-seq) 0
                    (inc ; To calculate the # steps we inc the index
                     (index-from-last
                      (butlast curr-seq)
                      (last curr-seq))))]
    (lazy-seq
      (cons next-num (van-eck (conj curr-seq next-num)))))))

(defn index-from-last
  "Finds the index of 'to-find' from the last element in the given sequence 'seq-from'
  If the element is not found, -1 is returned."
  [seq-from to-find]
  (let [seq-len (count seq-from)]
    (loop [rev-index 0]
      (let [index (- (dec seq-len) rev-index)]
        (if (= to-find (nth seq-from index))
            rev-index
            (if (>= rev-index (dec seq-len))
             -1
             (recur (inc rev-index))))))))
