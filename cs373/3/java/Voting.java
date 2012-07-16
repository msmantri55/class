import java.io.*;
import java.util.*;

public class Voting {

	/**
	 * Reads the number of elections and store then in the array
	 * @param r Reader object to handle input I/O
	 * @param a int array to hold number of elections
	 * @return true upon success
	 */
	public static boolean readNumElections (Reader r, int[] a) {
		assert a != null;
		a[0] = r.getInt();
		r.getLine();
		assert a[0] != 0;
		return true;
	}

	/**	
	 * Reads candidates and places them in a List
	 * @param r Reader to handle input I/O
	 * @param candidates A List to hold to candidate objects
	 * @return True on success
	 */
	public static boolean readCandidates (Reader r, List<Candidate> candidates) {
		assert candidates != null;
		int numCandidates = r.getInt();
		int i = 0;
		while(i++ < numCandidates) {
			String name = r.getLine();
			candidates.add(new Candidate(name));
		}
		assert numCandidates != 0;
		assert candidates.size() == numCandidates;
		return true;
	}	

	/**
	 * Reads ballots and places them in a List
 	 * @param r Reader to handle input I/O
	 * @param candidates A List of candidates in the election
	 * @param ballots A List to hold the ballots from the election
	 * @return true on success
	 */
	public static boolean readBallots (Reader r, List<Candidate> candidates, List<Ballot> ballots)
	{	
		assert candidates != null;
		assert ballots != null;
		String line;
		while(!((line = r.getLine()) == null) && !(line.equals(""))) {
			ballots.add(new Ballot(candidates.size(), line));
		}
		
		return true;
	}
		
	/**
	 * Evaluates the outcome of the election
	 * @param candidates A List of candidates in the Election
	 * @param ballots A List of ballots cast in the Election
	 * @return A List containing winning or tied Candidate(s)
	 */
	public static List<Candidate> eval(List<Candidate> candidates, List<Ballot> ballots) {
		assert candidates != null;
		assert ballots != null;
		int majority = ballots.size() / 2 + 1;
		List<Candidate> winners = new ArrayList<Candidate>();
		assignBallots(candidates, ballots, true);
		while(true) {
			
			boolean tie = true;
			int lowest = 0;
			for(Candidate c : candidates) {
				if(c.numVotes() >= majority) {
					winners.add(c);
					assert winners.size() == 1;
					return winners;
				}
				int nV;
				if((nV = c.numVotes()) > 0) {
					if(lowest == 0) {
					 lowest = nV;
					}
					else if(lowest != nV) {
						tie = false;
						lowest = Math.min(lowest, nV);
					}
				}
			}
			assert lowest != 0;

			for(Candidate c: candidates) {
				if(c.numVotes() == lowest) {
					if(tie) {
						winners.add(c);
					}
					else {
						ballots.addAll(c.removeBallots());
						assert ballots.size() > 0;
					}
				}	
			}
			if(tie) {
				assert winners.size() > 1;
				return winners;
			}
			assert ballots.size() > 0;
			assignBallots(candidates, ballots, false);
		}
	}

	/**
	 * Assigns ballots to the candidates
	 * @param candidates A List of candidates in the election
	 * @param ballots A List of ballots to be assigned
	 * @param first True if first round of voting; False otherwise
	 * @return True upon success
	 */
	public static boolean assignBallots(List<Candidate> candidates, List<Ballot> ballots, boolean first) {
		assert candidates != null;
		assert ballots != null;
		int i = 0;
		while (i < ballots.size()) {
			Ballot b = ballots.get(i);
			int vote = b.getVote();
			if(!first) {
				while(candidates.get(vote - 1).numVotes() == 0) {
					vote = b.getVote();
				}
			}
			candidates.get(vote - 1).associateBallot(b);
			i++;
		}
		ballots.clear();
		assert ballots.size() == 0;
		return true;
	}

	/**
	 * Prints the names of the winning candidate(s)
	 * @param w A Writer to handle output I/O
	 * @param candidates A List of the winning Candidate(s)
	 * @return true upon success
	 */
	public static boolean print(Writer w, List<Candidate> candidates) {
		Iterator iter = candidates.iterator();
		while(iter.hasNext()) {
			w.write(iter.next().toString());
		}
		return true;
	}

}

class Candidate {
	String name;
	List<Ballot> ballots;

	/**
	 * Candidate constructor
	 * @param nombre The name of the candidate
	 */
	public Candidate (String nombre) {
		name = nombre;
		ballots = new LinkedList<Ballot>();
	}

	/**
	 * Assigns a Ballot to this candidate
	 * @param ballot The Ballot being assigned
	 */
	public void associateBallot (Ballot ballot) {
		assert ballots != null;
		ballots.add(ballot);
	}

	/**
	 * Gives the name of the Candidate
	 * @return The name of the Candidate
	 */
	public String toString () {
		return name;
	}
		
	/**
	 * Accesses the number of votes the Candidate has
	 * @return The number of ballots assigned to this candidate
	 */
	public int numVotes() {
		if(ballots == null)
			return 0;
		return ballots.size();
	}

	/**
	 * Removes ballots from the candidate and removes his from
	 * the election. Ballots are then assigned to the next candidate on the ballots
	 * @return A List of the ballots assigned to this candidate
	 */
	public List<Ballot> removeBallots() {
		assert ballots != null;
		List<Ballot> temp = ballots;
		ballots = null;
		assert numVotes() == 0;
		return temp;
	}
}

class Ballot {
	Integer[] votes;
	Integer pointer;

	/**
	 * Ballot constructor
	 * @param numCandidates The number of candidates in the election
	 * @param line	A String of input containing the votes
	 */
	public Ballot (Integer numCandidates, String line) {
		String[] tmp;
			
		pointer = 0;
		tmp = line.split("\\s+");
		votes = new Integer[numCandidates];
		assert(votes.length == tmp.length);

		for (int i = 0; i < tmp.length; i++) {
			votes[i] = Integer.parseInt(tmp[i]);
		}
	}

	/**
	 * Accesses the next vote on the Ballot.
	 * @return An Integer representing the candidate the vote is for
	 */
	public Integer getVote () {
		assert pointer < votes.length;
		return votes[pointer++];
	}
}
