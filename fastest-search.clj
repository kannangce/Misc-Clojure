; Define engines along with functions to return 
; the search urls for a given term
(def search-url-map
  {
  :bing #(str "https://www.bing.com/search?q=" %)
  :google #(str "https://google.com?q=" %)
  })

(defn engine-type
  "Returns the engine type"
  [engine-entry]
  (first engine-entry))

(defn search-fn
"Returns the function to generate the search-url"
 [engine-entry]
 (last engine-entry))

(defn search
  "Searches the given string from bing"
  [engine-entry to-search]
  { (engine-type engine-entry)
    (slurp ((search-fn engine-entry) to-search))})


(defn search-all-engines
"Searches all the engines for the given term
and prints the name of the engine that returned the fastest result"
[term]
(let [possiblties (count search-url-map)
      result-chans (repeatedly possiblties chan)]
      (doall (map #(go (>! %2 (search %1 term))) search-url-map result-chans))
      (let [fastest-result (first (first (alts!! result-chans)))]
        (println (str "Fastest result from " (engine-type fastest-result)))
      )))

; Do the search 100 times for testing
(repeatedly 100 #(search-all-engines "Clojure"))
