(ns wi-congress.core
  (:require [clj-http.client :as http]
            [clojure.data.xml :as xml]))

(def feeds { :intros-and-committee-all "https://docs.legis.wisconsin.gov/feed/custom/floor"
             :intros-and-committee-senate "https://docs.legis.wisconsin.gov/feed/custom/floor/senate"
             :intros-and-committee-assembly "https://docs.legis.wisconsin.gov/feed/custom/floor/assembly"
             :actions-related-to-committee-all "https://docs.legis.wisconsin.gov/feed/custom/committee"
             :actions-related-to-committee-senate "https://docs.legis.wisconsin.gov/feed/custom/committee/senate"
             :actions-related-to-committee-assembly "https://docs.legis.wisconsin.gov/feed/custom/committee/assembly"
             :floor-actions-all "https://docs.legis.wisconsin.gov/feed/custom/allfloor"
             :floor-actions-senate "https://docs.legis.wisconsin.gov/feed/custom/allfloor/senate"
             :floor-actions-assembly "https://docs.legis.wisconsin.gov/feed/custom/allfloor/assembly" })

(def sample-xml (:body 
                  (http/get
                    (:intros-and-committee-all feeds))))

(def tagged-xml (xml/parse (java.io.StringReader. sample-xml)))

;; would like to find/write methods to retrieve:
;;  - top-level elements
;;  - frequency of element calls
;;  - some tree-like structure of the XML element
;;  - general inspection utilities for xml
