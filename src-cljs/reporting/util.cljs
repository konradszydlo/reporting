(ns reporting.util
  (:require [datascript.core :as d]))

(defn transact [conn data]
  (d/transact! conn data))
