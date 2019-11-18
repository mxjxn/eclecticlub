(ns mxjxn.utils)

(defn p [& args]
  (println "\n")
  (doseq [i args] (println i))
  (println "\n"))
