public class Event implements Comparable<Event> {
	private int time;
	private Cons action;
	
	public Event (Cons action, int time) {
		this.time = time;
		this.action = action;
	}

	public int time () {
		return this.time;
	}

	public Cons action () {
		return this.action;
	}

	public int compareTo (Event event) {
		return this.time - event.time;
	}
}
