(ns wi-congress.core
  (:require [clj-http.client :as http]
            ;;[clojure.data.xml :as xml]
            ;;[clojure.zip :as zip]
            [datomic.api :as d]
            [clojure.data.zip :as zip]
            [clojure.data.zip.xml :as xml]
            [clojure.walk :as walk])
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


(def tagged-xml (clojure.xml/parse (clojure.java.io/input-stream (clojure.java.io/resource "intros-and-committees-senate.rss"))))


(def raw-map (zipmap (keys tagged-xml) (vals tagged-xml)))

(def tzip (clojure.zip/xml-zip tagged-xml))

(def mapping (xml/xml-> tzip :channel :item :guid))

(count mapping)

(def one (nth mapping 25))

(first (:content (first one)))

(def random-title
  (first (:content (ffirst 
    (xml/xml-> tzip :channel :item :title)))))

(defn guid [rss pos]
  (first (:content (first (nth
                     (xml/xml-> rss :channel :item :guid) pos)))))

(defn link [rss pos]
  (first (:content (first (nth
                     (xml/xml-> rss :channel :item :link) pos))))
  
  )

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
  (first (:content (first (nth
                            (xml/xml-> rss :channel :item tag) pos)))))