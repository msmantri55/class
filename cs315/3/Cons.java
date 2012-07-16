/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

/* convert a list to a string for printing */
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : first(lst).toString()
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

	// -----------------------------------------------------------------
	// START my code
	// -----------------------------------------------------------------

	public static int sum (Cons list) {
		int sum = 0;

		while (list != null) {
			sum += (Integer) first(list);
			list = rest(list);
		}

		return sum;
	}

	public static int length (Cons list) {
		int length = 0;

		while (list != null) {
			length++;
			list = rest(list);
		}

		return length;
	}

	public static double mean (Cons list) {
		int sum = sum(list);
		int length = length(list);

		return (double) sum / length;
	}

	public static double meansq (Cons list) {
		int sumsq = 0;
		int length = length(list);

		while (list != null) {
			int first = (Integer) first(list);
			sumsq += first * first;
			list = rest(list);
		}

		return (double) sumsq / length;
	}

	public static double variance (Cons list) {
		double mean = mean(list);
		double meansq = meansq(list);

		return meansq - (mean * mean);
	}

	public static double stddev (Cons list) {
		double variance = variance(list);

		if (variance < 0) {
			variance *= -1;
		}

		return Math.sqrt(variance);
	}

	public static double sine (double x) {
		return sine(x, 10);
	}

	public static double sine (double x, int degree) {
		return sine_helper(x, degree, 0, 0, 0);
	}

	public static double sine_helper (double x, int degree, int n, double sum, double last_term) {
		if (n > degree) {
			return sum;
		}

		double term;

		if (n == 0) {
			term = x;
		} else {
			term = last_term * -(x * x / ((2 * n + 1) * (2 * n)));
		}

		sum += term;

		return sine_helper(x, degree, ++n, sum, term);
	}

	public static Cons nthcdr (int n, Cons list) {
		if (n <= 0 || list == null) {
			return list;
		}

		return nthcdr(--n, rest(list));
	}

	public static Object elt (Cons list, int n) {
		if (n < 0 || list == null) {
			return null;
		}

		while (n > 0) {
			list = rest(list);
			n--;
		}

		return first(list);
	}

	public static double interpolate (Cons list, double x) {
		if (x < 0 || x > length(list) - 1) {
			return 0;
		}

		int i = (int) x;
		double delta = x - i;
		int l_1 = (Integer) elt(list, i);
		int l_2 = (Integer) elt(list, i + 1);

		return l_1 + delta * (l_2 - l_1);
	}

	public static int sumtr (Cons list) {
		int sum = 0;

		while (list != null) {
			Object first = first(list);

			if (consp(first)) {
				sum += sumtr((Cons) first);
			} else {
				sum += (Integer) first;
			}

			list = rest(list);
		}

		return sum;
	}

	public static Cons subseq (Cons list, int start, int end) {
		if (list == null || start < 0 || start > end || end > length(list)) {
			return null;
		}

		Cons result = null;

		for (int n = 0; n < start; n++) {
			list = rest(list);
		}

		for (int n = 0; n < end - start; n++) {
			result = cons(first(list), result);
			list = rest(list);
		}

		return reverse(result);
	}

	public static Cons reverse (Cons list) {
		Cons result = null;

		while (list != null) {
			result = cons(first(list), result);
			list = rest(list);
		}

		return result;
	}

	public static Cons posfilter (Cons list) {
		if (list == null) {
			return null;
		}

		int first = (Integer) first(list);

		if (first < 0) {
			return posfilter(rest(list));
		}

		return cons(first, posfilter(rest(list)));
	}

	public static Cons subset (Predicate p, Cons list) {
		if (list == null) {
			return null;
		}

		if (p.pred(first(list))) {
			return cons(first(list), subset(p, rest(list)));
		}

		return subset(p, rest(list));
	}

	public static Cons mapcar (Functor f, Cons list) {
		if (list == null) {
			return null;
		}

		return cons(f.fn(first(list)), mapcar(f, rest(list)));
	}

	public static Object some (Predicate p, Cons list) {
		if (list == null) {
			return null;
		}

		if (p.pred(first(list))) {
			return first(list);
		}

		return some(p, rest(list));
	}

	public static boolean every (Predicate p, Cons list) {
		if (list == null) {
			return true;
		}

		if (!p.pred(first(list))) {
			return false;
		}

		return every(p, rest(list));
	}

	// -----------------------------------------------------------------
	// BEGIN my code from assignment 2
	// -----------------------------------------------------------------

	public static Cons binomial (int n) {
		int[] result = new int[n + 1];

		int limit = result.length / 2;
		if (result.length % 2 == 1) {
			limit++;
		}

		for (int k = 0; k < limit; k++) {
			int coefficient = binomial_helper(n, k, 1);
			result[k] = result[n - k] = coefficient;
		}

		Cons coefficients = null;

		for (int i = 0; i < result.length; i++) {
			coefficients = cons(result[i], coefficients);
		}

		return coefficients;
	}

	public static int binomial_helper(int n, int k, double coefficient) {
		if (k == 0) {
			return (int) coefficient;
		}

		return binomial_helper(n - 1, k - 1, coefficient * n / k);
	}

	// -----------------------------------------------------------------
	// END my code from assignment 2
	// -----------------------------------------------------------------

	// -----------------------------------------------------------------
	// END my code
	// -----------------------------------------------------------------

    public static void main( String[] args )
      { 
        Cons mylist =
            list(new Integer(95), new Integer(72), new Integer(86),
                 new Integer(70), new Integer(97), new Integer(72),
                 new Integer(52), new Integer(88), new Integer(77),
                 new Integer(94), new Integer(91), new Integer(79),
                 new Integer(61), new Integer(77), new Integer(99),
                 new Integer(70), new Integer(91) );
        System.out.println("mylist = " + mylist.toString());
        System.out.println("sum = " + sum(mylist));
        System.out.println("mean = " + mean(mylist));
        System.out.println("meansq = " + meansq(mylist));
        System.out.println("variance = " + variance(mylist));
        System.out.println("stddev = " + stddev(mylist));
        System.out.println("sine(0.5) = " + sine(0.5));  // 0.47942553860420301
        System.out.println("nthcdr 5 = " + nthcdr(5, mylist).toString());
        System.out.println("elt 5 = " + elt(mylist,5));

        Cons mylistb = list(new Integer(0), new Integer(30), new Integer(56),
                           new Integer(78), new Integer(96));
        System.out.println("mylistb = " + mylistb.toString());
        System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
        Cons binom = binomial(12);
        System.out.println("binom = " + binom.toString());
        System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

        Cons mylistc = list(new Integer(1),
                            list(new Integer(2), new Integer(3)),
                            list(list(new Integer(4), new Integer(5)),
                                 new Integer(6)));
        System.out.println("mylistc = " + mylistc.toString());
        System.out.println("sumtr = " + sumtr(mylistc));

        Cons mylistd = list(new Integer(0), new Integer(1), new Integer(2),
                            new Integer(3), new Integer(4), new Integer(5),
                            new Integer(6) );
        System.out.println("mylistd = " + mylistd.toString());
        System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

        Cons myliste = list(new Integer(3), new Integer(17), new Integer(-2),
                            new Integer(0), new Integer(-3), new Integer(4),
                            new Integer(-5), new Integer(12) );
        System.out.println("myliste = " + myliste.toString());
        System.out.println("posfilter = " + posfilter(myliste));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        System.out.println("subset = " + subset(myp, myliste).toString());

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, mylistd).toString());

        System.out.println("some = " + some(myp, myliste).toString());

        System.out.println("every = " + every(myp, myliste));

      }
}
