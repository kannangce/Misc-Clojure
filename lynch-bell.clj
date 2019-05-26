(defn uniq-digits
  "Returns a set with unique digits from the given number."
  [num]
  (loop [digits #{} curr-num num]
    (if (or (nil? curr-num) (< curr-num 1))
      digits
      (recur
        (conj digits (mod curr-num 10))
        (quot curr-num 10)))))

(defn is-lynch-bell?
  "Tells if the given number is a lynch bell"
  [num]
  (def reducer (fn [a b]
                 (if (= 0 (mod num b))
                    (and a true)
                    (reduced false)))) ; Shortcircuit

  (def digits (uniq-digits num))
  (and
    (= (count digits) (count (str num))) ; no repeated digits
    (nil? (digits 0)) ; no 0
    (reduce ; The condition of being divisible by all digits
         reducer
         true
         digits)))

(first
     (filter
       is-lynch-bell?
       (range 9876543 1 -1)))
;; The lynch bell cannot have 9 or 8 digits
;; Well explained here https://math.stackexchange.com/a/2766151/104924
