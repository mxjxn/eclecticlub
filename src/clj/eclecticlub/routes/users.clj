(ns eclecticlub.routes.users
  (:require
   [mxjxn.utils :refer [p]]
   [eclecticlub.middleware :refer [basic-auth auth]]
   [eclecticlub.db.users :as users]
   [ring.util.http-response :refer :all]))

(defn login-handler [{{{:keys [email password]} :body} :parameters}]
  (users/login-user! email password)
  (ok {:body "what-up"}))

(defn signup-handler [{{{:keys [email password username] :as creds } :body} :parameters}]
  (let [result (users/create-user! creds)]
    (condp = (:status result)
      :success (ok result)
      :exists (not-found result))))

(defn user-routes []
  ["/user"
   {:swagger {:tags ["users"]}}
   ["/login"
    {:post
     {:summary "authenticate user"
      :middleware [(basic-auth) auth]
      :handler login-handler
      :parameters {:body {:email string? :password string?}}}}]

   ["/signup"
    {:post
     {:summary "create user"
      :handler signup-handler
      :parameters {:body {:email string? :password string? :username string?}}}}]
   ])
