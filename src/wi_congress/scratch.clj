(ns wi-congress.scratch
  (:require [clojure.data.zip.xml :as xml])
  (:use [wi-congress.core]))


(def tagged-xml (clojure.xml/parse (clojure.java.io/input-stream (clojure.java.io/resource "intros-and-committees-senate.rss"))))


(def raw-map (zipmap (keys tagged-xml) (vals tagged-xml)))

(def rss (clojure.zip/xml-zip tagged-xml))

(def mapping (xml/xml-> rss :channel :item :guid))

(count mapping)

(def one (nth mapping 25))

(first (:content (first one)))