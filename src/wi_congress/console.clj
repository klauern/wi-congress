(ns wi-congress.console
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [clojure.data.zip.xml :as xml]
            [wi-congress.rss :as rss]))


;; Note: alot of this is borrowed heavily as inspiration from Steve Losh's
;; Caves of Clojure series: http://stevelosh.com/blog/
;; Also, his lanterna wrapper is awesome, try it

(defrecord ScreenSize [width height])
(defrecord Position [x-pos y-pos])

(defrecord Console [^ScreenSize screen-size ^Position cursor])


;; printing a feed item
(defn print-feed-item [screen x y item]
  (let [item-map (rss/get-item-map item)
        {:keys [guid link title description pubdate updated]} item-map]
    (s/put-string screen x y (str "Feed " guid 
             " on " pubdate 
             " last updated on " updated))
    (s/put-string screen x (+ y 1) link)
    (s/put-string screen x (+ y 2) (str "Title: " title))
    (s/put-string screen x (+ y 3) (str "Description: " description))))