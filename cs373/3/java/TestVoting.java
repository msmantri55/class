// setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar

import java.io.*;
import java.util.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestVoting extends TestCase {
	public static void main (String[] args) {
		TestRunner.run(new TestSuite(TestVoting.class));
	}

	// testRead ------------------------------------------------------------

	/**
	 * Tests reading number of elections
	 */
	public void testReadNumElections () {
		Reader r = new Reader("1\n\n1\nJohn Doe\n1\n");
		int[] a = new int[1];
		boolean result = Voting.readNumElections (r, a);
		Assert.assertTrue(result);
		Assert.assertTrue(a[0] == 1);
		Assert.assertTrue(r.getLine().equals("1"));
		Assert.assertTrue(r.getLine().equals("John Doe"));
	}

	/**
	 * Tests reading in candidates for an election
	 */
	public void testReadCandidates () {
		Reader r = new Reader("3\nJohn Doe\nBarack Obama\nChewbacca\n");
		List<Candidate> candidates = new ArrayList<Candidate>();
		boolean result = Voting.readCandidates(r, candidates);
		
		Assert.assertTrue(result);
		Assert.assertTrue(candidates.size() == 3);
		Assert.assertTrue(candidates.get(0).toString().equals("John Doe"));
		Assert.assertTrue(candidates.get(1).toString().equals("Barack Obama"));
		Assert.assertTrue(candidates.get(2).toString().equals("Chewbacca"));
	}

	/**
	 Tests reading ballots that have another election after the ballots.
	 Also checks the consistency of data contained in the ballots.
	*/
	public void testReadBallots1 () {
		Reader r = new Reader("1 2 3\n1 3 2\n2 1 3\n3 1 2\n\nStart of Next Election\n");

		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		candidates.add(new Candidate("C"));

		List<Ballot> ballots = new LinkedList<Ballot>();
		boolean result = Voting.readBallots(r, candidates, ballots);
	
		Assert.assertTrue(result);
		Assert.assertTrue(ballots.size() == 4);;

		Ballot first = ballots.get(0);
		Assert.assertTrue(first.getVote() == 1);
		Assert.assertTrue(first.getVote() == 2);
		Assert.assertTrue(first.getVote() == 3);
		
		Ballot second = ballots.get(1);
		Assert.assertTrue(second.getVote() == 1);
		Assert.assertTrue(second.getVote() == 3);

		Ballot third = ballots.get(2);
		Assert.assertTrue(third.getVote() == 2);

		Ballot fourth = ballots.get(3);
		Assert.assertTrue(fourth.getVote() == 3);

		Assert.assertTrue(r.getLine().equals("Start of Next Election"));

	}

	/**
	 Test reading ballots that have the end of the file after the ballots.
	*/
	public void testReadBallots2 () {

		Reader r = new Reader("1 2 3\n1 3 2\n2 1 3\n3 1 2\n");

		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		candidates.add(new Candidate("C"));

		List<Ballot> ballots = new LinkedList<Ballot>();
		boolean result = Voting.readBallots(r, candidates, ballots);
	
		Assert.assertTrue(result);
		Assert.assertTrue(ballots.size() == 4);
	}

	/**
	 * Tests print function with one winning candidate
	 */
	public void testPrint1 () {
		Writer w = new Writer();
		List<Candidate> candidates = new LinkedList<Candidate>();
		candidates.add(new Candidate("John Doe"));
		boolean result = Voting.print(w, candidates);

		Assert.assertTrue(result);
		Assert.assertTrue(w.toString().equals("John Doe\n"));
	}

	/**
	 * Tests print function with multiple tied candidates
	 */
	public void testPrint2 () {
		Writer w = new Writer();
		List<Candidate> candidates = new LinkedList<Candidate>();
		candidates.add(new Candidate("John Doe"));
		candidates.add(new Candidate("Barack Obama"));
		candidates.add(new Candidate("Chewbacca"));
		boolean result = Voting.print(w, candidates);

		Assert.assertTrue(result);
		Assert.assertTrue(w.toString().equals("John Doe\nBarack Obama\nChewbacca\n"));
	}

	/**
	 * Test assigning a list of fresh ballots to a candidate
	 */
	public void testAssignBallots1 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2"));
		ballots.add(new Ballot(candidates.size(), "2 1"));
		ballots.add(new Ballot(candidates.size(), "1 2"));

		boolean result = Voting.assignBallots(candidates, ballots, true);
		Assert.assertTrue(result);
		Assert.assertTrue(candidates.get(0).numVotes() == 2);
		Assert.assertTrue(candidates.get(1).numVotes() == 1);
	}

	/**
	 * Tests assigning a list of used ballots to a candidate
	 */
	public void testAssignBallots2 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2"));
		ballots.add(new Ballot(candidates.size(), "2 1"));
		ballots.add(new Ballot(candidates.size(), "1 2"));
		candidates.get(0).associateBallot(new Ballot(candidates.size(), "1 2"));

		boolean result = Voting.assignBallots(candidates, ballots, false);
		Assert.assertTrue(result);
		Assert.assertTrue(candidates.get(0).numVotes() == 4);
		Assert.assertTrue(candidates.get(1).numVotes() == 0);
	}

	/**
	 * Tests a simple tied election
	 */
	public void testEval1 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2"));
		ballots.add(new Ballot(candidates.size(), "2 1"));
		List<Candidate> winners = Voting.eval(candidates, ballots);
		
		Assert.assertTrue(winners.size() == 2);
		Assert.assertTrue(winners.get(0).toString().equals("A"));
		Assert.assertTrue(winners.get(0).numVotes() == 1);
		Assert.assertTrue(winners.get(1).toString().equals("B"));
		Assert.assertTrue(winners.get(1).numVotes() == 1);
	}

	/**
	 * Tests a simple election with one winner
	 */
	public void testEval2 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2"));
		ballots.add(new Ballot(candidates.size(), "1 2"));
		List<Candidate> winners = Voting.eval(candidates, ballots);
		
		Assert.assertTrue(winners.size() == 1);
		Assert.assertTrue(winners.get(0).toString().equals("A"));
		Assert.assertTrue(winners.get(0).numVotes() == 2);
	}

	/**
	 * Tests an election with one reassignment of ballots
	 */
	public void testEval3 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		candidates.add(new Candidate("C"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2 3"));
		ballots.add(new Ballot(candidates.size(), "1 3 2"));
		ballots.add(new Ballot(candidates.size(), "2 3 1"));
		ballots.add(new Ballot(candidates.size(), "3 2 1"));
		ballots.add(new Ballot(candidates.size(), "2 1 3"));
		List<Candidate> winners = Voting.eval(candidates, ballots);
		
		Assert.assertTrue(winners.size() == 1);
		Assert.assertTrue(winners.get(0).toString().equals("B"));
		Assert.assertTrue(winners.get(0).numVotes() == 3);
	}

	/**
	 * Test and election with two candidates tied for last
	 * Also tests assigning a ballot where the next candidate 
	 *	has already been eliminated
	 */
	public void testEval4 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		candidates.add(new Candidate("C"));
		candidates.add(new Candidate("D"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2 3 4"));
		ballots.add(new Ballot(candidates.size(), "1 3 2 4"));
		ballots.add(new Ballot(candidates.size(), "2 3 1 4"));
		ballots.add(new Ballot(candidates.size(), "3 4 1 2"));
		ballots.add(new Ballot(candidates.size(), "2 1 3 4"));
		ballots.add(new Ballot(candidates.size(), "4 3 2 1"));
		List<Candidate> winners = Voting.eval(candidates, ballots);
		
		Assert.assertTrue(winners.size() == 2);
		Assert.assertTrue(winners.get(0).toString().equals("A"));
		Assert.assertTrue(winners.get(0).numVotes() == 3);
		Assert.assertTrue(winners.get(1).toString().equals("B"));
		Assert.assertTrue(winners.get(1).numVotes() == 3);		
	}

	/**
	 * Tests an election with multiple iterations of reassigning ballots
	 */
	public void testEval5 () {
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("A"));
		candidates.add(new Candidate("B"));
		candidates.add(new Candidate("C"));
		candidates.add(new Candidate("D"));
		LinkedList<Ballot> ballots = new LinkedList<Ballot>();
		ballots.add(new Ballot(candidates.size(), "1 2 3 4"));
		ballots.add(new Ballot(candidates.size(), "1 3 2 4"));
		ballots.add(new Ballot(candidates.size(), "2 3 1 4"));
		ballots.add(new Ballot(candidates.size(), "3 4 1 2"));
		ballots.add(new Ballot(candidates.size(), "2 1 3 4"));
		ballots.add(new Ballot(candidates.size(), "3 1 2 4"));
		ballots.add(new Ballot(candidates.size(), "2 1 3 4"));
		ballots.add(new Ballot(candidates.size(), "4 1 2 3"));
		List<Candidate> winners = Voting.eval(candidates, ballots);
		
		Assert.assertTrue(winners.size() == 1);
		Assert.assertTrue(winners.get(0).toString().equals("A"));
		Assert.assertTrue(winners.get(0).numVotes() == 5);		
	}
}

class Reader {
	private Scanner scanner;

	public Reader(String input) {
		scanner = new Scanner(input);
	}

	/**
	 * Reads an interger on a line of input
	 * @return The integer parsed from the line
	 */
	public Integer getInt() {
		String line = scanner.nextLine();
		return Integer.parseInt(line);
	}

	/**
	 * Reads a line of input
	 * @return The String containing the line read; null if no more input
	 */
	public String getLine() {
		if(!scanner.hasNext()) {
			return null;
		}
		return scanner.nextLine();
	}

}

class Writer {
	private StringBuilder sb;

	public Writer () {
		sb = new StringBuilder();
	}
	
	public String toString () {
		return sb.toString();
	}

	public void write (String s) {
		sb.append(s + "\n");
	}
}
