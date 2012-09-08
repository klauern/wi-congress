(ns wi-congress.console
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]))

(def term (t/get-terminal :swing))

(t/start term)

(t/put-character term \!)

(def put-character-to-term (partial t/put-character term))
(def write #(dorun (map put-character-to-term %)))


