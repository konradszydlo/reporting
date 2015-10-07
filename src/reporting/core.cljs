(ns ^:figwheel-always reporting.core
    (:require [rum.core :as rum]))

(enable-console-print!)

(defn el [id]
  (.getElementById js/document id))

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

#_(rum/defc table-week < rum/reactive []
  [])

(rum/defcs stateful < (rum/local 0) [state title]
  (let [local (:rum/local state)]
    [:div
     {:on-click (fn [_] (swap! local inc))}
     title ": " @local]))



(defn on-js-reload []
  ;(rum/mount (label "texxxt") (el "report-table"))
  (rum/mount (stateful "Clicks count") (el "click-counter"))
)

