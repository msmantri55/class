/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08
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
    /* access functions for expression representation */
    public static Object op  (Cons x) { return first(x); }
    public static Object lhs (Cons x) { return first(rest(x)); }
    public static Object rhs (Cons x) { return first(rest(rest(x))); }
    public static boolean numberp (Object x)
       { return ( (x != null) &&
                  (x instanceof Integer || x instanceof Double) ); }
    public static boolean integerp (Object x)
       { return ( (x != null) && (x instanceof Integer ) ); }
    public static boolean floatp (Object x)
       { return ( (x != null) && (x instanceof Double ) ); }
    public static boolean stringp (Object x)
       { return ( (x != null) && (x instanceof String ) ); }

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

// member returns null if requested item not found
public static Cons member (Object item, Cons lst) {
  if ( lst == null )
     return null;
   else if ( item.equals(first(lst)) )
           return lst;
         else return member(item, rest(lst)); }

public static Cons union (Cons x, Cons y) {
  if ( x == null ) return y;
  if ( member(first(x), y) != null )
       return union(rest(x), y);
  else return cons(first(x), union(rest(x), y)); }

    // combine two lists: (append '(a b) '(c d e))  =  (a b c d e)
public static Cons append (Cons x, Cons y) {
  if (x == null)
     return y;
   else return cons(first(x),
                    append(rest(x), y)); }

    // look up key in an association list
    // (assoc 'two '((one 1) (two 2) (three 3)))  =  (two 2)
public static Cons assoc(Object key, Cons lst) {
  if ( lst == null )
     return null;
  else if ( key.equals(first((Cons) first(lst))) )
      return ((Cons) first(lst));
          else return assoc(key, rest(lst)); }

    public static int square(int x) { return x*x; }
    public static int pow (int x, int n) {
        if ( n <= 0 ) return 1;
        if ( (n & 1) == 0 )
            return square( pow(x, n / 2) );
        else return x * pow(x, n - 1); }

    // you can use these lookup tables with assoc if you wish.
    public static Cons engwords = list(list("+", "sum"),
                                       list("-", "difference"),
                                       list("*", "product"),
                                       list("/", "quotient"),
                                       list("expt", "power"));

    public static Cons opprec = list(list("=", new Integer(1)),
                                     list("+", new Integer(5)),
                                     list("-", new Integer(5)),
                                     list("*", new Integer(6)),
                                     list("/", new Integer(6)) );

	//
	// BEGIN my code
	//

	/*
	 * Finds the maximum value in a binary tree where every leaf is an Integer. Assumes every leaf is greater than Integer.MIN_VALUE.
	 * Returns the max value or Integer.MIN_VALUE if the tree is null or has only null leaves.
	 */
	public static Integer maxbt (Object tree) {
		Integer max = Integer.MIN_VALUE;

		if (consp(tree)) {
			Integer first_max = maxbt(first((Cons) tree));
			Integer rest_max = maxbt(rest((Cons) tree));

			if (first_max > max) {
				max = first_max;
			}

			if (rest_max > max) {
				max = rest_max;
			}
		}
		else if (tree != null && (Integer) tree > max) {
			max = (Integer) tree;
		}

		return max;
	}

	/*
	 * Returns a set of variables in an expression. Assumes the expression to consist of numbers, strings, or conses of the type (op lhs rhs).
	 * Returns a set (i.e., no repeats) of variables.
	 */
	public static Cons vars (Object expr) {
		Cons vars = null;

		if (consp(expr)) {
			Cons lhs = vars(lhs((Cons) expr));
			Cons rhs = vars(rhs((Cons) expr));

			vars = union(lhs, rhs);
		}
		else if (expr instanceof java.lang.String) {
			vars = cons(expr, vars);
		}

		return vars;
	}

	/*
	 * Sees if the value occurs in the expression. Assumes the value has a .equals() method and is not a Cons.
	 * Returns true if the value occurs in the expression, false otherwise.
	 */
	public static boolean occurs (Object value, Object expr) {
		boolean occurs = false;

		if (consp(expr)) {
			boolean lhs = occurs(value, lhs((Cons) expr));
			boolean rhs = occurs(value, rhs((Cons) expr));

			occurs = lhs || rhs;
		}
		else {
			if (value != null) {
				occurs = value.equals(expr);
			}
		}

		return occurs;
	}

	/*
	 * Evaluates a tree where the leaves are Integers.
	 * Returns an Integer.
	 */
	public static Integer eval (Object expr) {
		return eval(expr, null);
	}

	/*
	 * Evaluates a tree where the leaves are either Integers or variables given in the bindings.
	 * Returns an Integer.
	 */
	public static Integer eval (Object expr, Cons bindings) {
		Integer value = 0;

		if (consp(expr)) {
			String op = (String) op((Cons) expr);
			Integer lhs = eval(lhs((Cons) expr), bindings);
			Integer rhs = eval(rhs((Cons) expr), bindings);

			if ("+".equals(op)) {
				value = lhs + rhs;
			}
			else if ("-".equals(op)) {
				if (rest((Cons) rhs((Cons) expr) == null)) {
					value = lhs * -1;
				}
				else {
					value = lhs - rhs;
				}
			}
			else if ("*".equals(op)) {
				value = lhs * rhs;
			}
			else if ("/".equals(op)) {
				value = lhs / rhs;
			}
			else if ("expt".equals(op)) {
				value = pow(lhs, rhs);
			}
			else {
				// operator is unknown, value is 0
			}
		}
		else if (expr != null) {
			Cons assoc_value = assoc(expr, bindings);

			if (assoc_value == null) {
				value = (Integer) expr;
			}
			else {
				value = (Integer) first(rest(assoc_value));
			}
		}

		return value;
	}

	/*
	 * Translates an expression into a cons of strings in English.
	 * Returns a cons of strings.
	 */
	public static Cons english (Object expr) {
		Cons result = null;

		if (consp(expr)) {
			String op = (String) op((Cons) expr);
			Cons lhs = english(lhs((Cons) expr));
			Cons rhs = english(rhs((Cons) expr));

			result = cons("the", result);

			if ("+".equals(op)) {
				result = cons("sum", result);
			}
			else if ("-".equals(op)) {
				result = cons("difference", result);

				if (first(rhs) == null) {
					// Don't start "negative" with "the"
					result = cons("negative", null);
				}
			}
			else if ("*".equals(op)) {
				result = cons("product", result);
			}
			else if ("/".equals(op)) {
				result = cons("quotient", result);
			}
			else {
				result = cons(op, result);
			}

			if (first(rhs) == null && "-".equals(op)) {
				// Don't append "of" to "negative"
			}
			else {
				result = cons("of", result);
			}

			while (lhs != null) {
				result = cons(first(lhs), result);
				lhs = rest(lhs);
			}

			if (first(rhs) != null) {
				result = cons("and", result);

				while (rhs != null) {
					result = cons(first(rhs), result);
					rhs = rest(rhs);
				}
			}
		}
		else {
			return cons(expr, result);
		}

		return reverse(result);
	}

	/*
	 * Translates an expression into a string that can be interpreted by Java.
	 * Returns a string.
	 */
	public static String tojava (Object expr) {
		return tojava(expr, 0) + ";";
	}

	private static String tojava (Object expr, int p) {
		String result = "";
		int new_p = 0;

		if (consp(expr)) {
			String op = (String) op((Cons) expr);
			Object lhs = lhs((Cons) expr);
			Object rhs = rhs((Cons) expr);

			if ("=".equals(op)) {
				new_p = 1;
			}
			else if ("+".equals(op) || ("-".equals(op) && rhs != null)) {
				new_p = 5;
			}
			else if ("*".equals(op) || "/".equals(op) || "-".equals(op)) {
				new_p = 6;
			}

			String lhs_s = tojava(lhs, new_p);

			if (rhs != null) {
				String rhs_s = tojava(rhs, new_p);

				result = lhs_s + op + rhs_s;

				if (new_p <= p) {
					result = "(" + result + ")";
				}
			}
			else {
				if ("-".equals(op)) {
					result = "(" + op + lhs_s + ")";
				}
				else {
					result = "Math." + op + "(" + lhs_s + ")";
				}
			}
		}
		else {
			result = expr.toString();
		}

		return result;
	}

	private static Cons reverse (Cons list) {
		Cons result = null;

		while (list != null) {
			result = cons(first(list), result);
			list = rest(list);
		}

		return result;
	}

	//
	// END my code
	//
    public static void main( String[] args ) {
    	System.out.println(tojava(list("-", list("+", 1, 2))));
        Cons bt1 = cons( cons( cons(new Integer(23), cons(new Integer(77),
                                                          null)),
                               cons(new Integer(-3), cons(new Integer(88),
                                                          null))),
                         cons( cons(new Integer(99), cons(new Integer(7),
                                                          null)),
                               cons(new Integer(15), cons(new Integer(-1),
                                                          null))));
        System.out.println("bt1 = " + bt1.toString());
        System.out.println("maxbt(bt1) = " + maxbt(bt1));
        Cons expr1 = list("=", "f", list("*", "m", "a"));
        System.out.println("expr1 = " + expr1.toString());
        System.out.println("vars(expr1) = " + vars(expr1).toString());

        Cons expr2 = list("=", "f", list("/", list("*", "m",
                                                   list("expt", "v",
                                                        new Integer(2))),
                                         "r"));
        System.out.println("expr2 = " + expr2.toString());
        System.out.println("vars(expr2) = " + vars(expr2).toString());
        System.out.println("occurs(m, expr2) = " + occurs("m", expr2));
        System.out.println("occurs(7, expr2) = " + occurs(new Integer(7), expr2));

        Cons expr3 = list("+", new Integer(3), list("*", new Integer(5),
                                                         new Integer(7)));
        System.out.println("expr3 = " + expr3.toString());
        System.out.println("eval(expr3) = " + eval(expr3));

        Cons expr4 = list("+", list("-", new Integer(3)),
                               list("expt", list("-", new Integer(7),
                                                      list("/", new Integer(4),
                                                                new Integer(2))),
                                            new Integer(3)));
        System.out.println("expr4 = " + expr4.toString());
        System.out.println("eval(expr4) = " + eval(expr4));

        System.out.println("eval(b) = " + eval("b", list(list("b", 7))));

        Cons expr5 = list("+", new Integer(3), list("*", new Integer(5), "b"));
        System.out.println("expr5 = " + expr5.toString());
        System.out.println("eval(expr5) = " + eval(expr5, list(list("b", 7))));

        Cons expr6 = list("+", list("-", "c"),
                          list("expt", list("-", "b", list("/", "z", "w")),
                                            new Integer(3)));
        Cons alist = list(list("c", 3), list("b", 7), list("z", 4),
                          list("w", 2), list("fred", 5));
        System.out.println("expr6 = " + expr6.toString());
        System.out.println("alist = " + alist.toString());
        System.out.println("eval(expr6) = " + eval(expr6, alist));
        System.out.println("english(expr5) = " + english(expr5).toString());
        System.out.println("english(expr6) = " + english(expr6).toString());
        System.out.println("tojava(expr1) = " + tojava(expr1).toString());
        Cons expr7 = list("=", "x", list("*", list("+", "a", "b"), "c"));
        System.out.println("expr7 = " + expr7.toString());
        System.out.println("tojava(expr7) = " + tojava(expr7).toString());
        Cons expr8 = list("=", "x", list("*", "r", list("sin", "theta")));
        System.out.println("expr8 = " + expr8.toString());
        System.out.println("tojava(expr8) = " + tojava(expr8).toString());


       Cons set3 = list("d", "b", "c", "a");
      }

}
