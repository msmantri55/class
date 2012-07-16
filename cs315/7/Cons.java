/**
 * this class Cons implements a Lisp-like Cons cell
 *
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08; 23 Oct 08; 27 Mar 09
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
                : ( first(lst) == null ? "()" : first(lst).toString() )
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

public static boolean subsetp (Cons x, Cons y) {
    return ( (x == null) ? true
             : ( ( member(first(x), y) != null ) &&
                 subsetp(rest(x), y) ) ); }

public static boolean setEqual (Cons x, Cons y) {
    return ( subsetp(x, y) && subsetp(y, x) ); }

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

public static Cons formulas =
    list( list( "=", "s", list("*", new Double(0.5),
                               list("*", "a",
                                list("expt", "t", new Integer(2))))),
          list( "=", "s", list("+", "s0", list( "*", "v", "t"))),
          list( "=", "a", list("/", "f", "m")),
          list( "=", "v", list("*", "a", "t")),
          list( "=", "f", list("/", list("*", "m", "v"), "t")),
          list( "=", "f", list("/", list("*", "m",
                                         list("expt", "v", new Integer(2))),
                               "r")),
          list( "=", "h", list("-", "h0", list("*", new Double(4.94),
                                               list("expt", "t",
                                                    new Integer(2))))),
          list( "=", "c", list("sqrt", list("+",
                                            list("expt", "a",
                                                 new Integer(2)),
                                            list("expt", "b",
                                                 new Integer(2))))),
          list( "=", "v", list("*", "v0",
                               list("-", new Double(1.0),
                                    list("exp", list("/", list("-", "t"),
                                                     list("*", "r", "c"))))))
          );

    // Note: this list will handle most, but not all, cases.
    // The binary operators - and / have special cases.
public static Cons opposites =
    list( list( "+", "-"), list( "-", "+"), list( "*", "/"),
          list( "/", "*"), list( "sqrt", "expt"), list( "expt", "sqrt"),
          list( "log", "exp"), list( "exp", "log") );

public static void printanswer(String str, Object answer) {
    System.out.println(str +
                       ((answer == null) ? "null" : answer.toString())); }

	//
	// BEGIN my code
	//

	/*
	 * Finds a path through haystack to needle.
	 * Returns a list with elements "first" or "rest"; the last element is "done".
	 * Returns null if needle does not exist in haystack or either argument is null.
	 */
	public static Cons findpath (Object needle, Object haystack) {
		if (needle == null || haystack == null) {
			return null;
		}

		return reverse(findpath(needle, haystack, null));
	}

	/*
	 * Helper method for findpath.
	 * Reverses the order of elements in list.
	 */
	private static Cons reverse (Cons list) {
		Cons result = null;

		while (list != null) {
			result = cons(first(list), result);
			list = rest(list);
		}

		return result;
	}

	/*
	 * Recursive helper method for findpath.
	 * Performs a depth-first search of haystack for needle, storing the path in result until completion.
	 */
	private static Cons findpath (Object needle, Object haystack, Cons path) {
		if (consp(haystack)) {
			Cons result = findpath(needle, first((Cons) haystack), cons("first", path));

			if (result == null) {
				result = findpath(needle, rest((Cons) haystack), cons("rest", path));
			}

			return result;
		}
		else if (needle.equals(haystack)) {
			return cons("done", path);
		}

		return null;
	}

	/*
	 * Follows the directions in path to the element in haystack.
	 * The last element of the path ("done") is optional.
	 * Returns the element in haystack that corresponds to the path, or null on failure.
	 */
	public static Object follow (Cons path, Object haystack) {
		if (path == null || haystack == null) {
			return null;
		}

		while (path != null) {
			Object instruction = first(path);

			if ("first".equals(instruction)) {
				haystack = first((Cons) haystack);
			}
			else if ("rest".equals(instruction)) {
				haystack = rest((Cons) haystack);
			}

			path = rest(path);
		}

		return haystack;
	}

	/*
	 * Finds needle in a, then finds the element in b that exists at the same location.
	 * Returns the element in b that corresponds to needle in a, or null on failure.
	 */
	public static Object corresp (Object needle, Object a, Object b) {
		return follow(findpath(needle, a), b);
	}

	/*
	 * Rewrites equation to be in the form variable = ...
	 * Assumes variable only occurs in equation once, and that it is possible to rewrite in terms of variable.
	 * Supports: + - * / expt sqrt exp log
	 * For expt operator, the only supported power is 2 (i.e., x^2).
	 * Returns the new equation, or null on failure.
	 */
	public static Cons solve (Cons equation, String variable) {
		if (equation == null || variable == null) {
			return null;
		}

		Cons result = solve_helper(equation, variable);

		if (result == null) {
			result = solve_helper(list("=", rhs(equation), lhs(equation)), variable);
		}

		return result;
	}

	private static Cons solve_helper (Cons equation, String variable) {
		if (variable.equals(lhs(equation))) {
			return equation;
		}
		else if (variable.equals(rhs(equation))) {
			return list("=", variable, lhs(equation));
		}
		else if (consp(rhs(equation))) {
			Cons rhs = (Cons) rhs(equation);
			Object invop = first(rest(assoc(op(rhs), opposites)));

			if (rhs(rhs) == null) {
				if ("+".equals(invop)) {
					return solve_helper(list("=", list("-", lhs(equation)), lhs(rhs)), variable);
				}
				else if ("expt".equals(invop)) {
					return solve_helper(list("=", list(invop, lhs(equation), 2.0), lhs(rhs)), variable);
				}
				else {
					return solve_helper(list("=", list(invop, lhs(equation)), lhs(rhs)), variable);
				}
			}
			else {
				if ("sqrt".equals(invop)) {
					// kludgy, but only way to make 2 = 2.000... = "2.000..." = true
					if (Double.parseDouble(rhs(rhs).toString()) == 2.0) {
						return solve_helper(list("=", list(invop, lhs(equation)), lhs(rhs)), variable);
					}
				}
				else {
					Cons result = solve_helper(list("=", list(invop, lhs(equation), rhs(rhs)), lhs(rhs)), variable);

					if (result == null) {
						if ("*".equals(invop)) {
							result = solve_helper(list("=", list("/", lhs(rhs), lhs(equation)), rhs(rhs)), variable);
						}
						else if ("+".equals(invop)) {
							result = solve_helper(list("=", list("-", lhs(rhs), lhs(equation)), rhs(rhs)), variable);
						}
						else {
							result = solve_helper(list("=", list(invop, lhs(equation), lhs(rhs)), rhs(rhs)), variable);
						}
					}

					return result;
				}
			}
		}

		return null;
	}

	/*
	 * Scans formulas for an equation that contains variable and every variable in values.
	 * Proceeds to solve the equation using variable bindings and algebraic manipulation.
	 * Returns the value of variable evaluated as a double, or null on failure.
	 */
	public static Double solveit (Cons formulas, String variable, Cons values) {
		Cons equation = null;

		while (formulas != null && equation == null) {
			Cons vars = vars((Cons) first(formulas));

			if (member(variable, vars) != null) {
				Cons values_ = values;

				while (values_ != null) {
					if (member(first((Cons) first(values_)), vars) == null) {
						break;
					}

					values_ = rest(values_);
				}

				if (values_ == null) {
					equation = (Cons) first(formulas);
				}
			}

			formulas = rest(formulas);
		}

		return eval(rhs((Cons) solve(equation, variable)), values);
	}

	/*
	 * Helper method for solveit.
	 * Finds every variable in expr.
	 * Returns a list of variables (no repeats) in expr or null on failure.
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
	 * Helper method for solveit.
	 * Evaluates an expression tree whose leaves are either doubles or variables given in bindings.
	 * Adheres to GIGO policy. If a variable in expr isn't defined in bindings or the binding is not a double, bad things happen.
	 * Returns the value of expr as a double or 0.0 on failure.
	 */
	public static Double eval (Object expr, Cons bindings) {
		if (consp(expr)) {
			String op = (String) op((Cons) expr);
			Double lhs = eval(lhs((Cons) expr), bindings);
			Double rhs = eval(rhs((Cons) expr), bindings);

			if ("+".equals(op)) {
				return lhs + rhs;
			}
			else if ("-".equals(op)) {
				if (rhs((Cons) expr) == null) {
					return lhs * -1;
				}
				else {
					return lhs - rhs;
				}
			}
			else if ("*".equals(op)) {
				return lhs * rhs;
			}
			else if ("/".equals(op)) {
				return lhs / rhs;
			}
			else if ("expt".equals(op)) {
				return Math.pow(lhs, rhs);
			}
			else if ("sqrt".equals(op)) {
				return Math.sqrt(lhs);
			}
			else if ("exp".equals(op)) {
				return Math.exp(lhs);
			}
			else if ("log".equals(op)) {
				return Math.log(lhs);
			}
			else {
				// operator is unknown, result is 0.0
			}
		}
		else if (expr != null) {
			Cons assoc_value = assoc(expr, bindings);

			if (assoc_value == null) {
				return Double.parseDouble(expr.toString());
			}
			else {
				return (Double) first(rest(assoc_value));
			}
		}

		return 0.0;
	}

	//
	// END my code
	//

    public static void main( String[] args ) {
        Cons cave = list("rocks", "gold", list("monster"));
        Cons path = findpath("gold", cave);
        printanswer("cave = " , cave);
        printanswer("path = " , path);
        printanswer("follow = " , follow(path, cave));
        Cons treea = list(list("my", "eyes"),
                          list("have", "seen", list("the", "light")));
        Cons treeb = list(list("my", "ears"),
                          list("have", "heard", list("the", "music")));
        printanswer("treea = " , treea);
        printanswer("treeb = " , treeb);
        printanswer("corresp = " , corresp("light", treea, treeb));
        System.out.println("formulas = ");
        Cons frm = formulas;
        Cons vset = null;
        while ( frm != null ) {
            printanswer("   "  , ((Cons)first(frm)));
            vset = vars((Cons)first(frm));
            while ( vset != null ) {
                printanswer("       "  ,
                    solve((Cons)first(frm), (String)first(vset)) );
                vset = rest(vset); }
            frm = rest(frm); }

        printanswer("Tower: " , solveit(formulas, "h0",
                                            list(list("h", new Double(0.0)),
                                                 list("t", new Double(4.0)))));

        printanswer("Car: " , solveit(formulas, "a",
                                            list(list("v", new Double(88.0)),
                                                 list("t", new Double(8.0)))));

        printanswer("Capacitor: " , solveit(formulas, "c",
                                            list(list("v", new Double(3.0)),
                                                 list("v0", new Double(6.0)),
                                                 list("r", new Double(10000.0)),
                                                 list("t", new Double(5.0)))));

        printanswer("Ladder: " , solveit(formulas, "b",
                                            list(list("a", new Double(6.0)),
                                                 list("c", new Double(10.0)))));


      }
}
