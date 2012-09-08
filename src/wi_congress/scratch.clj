(ns wi-congress.scratch
  (:require [clojure.data.zip.xml :as xml])
  (:use [wi-congress.core]))


(def tagged-xml (clojure.xml/parse (clojure.java.io/input-stream (clojure.java.io/resource "intros-and-committees-senate.rss"))))


(def raw-map (zipmap (keys tagged-xml) (vals tagged-xml)))

(def rss (clojure.zip/xml-zip tagged-xml))

(def mapping (xml/xml1-> rss :channel :item))

(count mapping)
(def item (get mapping 1))



(def one (nth mapping 25))



(count one)


(first (:content (first one)))