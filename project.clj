(defproject issue-cljfx-multiupdate1 "0.1.0-SNAPSHOT"
  :description "my personal playground for messing with microphone input"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cljfx "1.2.8"]
                 [com.github.afester.javafx/FranzXaver "0.1"]
                 [thi.ng/geom-viz "0.0.908"]]
  :main ^:skip-aot issue-cljfx-multiupdate1.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
