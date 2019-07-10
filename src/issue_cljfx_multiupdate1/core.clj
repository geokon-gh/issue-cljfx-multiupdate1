(ns issue-cljfx-multiupdate1.core
  (:require [cljfx.api :as fx])
  (:import [javafx.scene.input KeyCode KeyEvent]))


(defn- reset-subtype
  [state]
  (assoc state :current-subtype (key (first (get-in state [:kingdoms
                                                           (:current-kingdom state)
                                                           (:current-type state)])))))
(defn- reset-type
  [state]
  (reset-subtype (assoc state :current-type (key (first (get-in state [:kingdoms
                                                                       (:current-kingdom state)]))))))
(defn- reset-kingdom
  [state]
  (reset-type (assoc state :current-kingdom (key (first (:kingdoms state))))))

(def *state
  ""
  (atom {:kingdoms {"plants" {"oak" {"old" 1100
                                     "new" 20}
                              "firn" {"christmas" 15
                                      "mast" 400}
                              "cactus" {"pokey" 1
                                        "decorative" 1}}
                    "animals" {"lion" {"african" 300
                                       "mountain" 500}
                               "bear" {"polar" 900
                                       "black" 300}
                               "human" {"woman" 120
                                        "man " 200}}
                    "mushrooms" {"toadstool" {"red" 1
                                              "white" 1}}}}))

;; set the initial state
(swap! *state reset-kingdom)


(defmulti event-handler :event/type)

(defmethod event-handler ::set-kingdom
  [event]
  (println "set kingdom")
  (swap! *state assoc :current-kingdom (:fx/event event))
  (swap! *state reset-type)) ;assoc :current-type (key (first (get-in @*state [:kingdoms (:current-kingdom @*state)])))))

(defmethod event-handler ::set-type
  [event]
  (println "set type")
  (swap! *state assoc :current-type (:fx/event event))
  (swap! *state reset-subtype))

(defmethod event-handler ::set-subtype
  [event]
  (println "set subtype")
  (swap! *state assoc :current-subtype (:fx/event event)))

(defn creatures
  [{:keys [kingdoms current-kingdom current-type current-subtype]}]
  {:fx/type :h-box
   :children [{:fx/type :choice-box 
               :on-value-changed {:event/type ::set-kingdom}
               :value current-kingdom
               :items (into [] (keys kingdoms))}
              {:fx/type :choice-box ;; DROPDOWN -> LINE
               :on-value-changed {:event/type ::set-type}
               :value current-type
               :items (into [] (keys (get kingdoms current-kingdom)))}
              {:fx/type :choice-box ;; DROPDOWN -> LINE
               :on-value-changed {:event/type ::set-subtype}
               :value current-subtype
               :items (into [] (keys (get-in kingdoms [current-kingdom current-type])))}]})

(defn root
  ""
  [{:keys [kingdoms current-kingdom current-type]}]
  {:fx/type :stage
   :showing true
   :scene {:fx/type :scene
           :root {:fx/type :h-box
                  :children [{:fx/type creatures
                              :kingdoms kingdoms
                              :current-kingdom current-kingdom
                              :current-type current-type}]}}})

(defn render-please
  ""
  []
  (fx/mount-renderer
   *state
   (fx/create-renderer
    :middleware (fx/wrap-map-desc assoc :fx/type root)
    :opts {:fx.opt/map-event-handler event-handler})))

(render-please)
