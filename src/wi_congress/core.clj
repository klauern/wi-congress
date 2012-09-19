(ns wi-congress.core
  (:require [clj-http.client :as http]
            [clojure.data.zip :as zip]
            [clojure.data.zip.xml :as xml]
            [lanterna.screen :as s])
  (:use     [clojure.pprint]))



(def screen-size (ref [142 50]))

(defn handle-resize [cols rows]
  (dosync (ref-set screen-size [cols rows])))


;; I actually can't believe I have to keep track of this... 
(def cursor (ref [0 0]))

(def screen (s/get-screen :swing {:cols (first @screen-size)
                                  :rows (second @screen-size)
                                  :resize-listener handle-resize}))