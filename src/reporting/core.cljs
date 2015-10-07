(ns ^:figwheel-always reporting.core
    (:require [rum.core :as rum]))

(enable-console-print!)

(defn el [id]
  (.getElementById js/document id))

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(rum/defc table-week < rum/reactive []
  [:div
   [:div
    [:h3 "Week 15"]
    [:span "2nd Oct - 8th Oct, 2015"]]])


(rum/defcs stateful < (rum/local 0) [state title]
  (let [local (:rum/local state)]
    [:div
     {:on-click (fn [_] (swap! local inc))}
     title ": " @local]))


(defn on-js-reload []
  ;(rum/mount (label "texxxt") (el "report-table"))
  (rum/mount (table-week) (el "report-table"))
  ;(rum/mount (stateful "Clicks count") (el "click-counter"))
)

