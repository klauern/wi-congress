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

(def screen-size (ref [142 50]))

;; I actually can't believe I have to keep track of this... 
(def cursor (ref [0 0]))


(defn handle-resize [cols rows]
  (dosync (ref-set screen-size [cols rows])))

(def screen (s/get-screen :swing {:cols (first @screen-size)
                                  :rows (second @screen-size)
                                  :resize-listener handle-resize}))

;; printing a feed item
(defn console-print-feed-item [screen x y item]
  (let [item-map (rss/get-item-map item)
        {:keys [guid link title description pubdate updated]} item-map]
    (s/put-string screen x y (str "Feed " guid 
             " on " pubdate 
             " last updated on " updated))
    (s/put-string screen x (+ y 1) link)
    (s/put-string screen x (+ y 2) (str "Title: " title))
    (s/put-string screen x (+ y 3) (str "Description: " description))))