(ns wi-congress.test.rss
  (:use midje.sweet
        wi-congress.rss))

(def feed (random-feed))

(def rss-zipper (rss-zipify feed))

(fact "rss feeds can be retrieved randomly"
      (random-feed) => (has-prefix #"https://")
      (type (random-feed)) => String)

(fact "retrieving an rss feed returns a zipper"
      (type rss-zipper) => clojure.lang.PersistentVector)

(defonce floor "floor.rss")

(fact "can retrieve a list of rss items from file"
      (type (rss-zipify-file floor)) => clojure.lang.PersistentVector)