#!/lusr/bin/python

import StringIO
import unittest
import Netflix

class TestNetflix (unittest.TestCase):
	# One customer, one rating
	def test_average_customer_rating_1 (self):
		customers = {'1' : [3, 1]}
		Netflix.average_customer_rating(customers)
		self.assert_(len(customers) == 1)
		self.assert_(customers == {'1' : 3.0})
	
	# One customer, many ratings
	def test_average_customer_rating_2 (self):
		customers = {'1' : [15, 5]}
		Netflix.average_customer_rating(customers)
		self.assert_(len(customers) == 1)
		self.assert_(customers == {'1' : 3.0})
	
	# Many customer, one rating each
	def test_average_customer_rating_3 (self):
		customers = {'1' : [5, 1], '2' : [2, 1], '3' : [3, 1], '4' : [4, 1], '5' : [1, 1]}
		Netflix.average_customer_rating(customers)
		self.assert_(len(customers) == 5)
		self.assert_(customers == {'1' : 5.0, '2' : 2.0, '3' : 3.0, '4' : 4.0, '5': 1.0})
	
	# Many customer, many ratings each
	def test_average_customer_rating_4 (self):
		customers = {'1' : [10, 2], '2' : [4, 2], '3' : [6, 2], '4' : [8, 2], '5' : [2, 2]}
		Netflix.average_customer_rating(customers)
		self.assert_(len(customers) == 5)
		self.assert_(customers == {'1' : 5.0, '2' : 2.0, '3' : 3.0, '4' : 4.0, '5': 1.0})
	
	# One customer, many ratings, non integer mean)
	def test_average_customer_rating_5 (self):
		customers = {'1' : [7, 2]}
		Netflix.average_customer_rating(customers)
		self.assert_(len(customers) == 1)
		self.assert_(customers == {'1' : 3.5})
	
	# One customer
	def test_parse_training_data_1 (self):
		reader = StringIO.StringIO('1:\n1,1,1111-11-11\n')
		customers = {}
		movie, average = Netflix.parse_training_data(reader, customers)
		self.assert_(movie == '1')
		self.assert_(average == 1.0)
		self.assert_(customers == {'1' : [1, 1]})
	
	# Many customers
	def test_parse_training_data_2 (self):
		reader = StringIO.StringIO('1:\n1,1,1111-11-11\n2,2,2222-22-22\n')
		customers = {}
		movie, average = Netflix.parse_training_data(reader, customers)
		self.assert_(movie == '1')
		self.assert_(average == 1.5)
		self.assert_(customers == {'1' : [1, 1], '2' : [2, 1]})

if __name__ == '__main__':
	unittest.main()
