# TodoMVC done with re-frame 对比学习 只是用Reagent的TodoMVC

## 1. subscribe: 订阅form表单的输入数据,订阅@API的数据

```clojure
  (r/with-let [date-selection ;; subscribe订阅的是form表单的输入数据, `@`表单的输入数据
               (subscribe [::form/get-form ::date-select
                           {:default          {:start (jsdate->str (js/Date.) true)
                                               :end   (jsdate->str (js/Date.) true)}
                            :submit-on-change true
                            :callback         (fn [{:keys [start end filter-text]}]
                                                (when (and start end)
                                                  (dispatch [::table-data/set-query
                                                             ::ledger-report
                                                             {:start       start
                                                              :end         end
                                                              :filter-text filter-text}])))}])

               ;; ledger-details 是API的返回数据, group和api都是代表API的路径, subscribe订阅`@`了API的数据
               ledger-details (subscribe [::table-data/get-data ::ledger-report
                                          {:group       :clinic-ledger
                                           :api         :ledger-report
                                           :no-padding? true
                                           :data        {:start  (jsdate->str (js/Date.) true)
                                                         :end    (jsdate->str (js/Date.) true)}}])]
   ... ...)

;; ledger-details返回API的数据示例:
{:values ({...}, {...}...), :timestamp #object[Object 20171109T150523], :paging {:page 0, :page-size 15}, :request-data {:start "2017-09-06", :end "2017-11-09", :filter-text nil}, :in-flight? false, :completed-request-data {:start "2017-09-06", :end "2017-11-09", :filter-text nil}}
```
## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run

## License

Copyright © 2017 FIXME
