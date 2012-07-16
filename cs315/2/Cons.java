/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08
 */

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
	
	public static int sumsq (int n) {
		int sum = 0;

		// Make this work for negative values of n
		if (n < 0) {
			n *= -1;
		}

		for (int i = 1; i <= n; i++) {
			sum += i * i;
		}

		return sum;
	}

	public static int peanoplus (int x, int y) {
		if (y > 0) {
			return peanoplus(++x, --y);
		}

		if (y < 0) {
			return peanoplus(--x, ++y);
		}

		return x;
	}

	public static int peanotimes (int x, int y) {
		if (x == 0 || y == 0) {
			return 0;
		}

		if (y > 1) {
			return peanoplus(peanotimes(x, --y), x);
		}

		if (y <= -1) {
			x *= -1;
			y *= -1;

			return peanotimes(x, y);
		}

		return x;
	}

	public static int choose (int n, int k) {
		if (n < 1 || k < 0 || k > n) {
			return 0;
		}

		if (k > n / 2) {
			k = n - k;
		}

		return choose_helper(n, k, 1);
	}

	public static int choose_helper(int n, int k, double choices) {
		if (k == 0) {
			return (int) choices;
		}

		return choose_helper(n - 1, k - 1, choices * n / k);
	}

	public static int sumlist (Cons list) {
		int sum = 0;

		while (list != null) {
			sum += (Integer) first(list);
			list = rest(list);
		}

		return sum;
	}

	public static int sumlistr (Cons list) {
		if (list == null) {
			return 0;
		}

		return (Integer) first(list) + sumlistr(rest(list));
	}

	public static int sumlisttr (Cons list) {
		return sumlisttr_helper(list, 0);
	}

	public static int sumlisttr_helper (Cons list, int sum) {
		if (list == null) {
			return sum;
		}

		sum += (Integer) first(list);

		return sumlisttr_helper(rest(list), sum);
	}

	public static int sumsqdiff (Cons list_a, Cons list_b) {
		int sum = 0;

		while (list_a != null && list_b != null) {
			int diff = (Integer) first(list_a) - (Integer) first(list_b);
			sum += diff * diff;

			list_a = rest(list_a);
			list_b = rest(list_b);
		}

		return sum;
	}

	public static int sumsqdiffr (Cons list_a, Cons list_b) {
		if (list_a == null || list_b == null) {
			return 0;
		}

		int diff = (Integer) first(list_a) - (Integer) first(list_b);

		return (diff * diff) + sumsqdiffr(rest(list_a), rest(list_b));
	}

	public static int sumsqdifftr (Cons list_a, Cons list_b) {
		return sumsqdifftr_helper(list_a, list_b, 0);
	}

	public static int sumsqdifftr_helper (Cons list_a, Cons list_b, int sum) {
		if (list_a == null || list_b == null) {
			return sum;
		}

		int diff = (Integer) first(list_a) - (Integer) first(list_b);
		sum += diff * diff;

		return sumsqdifftr_helper(rest(list_a), rest(list_b), sum);
	}

	public static int maxlist (Cons list) {
		if (list == null) {
			return 0;
		}

		int max = (Integer) first(list);

		while (list != null) {
			if ((Integer) first(list) > max) {
				max = (Integer) first(list);
			}

			list = rest(list);
		}

		return max;
	}

	public static int maxlistr (Cons list) {
		int first = (Integer) first(list);

		if (rest(list) == null) {
			return first;
		}

		int rest = maxlistr(rest(list));

		if (first > rest) {
			return first;
		} else {
			return rest;
		}
	}

	public static int maxlisttr (Cons list) {
		return maxlisttr_helper(list, (Integer) first(list));
	}

	public static int maxlisttr_helper (Cons list, int max) {
		if (list == null) {
			return max;
		}

		int first = (Integer) first(list);

		if (first > max) {
			max = first;
		}

		return maxlisttr_helper(rest(list), max);
	}

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
	// END my code
	// -----------------------------------------------------------------

    public static void main( String[] args )
      { 
        System.out.println("sumsq = " + sumsq(5));

        System.out.println("peanoplus = " + peanoplus(3, 5));
        System.out.println("peanotimes = " + peanotimes(3, 5));

        System.out.println("choose 5 3 = " + choose(5, 3));
        for (int i = 0; i <= 4; i++)
          System.out.println("choose 4 " + i + " = " + choose(4, i));


        Cons mylist = list(new Integer(3), new Integer(4),
                           new Integer(8), new Integer(2));
        Cons mylistb = list(new Integer(2), new Integer(1),
                           new Integer(6), new Integer(5));

        System.out.println("mylist = " + mylist.toString());
        System.out.println("mylistb = " + mylistb.toString());

        System.out.println("sumlist = " + sumlist(mylist));
        System.out.println("sumlistr = " + sumlistr(mylist));
        System.out.println("sumlisttr = " + sumlisttr(mylist));

        System.out.println("sumsqdiff = " + sumsqdiff(mylist, mylistb));
        System.out.println("sumsqdiffr = " + sumsqdiffr(mylist, mylistb));
        System.out.println("sumsqdifftr = " + sumsqdifftr(mylist, mylistb));

        System.out.println("maxlist = " + maxlist(mylist));
        System.out.println("maxlistr = " + maxlistr(mylist));
        System.out.println("maxlisttr = " + maxlisttr(mylist));

        System.out.println("binomial = " + binomial(4));
      }
}
