(ns user
  (:require [mount.core :as mount]
            [stevechan.figwheel :refer [start-fw stop-fw cljs]]
            stevechan.core))

(defn start []
  (mount/start-without #'stevechan.core/repl-server))

(defn stop []
  (mount/stop-except #'stevechan.core/repl-server))

(defn restart []
  (stop)
  (start))


