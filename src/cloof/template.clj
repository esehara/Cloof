(ns cloof.template
    (:import (java.io File))
    (:use
        [hiccup.core]
        [hiccup.page-helpers]
        [cloof.prof]
        [clojure.contrib.string :only (split)]))


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
             [:img {:src "./img/myface.jpg" :style "float:left;margin-right:30px"}]
             [:p (:description (:person site-configure))]
             [:p [:b "Twitter:"] [:a {:href (str "http://twitter.com/" (:twitter (:person site-configure)))} (str "@" (:twitter (:person site-configure)))]]
             [:p [:b "E-mail:"] (:e-mail (:person site-configure))] 
    )
  )

;;galary
(defn img-gallely [gallelypass]
  "Image gallely (img)"
  (apply str (map #(html [:img {:src (str "./gallery-icon/" %)}])
                  (map last (rest 
                              (map #(split #"/" %) 
                                   (map #(.getPath %) (file-seq (File. gallelypass )
                                                                ))))))))
(defn parse-sitelist [fseq]
  (apply str
         (map #(html [:h3 [:a {:href (second %)} (first %)]][:p (last %)]) fseq)
         ))

(defn parse-etc [fseq]
  (apply str
         (map #(html [:h3 (first %)][:p {:style "font-size:0.8em"}(last %)]) fseq)
         ))

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
        [:div {:id "person" :style "margin-bottom:120px;"} (person-div)]
      [:div {:id "gallery-out"}
       [:div {:id "gallery" :class "columbase" :style "position:relative"}
        [:div {:style "float:left;width:20%;background-color:#DDD;height:100%;font-size:1.2em;"} "Icons" ]
        [:div {:style "float:right;width:80%;"}
         [:div {:style "background-color:white;border:solid 1px black;padding 2px;"} (img-gallely "./public/gallery-icon/")]]
         [:img {:id "icon" :src "./img/grobot.png"}]
        ]]
      [:hr]
      [:div {:id "works" :class "columbase"}
       [:div {:id "left"} 
        [:h2 "作ったもの"]
        (parse-sitelist worklist)
        [:hr]
        [:h2 "Gistに公開したもの"]
        (parse-sitelist gistlist)
        ]
       [:div {:id "center"}
        [:h2 "Blog"]
        (parse-sitelist bloglist)
        [:hr]
        [:h2 "ニコニコ動画のもの"]
        (parse-sitelist nikolist)
        ]
       [:div {:id "right"}
        [:h2 "瑣末なこと"] 
        (parse-etc iroiro)
        [:hr]
        [:h2 "Thanks"]
        (parse-sitelist thankslist)
       ]]]
      [:div {:id "footer"}]
    ]
    ))
