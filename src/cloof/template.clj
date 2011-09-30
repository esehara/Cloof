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

(def worklist
  '(
    ["R.I.P esehara" "http://r-i-p-e-s-e-h-a-r-a.appspot.com/"
     "Google App Engine for Javaで作成。基本的にJavaの勉強の代わりに作られたもの。Twitterにおいて、任意に選ばれた発言を、他人のアカウントでつぶやき続けるというもの。"]
    ["悲しみよ、こんにちは" "http://esehara.dotera.net/Study/html5/canvas.html"
     "Twitterの発言から「死にたい」を検索し、表示する。HTML5(canvas) + Javascript(jQuery)の勉強のために作ったサイト。"]
    ["ROBOTS" "http://www.pyweek.org/e/esehara/" "Pyweekと呼ばれるPythonのゲームプログラミングに参加したときに作ったもの。とはいえ、殆どの挙動は知人のプログラムが作ったものであり、ゲーム的に面白いか？といえば……。Python製。"]
    ["aNegi" "https://github.com/esehara/aNegi" "『ねぎ姉さん』のサイトにアップされている四コマを見るためのアンドロイドアプリ。サンプルを製作する程度に作ったもの。Java製。"]
    )
)

(def bloglist
  '(
    ["微力" "http://b.hatena.ne.jp/nisemono_san/" "はてなブックマークです。自分が興味を持った話題は、できるだけこちらに集約させるようにしています。"]
    ["蟲！虫！蟲！" "http://bugrammer.g.hatena.ne.jp/nisemono_san/" "プログラム学習サイト。自分の学習目的のため、間違いも色々とあるようす。"]
    ["行乞記" "http://fragments.g.hatena.ne.jp/nisemono_san/" "自分が日々思っていたことをメモするためのサイト。"]
    ["SoundCloud - esehara" "http://soundcloud.com/esehara" "過去に自分が作った音楽みたいなもののリストです。"]
    ["Sayonara Records" "http://sayonararecords.tumblr.com/" "無料で音楽配信を行うネットレーベル。Tumblr上に存在。"]
    ["似非原路上日記。" "http://togetter.com/li/60272" "ホームレス時代の記憶。"]
    ["Favstar.fm - esehara" "http://favstar.fm/users/esheara" "過去に自分がつぶやいたもの。"]
    )
)

(def gistlist
  '(
    ["Nise Hack!!" "http://bugrammer.g.hatena.ne.jp/nisemono_san/20110919/1316400120" "Javascript、特にブックマークレットを使用した場合における。あたかもサイトが映画に出てくるようなハッカーのサイトのように表示される。"]
    ["社会学評論Download" "https://gist.github.com/1088146" "社会学評論で公開されている過去の記事を全てダウンロードするスクリプト。Python製。"]
    ["Aozora Author 2 epub" "https://gist.github.com/1101065" "そういえば、青空文庫って作品別のEpubはあるけれど、作者の著作全てをまとめてEpubにしたものはなかったな、ということで作成。実際の挙動はXHTMLで公開されているものだけを集めてくるという、至極手抜きのスクリプト。Python製"]
    )
)

(def nikolist
  '(
    ["眠れないので作っていたら「作者は病気」っぽくなったゲーム" "http://www.nicovideo.jp/watch/sm6416309" "自分が実家で引きこもっていたときに作られたゲーム。グロテスクで怖い雰囲気なので、苦手な人は苦手かもしれません。このゲームは『AzDesignADV』というソフトで作られています。"]
    ["途中まで作った「作者は病気」っぽいゲームを自分でプレイ" "http://www.nicovideo.jp/watch/sm6300734" "これも上と同じようなゲームです。これも、グロテスクで怖い感じです。製作ツールは、「Game Maker 7.0」です。海外では結構有名なツールのようです。"]
    )
)

(def iroiro
  '(["好きな言語はなんですか？" "Python、Javascript、Clojure辺りですね。Pythonは書いていてスッキリしますね。JavascriptはPrototype型のオブジェクト指向で、他のオブジェクト指向言語とはまた違った発想をしなきゃいけないことが面白いですね。Clojureは最近始めたばっかりで、Lispを勉強するがてら始めたのですが、Lispの表現がこれほどまでに強力であるとは知りませんでした。触っていて気もちいい言語の一つです。"]
    ["何に興味がありますか？" "元々は社会学を勉強していました。その一方で、音楽を作ったり、絵を描いたりすることにも興味があったりします。個人的には、ドット絵が好みです。最近では「技術と人間は如何に関わるのか」「人間は如何にして情報を得られるのか」の観点から、プログラミング等の勉強を行っています。"])
  )

(def thankslist
  '(["Free Material Makochin" "http://members3.jcom.home.ne.jp/i.makochin/" "素材サイト。切り取り線はここから頂きました。ありがとうございます。"])
  )

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
