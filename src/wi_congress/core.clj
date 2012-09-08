(ns wi-congress.core
  (:require [clj-http.client :as http]
            [clojure.data.zip :as zip]
            [clojure.data.zip.xml :as xml])
  (:use     [clojure.pprint]))

(def feeds { :intros-and-committee-all "https://docs.legis.wisconsin.gov/feed/custom/floor"
             :intros-and-committee-senate "https://docs.legis.wisconsin.gov/feed/custom/floor/senate"
             :intros-and-committee-assembly "https://docs.legis.wisconsin.gov/feed/custom/floor/assembly"
             :actions-related-to-committee-all "https://docs.legis.wisconsin.gov/feed/custom/committee"
             :actions-related-to-committee-senate "https://docs.legis.wisconsin.gov/feed/custom/committee/senate"
             :actions-related-to-committee-assembly "https://docs.legis.wisconsin.gov/feed/custom/committee/assembly"
             :floor-actions-all "https://docs.legis.wisconsin.gov/feed/custom/allfloor"
             :floor-actions-senate "https://docs.legis.wisconsin.gov/feed/custom/allfloor/senate"
             :floor-actions-assembly "https://docs.legis.wisconsin.gov/feed/custom/allfloor/assembly" })

(defn- load-xml [raw-data]
  (clojure.zip/xml-zip (clojure.xml/parse raw-data)))

(defn is-url [loc]
  )

(defn retrieve-rss-from-source [location]
  (load-xml (clojure.java.io/resource (.toString location))))

(def local-data (clojure.java.io/resource "intros-and-committees-senate.rss"))

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