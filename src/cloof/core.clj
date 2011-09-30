(ns cloof.core
  (:use [ring.adapter.jetty]
        [ring.middleware.params]
        [compojure.core]
        [cloof.template])
  (:require [compojure.route :as route]))

;--------------------------wrap!!

(defn wrap-charset [handler charset] 
  (fn [request] 
    (if-let [response (handler request)] 
      (if-let [content-type (get-in response [:headers "Content-Type"])] 
        (if (.contains content-type "charset") 
          response 
          (assoc-in response 
            [:headers "Content-Type"] 
            (str content-type "; charset=" charset))) 
        response)))) 

;--------------------------route!
(defroutes rts
           (GET "/" [] (index))
           (route/files "/")
           (route/not-found "Page not found"))

(run-jetty (wrap! rts (:charset "utf8")) {:port 8080})
