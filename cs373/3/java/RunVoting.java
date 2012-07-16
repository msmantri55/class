import java.io.*;
import java.util.*;

public class RunVoting {
	public static void main (String... args) {

		Reader r = new Reader();
		Writer w = new Writer();
		
		int[] a = new int[1];
		Voting.readNumElections(r, a);
		int i = 0;
		while(i++ < a[0]) {
			List<Candidate> candidates = new ArrayList<Candidate>();
			List<Ballot> ballots = new ArrayList<Ballot>();
			Voting.readCandidates(r, candidates);
			Voting.readBallots(r, candidates, ballots);
			List<Candidate> winners = Voting.eval(candidates, ballots);
			Voting.print(w, winners);
			if(i != a[0]) {
				w.write("");
			}
		}
	}
}

class Reader {
	private BufferedReader br;

	public Reader() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * Reads an interger on a line of input
	 * @return The integer parsed from the line
	 */
	public Integer getInt() {
		try {
			String line = br.readLine();
			return Integer.parseInt(line);
		}
		catch (IOException e) {
			return 0;
		}
	}

	/**
	 * Reads a line of input
	 * @return The String containing the line read; null if no more input
	 */
	public String getLine() {
		try {
			return br.readLine();
		}
		catch (IOException e) {
			return null;
		}
	}
}

class Writer {
	private PrintStream ps;
	
	public Writer () {
		ps = System.out;
	}
	
	public void write (String s) {
		ps.println(s);
	}
}
