import unittest

class SimpleTest (unittest.TestCase):
	def testName (self):
		self.assertTrue(False)

if __name__ == '__main__':
	unittest.main()