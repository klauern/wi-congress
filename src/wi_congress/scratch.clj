(ns wi-congress.scratch
  (:require [clojure.data.zip.xml :as xml])
  (:use [wi-congress.core]))


(def tagged-xml (clojure.xml/parse (clojure.java.io/input-stream (clojure.java.io/resource "intros-and-committees-senate.rss"))))

(def rss (clojure.zip/xml-zip tagged-xml))

(def mapping (xml/xml1-> rss :channel :item))


(def raw-map (zipmap (keys tagged-xml) (vals tagged-xml)))


(count mapping)
(def item (get mapping 0))


(def one (nth mapping 0))



(count one)


(first (:content (first one)))