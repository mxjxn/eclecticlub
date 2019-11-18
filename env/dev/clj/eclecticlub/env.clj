(ns eclecticlub.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [eclecticlub.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[eclecticlub started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[eclecticlub has shut down successfully]=-"))
   :middleware wrap-dev})
