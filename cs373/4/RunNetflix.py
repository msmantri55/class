#!/lusr/bin/python

'''
Taylor Fausak and Kimberly Lee
CS 373 Project 4
March 7, 2010
'''

import os
import sys
import Netflix

def main ():
	# Get the path to Netflix data from the command line
	try:
		directory = sys.argv[1]
		directory = os.path.abspath(directory)
	except IndexError:
		print 'Usage: RunNetflix.py path_to_netflix_data'
		exit(1)
	
	customers = {}
	movies = {}
	precision = 10
	
	# Get all the files in the training set
	training_set = os.path.join(directory, 'training_set/')
	files = os.listdir(training_set)

	# Parse all the training data
	for file in files:
		file = os.path.join(training_set, file)
		reader = open(file, 'r')
		movie, average = Netflix.parse_training_data(reader, customers)
		reader.close()

		movies[movie] = average
	average = Netflix.average_customer_rating(customers)

	file = os.path.join(directory, 'probe.txt')
	reader = open(file, 'r')
	for line in reader:
		if line[-2] == ':':
			movie = line[:-2]
			print movie + ':'
		else:
			customer = line[:-1]
			rating = Netflix.predict_rating(average, movies[movie], customers[customer])
			print '{0:.{1}f}'.format(rating, precision)
	reader.close()

if __name__ == '__main__':
	main()
