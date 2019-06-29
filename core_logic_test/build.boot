(def project 'logic_test)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
			    [org.clojure/core.logic "0.8.11"]])

(task-options!
 aot {:namespace   #{'logic-test.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/logic_test"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 repl {:init-ns    'logic-test.core}
 jar {:main        'logic-test.core
      :file        (str "logic_test-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (with-pass-thru fs
    (require '[logic-test.core :as app])
    (apply (resolve 'app/-main) args)))

(require '[adzerk.boot-test :refer [test]])
