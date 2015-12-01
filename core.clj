(ns cards-clojure.core
  (:gen-class))

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))
(def rank-names {1 :ace, 11 :jack, 12 :queen, 13 :king})


(defn create-deck []
  (set (for [suit suits
             rank ranks]
         {:suit suit
          :rank (get rank-names rank rank)})))

(defn create-hands [deck]
  (set (for [c1 deck
             c2 (disj deck c1)
             c3 (disj deck c1 c2)
             c4 (disj deck c1 c2 c3)]
         #{c1 c2 c3 c4})))

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn straight? [hand]
  (=1 (count (set (map :rank hand)))))

(defn straight-flush? [hand]
  (and (straight? hand (flush? hand))))

(defn four-of-a-kind? [hand]
  (== (apply max (vals (frequencies (map :rank hand)))) 4))

(defn three-of-a-kind? [hand]
  (== (apply max (vals (frequencies (map :rank hand)))) 3))
  )

(defn two-pair? [hand]
  (= '(2 2) (take 2 (most-popular-members hand))))



(defn -main [& args]
  (let [deck (create-deck)
        hands (create-hands deck)
        hands (filter flush? hands)
        hands (filter straight-flush? hands)]
    (println (count hands))))
