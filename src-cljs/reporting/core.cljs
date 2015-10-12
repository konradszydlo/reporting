(ns ^:figwheel-always reporting.core
    (:require-macros [cljs.core.async.macros :refer (go)])
    (:require [cljs.core.async :refer (<!)]
              [datascript.core :as d]
              [reporting.util :as u]
              [rum.core :as rum]))

(enable-console-print!)

#_(defn get-current-week-report-data []
  (go ( (let [response (<! )]))))

(defn el [id]
  (.getElementById js/document id))

(println "Edits to this text should show up in your developer console.")

(defonce app-state (atom {:text "Hello world!"}))

(rum/defc table-week < rum/reactive []
  [:div
   [:div
    [:h3 "Week 15"]
    [:span "2nd Oct - 8th Oct, 2015"]]
   [:table {:class "table-reporting"}
    [:tbody
     [:tr
      [:th]
      [:th]
      [:th "Fri 2 Oct"]]]]])

(rum/defcs stateful < (rum/local 0) [state title]
  (let [local (:rum/local state)]
    [:div
     {:on-click (fn [_] (swap! local inc))}
     title ": " @local]))

;; datascript

(def schema {})

(defonce conn (d/create-conn schema))

(def data-names [{:name "Maxim"} {:name "Otto"} {:name "Mark"}])


;; some weirdness with figwheel. It complains about datascript.core/transact! not being defined when used directly.
;(d/transact! conn data-names)
(u/transact conn data-names)

(defn get-names [db]
  (d/q '[:find ?name
         :where [?e :name ?name]]
       db))

(defn set-value! [el value]
  (set! (.-value el) value))

(defn clearn-form [id]
  (set-value! (el id) ""))

(defn save-name []
  (let [name (.-value (el "new-name"))]
    (clearn-form "new-name")
    (u/transact conn [{:name name}] )))

(rum/defc name-saver []
  [:.name-saver
   [:input {:id "new-name"}]
   [:button {:on-click save-name} "Save name"]])

(rum/defc canvas [db]
  [:.canvas
   [:p "All names are here:"]
   [:ul
    (for [[name] (get-names db)]
      [:li {:key name} (str "Name: " name)])]
   (name-saver)])

(defn render
  ([] (render @conn))
  ([db] (rum/mount (canvas db) (el "datascript-test"))))

(d/listen! conn :render
           (fn [tx-report]
             (render (:db-after tx-report))))

(defn on-js-reload []
  ;(rum/mount (label "texxxt") (el "report-table"))
  (rum/mount (table-week) (el "report-table"))
  (render)
  ;(rum/mount (stateful "Clicks count") (el "click-counter"))
  )


(defn ^:export init []
  (on-js-reload))
