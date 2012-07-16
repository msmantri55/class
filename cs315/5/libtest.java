// libtest.java      GSN    03 Oct 08
// 

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class libtest {
	//
	// BEGIN my code
	//
	
	/*
	 * Write a function sumlist(LinkedList<Integer> lst) that adds up a list of Integer.
	 */
	public static Integer sumlist (LinkedList<Integer> list) {
		Integer sum = 0;

		for (Integer item : list) {
			sum += item;
		}

		return sum;
	}
	
	/*
	 * Write a similar function sumarrlist(ArrayList<Integer> lst).
	 */
	public static Integer sumarrlist (ArrayList<Integer> list) {
		Integer sum = 0;

		for (Integer item : list) {
			sum += item;
		}

		return sum;
	}

	/*
	 * Write a function subset(Predicate p, LinkedList<Object> lst) that returns a new list containing only the values in lst that satisfy the predicate p.
	 */
	public static LinkedList<Object> subset (Predicate p, LinkedList<Object> list) {
		LinkedList<Object> result = new LinkedList<Object>();

		for (Object item : list) {
			if (p.pred(item)) {
				result.add(item);
			}
		}

		return result;
	}

	/*
	 * Write a destructive function dsubset(Predicate p, LinkedList<Object> lst) that removes from lst the values that do not satisfy the predicate p.
	 */
	public static LinkedList<Object> dsubset (Predicate p, LinkedList<Object> list) {
		// cannot use compact loop syntax because this removes items as it traverses
		for (Iterator itr = list.iterator(); itr.hasNext(); ) {
			Object item = itr.next();

			if (!p.pred(item)) {
				itr.remove();
			}
		}

		return list;
	}

	/*
	 * Write a function some(Predicate p, LinkedList<Object> lst) that returns the first item in lst that satisfies the predicate p.
	 */
	public static Object some (Predicate p, LinkedList<Object> list) {
		for (Object item : list) {
			if (p.pred(item)) {
				return item;
			}
		}

		return null;
	}

	/*
	 * Write a function mapcar(Functor f, LinkedList<Object> lst) that returns a new list containing the results of applying f.fn(item) to each item in the list lst. The output list should be in the same order as the original list.
	 */
	public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> list) {
		LinkedList<Object> result = new LinkedList<Object>();

		for (Object item : list) {
			result.add(f.fn(item));
		}

		return result;
	}

	/*
	 * Write a function merge(LinkedList<Object> lsta, LinkedList<Object> lstb) that returns a new list formed by merging the two input lists in order. The input lists are assumed to be in sorted order (ascending).
	 */
	public static LinkedList<Object> merge (LinkedList<Object> list_a, LinkedList<Object> list_b) {
		LinkedList<Object> result = new LinkedList<Object>();
		ListIterator itr_a = list_a.listIterator(), itr_b = list_b.listIterator();

		while (itr_a.hasNext() && itr_b.hasNext()) {
			Comparable item_a = (Comparable) list_a.get(itr_a.nextIndex());
			Comparable item_b = (Comparable) list_b.get(itr_b.nextIndex());
			int comparison = item_a.compareTo(item_b);

			if (comparison < 0) {
				result.add(itr_a.next());
			} else if (comparison > 0) {
				result.add(itr_b.next());
			} else {
				assert (comparison == 0);

				// NOTE: this allows duplicates (and is stable)
				result.add(itr_a.next());
				result.add(itr_b.next());
			}
		}

		while (itr_a.hasNext()) {
			result.add(itr_a.next());
		}

		while (itr_b.hasNext()) {
			result.add(itr_b.next());
		}

		return result;
	}

	/*
	 * Write a function sort(LinkedList<Object> lst) that produces a sorted list containing the elements of the input list. A list with only one element is sorted; otherwise, produce two new lists, each with half the input elements, sort and merge them.
	 */
	public static LinkedList<Object> sort (LinkedList<Object> list) {
		if (list.size() <= 1) {
			return list;
		}

		int middle = list.size() / 2, i = 0;
		LinkedList<Object> left = new LinkedList<Object>(), right = new LinkedList<Object>();

		for (Object item : list) {
			if (i++ < middle) {
				left.add(item);
			} else {
				right.add(item);
			}
		}

		left = sort(left);
		right = sort(right);

		return merge(left, right);
	}

	/*
	 * Write a function intersection(LinkedList<Object> lsta, LinkedList<Object> lstb) that returns a new list that is the set intersection of the two input lists. Sort the input lists first and use the merge technique to form the intersection.
	 */
	public static LinkedList<Object> intersection (LinkedList<Object> list_a, LinkedList<Object> list_b) {
		LinkedList<Object> result = new LinkedList<Object>();
		ListIterator itr_a = list_a.listIterator(), itr_b = list_b.listIterator();

		list_a = sort(list_a);
		list_b = sort(list_b);

		while (itr_a.hasNext() && itr_b.hasNext()) {
			Comparable item_a = (Comparable) list_a.get(itr_a.nextIndex());
			Comparable item_b = (Comparable) list_b.get(itr_b.nextIndex());
			int comparison = item_a.compareTo(item_b);

			if (comparison < 0) {
				itr_a.next();
			} else if (comparison > 0) {
				itr_b.next();
			} else {
				assert (comparison == 0);

				result.add(item_a);
				itr_a.next();
				itr_b.next();
			}
		}

		return result;
	}

	/*
	 * Write a function reverse(LinkedList<Object> lst) that produces a new list in the reverse order of the input list. The method addFirst(Object o) is an O(1) way to add an element at the front of a list, so that a linked list can be used as a stack as well as a queue.
	 */
	public static LinkedList<Object> reverse (LinkedList<Object> list) {
		LinkedList<Object> result = new LinkedList<Object>();

		for (Object item : list) {
			result.addFirst(item);
		}

		return result;
	}

	//
	// END my code
	//

    public static void main(String args[]) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(3));
        lst.add(new Integer(17));
        lst.add(new Integer(2));
        lst.add(new Integer(5));
        System.out.println("lst = " + lst.toString());
        System.out.println("sum = " + sumlist(lst));

        ArrayList<Integer> lstb = new ArrayList<Integer>();
        lstb.add(new Integer(3));
        lstb.add(new Integer(17));
        lstb.add(new Integer(2));
        lstb.add(new Integer(5));
        System.out.println("lstb = " + lstb.toString());
        System.out.println("sum = " + sumarrlist(lstb));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        LinkedList<Object> lstc = new LinkedList<Object>();
        lstc.add(new Integer(3));
        lstc.add(new Integer(17));
        lstc.add(new Integer(2));
        lstc.add(new Integer(5));
        System.out.println("lstc = " + lstc.toString());
        System.out.println("subset = " + subset(myp, lstc).toString());

        System.out.println("lstc     = " + lstc.toString());
	dsubset(myp, lstc);
        System.out.println("now lstc = " + lstc.toString());

        LinkedList<Object> lstd = new LinkedList<Object>();
        lstd.add(new Integer(3));
        lstd.add(new Integer(17));
        lstd.add(new Integer(2));
        lstd.add(new Integer(5));
        System.out.println("lstd = " + lstd.toString());
        System.out.println("some = " + some(myp, lstd).toString());
        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, lstd).toString());

        LinkedList<Object> lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(6));
        lste.add(new Integer(9));
        System.out.println("lste = " + lste.toString());
        LinkedList<Object> lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        System.out.println("lstf = " + lstf.toString());
        System.out.println("merge = " + merge(lste, lstf).toString());

        LinkedList<Object> lstg = new LinkedList<Object>();
        lstg.add(new Integer(39));
        lstg.add(new Integer(84));
        lstg.add(new Integer(5));
        lstg.add(new Integer(59));
        lstg.add(new Integer(86));
        lstg.add(new Integer(17));
        System.out.println("lstg = " + lstg.toString());

        System.out.println("intersection(lstd, lstg) = " + intersection(lstd, lstg).toString());
        System.out.println("reverse lste = " + reverse(lste).toString());
        System.out.println("sort(lstg) = " + sort(lstg).toString());
   }
}
