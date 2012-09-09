(ns wi-congress.scratch
  (:require [clojure.data.zip.xml :as xml]
            [clojure.data.zip :as z])
  (:use [wi-congress.rss]))


(def feed (rss-zipify (random-feed)))

(def items (xml/xml1-> feed :channel :item z/children-auto))



;; (def mapping (xml/xml1-> rss :channel :item))


;;(def raw-map (zipmap (keys tagged-xml) (vals tagged-xml)))