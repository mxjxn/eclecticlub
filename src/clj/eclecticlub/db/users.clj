(ns eclecticlub.db.users
  (:require
   [mxjxn.utils :refer [p]]
   [eclecticlub.db.core :as db]
   [buddy.hashers :as h]
   [buddy.sign.jwt :as jwt])
  (:import (java.util UUID)))

(defn- encrypt-password [pw] (h/encrypt pw))

(defn create-uuid [email]
  (let [user-bytes (.getBytes (str (.toString email)))]
    (UUID/nameUUIDFromBytes user-bytes)))

(defn create-user! [{:keys [email password username]}]
  (let [user-uuid (create-uuid email)
        hashed-pass (encrypt-password password)
        user-map {:username username
                  :uuid user-uuid
                  :email email
                  :password hashed-pass}
        result (db/create-user! user-map)]
    (if result
      {:status :success}
      {:status :exists})))

(defn login-user! [{:keys [email password]}]
  (let [result (db/login-user! email password)]
    {:status :success :result-of-login result}))
