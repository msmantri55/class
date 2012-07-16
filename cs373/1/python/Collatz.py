#!/usr/bin/env python

# ----------------------------------
# projects/python/collatz/Collatz.py
# Copyright (C) 2010
# Glenn P. Downing
# ----------------------------------
        
# ------------
# collatz_read
# ------------

def collatz_read (r, a) :
    """
    reads an int into a[0] and a[1]
    return true if that succeeds, false otherwise
    """
    try :
        s = r.read()
    except EOFError :
        return False
    assert type(s) is str
    l = s.split()
    assert type(l) is list
    a[0:] = [int(l[0])]
    a[1:] = [int(l[1])]
    return True
    
# -------------
# collatz_print
# -------------

def collatz_print (w, a, v) :
    """
    prints the values of a[0], a[1], and v
    """
    w.write(a, v)

# ------------
# collatz_eval
# ------------

def collatz_eval (bounds) :
    """
    Computes the maximum cycle length in a range
    return maximum cycle length within the bounds, zero on failure
    """
    for n in bounds :
        if n < 1 :
            return 0

    maximum = 0
    cache = dict()

    for n in range(20) :
        cache[1 << n] = n + 1

    for n in range(min(bounds), max(bounds) + 1) :
        cycle_length = collatz_get_cycle_length(n, cache)

        assert cycle_length > 0

        maximum = max(maximum, cycle_length)

    assert maximum > 0

    return maximum

# ------------------------
# collatz_get_cycle_length
# ------------------------

def collatz_get_cycle_length (n, cache) :
    """
    Computes the cycle length.
    return cycle length
    """
    assert n > 0

    cycle_length = 1;
    m = n

    while m != 1 :
        if m & 1 == 1 :
            m += 1 + m >> 1
            cycle_length += 1
        else :
            m >>= 1

        if m in cache :
            cycle_length += cache[m]
            break;

        cycle_length += 1

    assert cycle_length > 0

    cache[n] = cycle_length

    return cycle_length
