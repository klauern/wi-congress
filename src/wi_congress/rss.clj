(ns wi-congress.rss
  (:require [clojure.java.io :as io]
            [clojure.data.zip :as zip]
            [clojure.data.zip.xml :as xml]))

(def feeds { :intros-and-committee-all              "https://docs.legis.wisconsin.gov/feed/custom/floor"
             :intros-and-committee-senate           "https://docs.legis.wisconsin.gov/feed/custom/floor/senate"
             :intros-and-committee-assembly         "https://docs.legis.wisconsin.gov/feed/custom/floor/assembly"
             :actions-related-to-committee-all      "https://docs.legis.wisconsin.gov/feed/custom/committee"
             :actions-related-to-committee-senate   "https://docs.legis.wisconsin.gov/feed/custom/committee/senate"
             :actions-related-to-committee-assembly "https://docs.legis.wisconsin.gov/feed/custom/committee/assembly"
             :floor-actions-all                     "https://docs.legis.wisconsin.gov/feed/custom/allfloor"
             :floor-actions-senate                  "https://docs.legis.wisconsin.gov/feed/custom/allfloor/senate"
             :floor-actions-assembly                "https://docs.legis.wisconsin.gov/feed/custom/allfloor/assembly"
             })

(defn random-feed []
  ((-> 
     (keys feeds)
     shuffle
     first) 
    feeds))

(defn rss-zipify 
  "Taking a loction, read the data in, parse it to XML, and turn it into an XML zip" 
  [location]
  (-> location
    clojure.xml/parse
    clojure.zip/xml-zip))

(defn rss-zipify-file
  [file-location]
  (-> file-location
    .toString
    clojure.java.io/resource
    rss-zipify))


;; TODO: not sure how to retrieve the first few items from this if there are a huge number.
;; everything I've done is through the REPL, which--when I try to get the value from it--
;; evaluates the entire sequence, which takes forever, if it doesn't bomb out completely.
(defn get-items [rss]
  (xml/xml1-> rss :channel :item))


;; (def local-data (clojure.java.io/resource "intros-and-committees-senate.rss"))

(def possible-rss-items #{:guid :link :title :description :pubdate :a10:updated})

(defn item-field
  "Get a field from an RSS Feed given the root RSS feed, position element, and tag to retrieve. Some
tags to retrieve are:

  - guid
  - link
  - title
  - description
  - pubdate
  - a10:updated"
  [rss pos tag]
  {:pre [(contains? possible-rss-items tag)
         (>= 0 pos)]}
  (xml/text (nth
              (xml/xml-> rss :channel :item tag) pos)))