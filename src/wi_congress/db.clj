(ns wi-congress.db
  (:require [datomic.api :only [q db] :as d]
            [clojure.data.xml :as xml])
  (:use     [clojure.pprint]))

(def uri "datomic:mem://congress")

(d/create-database uri)

(def conn (d/connect uri))

(def schema-tx (read-string (slurp "src/datomic/seattle/seattle-schema.dtm")))

@(d/transact conn schema-tx)

(def data-tx (read-string (slurp "src/datomic/seattle/seattle-data0.dtm")))

(first data-tx)
(second data-tx)
(nth data-tx 2)

@(d/transact conn data-tx)

