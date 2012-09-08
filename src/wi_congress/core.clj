(ns wi-congress.core
  (:require [clj-http.client :as http]
            ;;[clojure.data.xml :as xml]
            ;;[clojure.zip :as zip]
            [datomic.api :as d]
            [clojure.data.zip :as zip]
            [clojure.data.zip.xml :as xml]
            [clojure.walk :as walk]
            [lanterna.screen :as s])
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

(defn guid [rss pos]
  (item-field rss pos :guid))

(defn link [rss pos]
  (item-field rss pos :link))

(defn title [rss pos]
  (item-field rss pos :title))

(defn description [rss pos]
  (item-field rss pos :description))

(defn pubdate [rss pos]
  (item-field rss pos :pubdate))

(defn updated [rss pos]
  (item-field rss pos :a10:updated))

(defn random-feed [rss]
  (shuffle (xml/xml-> rss :channel :item)))


;; printing a feed item
(defn console-print-feed-item [item]
  (let [guid (xml/xml1-> item :guid xml/text)
        link (xml/xml1-> item :link xml/text)
        title (xml/xml1-> item :title xml/text)
        description (xml/xml1-> item :description xml/text)
        pubdate (xml/xml1-> item :pubdate xml/text)
        updated (xml/xml1-> item :a10:updated xml/text)]
    (println "Feed " guid 
             " on " pubdate 
             " last updated on " updated 
             "\n\t" link 
             "\nTitle: " title
             "\nDescription: " description)))