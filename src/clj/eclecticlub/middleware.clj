(ns eclecticlub.middleware
  (:require
    [eclecticlub.env :refer [defaults]]
    [eclecticlub.config :refer [env]]
    [mxjxn.utils :refer [p]]
    [eclecticlub.auth :as auth]
    [ring-ttl-session.core :refer [ttl-memory-store]]
    [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
    [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
    [buddy.auth.accessrules :refer [restrict]]
    [buddy.auth :refer [authenticated?]]
    [ring.util.http-response :refer :all]
    [buddy.auth.backends.session :refer [session-backend]]))

(defn on-error [request response]
  {:status 403
   :headers {}
   :body (str "Access to " (:uri request) " is not authorized")})

(defn wrap-restricted [handler]
  (restrict handler {:handler authenticated?
                     :on-error on-error}))

(defn wrap-auth [handler]
  (let [backend (session-backend)]
    (-> handler
        (wrap-authentication backend)
        (wrap-authorization backend))))

(defn auth [handler]
  (fn [request]
    (if (authenticated? request)
      (handler request)
      (unauthorized {:error "not authorized"}))))

(defn basic-auth [handler]
    (wrap-authentication handler (auth/basic-auth-backend)))

(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      wrap-auth
      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] false)
            (assoc-in  [:session :store] (ttl-memory-store (* 60 30)))))))
