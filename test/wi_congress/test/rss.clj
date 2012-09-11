(ns wi-congress.test.rss
  (:use midje.sweet
        wi-congress.rss)
  (:require [clojure.data.zip.xml :as xml])
  (:import java.io.ByteArrayInputStream))

(def feed (random-feed))

(def rss-zipper (rss-zipify feed))

(fact "rss feeds can be retrieved randomly"
      (random-feed) => (has-prefix #"https://")
      (type (random-feed)) => String)

(fact "retrieving an rss feed returns a zipper"
      (type rss-zipper) => clojure.lang.PersistentVector)

(defonce floor "floor.rss")

(fact "can retrieve a list of rss items from file"
      (type (rss-zipify-file floor)) => clojure.lang.PersistentVector
      (count (take 5 (get-items (rss-zipify-file floor)))) => 5)

(def items (get-items (rss-zipify-file floor)))
(nth (take 5 items) 2)

(def xml-thing (ByteArrayInputStream. (.getBytes (.trim "
    <item>
      <guid isPermaLink='false'>7a73873c-ad22-4443-9855-f6cf2f078c9e</guid>
      <link>https://docs.legis.wisconsin.gov/2011/proposals/sb577</link>
      <title>SB577 in Sen: Read first time and referred to committee on Senate Organization</title>
      <description>Read first time and referred to &lt;a href=\"https://docs.legis.wisconsin.gov/2011/committees/534\"&gt;committee&lt;/a&gt; on Senate Organization&lt;br/&gt;Relating to: protective placement.</description>
      <pubDate>Thu, 15 Mar 2012 00:00:00 -0500</pubDate>
      <a10:updated>2012-03-15T00:00:00-05:00</a10:updated>
    </item>"))))


(def xml-thing-item (clojure.zip/xml-zip (clojure.xml/parse xml-thing)))      (count (take 5 (get-items (rss-zipify-file floor)))) => 5
      (xml/xml1-> (nth (get-items (rss-zipify-file floor)) 2) :guid xml/text)=> "0e30f9f8-b58e-44b8-9239-9ac775f900ba")