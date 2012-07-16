#!/lusr/bin/python

# python RunVoting.py < RunVoting.in > RunVoting.out

import sys
from Voting import read, Election

def main():
	numElections = read(sys.stdin)

	while numElections > 0:
		election = Election()
		election.read(sys.stdin)
		election.evaluate()
		print election

		numElections -= 1

if __name__ == '__main__':
	main()
