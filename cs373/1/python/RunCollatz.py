#!/usr/bin/env python

# -------------------------------------
# projects/python/collatz/RunCollatz.py
# Copyright (C) 2010
# Glenn P. Downing
# -------------------------------------

# To run the program
#     RunCollatz.py < RunCollatz.in > RunCollatz.out

# To document the program
#     pydoc -w Collatz RunCollatz TestCollatz

from Collatz import collatz_read, collatz_eval, collatz_print

# ------
# Reader
# ------

class Reader (object) :
    def read (self) :
        return raw_input()

# ------
# Writer
# ------

class Writer (object) :
    def write (self, a, v) :
        for i in a :
            print i,
        print v

# ----
# main
# ----

def main () :
    """
    runs the program
    """
    a = []
    while collatz_read(Reader(), a) :
        v = collatz_eval(a)
        collatz_print(Writer(), a, v)

if __name__ == "__main__" :
    main()