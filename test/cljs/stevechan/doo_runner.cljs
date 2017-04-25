(ns stevechan.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [stevechan.core-test]))

(doo-tests 'stevechan.core-test)

