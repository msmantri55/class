#!/lusr/bin/python

# python Voting.py
# pydoc -w Voting RunVoting TestVoting

def read (reader):
	'''
	Reads two lines. Gets a number from the first line.

	Returns the number of elections to process.
	'''
	line = reader.readline()
	reader.readline()

	try:
		elections = int(line)
	except ValueError:
		elections = 0

	return elections

class Election:
	'''
	An election using Australian voting.
	'''
	def __init__ (self):
		self.ballots = []
		self.candidates = []
		self.winners = []
	
	def __str__ (self):
		'''
		Returns the winner(s) of this election. One name per line.
		'''
		output = ''

		# '\n'.join(self.winners) gives a bunch of pointers
		for winner in self.winners:
			output += str(winner) + '\n'

		return output

	def read (self, reader):
		'''
		This method exists only for convenience.
		'''
		self.readCandidates(reader)
		self.readBallots(reader)

	def readCandidates (self, reader):
		'''
		Reads in a list of candidates. First line is assumed to be the number of
		candidates (i.e., the number of lines to eat).
		'''
		line = reader.readline()

		try:
			candidates = int(line)
		except ValueError:
			candidates = 0

		while (candidates > 0):
			line = reader.readline()
			line = line.strip() # Remove trailing '\n'
			candidate = Candidate(line)
			self.candidates.append(candidate)

			candidates -= 1

	def readBallots (self, reader):
		'''
		Reads in a list of ballots. Eats ballots until it hits a new
		line or the end of file.
		'''
		while (True): # Scary, but `do {..} while (..)` doesn't exist
			line = reader.readline()
			ballot = Ballot(line)

			if line == '\n' or line == '':
				break

			self.ballots.append(ballot)
	
	def evaluate (self):
		'''
		Determines the winner of the election.
		'''
		# Assign ballots to their first choice candidate.
		for ballot in self.ballots:
			vote = ballot.getVote()
			candidate = self.candidates[vote - 1]
			candidate.add(ballot)

		while (True):
			# Check for a clear winner
			majority = len(self.ballots) / 2
			for candidate in self.candidates:
				if candidate.numVotes() > majority:
					self.winners = [candidate]
					return self.winners

			# Check for an all-way tie
			votes = []
			for candidate in self.candidates:
				numVotes = candidate.numVotes()

				if numVotes != 0:
					votes.append(numVotes)
			if len(votes) > 0 and votes.count(votes[0]) == len(votes):
				# There is an all-way tie
				for candidate in self.candidates:
					if candidate.numVotes() != 0:
						self.winners.append(candidate)
				return self.winners

			# Find the least number of votes
			leastVotes = -1
			for candidate in self.candidates:
				numVotes = candidate.numVotes()

				if numVotes <= 0:
					continue

				if leastVotes == -1:
					leastVotes = numVotes
				else:
					leastVotes = min(leastVotes, numVotes)

			# Remove all candidates with the least number of votes
			ballots = []
			for candidate in self.candidates:
				if leastVotes == candidate.numVotes():
					ballots.extend(candidate.getBallots())
					candidate.remove()

			# Redistribute ballots from removed candidates
			for ballot in ballots:
				while (True):
					vote = ballot.getVote()
					candidate = self.candidates[vote - 1]

					if (candidate.numVotes() != 0):
						candidate.add(ballot)
						break

class Candidate:
	'''
	A candidate in an election. Has a name and an associated ballot.
	'''
	def __init__ (self, name):
		assert type(name) is str

		self.name = name
		self.ballots = []
	
	def __str__ (self):
		return self.name
	
	def add (self, ballot):
		'''
		Associates a ballot with this candidate.
		'''
		assert type(ballot) is type(Ballot(''))

		self.ballots.append(ballot)
	
	def getBallots (self):
		'''
		Returns all ballots associated with this candidate.
		'''
		return self.ballots
	
	def numVotes (self):
		'''
		Returns the number of votes this candidate has.
		'''
		return len(self.ballots)
	
	def remove (self):
		'''
		Removes all ballots from this candidate.
		'''
		self.ballots = []

class Ballot:
	'''
	A ballot in an election. Contains a list of integers represnting
	candidates.
	'''
	def __init__ (self, line):
		assert type(line) is str

		self.pointer = 0
		self.votes = []

		for vote in line.split():
			vote = int(vote)
			self.votes.append(vote)
	
	def __str__ (self):
		return str(self.votes)
	
	def getVote (self):
		pointer = self.pointer
		self.pointer += 1
		return self.votes[pointer]
