/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08; 23 Oct 08; 30 Oct 08; 03 Nov 08
 *          11 Nov 09; 18 Nov 08; 20 Nov 08
 */

import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Random;

interface Functor { Object fn(Object x); }
interface Predicate { boolean pred(Object x); }

interface Mapper { void map(String key, String value, MapReduce mr); }
interface Reducer { void reduce(String key, Cons value, MapReduce mr); }

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

public static Cons nreverse (Cons lst) {
  Cons last = null; Cons next;
  while (lst != null)
    { next =  rest(lst);
      setrest(lst, last);
      last = lst;
      lst = next; };
  return last; }

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
        if ( tok.charAt(0) >= '0' && tok.charAt(0) <= '9' ) {
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


	public static void main (String[] args) throws java.io.FileNotFoundException {
		//
		// 1. Line count
		// Code by proffesor Novak. I edited & reformatted it.
		//

		final Mapper map1 = new Mapper() {
			public void map (String k, String v, MapReduce mr) {
				mr.collect_map("line", list("1"));
			}
		};

		final Reducer red1 = new Reducer() {
			public void reduce (String k, Cons v, MapReduce mr) {
				int s = 0;
				Object tmp;

				for (Cons l = v; l != null; l = rest(l)) {
					tmp = first(l);
					tmp = first((Cons) tmp);
					tmp = Integer.decode((String) tmp);

					s += (Integer) tmp;
				}

				mr.collect_reduce(k, new Integer(s));
			}
		};

		// -------------------------------------------------------------
		// BEGIN my code
		// -------------------------------------------------------------
		
		//
		// 2. Letter count (case insensitive)
		//

		final Mapper map2 = new Mapper() {
			public void map (String k, String v, MapReduce mr) {
				v = v.toLowerCase();
				int l = v.length();

				for (int i = 0; i < l; i++) {
					char c = v.charAt(i);
					int o = (int) c;

					// a - z = 97 - 122
					if (o < 97 || o > 122) {
						continue;
					}

					mr.collect_map("" + c, list("1"));
				}
			}
		};

		final Reducer red2 = new Reducer() {
			public void reduce (String k, Cons v, MapReduce mr) {
				int s = 0;
				Object tmp;

				for (Cons l = v; l != null; l = rest(l)) {
					tmp = first(l);
					tmp = first((Cons) tmp);
					tmp = Integer.decode((String) tmp);

					s += (Integer) tmp;
				}

				mr.collect_reduce(k, new Integer(s));
			}
		};

		//
		// 3. Word count (case insensitive)
		//

		final Mapper map3 = new Mapper() {
			public void map (String k, String v, MapReduce mr) {
				v = v.toLowerCase();
				String[] words = v.split("[^a-z]");

				for (String word : words) {
					if ("".equals(word)) {
						continue;
					}

					mr.collect_map(word, list("1"));
				}
			}
		};

		final Reducer red3 = new Reducer() {
			public void reduce (String k, Cons v, MapReduce mr) {
				int s = 0;
				Object tmp;

				for (Cons l = v; l != null; l = rest(l)) {
					tmp = first(l);
					tmp = first((Cons) tmp);
					tmp = Integer.decode((String) tmp);

					s += (Integer) tmp;
				}

				mr.collect_reduce(k, new Integer(s));
			}
		};

		// 
		// 4. grep
		//

		final Mapper map4 = new Mapper() {
			public void map (String k, String v, MapReduce mr) {
				String n = mr.parameter();

				if (v.indexOf(n) == -1) {
					return;
				}

				mr.collect_map(n, list(k));
			}
		};

		final Reducer red4 = new Reducer() {
			public void reduce(String k, Cons v, MapReduce mr) {
				Cons r = null;
				Object tmp;

				for (Cons l = v; l != null; l = rest(l)) {
					tmp = first(l);
					tmp = first((Cons) tmp);
					tmp = Integer.decode((String) tmp);

					r = cons(tmp, r);
				}

				r = nreverse(r);

				mr.collect_reduce(k, r);
			}
		};

		//
		// 5. Index (case insensitive)
		//

		final Mapper map5 = new Mapper() {
			public void map(String k, String v, MapReduce mr) {
				v = v.toLowerCase();
				String[] words = v.split("[^a-z]");

				for (String word : words) {
					if ("".equals(word)) {
						continue;
					}

					mr.collect_map(word, list(k));
				}
			}
		};

		final Reducer red5 = new Reducer() {
			public void reduce(String k, Cons v, MapReduce mr) {
				Cons r = null;
				Object tmp;

				for (Cons l = v; l != null; l = rest(l)) {
					tmp = first(l);
					tmp = first((Cons) tmp);
					tmp = Integer.decode((String) tmp);

					r = cons(tmp, r);
				}

				r = nreverse(r);

				mr.collect_reduce(k, r);
			}
		};

		//
		// 6. Movie reviews
		//

		final Mapper map6 = new Mapper() {
			public void map(String k, String v, MapReduce mr) {
				String[] info = v.split(",");

				mr.collect_map(info[0], list(info[2]));
			}
		};

		final Reducer red6 = new Reducer() {
			public void reduce(String k, Cons v, MapReduce mr) {
				int t = 0;
				int n = 0;
				Object tmp;

				for (Cons l = v; l != null; l = rest(l)) {
					tmp = first(l);
					tmp = first((Cons) tmp);
					tmp = Integer.decode((String) tmp);

					t += (Integer) tmp;
					n++;
				}

				mr.collect_reduce(k, list((double) t / n, n));
			}
		};

		//
		// Tests
		//

		MapReduce mr1 = new MapReduce(map1, red1);
		Cons res1 = mr1.mapreduce("decind.txt", "");
		System.out.println("Result = " + res1.toString());

		MapReduce mr2 = new MapReduce(map2, red2);
		Cons res2 = mr2.mapreduce("decind.txt", "");
		System.out.println("Result = " + res2.toString());

		MapReduce mr3 = new MapReduce(map3, red3);
		Cons res3 = mr3.mapreduce("file1.txt", "");
		System.out.println("Result = " + res3.toString());

		MapReduce mr3b = new MapReduce(map3, red3);
		Cons res3b = mr3b.mapreduce("decind.txt", "");
		for (Cons lst = res3b; lst != null; lst = rest(lst)) {
			System.out.println("\t" + (Cons) first(lst));
		}

		MapReduce mr4 = new MapReduce(map4, red4);
		Cons res4 = mr4.mapreduce("decind.txt", "ther");
		System.out.println("Result = " + res4.toString());

		MapReduce mr5 = new MapReduce(map5, red5);
		Cons res5 = mr5.mapreduce("decind.txt", "");
		for (Cons lst = res5; lst != null; lst = rest(lst)) {
			System.out.println("\t" + (Cons) first(lst));
		}

		MapReduce mr6 = new MapReduce(map6, red6);
		Cons res6 = mr6.mapreduce("movies.txt", "");
		for (Cons lst = res6; lst != null; lst = rest(lst)) {
			System.out.println("\t" + (Cons) first(lst));
		}

		// -------------------------------------------------------------
		// END my code
		// -------------------------------------------------------------
	}
}
