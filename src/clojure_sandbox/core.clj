(ns clojure-sandbox.core 
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async])
  (:import (java.net ServerSocket)))

; ソケット通信
(def port 8080)

; input stream
(defn parse-input [in]
  (when-let [line (.readLine (io/reader in))]))

; fiszbuzz
(defn fizzbuzz
  [num]
  (cond 
    (= (mod num 15) 0) "FizzBuzz"
    (= (mod num 5) 0) "Fizz"
    (= (mod num 3) 0) "Buzz"
    :else (str num)))

(defn -main
  "http server写経するよ"
  [& args]
  ;(println 
  ;  (map fizzbuzz 
  ;    (range 1 (+ 1 (read-string (first args))))))
  (let [server-socket (new ServerSocket port)]
    (prn (str "hello http server" port "!"))
    (while true
      (let [socket (.accept server-socket)]
        (async/thread
          (with-open [s socket
                      in (io/input-stream (.getInputStream s))
                      out (io/output-stream (.getOutputStream s))]
            (prn (parse-input in))
          )
        )
      )
    )
  )
)

