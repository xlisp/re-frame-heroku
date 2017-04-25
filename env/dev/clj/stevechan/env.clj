(ns stevechan.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [stevechan.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[stevechan started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[stevechan has shut down successfully]=-"))
   :middleware wrap-dev})
