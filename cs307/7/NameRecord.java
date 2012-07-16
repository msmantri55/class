import java.util.ArrayList;

public class NameRecord {
	private static final int EPOCH = 1900;
	private static final int DEFAULT_SIZE = 11;
	private String name;
	private ArrayList<Integer> ranks = new ArrayList<Integer>(DEFAULT_SIZE);
	
	public NameRecord (String data) {
		String[] parsedData = data.split("\\s+");
		
		name = parsedData[0];
		
		for (int i = 1; i < parsedData.length; i++) {
			ranks.add(Integer.parseInt(parsedData[i]));
		}
	}
	
	public String getName () {
		return name;
	}
	
	public int getRank (int decade) {
		return ranks.get(decade);
	}
	
	public int bestDecade () {
		int index = 0;
		int min = ranks.get(index);
		
		for (int i = 1; i < ranks.size(); i++) {
			if (ranks.get(i) < min && ranks.get(i) != 0) {
				index = i;
				min = ranks.get(i);
			}
		}
		
		return EPOCH + (index * 10);
	}
	
	public int rankedDecades () {
		int total = 0;
		
		for (int rank : ranks) {
			if (rank > 0) {
				total++;
			}
		}
		
		return total;
	}
	
	public boolean everyDecade () {
		if (this.rankedDecades() == ranks.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean oneDecade () {
		if (this.rankedDecades() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean increasingRank () {
		boolean result = true;
		
		for (int i = 1; i < ranks.size() && result; i++) {
			if ((ranks.get(i) > ranks.get(i - 1) && ranks.get(i - 1) != 0) || (ranks.get(i) == 0 && ranks.get(i - 1) != 0)) {
				result = false;
			}
		}
		
		return result;
	}
	
	public boolean decreasingRank () {
		return !this.increasingRank();
	}
}