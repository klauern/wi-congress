(ns wi-congress.scratch
  (:require [clojure.data.zip.xml :as xml]
            [clojure.data.zip :as z])
  (:use [wi-congress.rss])
  (:import java.io.ByteArrayInputStream))


(def feed (rss-zipify (random-feed)))

(def items (xml/xml1-> feed :channel :item z/children-auto))

;;(nth (take 2 items) 2)

(def xml-thing (ByteArrayInputStream. (.getBytes (.trim "
    <item>
      <guid isPermaLink='false'>7a73873c-ad22-4443-9855-f6cf2f078c9e</guid>
      <link>https://docs.legis.wisconsin.gov/2011/proposals/sb577</link>
      <title>SB577 in Sen: Read first time and referred to committee on Senate Organization</title>
      <description>Read first time and referred to &lt;a href=\"https://docs.legis.wisconsin.gov/2011/committees/534\"&gt;committee&lt;/a&gt; on Senate Organization&lt;br/&gt;Relating to: protective placement.</description>
      <pubDate>Thu, 15 Mar 2012 00:00:00 -0500</pubDate>
      <a10:updated>2012-03-15T00:00:00-05:00</a10:updated>
    </item>"))))

(def items (get-items (rss-zipify xml-thing)))

;(def xml-thing-item (clojure.zip/xml-zip (clojure.xml/parse xml-thing)))

;; (def mapping (xml/xml1-> rss :channel :item))


;;(def raw-map (zipmap (keys tagged-xml) (vals tagged-xml)))