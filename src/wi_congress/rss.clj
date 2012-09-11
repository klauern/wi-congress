(ns wi-congress.rss
  (:require [clojure.java.io :as io]
            [clojure.data.zip :as zip]
            [clojure.zip :as z]
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
  (-> 
    (vals feeds)
    shuffle
    first))

(defn rss-zipify 
  "Taking a loction, read the data in, parse it to XML, and turn it into an XML zip" 
  [location]
  (-> location
    clojure.xml/parse
    clojure.zip/xml-zip))

(defn rss-zipify-file
  [file-location]
  (-> file-location
    clojure.java.io/resource
    .toString
    rss-zipify))


(defn get-items [rss]
  (xml/xml-> rss :channel :item))

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