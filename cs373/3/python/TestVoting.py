#!/lusr/bin/python

# python TestVoting.py

import StringIO
import unittest

from Voting import Ballot, Candidate, Election

class TestVoting (unittest.TestCase):
	# Blank input
	def test_ballot1 (self):
		ballot = Ballot('')
		self.assert_(str(ballot) == '[]')

	# Single input
	def test_ballot2 (self):
		ballot = Ballot('2')
		self.assert_(str(ballot) == '[2]')
		self.assert_(ballot.getVote() == 2)

	# Multiple input
	def test_ballot3 (self):
		ballot = Ballot('2 3 5 7')
		self.assert_(str(ballot) == '[2, 3, 5, 7]')
		self.assert_(ballot.getVote() == 2)
		self.assert_(ballot.getVote() == 3)
		self.assert_(ballot.getVote() == 5)
		self.assert_(ballot.getVote() == 7)

	# White space in input
	def test_ballot4 (self):
		ballot = Ballot(' 2  3\t5\t\t7 \t')
		self.assert_(str(ballot) == '[2, 3, 5, 7]')
		self.assert_(ballot.getVote() == 2)
		self.assert_(ballot.getVote() == 3)
		self.assert_(ballot.getVote() == 5)
		self.assert_(ballot.getVote() == 7)
	
	# Blank input
	def test_candidate1 (self):
		candidate = Candidate('')
		self.assert_(str(candidate) == '')
		self.assert_(candidate.numVotes() == 0)
	
	# No ballots
	def test_candidate2 (self):
		candidate = Candidate('John Doe')
		self.assert_(str(candidate) == 'John Doe')
		self.assert_(candidate.numVotes() == 0)
	
	# One ballot
	def test_candidate3 (self):
		candidate = Candidate('John Doe')
		candidate.add(Ballot(''))
		self.assert_(str(candidate) == 'John Doe')
		self.assert_(candidate.numVotes() == 1)
	
	# Multiple ballots
	def test_candidate4 (self):
		candidate = Candidate('John Doe')
		candidate.add(Ballot(''))
		candidate.add(Ballot(''))
		candidate.add(Ballot(''))
		self.assert_(str(candidate) == 'John Doe')
		self.assert_(candidate.numVotes() == 3)
	
	# readCandidates: blank input
	def test_election1 (self):
		reader = StringIO.StringIO('')
		election = Election()
		election.readCandidates(reader)
		self.assert_(len(election.candidates) == 0)
	
	# readCandidates: single input
	def test_election2 (self):
		reader = StringIO.StringIO('1\nJohn Doe\n')
		election = Election()
		election.readCandidates(reader)
		self.assert_(len(election.candidates) == 1)
		self.assert_(str(election.candidates[0]) == 'John Doe')
	
	# readCandidates: multiple input
	def test_election3 (self):
		reader = StringIO.StringIO('2\nJohn Doe\nJane Smith\n')
		election = Election()
		election.readCandidates(reader)
		self.assert_(len(election.candidates) == 2)
		self.assert_(str(election.candidates[0]) == 'John Doe')
		self.assert_(str(election.candidates[1]) == 'Jane Smith')
	
	# readBallots: blank input
	def test_election4 (self):
		reader = StringIO.StringIO('')
		election = Election()
		election.readBallots(reader)
		self.assert_(len(election.ballots) == 0)
		self.assert_(election.ballots == [])
	
	# readBallots: single input
	def test_election5 (self):
		reader = StringIO.StringIO('1 2 3 4\n')
		election = Election()
		election.readBallots(reader)
		self.assert_(len(election.ballots) == 1)
		self.assert_(str(election.ballots[0]) == '[1, 2, 3, 4]')
	
	# readBallots: multiple input
	def test_election6 (self):
		reader = StringIO.StringIO('1 2 3 4\n2 3 5 7\n')
		election = Election()
		election.readBallots(reader)
		self.assert_(len(election.ballots) == 2)
		self.assert_(str(election.ballots[0]) == '[1, 2, 3, 4]')
		self.assert_(str(election.ballots[1]) == '[2, 3, 5, 7]')
	
	# read: simple input
	def test_election7 (self):
		reader = StringIO.StringIO('2\nJohn Doe\nJane Smith\n1 2 3 4\n2 3 5 7\n')
		election = Election()
		election.read(reader)
		self.assert_(len(election.candidates) == 2)
		self.assert_(str(election.candidates[0]) == 'John Doe')
		self.assert_(str(election.candidates[1]) == 'Jane Smith')
		self.assert_(len(election.ballots) == 2)
		self.assert_(str(election.ballots[0]) == '[1, 2, 3, 4]')
		self.assert_(str(election.ballots[1]) == '[2, 3, 5, 7]')

if __name__ == '__main__':
	unittest.main()
