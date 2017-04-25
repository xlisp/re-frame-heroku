(ns stevechan.core
  (:require [stevechan.handler :as handler]
            [luminus.repl-server :as repl]
            [luminus.http-server :as http]
            [luminus-migrations.core :as migrations]
            [stevechan.config :refer [env]]
            [clojure.tools.cli :refer [parse-opts]]
            ;;[clojure.tools.logging :as log]
            [mount.core :as mount]
            [clojure.java.jdbc :as j]
            [honeysql.core :as sql]
            [honeysql.helpers :as q]
            [taoensso.timbre :as log])
  (:gen-class))

(def jim-db
  {:subprotocol "postgres", :subname "//ec2-54-83-198-111.compute-1.amazonaws.com:5432/d51b4it8f1v046", :user "arwaolgvqubsma", :password "r5kogSLuB7l53w9Cqm-iAWYt2S"})

;; org.postgresql.util.PSQLException: FATAL: no pg_hba.conf entry for host "114.244.149.38", user "arwaolgvqubsma", database "d51b4it8f1v046", SSL off
(defn sections-last
  []
  (j/query
   jim-db (-> (q/select :*) (q/from :sections) (q/limit 1) (sql/format))
   ))

(def cli-options
  [["-p" "--port PORT" "Port number"
    :parse-fn #(Integer/parseInt %)]])

(mount/defstate ^{:on-reload :noop}
                http-server
                :start
                (http/start
                  (-> env
                      (assoc :handler (handler/app))
                      (update :port #(or (-> env :options :port) %))))
                :stop
                (http/stop http-server))

(mount/defstate ^{:on-reload :noop}
                repl-server
                :start
                (when-let [nrepl-port (env :nrepl-port)]
                  (repl/start {:port nrepl-port}))
                :stop
                (when repl-server
                  (repl/stop repl-server)))


(defn stop-app []
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (shutdown-agents))

(defn start-app [args]
  (doseq [component (-> args
                        (parse-opts cli-options)
                        mount/start-with-args
                        :started)]
    (log/info component "started"))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app)))

(defn -main [& args]
  (println "====args===" args)
  (cond
    (some #{"migrate" "rollback"} args)
    (do
      (mount/start #'stevechan.config/env)
      (migrations/migrate args (select-keys env [:database-url]))
      (System/exit 0))
    (= (first args) "slast") (log/info "=====Result:" (sections-last))
    :else
    (start-app args)))
  
