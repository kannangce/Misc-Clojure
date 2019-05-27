(defn custom-comparator
  "Sorts the given 2 numbers in alphanumerically reverse order.
  ie., each digit will be compared to the corresponding digit(from left) in the
  other number. If all the digits of the least number is equal to the corresponding
  digits in big number, then the least number is considered big.
  Ex, 2>20>200>1>119"
  [num1 num2]
  (loop [str1 (str num1) str2 (str num2)]
    (if (or (empty? str1) (empty? str2))
        (or (if(empty? str1) -1 1))
        (do
          (if-not (= (first str1) (first str2))
            (compare str2 str1)
            (recur (subs str1 1) (subs str2 1)))))))




(defn maxcat
  "Returns the largest integer you can create by concatenating
  the integers in ns."
  [ns]
  (BigInteger. (reduce
                 #(str %1 %2) ; Concatenate the sorted collection
                  "" ; Start reducing with empty string
                 (sort ; sort in custom reverse order
                   custom-comparator
                   ns))))

(time
  (sort
    custom-comparator
    [20 2 19 11 900 9 90]))

;; "Elapsed time: 0.106439 msecs"
