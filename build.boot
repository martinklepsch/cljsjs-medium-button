(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.5.1"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +commit+ "225390f882986a8a7aee786bde247b5b2122a40b")
(def +lib-version+ (str "0.0.0-" +commit+))
(def +version+ (str +lib-version+ "-1"))

(task-options!
 pom {:project     'org.martinklepsch/cljsjs-medium-button
      :version     +version+
      :description "MediumButton extends your Medium Editor with the possibility add buttons."
      :url         "https://github.com/arcs-/MediumButton"
      :scm         {:url "https://github.com/arcs-/MediumButton"}
      :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
   (download  :url      (str "https://raw.githubusercontent.com/arcs-/MediumButton/" +commit+ "/src/MediumButton.min.js")
              :checksum "78A22324935B45CF3930F95C1C87DAC2")
   (download  :url      (str "https://raw.githubusercontent.com/arcs-/MediumButton/" +commit+ "/src/MediumButton.js")
              :checksum "C098F3232C820D598B432888A170E3DF")
   (sift      :move     {#"^MediumButton\.min\.js$"
                         "cljsjs/medium-button/production/medium-button.min.inc.js"
                         #"^MediumButton\.js$"
                         "cljsjs/medium-button/development/medium-button.inc.js"})
   (sift      :include  #{#"^cljsjs"})
   (deps-cljs :name     "org.martinklepsch.cljsjs-medium-button")
   (pom)
   (jar)))
