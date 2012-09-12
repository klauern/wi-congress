(ns wi-congress.console
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [clojure.data.zip.xml :as xml]
            [wi-congress.rss :as rss]))
;; Note: alot of this is borrowed heavily as inspiration from Steve Losh's
;; Caves of Clojure series: http://stevelosh.com/blog/
;; Also, his lanterna wrapper is awesome, try it

(def screen-size (ref [142 50]))

(def term (t/get-terminal :swing))


;; printing a feed item
(defn console-print-feed-item [item]
  (let [guid        (xml/xml1-> item :guid xml/text)
        link        (xml/xml1-> item :link xml/text)
        title       (xml/xml1-> item :title xml/text)
        description (xml/xml1-> item :description xml/text)
        pubdate     (xml/xml1-> item :pubdate xml/text)
        updated     (xml/xml1-> item :a10:updated xml/text)]
    (t/put-string term (str "Feed " guid 
             " on " pubdate 
             " last updated on " updated))
    (t/put-string term link)
    (t/put-string term (str "Title: " title))
    (t/put-string term (str "Description: " description))))