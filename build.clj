(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'net.clojars.josephinoo/regexx)
(def version "0.1.0")
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (clean nil)
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]
                :scm {:url "https://github.com/josephinoo/regexx"
                      :connection "scm:git:git://github.com/josephinoo/regexx.git"
                      :developerConnection "scm:git:ssh://git@github.com/josephinoo/regexx.git"
                      :tag (str "v" version)}
                :pom-data [[:description "A Clojure library for generating regular expressions using a composable, data-driven DSL"]
                           [:url "https://github.com/josephinoo/regexx"]
                           [:licenses
                            [:license
                             [:name "Eclipse Public License 1.0"]
                             [:url "https://opensource.org/licenses/EPL-1.0"]]]
                           [:developers
                            [:developer
                             [:name "josephinoo"]]]]})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file})
  ;; Copy pom to root of target for deps-deploy
  (b/copy-file {:src "target/classes/META-INF/maven/net.clojars.josephinoo/regexx/pom.xml"
                :target "pom.xml"}))

(defn deploy [_]
  (jar nil)
  (println "\nDeploying to Clojars...")
  (println "Run: clojure -X:deploy :artifact '\"target/regexx-0.1.0.jar\"'"))
