; patterns.lsp                         30 Oct 08; 06 Apr 09

; Data for CS 315 assignment 8 in Lisp

; (load "patm.lsp")    ; this file is needed also.

(defvar substitutions)
(setq substitutions '(
       ( (?function addnums) (?combine +) (?baseanswer (if (numberp tree) tree 0)))

       ; add to the following
       ( (?function countstrings) )
       ( (?function copytree) )
       ( (?function mintree) )
       ( (?function conses) )
       ))

; (trans '(+ z 0) 'optpats)
(redefpatterns 'optpats '(
       ( (+ ?x 0)   ?x)
       ( (+ 0 ?x)   ?x)
       ( (expt ?x 1) ?x)
       ; add more

       ))
; (trans '(deriv (+ z 3) z) 'derivpats)
(redefpatterns 'derivpats '(
       ( (deriv ?x ?x)   1)
       ( (deriv (+ ?u ?v) ?x)  (+ (deriv ?u ?x) (deriv ?v ?x)))
       ; add more

       ( (deriv ?y ?x)   0)   ; this must be last!
       ))

; For functions, do this set first, then the javapats.
; (trans '(setq i (+ i 1)) 'javarestructpats)
(redefpatterns 'javarestructpats '(
       ( (return (if ?test ?then)) (if ?test (return ?then)) )
       ( (return (if ?test ?then ?else)) (if ?test (return ?then) (return  ?else)) )
       ( (defun ?fun ?args ?code) (zdefun ?fun (arglist ?args) (progn (return ?code))) )
       ( (setq ?x (+ ?x 1)) (incf ?x) )
       ))

; (trans '(progn (setq j 3)) 'javapats)
(redefpatterns 'javapats '(
       ( (if ?test ?then) ("if (" " " ?test " " ")" #\Tab #\Return ?then))
       ( (< ?x ?y)  ("(" ?x " " < " " ?y ")"))
       ( (min ?x ?y) ("Math.min(" ?x "," " " ?y ")"))
       ( (cons ?x ?y) ("" cons "(" ?x "," " " ?y ")"))
       ( (zdefun ?fun ?args ?code) ("public static " ?fun " " ?args #\Return ?code #\Return) )
       ( (arglist (?x))   ("(" ?x ")"))
       ( (progn ?x) ("{" #\Tab #\Return ?x #\Return "}") )
       ( (setq ?x ?y) ("" ?x " " = " " ?y ";" #\Return) )
       ( (first ?x) ("" first "(" "(" Cons ")" ?x) )
       ; add more

       ( (?fun ?x)   ("" ?fun "(" ?x ")"))  ; must be last
       ))

(defvar eqnsbat)
(setq eqnsbat '((= loss_voltage (* internal_resistance current))
                   (= loss_power (* internal_resistance (expt current 2)))
                   (= terminal_voltage (- voltage loss_voltage))
                   (= power (* terminal_voltage current))
                   (= work (* charge terminal_voltage)) ) )

(defvar databat)
(setq databat '((current 0.3)(internal_resistance 4.0)(voltage 12.0)) )

(defvar eqnscirc)
(setq eqnscirc '((= acceleration (/ (expt velocity 2) radius))
                   (= force        (* mass acceleration))
                   (= kinetic_energy   (* (/ mass 2) (expt velocity 2)))
                   (= moment_of_inertia (* mass (expt radius 2)))
                   (= omega (/ velocity radius))
                   (= angular_momentum (* omega moment_of_inertia)) ))

(defvar datacirc)
(setq datacirc '((radius 4.0)(mass 2.0)(velocity 3.0)) )

(defvar eqnslens)
(setq eqnslens '((= focal-length (/ radius 2))
                   (= (/ 1 focal_length) (+ (/ 1 image_distance) (/ 1 subject_distance)))
                   (= magnification (- (/ image_distance subject_distance)))
                   (= image_height (* magnification subject_height)) ))

(defvar datalens)
(setq datalens '((subject_distance 6.0)(focal_length 9.0)) )
 
(defvar eqnslift)
(setq eqnslift '((= gravity     9.80665 )
                   (= weight      (* gravity mass))
                   (= force       weight)
                   (= work        (* force height))
                   (= speed       (/ height time))
                   (= power       (* force speed))
                   (= power       (/ work time)) ) )

(defvar datalift)
(setq datalift '((weight 700.0)(height 8.0)(time 10.0)) )

(defvar opttests)
(setq opttests '(
          (+ 0 foo)
          (* fum 1)
          (- (- y))
          (- (- x y))
          (+ foo (* fum 0))
          ))

(defvar derivtests)
(setq derivtests '(
          (deriv x x)
          (deriv 3 x)
          (deriv z x)
          (deriv (+ x 3) x)
          (deriv (* x 3) x)
          (deriv (* 5 x) x)
          (deriv (sin x) x)
          (deriv (sin (* 2 x)) x)
          (deriv (+ (expt x 2) (+ (* 3 x) 6)) x)
          (deriv (sqrt (+ (expt x 2) 2)) x)
          (deriv (log (expt (+ 1 x) 3)) x)
          ))


; (cpr (trans '(progn (setq j 3)) 'javapats))

(defvar javatests)
(setq javatests '(
          (* fum 7)
          (setq x y)
          (setq x (+ x 1))
          (setq area (* pi (expt r 2)))
          (if (> x 7) (setq y x))
          (if (or (> x 7) (< y 3)) (setq y x))
          (if (and (> x 7) (not (< y 3))) (setq y x))
          (defun factorial (n) (if (<= n 1) 1 (* n (factorial (- n 1)))))
          ))
