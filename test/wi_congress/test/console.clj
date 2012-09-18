(ns wi-congress.test.console
  (:use midje.sweet
        wi-congress.console
        [wi-congress.rss :only (rss-slurp-file get-items)])
  (:require [lanterna.terminal :as t]))

(def rss-items (-> "floor.rss"
                 rss-slurp-file
                 get-items))

(def second-one (nth rss-items 1))

(fact "can print a feed item to console"
      (console-print-feed-item second-one) => nil
      @screen-size => [142 50])

