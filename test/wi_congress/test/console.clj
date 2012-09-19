(ns wi-congress.test.console
  (:use midje.sweet
        [wi-congress.rss :only (rss-slurp-file get-items)]
        wi-congress.core)
  (:require [lanterna.screen :as s]
            [wi-congress.console.core :as console]))

(def rss-items (-> "floor.rss"
                 rss-slurp-file
                 get-items))

(def second-one (nth rss-items 1))


(fact "can print a feed item to console"
      (console/print-feed-item screen 0 0 second-one) => nil
      (s/get-size screen) => [142 50])