#!/usr/bin/env python

# --------------------------------------
# projects/python/collatz/TestCollatz.py
# Copyright (C) 2009
# Glenn P. Downing
# --------------------------------------

# To run the tests
#     TestCollatz.py

import unittest

from Collatz import collatz_read, collatz_eval, collatz_print

# ------
# Reader
# ------

class Reader (object) :
    def __init__ (self, s) :
        self.s = s

    def read (self) :
        return self.s

# ------
# Writer
# ------

class Writer (object) :
    def str (self) :
        return self.s

    def write (self, a, v) :
        self.s  = str(a[0]) + " "
        self.s += str(a[1]) + " "
        self.s += str(v)    + "\n"

# -----------
# TestCollatz
# -----------

class TestCollatz (unittest.TestCase) :
    # ----
    # read
    # ----

    def test_read (self) :
        r = Reader("1 10\n")
        a = []
        b = collatz_read(r, a)
        self.assert_(b    == True)
        self.assert_(a[0] ==  1)
        self.assert_(a[1] == 10)

    # ----
    # eval
    # ----

    def test_eval_1 (self) :
        v = collatz_eval([1, 10])
        self.assert_(v == 20)

    def test_eval_2 (self) :
        v = collatz_eval([100, 200])
        self.assert_(v == 125)

    def test_eval_3 (self) :
        v = collatz_eval([201, 210])
        self.assert_(v == 89)

    def test_eval_4 (self) :
        v = collatz_eval([900, 1000])
        self.assert_(v == 174)

    def test_eval_5 (self) :
        v = collatz_eval([10, 1])
        self.assert_(v == 20)

    def test_eval_6 (self) :
        v = collatz_eval([200, 100])
        self.assert_(v == 125)

    def test_eval_7 (self) :
        v = collatz_eval([210, 201])
        self.assert_(v == 89)

    def test_eval_8 (self) :
        v = collatz_eval([419839, 419839])
        self.assert_(v == 162)

    def test_eval_9 (self) :
        v = collatz_eval([1, 1000000])
        self.assert_(v == 525)

    def test_eval_10 (self) :
        v = collatz_eval([-1, 1])
        self.assert_(v == 0)

    # -----
    # print
    # -----

    def test_print (self) :
        w = Writer()
        collatz_print(w, [1, 10], 20)
        self.assert_(w.str() == "1 10 20\n")

if __name__ == "__main__" :
    print "TestCollatz.py"
    unittest.main()
    print "Done."
