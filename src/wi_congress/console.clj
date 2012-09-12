(ns wi-congress.console
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [clojure.data.zip.xml :as xml]
            [wi-congress.rss :as rss]))
;; Note: alot of this is borrowed heavily as inspiration from Steve Losh's
;; Caves of Clojure series: http://stevelosh.com/blog/
;; Also, his lanterna wrapper is awesome, try it

(def screen-size (ref [142 50]))

;; I actually can't believe I have to keep track of this... 
(def cursor (ref [0 0]))


(defn handle-resize [cols rows]
  (dosync (ref-set screen-size [cols rows])))

(def term (t/get-terminal :swing {:cols (first @screen-size)
                                  :rows (second @screen-size)
                                  :resize-listener handle-resize}))

;; printing a feed item
(defn console-print-feed-item [item]
  (let [item-map (rss/get-item-map item)
        {:keys [guid link title description pubdate updated]} item-map]
    (t/put-string term (str "Feed " guid 
             " on " pubdate 
             " last updated on " updated))
    (t/put-string term link)
    (t/put-string term (str "Title: " title))
    (t/put-string term (str "Description: " description))))