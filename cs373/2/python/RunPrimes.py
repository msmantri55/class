#!/lusr/bin/python

# python RunPrimes.py < RunPrimes.in > RunPrimes.out

import sys
from Primes import primes_read, primes_print, primes_eval

def main ():
	value = [0]

	while primes_read(sys.stdin, value):
		primes_print(sys.stdout, primes_eval(value[0]))

if __name__ == '__main__':
	main()
