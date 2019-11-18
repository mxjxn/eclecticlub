(ns eclecticlub.auth
  (:require
   [buddy.auth.backends :refer [jws]]
   [buddy.sign.jwt :as jwt]
   [mxjxn.utils :refer [p]]
   [eclecticlub.db.core :refer [get-user]]
   [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
   [buddy.hashers :as h]

   ))

(def secret "wow so secret")
;replace w (env :auth-key)
(def sign #(jwt/sign % secret {:alg :hs512}))
(def unsign #(jwt/unsign % secret {:alg :hs512}))

(defn create-token
  [user & {:keys [terse? valid-seconds]
           :or {terse? false
                valid-seconds 86400}}] ; one day
  (let [fields (if terse?
                 [:id]
                 [:id :email :username :user-data :permissions])
        payload (-> user
                    (select-keys fields)
                    (assoc :exp (.plusSeconds
                                 (java.time.Instant/now) valid-seconds)))]
    (sign payload)))

(defn basic-auth [request {:keys [email password]}]
  (let [user (get-user {:email email})]
    (if (and user (h/check password (:password user)))
      (-> user
          (dissoc :password)
          (assoc :token (create-token user)))
      false)))

(defn basic-auth-backend []
  (http-basic-backend {:authfn basic-auth}))
