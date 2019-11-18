(ns eclecticlub.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[eclecticlub started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[eclecticlub has shut down successfully]=-"))
   :middleware identity})
