/*
 * Assignment 8: Patterns
 *
 * Authors:
 * 	Gordon Novak
 * 	Taylor Fausak
 * Last edit:
 * 	8 April 2009
 */

import java.util.StringTokenizer;

interface Functor {
	Object fn (Object x);
}

interface Predicate {
	boolean pred (Object x);
}

public class Cons {
	private Object car;
	private Cons cdr;

	public static Cons dummysub = list(
		list("t", "t")
	);

	public static Cons opposites = list(
		list("+", "-"), list("-", "+"),
		list("*", "/"), list("/", "*"),
		list("sqrt", "expt"), list("expt", "sqrt"),
		list("log", "exp"), list("exp", "log")
	);

	private Cons (Object first, Cons rest) {
		car = first;
		cdr = rest;
	}

	public static Cons cons (Object first, Cons rest) {
		return new Cons(first, rest);
	}

	public static boolean consp (Object x) {
		return (x != null) && (x instanceof Cons);
	}

	public static Object first (Cons list) {
		if (list == null) {
			return null;
		}

		return list.car;
	}

	public static Cons rest (Cons list) {
		if (list == null) {
			return null;
		}

		return list.cdr;
	}

	public static Object second (Cons x) {
		return first(rest(x));
	}

	public static Object third (Cons x) {
		return first(rest(rest(x)));
	}

	public static void setfirst (Cons x, Object i) {
		x.car = i;
	}

	public static void setrest (Cons x, Cons y) {
		x.cdr = y;
	}

	public static Cons list (Object ... elements) {
		Cons result = null;

		for (int i = elements.length - 1; i >= 0; i--) {
			result = cons(elements[i], result);
		}

		return result;
	}

	public static Object op (Cons x) {
		return first(x);
	}

	public static Object lhs (Cons x) {
		return second(x);
	}

	public static Object rhs (Cons x) {
		return third(x);
	}

	public static boolean numberp (Object x) {
		return (x != null) && ((x instanceof Integer) || (x instanceof Double));
	}

	public static boolean integerp (Object x) {
		return (x != null) && (x instanceof Integer);
	}

	public static boolean floatp (Object x) {
		return (x != null) && (x instanceof Double);
	}

	public static boolean stringp (Object x) {
		return (x != null) && (x instanceof String);
	}

	public String toString () {
		return "(" + toStringb(this);
	}

	public static String toString (Cons list) {
		return "(" + toStringb(list);
	}

	public static String toStringb (Cons list) {
		String result = null;

		if (list == null) {
			result = ")";
		}
		else {
			result = first(list).toString();

			if (rest(list) == null) {
				result = result + ")";
			}
			else {
				result = result + " " + toStringb(rest(list));
			}
		}

		return result;
	}

	public static int length (Cons list) {
		int result = 0;

		while (list != null) {
			result++;
			list = rest(list);
		}

		return result;
	}

	public static Cons member (Object item, Cons list) {
		if (item == null || list == null) {
			return null;
		}

		if (item.equals(first(list))) {
			return list;
		}

		return member(item, rest(list));
	}

	public static Cons union (Cons x, Cons y) {
		if (x == null) {
			return y;
		}

		if (member(first(x), y) != null) {
			return union(rest(x), y);
		}

		return cons(first(x), union(rest(x), y));
	}

	public static boolean subsetp (Cons x, Cons y) {
		if (x == null) {
			return true;
		}

		return member(first(x), y) != null && subsetp(rest(x), y);
	}

	public static boolean setEqual (Cons x, Cons y) {
		return subsetp(x, y) && subsetp(y, x);
	}

	public static Cons append (Cons x, Cons y) {
		if (x == null) {
			return y;
		}

		return cons(first(x), append(rest(x), y));
	}

	public static Cons assoc (Object key, Cons list) {
		if (key == null || list == null) {
			return null;
		}

		Cons first = (Cons) first(list);

		if (key.equals(first(first))) {
			return first;
		}

		return assoc(key, rest(list));
	}

	public static int square (int x) {
		return x * x;
	}

	public static int pow (int x, int y) {
		if (y <= 0) {
			return 1;
		}

		if ((y & 1) == 0) {
			return square(pow(x, y / 2));
		}

		return x * pow(x, --y);
	}

	public static Object copy_tree (Object tree) {
		if (consp(tree)) {
			return cons(copy_tree(first((Cons) tree)), (Cons) copy_tree(rest((Cons) tree)));
		}

		return tree;
	}

	public static Object subst (Object gnew, String old, Object tree) {
		if (consp(tree)) {
			return cons(subst(gnew, old, first((Cons) tree)), (Cons) subst(gnew, old, rest((Cons) tree)));
		}

		if (old.equals(tree)) {
			return gnew;
		}

		return tree;
	}

	public static Object sublis (Cons alist, Object tree) {
		if (consp(tree)) {
			return cons(sublis(alist, first((Cons) tree)), (Cons) sublis(alist, rest((Cons) tree)));
		}

		if (tree == null) {
			return null;
		}

		Cons pair = assoc(tree, alist);

		if (pair == null) {
			return tree;
		}

		return second(pair);
	}

	public static boolean equal (Object tree, Object other) {
		if (consp(tree)) {
			return consp(other) && equal(first((Cons) tree), first((Cons) other)) && equal(rest((Cons) tree), rest((Cons) other));
		}

		return eql(tree, other);
	}

	public static boolean eql (Object tree, Object other) {
		return (tree == other) || ((tree != null) && (other != null) && tree.equals(other));
	}

	public static Cons match (Object pattern, Object input) {
		return matchb(pattern, input, dummysub);
	}

	public static Cons matchb (Object pattern, Object input, Cons bindings) {
		if (bindings == null) {
			return null;
		}

		if (consp(pattern)) {
			if (consp(input)) {
				return matchb(rest((Cons) pattern), rest((Cons) input), matchb(first((Cons) pattern), first((Cons) input), bindings));
			}
		}

		if (varp(pattern)) {
			Cons binding = assoc(pattern, bindings);

			if (binding != null) {
				if (equal(input, second(binding))) {
					return bindings;
				}
			}
			else {
				return cons(list(pattern, input), bindings);
			}
		}

		if (eql(pattern, input)) {
			return bindings;
		}

		return null;
	}

	public static Object reader (String str) {
		return readerb(new StringTokenizer(str, " \t\n\r\f()'", true));
	}

	public static Object readerb (StringTokenizer st) {
		if (st.hasMoreTokens()) {
			String nexttok = st.nextToken();

			if (nexttok.charAt(0) == ' ' || nexttok.charAt(0) == '\t' || nexttok.charAt(0) == '\n' || nexttok.charAt(0) == '\r' || nexttok.charAt(0) == '\f') {
				return readerb(st);
			}

			if (nexttok.charAt(0) == '(') {
				return readerlist(st);
			}

			if (nexttok.charAt(0) == '\'') {
				return list("QUOTE", readerb(st));
			}

			return readtoken(nexttok);
		}

		return null;
	}

	public static Object readtoken (String tok) {
		if (tok.charAt(0) >= '0' && tok.charAt(0) <= '9') {
			boolean dot = false;

			for (int i = 0; i < tok.length(); i++) {
				if (tok.charAt(i) == '.') {
					dot = true;
				}
			}

			if (dot) {
				return Double.parseDouble(tok);
			}

			return Integer.parseInt(tok);
		}

		return tok;
	}

	public static Cons readerlist (StringTokenizer st) {
		if (st.hasMoreTokens()) {
			String nexttok = st.nextToken();

			if (nexttok.charAt(0) == ' ' || nexttok.charAt(0) == '\t' || nexttok.charAt(0) == '\n' || nexttok.charAt(0) == '\r' || nexttok.charAt(0) == '\f') {
				return readerlist(st);
			}

			if (nexttok.charAt(0) == ')') {
				return null;
			}

			if (nexttok.charAt(0) == '(') {
				Cons temp = readerlist(st);
				return cons(temp, readerlist(st));
			}

			if (nexttok.charAt(0) == '\'') {
				Cons temp = list("QUOTE", readerb(st));
				return cons(temp, readerlist(st));
			}

			return cons(readtoken(nexttok), readerlist(st));
		}

		return null;
	}

	public static Cons readlist (Cons lst) {
		if (lst == null) {
			return null;
		}

		return cons(reader((String) first(lst)), readlist(rest(lst)));
	}

	public static Object transform (Cons patpair, Cons input) {
		Cons bindings = match(first(patpair), input);

		if (bindings == null) {
			return null;
		}

		return sublis(bindings, second(patpair));
	}

	public static Cons transformlst (Cons allpats, Cons input) {
		if (input == null) {
			return null;
		}

		Cons restt = transformlst(allpats, rest(input));
		Object thist = transformr(allpats, first(input));

		if (thist == first(input) && restt == rest(input)) {
			return input;
		}

		return cons(thist, restt);
	}

	public static Object transformr (Cons allpats, Object input) {
		if (consp(input)) {
			Cons listt = transformlst(allpats, (Cons) input);
			return transformrb(allpats, allpats, transformlst(allpats, listt));
		}

		return transformrb(allpats, allpats, input);
	}

	public static Object transformrb (Cons pats, Cons allpats, Object input) {
		if (pats == null) {
			return input;
		}

		if (input == null) {
			return null;
		}

		Cons bindings = match(first((Cons)first(pats)), input);

		if (bindings == null) {
			return transformrb(rest(pats), allpats, input);
		}

		return sublis(bindings, second((Cons)first(pats)));
	}

	public static Object transformfp (Cons allpats, Object input) {
		Object trans = transformr(allpats, input);

		if (trans == input) {
			return input;
		}

		return transformfp(allpats, trans);
	}

	public static boolean varp (Object x) {
		return stringp(x) && (((String) x).charAt(0) == '?');
	}

	public static String opposite (String op) {
		Cons pair = assoc(op, opposites);

		if (pair != null) {
			return (String) second(pair);
		}

		return "error";
	}

	public static void javaprint (Object item, int tabs) {
		if (item == null) {
			System.out.print("null");
		}
		else if (consp(item)) {
			javaprintlist((Cons) item, tabs);
		}
		else if (stringp(item)) {
			if (item.equals("zlparen")) {
				System.out.print("(");
			}
			else if (item.equals("zrparen")) {
				System.out.print(")");
			}
			else if (item.equals("zspace")) {
				System.out.print(" ");
			}
			else if (item.equals("znothing")) {
				// nothing
			}
			else if (item.equals("ztab")) {
				System.out.print("\t");
			}
			else if (item.equals("zreturn")) {
				System.out.print("\n");
			}
			else {
				System.out.print((String)item);
			}
		}
		else {
			System.out.print(item.toString());
		}
	}

	public static void javaprintlist (Cons lst, int tabs) {
		if ( lst != null ) {
			if (stringp(first(lst))) {
				if (((String)first(lst)).equals("ztab")) {
					tabs++;
				}
				else if (((String)first(lst)).equals("zreturn")) {
					System.out.print("\n");

					for (int i = 0; i < tabs; i++) {
						System.out.print("\t");
					}
				}
				else {
					javaprint(first(lst), tabs);
				}
			}
			else {
				javaprint(first(lst), tabs);
			}

			javaprintlist(rest(lst), tabs);
		}
	}

	//
	// BEGIN my code
	//

	//
	// BEGIN code from previous assignment
	//

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
	// END code from previous assignment
	//

	public static Double solveqns (Cons formulas, Cons values, String variable) {
		if (formulas == null || values == null || variable == null) {
			return null;
		}

		Cons values_ = values;
		while (values_ != null) {
			Cons value = (Cons) first(values_);

			if (variable.equals(first(value))) {
				return (Double) first(rest(value));
			}

			values_ = rest(values_);
		}

		Cons formulas_ = formulas;
		while (formulas_ != null) {
			Cons equation = (Cons) first(formulas_);
			Cons vars = vars(equation);
			String unknown = null;

			while (vars != null) {
				if (assoc(first(vars), values) == null) {
					if (unknown == null) {
						unknown = first(vars).toString();
					}
					else {
						unknown = null;
						break;
					}
				}

				vars = rest(vars);
			}

			if (unknown != null) {
				values = cons(list(unknown, eval(rhs((Cons) solve(equation, unknown)), values)), values);
				return solveqns(formulas, values, variable);
			}

			formulas_ = rest(formulas_);
		}

		return null;
	}

	public static Cons substitutions = readlist(
		list(
			"((?function addnums) (?combine +) (?baseanswer (if (numberp tree) tree 0)))",
			"((?function countstrings) (?combine +) (?baseanswer (if (stringp tree) 1 0)))",
			"((?function copytree) (?combine cons) (?baseanswer tree))",
			"((?function mintree) (?combine min) (?baseanswer (if (numberp tree) tree nil)))",
			"((?function conses) (?combine add1) (?baseanswer (if (consp tree) 1 0)))"
		)
	);

	public static Cons optpats = readlist(
		list(
			"((+ ?x 0) ?x)",
			"((+ 0 ?x) ?x)",
			"((+ ?x ?x) (* 2 ?x))",

			"((- (- ?x)) ?x)",

			"((- ?x 0) ?x)",
			"((- 0 ?x) (- ?x))",
			"((- ?x ?x) 0)",

			"((* ?x 0) 0)",
			"((* 0 ?x) 0)",
			"((* ?x 1) ?x)",
			"((* 1 ?x) ?x)",
			"((* ?x ?x) (expt ?x 2))",

			"((/ 0 ?x) 0)",
			"((/ ?x 1) ?x)",
			"((/ ?x ?x) 1)",

			"((expt ?x 0) 1)",
			"((expt 0 ?x) 0)",
			"((expt ?x 1) ?x)",
			"((expt 1 ?x) 1)",

			"((sqrt (expt ?x 2)) ?x)",

			"((exp 0) 1)",
			"((exp (log ?x)) ?x)",

			"((log 1) 0)",
			"((log (exp ?x)) ?x)",

			"((- 2 1) 1)",
			"((- 3 1) 2)",
			"((- 4 1) 3)",
			"((- (- ?x ?y)) (- ?y ?x))",
			"((* (/ 1 2) (* 2 ?x)) ?x)"
		)
	);

	public static Cons derivpats = readlist(
		list(
			"((deriv ?x ?x) 1)",
			"((deriv (+ ?u ?v) ?x) (+ (deriv ?u ?x) (deriv ?v ?x)))",
			"((deriv (- ?u ?v) ?x) (- (deriv ?u ?x) (deriv ?v ?x)))",
			"((deriv (- ?v) ?x) (- (deriv ?v ?x)))",
			"((deriv (* ?u ?v) ?x) (+ (* ?u (deriv ?v ?x)) (* ?v (deriv ?u ?x))))",
			"((deriv (/ ?u ?v) ?x) (/ (- (* ?v (deriv ?u ?x)) (* ?u (deriv ?v ?x))) (expt ?v 2)))",
			"((deriv (expt ?u ?c) ?x) (* (* ?c (expt ?u (- ?c 1))) (deriv ?u ?x)))",
			"((deriv (sqrt ?u) ?x) (/ (* (/ 1 2) (deriv ?u ?x)) (sqrt ?u)))",
			"((deriv (exp ?u) ?x) (* (exp ?u) (deriv ?u ?x)))",
			"((deriv (log ?u) ?x) (/ (deriv ?u ?x) ?u))",
			"((deriv (sin ?u) ?x) (* (cos ?u) (deriv ?u ?x)))",
			"((deriv (cos ?u) ?x) (* (- (sin ?u)) (deriv ?u ?x)))",
			"((deriv (tan ?u) ?x) (* (+ 1 (expt (tan ?u) 2) (deriv ?u ?x)))",
			"((deriv2 ?u ?x) (deriv (deriv ?u ?x) ?x))",
			"((deriv ?y ?x) 0)"
		)
	);

	public static Cons javarestructpats = readlist(
		list(
			"( (return (if ?test ?then)) (if ?test (return ?then)) )",
			"( (return (if ?test ?then ?else)) (if ?test (return ?then) (return  ?else)) )",
			"( (defun ?fun ?args ?code) (zdefun ?fun (arglist ?args) (progn (return ?code))) )",
			"( (setq ?x (+ ?x 1)) (incf ?x) )"
		)
	);

	public static Cons javapats = readlist(
		list(
			"((setq ?x ?y) (znothing ?x zspace = zspace ?y ; zreturn)",

			"((+ ?x ?y) (znothing zlparen ?x zspace + zspace ?y zrparen))",
			"((- ?x ?y) (znothing zlparen ?x zspace - zspace ?y zrparen))",
			"((* ?x ?y) (znothing zlparen ?x zspace * zspace ?y zrparen))",
			"((/ ?x ?y) (znothing zlparen ?x zspace / zspace ?y zrparen))",
			"((% ?x ?y) (znothing zlparen ?x zspace % zspace ?y zrparen))",

			// ? unary plus
			"((- ?x) (znothing - ?x))",
			"((incf ?x) (znothing ?x + +))",
			"((decf ?x) (znothing ?x - -))",
			"((not ?x) (znothing ! ?x))",

			"((equal ?x ?y) (znothing zlparen ?x zspace == zspace ?y zrparen))", // ? eq, eql, equalp
			// ? not equal to
			"((> ?x ?y) (znothing zlparen ?x zspace > zspace ?y zrparen))",
			"((>= ?x ?y) (znothing zlparen ?x zspace >= zspace ?y zrparen))",
			"((< ?x ?y) (znothing zlparen ?x zspace < zspace ?y zrparen))",
			"((<= ?x ?y) (znothing zlparen ?x zspace <= zspace ?y zrparen))",

			"((and ?x ?y) (znothing zlparen ?x zspace && zspace ?y zrparen))",
			"((or ?x ?y) (znothing zlparen ?x zspace || zspace ?y zrparen))",
			// ? ternary

			"((lognot ?x) (znothing ~ ?x))",
			// ? signed left shift
			// ? signed right shift
			// ? unsigned right shift
			"((logand ?x ?y) (znothing zlparen ?x zspace & zspace ?y zrparen))",
			"((logxor ?x ?y) (znothing zlparen ?x zspace ^ zspace ?y zrparen))",
			"((logior ?x ?y) (znothing zlparen ?x zspace | zspace ?y zrparen))",

			"((if ?test ?then) (znothing if zspace zlparen ?test zrparen zspace { ztab zreturn ?then zreturn }))",
			"((if ?test ?then ?else) (znothing if zspace zlparen ?test zrparen zspace { ztab zreturn ?then zreturn } zreturn else zspace { zreturn ?else zreturn }))",

			"((zdefun ?fun ?args ?code) (znothing public zspace static zspace Object zspace ?fun zspace ?args zreturn ?code zreturn))",
			"((arglist (?x)) (znothing zlparen ?x zrparen))",
			"((progn ?x) (znothing { ztab zreturn ?x zreturn }))",
			"((return ?x) (znothing return zspace ?x ;))",

			"((first ?x) (znothing first zlparen zlparen Cons zrparen zspace ?x zrparen))",
			"((cons ?x ?y) (znothing cons zlparen ?x , zspace zlparen Cons zrparen zspace ?y zrparen))",

			"((expt ?x ?y) (znothing Math.pow zlparen ?x , zspace ?y zrparen))",
			"((min ?x ?y) (znothing Math.min zlparen ?x , zspace ?y zrparen))",
			"((max ?x ?y) (znothing Math.max zlparen ?x , zspace ?y zrparen))",
			"((sqrt ?x) (znothing Math.sqrt zlparen ?x zrparen))",
			"((exp ?x) (znothing Math.exp zlparen ?x zrparen))",
			"((log ?x) (znothing Math.log zlparen ?x zrparen))",

			"((?fun ?x) (znothing ?fun zlparen ?x zrparen))"
		)
	);

	//
	// END my code
	//

	public static void main (String[] args) {
		Cons eqnsbat = readlist(
			list(
				"(= loss_voltage (* internal_resistance current))",
				"(= loss_power (* internal_resistance (expt current 2)))",
				"(= terminal_voltage (- voltage loss_voltage))",
				"(= power (* terminal_voltage current))",
				"(= work (* charge terminal_voltage))"
			)
		);

		// expected result: 10.8
		System.out.println("battery = " + solveqns(eqnsbat, (Cons) reader("((current 0.3) (internal_resistance 4.0) (voltage 12.0))"), "terminal_voltage"));

		Cons eqnscirc = readlist(
			list(
				"(= acceleration (/ (expt velocity 2) radius))",
				"(= force (* mass acceleration))",
				"(= kinetic_energy (* (/ mass 2) (expt velocity 2)))",
				"(= moment_of_inertia (* mass (expt radius 2)))",
				"(= omega (/ velocity radius))",
				"(= angular_momentum (* omega moment_of_inertia))"
			)
		);

		// expected result: 24
		System.out.println("circular = " + solveqns(eqnscirc, (Cons) reader("((radius 4.0) (mass 2.0) (velocity 3.0))"), "angular_momentum"));

		Cons eqnslens = readlist(
			list(
				"(= focal_length (/ radius 2))",
				"(= (/ 1 focal_length) (+ (/ 1 image_distance) (/ 1 subject_distance)))",
				"(= magnification (- (/ image_distance subject_distance)))",
				"(= image_height (* magnification subject_height))"
			)
		);

		// expected result: 3
		System.out.println("magnification = " + solveqns(eqnslens, (Cons) reader("((subject_distance 6.0) (focal_length 9.0))"), "magnification"));

		Cons eqnslift = readlist(
			list(
				"(= gravity 9.80665 )",
				"(= weight (* gravity mass))",
				"(= force weight)",
				"(= work (* force height))",
				"(= speed (/ height time))",
				"(= power (* force speed))",
				"(= power (/ work time)) ))"
			)
		);

		// expected result: 560
		System.out.println("power = " + solveqns(eqnslift, (Cons) reader("((weight 700.0) (height 8.0) (time 10.0)))"), "power"));

		Cons binaryfn = (Cons) reader("(defun ?function (tree) (if (consp tree) (?combine (?function (first tree)) (?function (rest tree))) ?baseanswer))");

		for (Cons ptr = substitutions; ptr != null; ptr = rest(ptr)) {
			Object trans = sublis((Cons) first(ptr), binaryfn);

			System.out.println("sublis: " + trans.toString());
		}

		Cons opttests = readlist(
			list(
				"(+ 0 foo)",
				"(* fum 1)",
				"(- (- y))",
				"(- (- x y))",
				"(+ foo (* fum 0))"
				)
		);

		for (Cons ptr = opttests; ptr != null; ptr = rest(ptr)) {
			System.out.println("opt:  " + ((Cons)first(ptr)).toString());
			Object trans = transformfp(optpats, first(ptr));
			System.out.println("      " + trans.toString());
		}


		Cons derivtests = readlist(
			list(
				"(deriv x x)",
				"(deriv 3 x)",
				"(deriv z x)",
				"(deriv (+ x 3) x)",
				"(deriv (* x 3) x)",
				"(deriv (* 5 x) x)",
				"(deriv (sin x) x)",
				"(deriv (sin (* 2 x)) x)",
				"(deriv2 (+ (expt x 2) (+ (* 3 x) 6)) x)",
				"(deriv (sqrt (+ (expt x 2) 2)) x)",
				"(deriv (log (expt (+ 1 x) 3)) x)"
			)
		);

		for (Cons ptr = derivtests; ptr != null; ptr = rest(ptr)) {
			System.out.println("deriv:  " + ((Cons)first(ptr)).toString());
			Object trans = transformfp(derivpats, first(ptr));
			System.out.println("  der:  " + trans.toString());
			Object transopt = transformfp(optpats, trans);
			System.out.println("  opt:  " + transopt.toString());
		}

		Cons javatests = readlist(
			list(
				"(* fum 7)",
				"(setq x y)",
				"(setq x (+ x 1))",
				"(setq area (* pi (expt r 2)))",
				"(if (> x 7) (setq y x))",
				"(if (or (> x 7) (< y 3)) (setq y x))",
				"(if (and (> x 7) (not (< y 3))) (setq y x))",
				"(defun factorial (n) (if (<= n 1) 1 (* n (factorial (- n 1)))))"
			)
		);

		for (Cons ptr = javatests; ptr != null; ptr = rest(ptr)) {
			System.out.println("java:  " + ((Cons)first(ptr)).toString());
			Cons restr = (Cons) transformfp(javarestructpats, first(ptr));
			System.out.println("       " + restr.toString());
			Cons trans = (Cons) transformfp(javapats, restr);
			System.out.println("       " + trans.toString());
			javaprintlist(trans, 0);
			System.out.println();
		}


		for (Cons ptr = substitutions; ptr != null; ptr = rest(ptr)) {
			Object ltrans = sublis((Cons) first(ptr), binaryfn);
			System.out.println("sublis:  " + ltrans.toString());
			Cons restr = (Cons) transformfp(javarestructpats, ltrans);
			System.out.println("       " + restr.toString());
			Cons trans = (Cons) transformfp(javapats, restr);
			System.out.println("       " + trans.toString());
			javaprintlist(trans, 0);
			System.out.println();
		}
	}
}
