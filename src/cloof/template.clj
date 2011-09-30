(ns cloof.template
    (:import (java.io File))
    (:use
        [hiccup.core]
        [hiccup.page-helpers]
        [clojure.contrib.string :only (split)]))

(def whoisyou
  {:name "似非原重雄"
   :description "貴方の人生のお荷物です。"
   :twitter "esehara"
   :blog "http://bugrammer.g.hatena.ne.jp/nisemono_san/" 
   :blog-name "蟲！虫！蟲！" 
   :e-mail "esehara at gmail dot com"})

(def site-configure 
  {:title "似非原Profile"
   :person whoisyou})


(defn view-template [& content]
  "Add HTML header and fooder"
  (html (doctype :xhtml-strict) (xhtml-tag "ja" 
    [:head
     [:meta {:http-equiv "Content-type"
             :content "text/html;charset=utf-8"}]
     [:link {:rel "stylesheet" :href "main.css"}]
     [:script {:src "scroll.js"}]
     [:script {:src "jquery.min.js"}]
     [:title (:title site-configure)]]
     [:body content])))

;;person-div
(defn person-div []
  "Personal Data Div"
  (html 
             [:h2 (:name (:person site-configure))]
             [:p (:description (:person site-configure))]
             [:p [:b "Twitter:"] [:a {:href (str "http://twitter.com/" (:twitter (:person site-configure)))} (str "@" (:twitter (:person site-configure)))]] )
  )
;;galary
(defn img-gallely [gallelypass]
  "Image gallely (img)"
  (apply str (map #(html [:img {:src (str "./gallery-icon/" %)}])
                  (map last (rest 
                              (map #(split #"/" %) 
                                   (map #(.getPath %) (file-seq (File. gallelypass )
                                                                ))))))))

;;page index
(defn index []
  "index page"
  (view-template
    [:div {:id "main"}
      [:div {:id "header"}
        [:h1 {:id "header"} (:title site-configure)]
      ]
      [:div {:id "nav"}]
      [:div {:id "content"}
        [:div {:id "person"} (person-div)]
      [:div {:id "gallery-out"}
      [:div {:id "gallery" :style "position:relative"}
       [:div {:style "float:left;width:20%;background-color:#DDD;height:100%;font-size:1.2em;"} "Icons" ]
       [:div {:style "float:right;width:80%;"}
        [:div {:style "background-color:white;border:solid 1px black;padding 2px;"} (img-gallely "./public/gallery-icon/")]]
      [:img {:id "icon" :src "./img/grobot.png"}]
      ]]]
      [:div {:id "footer"}]
    ]
    ))
