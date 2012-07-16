/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 ;Oct 08; 23 Oct 08; 30 Oct 08; 03 Nov 08
 *          11 Nov 09; 18 Nov 08
 */

import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Random;

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
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

/* iterative version of length */
public static int length (Cons lst) {
  int n = 0;
  while ( lst != null ) {
    n++;
    lst = rest(lst); }
  return n; }

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

public static Object copy_tree(Object tree) {
    if ( consp(tree) )
        return cons(copy_tree(first((Cons) tree)),
                    (Cons) copy_tree(rest((Cons) tree)));
    return tree; }

public static Object subst(Object gnew, String old, Object tree) {
    if ( consp(tree) )
        return cons(subst(gnew, old, first((Cons) tree)),
                    (Cons) subst(gnew, old, rest((Cons) tree)));
    return (old.equals(tree)) ? gnew : tree; }

public static Object sublis(Cons alist, Object tree) {
    if ( consp(tree) )
        return cons(sublis(alist, first((Cons) tree)),
                    (Cons) sublis(alist, rest((Cons) tree)));
    if ( tree == null ) return null;
    Cons pair = assoc(tree, alist);
    return ( pair == null ) ? tree : second(pair); }

    // tree equality
public static boolean equal(Object tree, Object other) {
    if ( consp(tree) )
        return ( consp(other) &&
                 equal(first((Cons) tree), first((Cons) other)) &&
                 equal(rest((Cons) tree), rest((Cons) other)) );
    return eql(tree, other); }

    // simple equality test
public static boolean eql(Object tree, Object other) {
    return ( (tree == other) ||
             ( (tree != null) && (other != null) &&
               tree.equals(other) ) ); }

public static Cons dummysub = list(list("t", "t"));

public static Cons match(Object pattern, Object input) {
    return matchb(pattern, input, dummysub); }

public static Cons matchb(Object pattern, Object input, Cons bindings) {
    if ( bindings == null ) return null;
    if ( consp(pattern) )
        if ( consp(input) )
            return matchb( rest( (Cons) pattern),
                           rest( (Cons) input),
                           matchb( first( (Cons) pattern),
                                   first( (Cons) input),
                                   bindings) );
        else return null;
    if ( varp(pattern) ) {
        Cons binding = assoc(pattern, bindings);
        if ( binding != null )
            if ( equal(input, second(binding)) )
                return bindings;
            else return null;
        else return cons(list(pattern, input), bindings); }
    if ( eql(pattern, input) )
        return bindings;
    return null; }

public static Object reader(String str) {
    return readerb(new StringTokenizer(str, " \t\n\r\f()'", true)); }

public static Object readerb( StringTokenizer st ) {
    if ( st.hasMoreTokens() ) {
        String nexttok = st.nextToken();
        if ( nexttok.charAt(0) == ' ' ||
             nexttok.charAt(0) == '\t' ||
             nexttok.charAt(0) == '\n' ||
             nexttok.charAt(0) == '\r' ||
             nexttok.charAt(0) == '\f' )
            return readerb(st);
        if ( nexttok.charAt(0) == '(' )
            return readerlist(st);
        if ( nexttok.charAt(0) == '\'' )
            return list("QUOTE", readerb(st));
        return readtoken(nexttok); }
    return null; }

    public static Object readtoken( String tok ) {
        if ( (tok.charAt(0) >= '0' && tok.charAt(0) <= '9') ||
             ((tok.length() > 1) &&
              (tok.charAt(0) == '+' || tok.charAt(0) == '-' ||
               tok.charAt(0) == '.') &&
              (tok.charAt(1) >= '0' && tok.charAt(1) <= '9') ) ||
             ((tok.length() > 2) &&
              (tok.charAt(0) == '+' || tok.charAt(0) == '-') &&
              (tok.charAt(1) == '.') &&
              (tok.charAt(2) >= '0' && tok.charAt(2) <= '9') )  ) {
            boolean dot = false;
            for ( int i = 0; i < tok.length(); i++ )
                if ( tok.charAt(i) == '.' ) dot = true;
            if ( dot )
                return Double.parseDouble(tok);
            else return Integer.parseInt(tok); }
        return tok; }

public static Cons readerlist( StringTokenizer st ) {
    if ( st.hasMoreTokens() ) {
        String nexttok = st.nextToken();
        if ( nexttok.charAt(0) == ' ' ||
             nexttok.charAt(0) == '\t' ||
             nexttok.charAt(0) == '\n' ||
             nexttok.charAt(0) == '\r' ||
             nexttok.charAt(0) == '\f' )
            return readerlist(st);
        if ( nexttok.charAt(0) == ')' )
            return null;
        if ( nexttok.charAt(0) == '(' ) {
            Cons temp = readerlist(st);
            return cons(temp, readerlist(st)); }
        if ( nexttok.charAt(0) == '\'' ) {
            Cons temp = list("QUOTE", readerb(st));
            return cons(temp, readerlist(st)); }
        return cons( readtoken(nexttok),
                     readerlist(st) ); }
    return null; }

    // read a list of strings, producing a list of results.
public static Cons readlist( Cons lst ) {
    if ( lst == null )
        return null;
    return cons( reader( (String) first(lst) ),
                 readlist( rest(lst) ) ); }

public static Object transform(Cons patpair, Cons input) {
    Cons bindings = match(first(patpair), input);
    if ( bindings == null ) return null;
    return sublis(bindings, second(patpair)); }

    // Transform a list of arguments.  If no change, returns original.
public static Cons transformlst(Cons allpats, Cons input) {
    if ( input == null ) return null;
    Cons restt = transformlst(allpats, rest(input));
    Object thist = transformr(allpats, first(input));
    if ( thist == first(input) && restt == rest(input) )
        return input;
    return cons(thist, restt); }

    // Transform a single item.  If no change, returns original.
public static Object transformr(Cons allpats, Object input) {
    //    System.out.println("transformr:  " + input.toString());
    if ( consp(input) ) {
        Cons listt = transformlst(allpats, (Cons) input);
        //       System.out.println("   lst =  " + listt.toString());
        return transformrb(allpats, allpats,
                           transformlst(allpats, listt)); }
    Object res = transformrb(allpats, allpats, input);
    //    System.out.println("   result =  " + res.toString());
    return res; }

    // Transform a single item.  If no change, returns original.
public static Object transformrb(Cons pats, Cons allpats, Object input) {
    if ( pats == null ) return input;
    if ( input == null ) return null;
    Cons bindings = match(first((Cons)first(pats)), input);
    if ( bindings == null ) return transformrb(rest(pats), allpats, input);
    return sublis(bindings, second((Cons)first(pats))); }

    // Transform a single item repeatedly, until fixpoint (no change).
public static Object transformfp(Cons allpats, Object input) {
    //    System.out.println("transformfp: " + input.toString());
    Object trans = transformr(allpats, input);
    if ( trans == input ) return input;
    //    System.out.println("    result = " + trans.toString());
    return transformfp(allpats, trans); }          // potential loop!

public static boolean varp(Object x) {
    return ( stringp(x) &&
             ( ((String) x).charAt(0) == '?' ) ); }

    // Note: this list will handle most, but not all, cases.
    // The binary operators - and / have special cases.
public static Cons opposites = 
    list( list( "+", "-"), list( "-", "+"), list( "*", "/"),
          list( "/", "*"), list( "sqrt", "expt"), list( "expt", "sqrt"),
          list( "log", "exp"), list( "exp", "log") );

public static String opposite(String op) {
    Cons pair = assoc(op, opposites);
    if ( pair != null ) return (String) second(pair);
    return "error"; }

public static void javaprint(Object item, int tabs) {
    if ( item == null ) System.out.print("null");
    else if ( consp(item) ) javaprintlist((Cons) item, tabs);
    else if ( stringp(item) )
        if ( item.equals("zlparen") ) System.out.print("(");
        else if ( item.equals("zrparen") ) System.out.print(")");
        else if ( item.equals("zspace") ) System.out.print(" ");
        else if ( item.equals("znothing") ) ;
        else if ( item.equals("ztab") ) System.out.print("\t");
        else if ( item.equals("zreturn") ) System.out.println();
        else System.out.print((String)item);
    else System.out.print(item.toString()); }

public static void javaprintlist(Cons lst, int tabs) {
    if ( lst != null ) {
        if ( stringp(first(lst)) )
            if ( ((String)first(lst)).equals("ztab" ) ) tabs++;
            else if ( ((String)first(lst)).equals("zreturn" ) ) {
                System.out.println();
                for (int i = 0; i < tabs; i++) System.out.print("\t"); }
            else javaprint(first(lst), tabs);
        else javaprint(first(lst), tabs);
        javaprintlist(rest(lst), tabs); } }
          
public static Cons formulas = readlist( list( 
   "(= s (* 0.5 (* a (expt t 2))))",
   "(= s (+ s0 (* v t)))",
   "(= a (/ f m))",
   "(= v (* a t))",
   "(= f (/ (* m v) t))",
   "(= f (/ (* m (expt v 2)) r))",
   "(= h (- h0 (* 4.94 (expt t 2))))",
   "(= c (sqrt (+ (expt a 2) (expt b 2))))",
   "(= v (* v0 (- 1 (exp (/ (- t) (* r c))))))" ));

public static int fcount = 0;
public static Random random = new Random();

	// ---------------------------------------------------------------------
	// BEGIN my code
	// ---------------------------------------------------------------------

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

	public static Cons algpats = readlist(
		list(
			"((= ?x (+ ?y ?z)) (= (- ?x ?z) ?y))",
			"((= ?x (+ ?y ?z)) (= (- ?x ?y) ?z))",
			"((= ?x (- ?y)) (= (- ?x) ?y))",
			"((= ?x (- ?y ?z)) (= (+ ?x ?z) ?y))",
			"((= ?x (- ?y ?z)) (= (- ?y ?x) ?z))",
			"((= ?x (* ?y ?z)) (= (/ ?x ?z) ?y))",
			"((= ?x (* ?y ?z)) (= (/ ?x ?y) ?z))",
			"((= ?x (/ ?y ?z)) (= (* ?x ?z) ?y))",
			"((= ?x (/ ?y ?z)) (= (/ ?y ?x) ?z))",
			"((= ?x (sqrt ?y)) (= (expt ?x 2) ?y))",
			"((= ?x (expt ?y 2)) (= (sqrt ?x) ?y))",
			"((= ?x (log ?y)) (= (exp ?x) ?y))",
			"((= ?x (exp ?y)) (= (log ?x) ?y))"
		)
	);

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
			Cons patterns = algpats;

			while (patterns != null) {
				Cons transformation = (Cons) transform((Cons) first(patterns), equation);
				patterns = rest(patterns);

				if (transformation == null) {
					continue;
				}

				Cons solution = solve_helper(transformation, variable);

				if (solution == null) {
					continue;
				}

				return solution;
			}
		}

		return null;
	}

	public int hashCode () {
		if (this == null) {
			return 0;
		}

		return conshash(this);
	}

	public static int conshash (Object e) {
		Object first = first((Cons) e);
		Object rest = rest((Cons) e);

		int first_hash = (first == null) ? 0 : first.hashCode();
		int rest_hash = (rest == null) ? 0 : rest.hashCode();

		return (first_hash * 17) ^ (rest_hash * 127);
	}

	// Total time to execute an action
	public static int totaltime (Cons action) {
		Object first = first(action);
		Object rest = rest(action);

		if ("boomchucka".equals(first)) {
			return 2 * (Integer) first((Cons) rest);
		}
		if ("seq".equals(first)) {
			int time = 0;
			while (rest != null) {
				time += totaltime((Cons) first((Cons) rest));
				rest = rest((Cons) rest);
			}

			return time;
		}
		if ("sync".equals(first)) {
			int time = 0;
			while (rest != null) {
				int tmp = totaltime((Cons) first((Cons) rest));
				if (tmp > time) {
					time = tmp;
				}
				rest = rest((Cons) rest);
			}

			return time;
		}
		if ("repeat".equals(first)) {
			int n = (Integer) first((Cons) rest);

			return n * totaltime((Cons) first(rest((Cons) rest)));
		}

		return (Integer) first((Cons) rest);
	}

	// Start by putting the total program on the queue at time 0
	public static PriorityQueue<Event> initpq (Cons action) {
		PriorityQueue<Event> pq = new PriorityQueue<Event>();

		pq.add(new Event(action, 0));
		System.out.println("new program: " + action.toString());

		return pq;
	}

	// Add an event to the priority queue
	public static PriorityQueue<Event> addevent (PriorityQueue<Event> pq, Cons action, int time) {
		if (action != null) {
			pq.add(new Event(action, time));
		}

		return pq;
	}

	// If the queue is not empty, remove the next event and execute it
	public static void simulator (PriorityQueue<Event> pq) {
		System.out.print(simulatorb(pq));
	}

	// If the queue is not empty, remove the next event and execute it
	public static String simulatorb (PriorityQueue<Event> pq) {
		String res = "";
		while (pq.size() > 0) {
			Event e = pq.poll();
			int tm = e.time();
			Cons act = e.action();
			//     System.out.println(":" + tm + " " + act.toString());
			String s = execute(pq, act, tm);

			if (!s.equals("")) {
				res += " " + tm + " " + s + "\n";
			}
		}

		return res;
	}


	// Execute an event
	public static String execute (PriorityQueue<Event> queue, Cons action, int time) {
		Object command = first(action);

		if ("delay".equals(command)) {
			return "";
		}
		if ("moo".equals(command)) {
			return "moo";
		}
		if ("cowbell".equals(command)) {
			return "ding";
		}
		if ("cymbal".equals(command)) {
			return "cym";
		}
		if ("boom".equals(command)) {
			return "boom";
		}
		if ("chucka".equals(command)) {
			return "chucka";
		}
		if ("boomchucka".equals(command)) {
			int d = (Integer) first(rest(action)) / 2;
			queue.add(new Event(list("chucka", d), time + d));

			return "boom";
		}
		if ("tambourine".equals(command)) {
			int d = (Integer) first(rest(action));
			int r = (int) (d * random.nextDouble());

			if (r > 1) {
				queue.add(new Event(list("tambourine", r), time + d - r));
			}

			return "jingle";
		}
		if ("seq".equals(command)) {
			Cons actions = rest(action);
			Cons first = (Cons) first(actions);

			queue.add(new Event(first, time));

			if (rest(actions) != null) {
				Cons rest = cons("seq", rest(actions));
				queue.add(new Event(rest, time + totaltime(first)));
			}

			return "";
		}
		if ("sync".equals(command)) {
			Cons actions = rest(action);

			while (actions != null) {
				queue.add(new Event((Cons) first(actions), time));
				actions = rest(actions);
			}

			return "";
		}
		if ("repeat".equals(command)) {
			int n = (Integer) first(rest(action));
			Cons a = (Cons) first(rest(rest(action)));

			queue.add(new Event(a, time));

			if (n > 1) {
				queue.add(new Event(list("repeat", --n, a), time + totaltime(a)));
			}

			return "";
		}

		return null;
	}

	// ---------------------------------------------------------------------
	// END my code
	// ---------------------------------------------------------------------

    public static void main( String[] args ) {
        System.out.println("formulas = ");
        Cons frm = formulas;
        Cons vset = null;
        while ( frm != null ) {
            System.out.println("   "  + ((Cons)first(frm)).toString());
            vset = vars((Cons)first(frm));
            while ( vset != null ) {
                System.out.println("       "  +
                    solve((Cons)first(frm), (String)first(vset)) );
                vset = rest(vset); }
            frm = rest(frm); }

        Cons ls = (Cons) reader("(a b c ab +)");
        for (Cons ptr = ls; ptr != null; ptr = rest(ptr) ) {
            String s = (String) first(ptr);
            int shash = s.hashCode();
            System.out.println("Hash of " + s + " = " + shash); }

        System.out.println("Hash of " + ls.toString() + " = "
                           + ls.hashCode());

        fcount = 0;

        final Functor myf = new Functor()
            { public Object fn (Object x)
                { fcount++;
                  return Math.sqrt((Double) x); }};

        Memoizer mymem = new Memoizer(myf);
        Double[] vals = { 2.0, 3.0, 4.0, 2.0, 2.5, 3.0, 3.5};
        for (int i=0; i < vals.length; i++ )
            System.out.println("Fn of " + vals[i] + " = " +
                               mymem.call(vals[i]));
        System.out.println("Number of function calls = " + fcount);

        PriorityQueue pqa = initpq((Cons) reader("(seq (boom 4) (moo 4))"));
        simulator(pqa);

        PriorityQueue pqb = initpq((Cons) reader("(repeat 2 (seq (boom 4) (moo 4)))"));
        simulator(pqb);

        PriorityQueue pqc =
            initpq((Cons)
                   reader("(seq (repeat 2 (boomchucka 4)) (repeat 3 (cymbal 2)))"));
        simulator(pqc);

        PriorityQueue pqd =
            initpq((Cons)
                   reader("(tambourine 12)"));
        simulator(pqd);

        PriorityQueue pqe =
            initpq((Cons)
                   reader("(sync (tambourine 12) (seq (repeat 4 (boomchucka 4)) (repeat 3 (cowbell 2))) (repeat 3 (cymbal 6)))"));
        simulator(pqe);

        PriorityQueue pqf =
            initpq((Cons)
                   reader("(sync (repeat 2 (boomchucka 8)) (repeat 4 (cowbell 2)) (repeat 5 (tambourine 5)) (seq (delay 10) (moo 2)))"));
        simulator(pqf);

        PriorityQueue pqg =
            initpq((Cons)
                   reader("(repeat 2 (seq (repeat 2 (boomchucka 4)) (repeat 3 (cymbal 2))))"));
        simulator(pqg);

        PriorityQueue pqh =
            initpq((Cons)
                   reader("(repeat 2 (sync (seq (repeat 2 (boomchucka 4)) (repeat 3 (cymbal 2))) (seq (delay 10) (moo 2))))"));
        simulator(pqh);
/**/	}
}
