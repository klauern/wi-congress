(ns wi-congress.db
  (:require [datomic.api :as datomic]
            [clojure.data.xml :as xml])
  (:use     [clojure.pprint]))

(def uri "datomic:mem://congress")

(d/create-database uri)


