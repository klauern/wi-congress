(ns wi-congress.test.rss
  (:use midje.sweet
        wi-congress.rss)
  (:require [clojure.data.zip.xml :as xml])
  (:import java.io.ByteArrayInputStream))

(def feed (random-feed))

(def rss-zipper (rss-zipify feed))

(fact "rss feeds can be retrieved randomly"
      (random-feed) => (has-prefix #"https://")
      (type (random-feed)) => String)

(fact "retrieving an rss feed returns a zipper"
      (type rss-zipper) => clojure.lang.PersistentVector)

(defonce floor "floor.rss")

(fact "can retrieve a list of rss items from file"
      (type (rss-zipify-file floor)) => clojure.lang.PersistentVector
      (count (take 5 (get-items (rss-zipify-file floor)))) => 5
      (xml/xml1-> (nth (get-items (rss-zipify-file floor)) 2) :guid xml/text)=> "0e30f9f8-b58e-44b8-9239-9ac775f900ba")