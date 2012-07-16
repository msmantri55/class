import java.util.HashMap;

public class Memoizer {
	private HashMap<Object, Object> hash;
	private Functor function;

	public Memoizer () {
		this(null);
	}

	public Memoizer (Functor function) {
		this.hash = new HashMap<Object, Object>();
		this.function = function;
	}

	public Object call (Object parameter) {
		if (this.hash.containsKey(parameter)) {
			return this.hash.get(parameter);
		}

		Object value = function.fn(parameter);

		this.hash.put(parameter, value);

		return value;
	}
}
