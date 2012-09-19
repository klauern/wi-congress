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


(defn print-welcome
  [screen]
  (s/clear screen)
  (s/put-string screen 0 0 "Welcome")
  (s/redraw screen))

(def key-chart ["Keyboard Shortcuts:"
                "-------------------"
                "  l: list RSS feeds"
                "  n: next rss item"
                "  r: random feed"
                "  x: exit"])

(defn print-key-chart [screen x y key-chart]
  (loop [y-pos y
         keys key-chart]
    (s/put-string screen x y-pos (first keys))
    (if (nil? (next keys))
      (s/redraw screen)
      (recur (+ 1 y-pos) (next keys)))))

(defn print-status-bar [screen info]
  (let [bar-row (dec (second (s/get-size screen)))]
    (s/put-string screen 0 bar-row info))
  (s/redraw screen))