(ns stevechan.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[stevechan started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[stevechan has shut down successfully]=-"))
   :middleware identity})
