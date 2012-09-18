(ns wi-congress.test.rss
  (:use midje.sweet
        wi-congress.rss)
  (:require [clojure.data.zip.xml :as xml])
  (:import java.io.ByteArrayInputStream))

(def feed (random-feed))

(def rss-zipper (rss-slurp feed))

(fact "rss feeds can be retrieved randomly"
      (random-feed) => (has-prefix #"https://")
      (type (random-feed)) => String)

(fact "retrieving an rss feed returns a zipper"
      (type rss-zipper) => clojure.lang.PersistentVector)

(def floor (rss-slurp-file "floor.rss"))

(fact "can retrieve a list of rss items from file"
      (type floor) => clojure.lang.PersistentVector
      (count (take 5 (get-items floor))) => 5
      (xml/xml1-> (nth (get-items floor) 2) :guid xml/text)=> "0e30f9f8-b58e-44b8-9239-9ac775f900ba")

(fact "can create maps of individual rss items"
      (let [item (get-item-map (nth (get-items floor) 2))] 
        (:guid item) => "0e30f9f8-b58e-44b8-9239-9ac775f900ba"
        (:title item) => "SB576 in Sen: Read first time and referred to committee on Senate Organization"
        (:pubdate item) => "Thu, 15 Mar 2012 00:00:00 -0500"
        (:updated item) => "2012-03-15T00:00:00-05:00"
        (:link item) => "https://docs.legis.wisconsin.gov/2011/proposals/sb576"
        (:description item) => "Read first time and referred to <a href=\"https://docs.legis.wisconsin.gov/2011/committees/534\">committee</a> on Senate Organization<br/>Relating to: battery requirements for smoke detectors."))

(fact "can create map of indexed individual rss items"
      (let [item (get-nth-item-map-from-feed floor 2)]
        (:guid item) => "0e30f9f8-b58e-44b8-9239-9ac775f900ba"))
