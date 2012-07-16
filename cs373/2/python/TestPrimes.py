#!/lusr/bin/python

# python TestPrimes.py

import StringIO
import unittest

from Primes import primes_read, primes_print, primes_eval, primes_is_prime

class TestPrimes (unittest.TestCase):
	# test_read ------------------------------------------------------------

	# Can it deal with typical input?
	def test_read1 (self):
		reader = StringIO.StringIO('8\n')
		value = [0]
		result = primes_read(reader, value)
		self.assert_(result == True)
		self.assert_(value[0] == 8)

	# Can it deal with zeros?
	def test_read2 (self):
		reader = StringIO.StringIO('0\n')
		value = [0]
		result = primes_read(reader, value)
		self.assert_(result == True)
		self.assert_(value[0] == 0)

	# Can it deal with huge numbers?
	def test_read3 (self):
		reader = StringIO.StringIO('10000000\n')
		value = [0]
		result = primes_read(reader, value)
		self.assert_(result == True)
		self.assert_(value[0] == 10000000)

	# Can it deal with negative numbers?
	def test_read4 (self):
		reader = StringIO.StringIO('-1\n')
		value = [0]
		result = primes_read(reader, value)
		self.assert_(result == True)
		self.assert_(value[0] == -1)

	# Can it deal with white space?
	def test_read5 (self):
		reader = StringIO.StringIO(' \t1 \t\n')
		value = [0]
		result = primes_read(reader, value)
		self.assert_(result == True)
		self.assert_(value[0] == 1)

	# test_print -----------------------------------------------------------

	# Can it deal with typical input?
	def test_print1 (self):
		writer = StringIO.StringIO()
		primes_print(writer, [2, 3, 5, 7])
		self.assert_(writer.getvalue() == '2 3 5 7\n')

	# Can it deal with huge numbers?
	def test_print2 (self):
		writer = StringIO.StringIO()
		primes_print(writer, [10000000, 3, 5, 7])
		self.assert_(writer.getvalue() == '10000000 3 5 7\n')

	# Can it deal with impossible input?
	def test_print3 (self):
		writer = StringIO.StringIO()
		primes_print(writer, [])
		self.assert_(writer.getvalue() == 'Impossible.\n')

	# test_eval ------------------------------------------------------------

	# Can it deal with typical input?
	def test_eval1 (self):
		primes = primes_eval(17)
		self.assert_(primes == [2, 3, 5, 7])

	# Can it deal with huge numbers?
	def test_eval2 (self):
		primes = primes_eval(10000000)
		self.assert_(primes == [2, 2, 5, 9999991])

	# Can it deal with input that fails?
	def test_eval3 (self):
		primes = primes_eval(7)
		self.assert_(primes == [])

	# Can it deal with negative input?
	def test_eval4 (self):
		primes = primes_eval(-1)
		self.assert_(primes == [])

	# test_is_prime --------------------------------------------------------

	# Can it deal with typical prime input?
	def test_is_prime1 (self):
		self.assert_(primes_is_prime(2) == True)

	# Can it deal with typical composite input?
	def test_is_prime2 (self):
		self.assert_(primes_is_prime(4) == False)

if __name__ == '__main__':
	unittest.main()
