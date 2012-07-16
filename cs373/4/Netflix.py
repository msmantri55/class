#!/lusr/bin/python

'''
Taylor Fausak and Kimberly Lee
CS 373 Project 4
March 7, 2010
'''

# Used to quickly map ratings to integers (faster than calling int())
ratings_map = {'1' : 1, '2' : 2, '3' : 3, '4' : 4, '5' : 5}

def average_customer_rating (customers):
	'''
	Calculates the average rating for all customers.
	@param customers a dict that maps customer IDs to a rolling sum of ratings and the number of ratings they've given
	@return the average rating for all customers (modifies the customers dict in place)
	'''
	S = N = A = 0

	for customer in customers:
		s = customers[customer][0]
		n = customers[customer][1]
		S += s
		N += n
		n = float(n) # Avoid integer division
		a = s / n
		assert 1.0 <= a <= 5.0
		customers[customer] = a
	
	N = float(N)
	A = S / N
	assert 1.0 <= A <= 5.0
	
	return A

def parse_training_data (reader, customers):
	'''
	Parses training data for the Netflix Prize. Calculates the average movie rating and tracks each rating by customer.
	@param reader a file reader, usually to 'mv_1234567.txt' or similar
	@param customers a dict that maps customer IDs to a rolling sum of ratings and the number of ratings they've given
	@return the movie ID and the average rating for that movie (modifies the customers dict in place)
	'''
	global ratings_map

	s = 0
	n = 0
	movie = reader.readline()
	movie = movie[:-2]

	for line in reader:
		# Don't convert customer ID to an integer. It's slower than string hashing.
		customer = line[:-14]
		rating = line[-13:-12]
		rating = ratings_map[rating]
		assert 1 <= rating <= 5

		# Calculate the average rating for this movie
		s += rating
		n += 1

		# Track each customer's ratings
		if customer not in customers:
			customers[customer] = [0, 0]
		customers[customer][0] += rating
		customers[customer][1] += 1

	n = float(n) # Avoid integer division
	average = s / n

	return movie, average

def predict_rating (average, movie, customer):
	'''
	@param average the average ratings for all movies
	@param movie the average rating given to this movie
	@param customer the average rating given by this customer
	@return a prediction of this customer's rating of this movie
	'''
	prediction = 0.476 * movie + 0.524 * customer

	return prediction
