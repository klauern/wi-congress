(defproject wi-senate "1.0.0-SNAPSHOT"
  :description "project to show and dig in to the Wisconsin Legislature's RSS Feeds on bills and motions"
  :url "https://www.github.com/klauern/wi-congress"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-http "0.5.3"]
                 [org.clojure/data.xml "0.0.6"]
                 [org.clojure/data.zip "0.1.1"]
                 [clojure-lanterna "0.9.2"]])
