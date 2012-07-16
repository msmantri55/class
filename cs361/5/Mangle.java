import java.util.HashSet;

public abstract class Mangle {
	public static HashSet<String> applyAll (String string) {
		HashSet<String> strings = new HashSet<String>();

		strings.addAll(deleteFirst(string));
		strings.addAll(deleteLast(string));
		strings.addAll(reverse(string));
		strings.addAll(duplicate(string));
		strings.addAll(reflect(string));
		strings.addAll(lowercase(string));
		strings.addAll(uppercase(string));
		strings.addAll(capitalize(string));
		strings.addAll(inverseCapitalize(string));
		strings.addAll(toggleCase(string));
		strings.addAll(prepend(string));
		strings.addAll(append(string));

		return strings;
	}

	public static HashSet<String> applyAll (HashSet<String> strings) {
		HashSet<String> result = new HashSet<String>();

		for (String string : strings) {
			result.addAll(applyAll(string));
		}

		return result;
	}

	public static String truncate (String string, int length) {
		if (string.length() > length) {
			return string.substring(0, length);
		}

		return string;
	}

	public static HashSet<String> truncate (HashSet<String> strings, int length) {
		HashSet<String> result = new HashSet<String>();

		for (String string : strings) {
			result.add(truncate(string, length));
		}

		return result;
	}

	public static HashSet<String> deleteFirst (String string) {
		HashSet<String> strings = new HashSet<String>();

		if (string.length() > 1) {
			strings.add(string.substring(1, string.length()));
		}

		return strings;
	}

	public static HashSet<String> deleteLast (String string) {
		HashSet<String> strings = new HashSet<String>();

		if (string.length() > 1) {
			strings.add(string.substring(0, string.length() - 1));
		}

		return strings;
	}

	public static HashSet<String> reverse (String string) {
		HashSet<String> strings = new HashSet<String>();
		String gnirts = "";
		
		for (int i = string.length() - 1; i >= 0; i--) {
			gnirts += string.charAt(i);
		}

		strings.add(gnirts);

		return strings;
	}

	public static HashSet<String> duplicate (String string) {
		HashSet<String> strings = new HashSet<String>();

		strings.add(string + string);

		return strings;
	}

	public static HashSet<String> reflect (String string) {
		HashSet<String> strings = new HashSet<String>();
		String gnirts = "";
		
		for (int i = string.length() - 1; i >= 0; i--) {
			gnirts += string.charAt(i);
		}

		strings.add(string + gnirts);
		strings.add(gnirts + string);

		return strings;
	}

	public static HashSet<String> lowercase (String string) {
		HashSet<String> strings = new HashSet<String>();

		strings.add(string.toLowerCase());

		return strings;
	}

	public static HashSet<String> uppercase (String string) {
		HashSet<String> strings = new HashSet<String>();

		strings.add(string.toUpperCase());

		return strings;
	}

	public static HashSet<String> capitalize (String string) {
		HashSet<String> strings = new HashSet<String>();

		if (string.length() > 1) {
			String first = string.substring(0, 1);
			String rest = string.substring(1, string.length());

			strings.add(first.toUpperCase() + rest.toLowerCase());
		}

		return strings;
	}

	public static HashSet<String> inverseCapitalize (String string) {
		HashSet<String> strings = new HashSet<String>();

		if (string.length() > 1) {
			String first = string.substring(0, 1);
			String rest = string.substring(1, string.length());

			strings.add(first.toLowerCase() + rest.toUpperCase());
		}

		return strings;
	}

	public static HashSet<String> toggleCase (String string) {
		HashSet<String> strings = new HashSet<String>();
		String a, b;

		a = b = "";

		for (int i = 0; i < string.length(); i++) {
			String c = string.substring(i, i + 1).toLowerCase();

			if ((i & 1) == 0) {
				assert (i % 2 == 0);

				a += c.toUpperCase();
				b += c;
			}
			else {
				a += c;
				b += c.toUpperCase();
			}
		}

		strings.add(a);
		strings.add(b);

		return strings;
	}

	public static HashSet<String> prepend (String string) {
		HashSet<String> strings = new HashSet<String>();

		for (int c = 32; c <= 126; c++) {
			strings.add((char) c + string);
		}

		return strings;
	}

	public static HashSet<String> append (String string) {
		HashSet<String> strings = new HashSet<String>();

		for (int c = 32; c <= 126; c++) {
			strings.add(string + (char) c);
		}

		return strings;
	}
}
