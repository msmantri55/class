/*
	Student information for assignment 9
	
	Name: Taylor Fausak
	EID: tdf268
	E-mail address: tfausak@gmail.com
	TA name: Vishvas
	
	Slip days information
	
	Slip days used on this assignment: 0
	Slip days I think I have used for the term thus far: 
*/

// Imports
import java.util.Iterator;

public class LinkedList implements IList {
	private DoubleListNode _head;
	private DoubleListNode _tail;
	private int _size;
	
	/**
	 * O(1)
	 */
	public LinkedList () {
		_head = new DoubleListNode();
		_tail = new DoubleListNode();
		_head.setNext(_tail);
		_tail.setPrev(_head);
		_size = 0;
	}
	
	/**
	 * O(1)
	 * Add an item to the end of this list.
	 * <br>pre: none
	 * <br>post: size() = old size() + 1, get(size() - 1) = item
	 * @param item the data to be added to the end of this list
	 */
	public void add (Object item) {
		DoubleListNode cursor = _tail.getPrev();
		DoubleListNode tmp = new DoubleListNode(item, cursor.getNext(), cursor);
		_tail.setPrev(tmp);
		cursor.setNext(tmp);
		_size++;
	}
	
	/**
	 * O(N)
	 * Add an item at a specified position in the list.
	 * <br>pre: 0 <= pos <= size()
	 * <br>post: size() = old size() + 1, get(pos) = item, all elements in
	 * the list with a positon >= pos have a position = old position + 1
	 * @param pos the position to insert the data at in the list
	 * @param item the data to add to the list
	*/
	public void add (int pos, Object item) {
		assert (pos >= 0 && pos <= this.size()) : "violation of precondition: add";
		
		DoubleListNode cursor;
		DoubleListNode tmp;
		
		if (pos > this.size() / 2) {
			cursor = _tail;
			for (int i = this.size(); i >= pos; i--) {
				cursor = cursor.getPrev();
			}
		} else {
			cursor = _head;
			for (int i = 0; i < pos; i++) {
				cursor = cursor.getNext();
			}
		}
		
		tmp = new DoubleListNode(item, cursor.getNext(), cursor);
		cursor.getNext().setPrev(tmp);
		cursor.setNext(tmp);
		_size++;
	}

	/**
	 * O(N)
	 * Change the data at the specified position in the list.
	 * the old data at that position is returns
	 * <br>pre: 0 <= pos < size()
	 * <br>post: get(pos) = item, return the
	 * old get(pos)
     * @param pos the position in the list to overwrite	 
	 * @param item the new item that will overwrite the old item
	 * @return the old data at the specified position
	 */
	public Object set (int pos, Object item) {
		assert (pos >= 0 && pos < this.size()) : "violation of precondtion: set";
		
		DoubleListNode cursor;
		Object oldData;
		
		if (pos > this.size() / 2) {
			cursor = _tail;
			for (int i = this.size(); i > pos; i--) {
				cursor = cursor.getPrev();
			}
		} else {
			cursor = _head;
			for (int i = 0; i <= pos; i++) {
				cursor = cursor.getNext();
			}
		}
		
		oldData = cursor.getData();
		cursor.setData(item);
		
		return oldData;
	}

	/**
	 * O(N)
	 * Get an element from the list.
	 * <br>pre: 0 <= pos < size()
	 * <br>post: return the item at pos
	 * @param pos specifies which element to get
	 * @return the element at the specified position in the list
	 */
	public Object get (int pos) {
		assert (pos >= 0 && pos < this.size()) : "violation of precondtion: get";
		
		DoubleListNode cursor;
		
		if (pos > this.size() / 2) {
			cursor = _tail;
			for (int i = this.size(); i > pos; i--) {
				cursor = cursor.getPrev();
			}
		} else {
			cursor = _head;
			for (int i = 0; i <= pos; i++) {
				cursor = cursor.getNext();
			}
		}
		
		return cursor.getData();
	}

	/**
	 * O(N)
	 * Remove an element in the list based on position.
	 * <br>pre: 0 <= pos < size()
	 * <br>post: size() = old size() - 1, all elements of
	 * list with a positon > pos have a position = old position - 1
	 * @param pos the position of the element to remove from the list
	 * @return the data at position pos
	 */
	public Object remove (int pos) {
		assert (pos >= 0 && pos < this.size()) : "violation of precondtion: remove";
		
		DoubleListNode cursor;
		Object oldData;
		
		if (pos > this.size() / 2) {
			cursor = _tail;
			for (int i = this.size(); i > pos; i--) {
				cursor = cursor.getPrev();
			}
		} else {
			cursor = _head;
			for (int i = 0; i <= pos; i++) {
				cursor = cursor.getNext();
			}
		}
		
		cursor.getNext().setPrev(cursor.getPrev());
		cursor.getPrev().setNext(cursor.getNext());
		oldData = cursor.getData();
		cursor.setNext(null);
		cursor.setPrev(null);
		cursor.setData(null);
		_size--;
		
		return oldData;
	}

	/**
	 * O(N)
	 * Remove the first occurrence of obj in this list.
	 * Return <tt>true</tt> if this list changed as a result of this call, <tt>false</tt> otherwise.
	 * <br>pre: none
	 * <br>post: if obj is in this list the first occurence has been removed and size() = old size() - 1. 
	 * If obj is not present the list is not altered in any way.
	 * @param obj The item to remove from this list.
	 * @return Return <tt>true</tt> if this list changed as a result of this call, <tt>false</tt> otherwise.
	 */
	public boolean remove (Object obj) {
		boolean result = false;
		int index = this.indexOf(obj);
		
		if (index != -1) {
			this.remove(index);
			result = true;
		}
		
		return result;
	}

	/**
	 * O(N)
	 * Return a sublist of elements in this list from <tt>start</tt> inclusive to <tt>stop</tt> exclusive.
	 * This list is not changed as a result of this call.
	 * <br>pre: <tt>0 <= start < size(), start <= stop <= size()</tt>
	 * <br>post: return a list whose size is stop - start and contains the elements at positions start through stop - 1 in this list.
	 * @param start index of the first element of the sublist.
	 * @param stop stop - 1 is the index of the last element of the sublist.
	 * @return a list with <tt>stop - start</tt> elements, The elements are from positions <tt>start</tt> inclusive to
	 * <tt>stop</tt> exclusive in this list.
	 */
	public IList getSubList (int start, int stop) {
		assert (start >= 0 && start < this.size() && start <= stop && stop <= this.size()) : "violation of precondition: getSubList";
		
		LinkedList list = new LinkedList();
		Iterator it = this.iterator();
		
		if (start - stop == 0) {
			list.add(this.get(start));
		} else {
			for (int i = 0; i < start; i++) {
				it.next();
			}
			
			for (int i = start; i < stop; i++) {
				list.add(it.next());
			}
		}
		
		return list;
	}

	/**
	 * O(1)
	 * Return the size of this list. In other words the number of elements in this list.
	 * <br>pre: none
	 * <br>post: return the number of items in this list
	 * @return the number of items in this list
	 */
	public int size () {
		return _size;
	}

	/**
	 * O(N)
	 * Find the position of an element in the list.
	 * <br>pre: none
	 * <br>post: return the index of the first element equal to item
	 * or -1 if item is not present
	 * @param item the element to search for in the list
	 * @return return the index of the first element equal to item or a -1 if item is not present
	 */
	public int indexOf (Object item) {
		return this.indexOf(item, 0);
	}

	/**
	 * O(N)
	 * find the position of an element in the list starting at a specified position.
	 * <br>pre: 0 <= pos < size()
	 * <br>post: return the index of the first element equal to item starting at pos
	 * or -1 if item is not present from position pos onward
	 * @param item the element to search for in the list
	 * @param pos the position in the list to start searching from
	 * @return starting from the specified position return the index of the first element equal to item or a -1 if item is not present between pos and the end of the list
	 */
	public int indexOf (Object item, int pos) {
		assert (pos >= 0 && pos < this.size()) : "violation of precondition: indexOf";
		
		DoubleListNode cursor = _head;
		Iterator it = this.iterator();
		int index = -1;
		
		for (int i = 0; i < pos; i++) {
			cursor = cursor.getNext();
			it.next();
		}
		
		for (int i = pos; it.hasNext() && index == -1; i++) {
			cursor = cursor.getNext();
			if (it.next() == item) {
				index = i;
			}
		}
		
		return index;
	}

	/**
	 * O(1)
	 * return the list to an empty state.
	 * <br>pre: none
	 * <br>post: size() = 0
	 */
	public void makeEmpty () {
		_head = new DoubleListNode();
		_tail = new DoubleListNode();
		_head.setNext(_tail);
		_tail.setPrev(_head);
		_size = 0;
	}

	/**
	 * O(1)
	* return an Iterator for this list.
	* <br>pre: none
	* <br>post: return an Iterator object for this List
	*/
	public Iterator iterator () {
		return new LLIterator();
	}
	
	private class LLIterator implements Iterator {
		DoubleListNode cursor;
		
		/**
		 * O(1)
		 */
		public LLIterator () {
			cursor = _head;
		}
		
		/**
		 * O(1)
		 */
		public boolean hasNext () {
			if (cursor.getNext() == _tail) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * O(1)
		 */
		public Object next () {
			cursor = cursor.getNext();
			return cursor.getData();
		}
		
		/**
		 * O(1)
		 */
		public void remove () {
			cursor.getNext().setPrev(cursor.getPrev());
			cursor.getPrev().setNext(cursor.getNext());
			cursor.setNext(null);
			cursor.setPrev(null);
			cursor.setData(null);
		}
	}
	
    /**
     * O(N)
     * Remove all elements in this list from <tt>start</tt> inclusive to <tt>stop</tt> exclusive.
     * <br>pre: <tt>0 <= start < size(), start <= stop <= size()</tt>
     * <br>post: <tt>size() = old size() - (stop - start)</tt>
     * @param start position at beginning of range of elements to be removed
     * @param stop stop - 1 is the position at the end of the range of elements to be removed
     */
    public void removeRange (int start, int stop) {
    	assert (start >= 0 && start < this.size() && start <= stop && stop <= this.size()) : "violation of precondition: removeRange";
		
		DoubleListNode cursor = _head;
		DoubleListNode front;
		int range = stop - start;
		
		if (range == this.size()) {
			this.makeEmpty();
		} else {
			if (start > this.size() / 2) {
				cursor = _tail;
				for (int i = this.size(); i >= start; i--) {
					cursor = cursor.getPrev();
				}
			} else {
				cursor = _head;
				for (int i = 0; i < start; i++) {
					cursor = cursor.getNext();
				}
			}
			
			front = cursor;
			
			for (int i = 0; i <= range; i++) {
				cursor = cursor.getNext();
			}
			
			front.setNext(cursor);
			cursor.setPrev(front);
			_size -= range;
		}
    }
    
    /**
     * O(1)
     * Add item to the front of the list
     * pre: none
     * post: size() = old size() + 1, get(0) = item
     * @param item the data to add tot he front of this list
     */
	public void addFirst (Object item) {
		this.add(0, item);
	}

	/**
	 * O(1)
	 * Add item to the end of the list
	 * pre: none
	 * post: size() = old size() + 1, get(size() - 1) = item
	 * @param item the data to add to the end of this list
	 */
	public void addLast (Object item) {
		this.add(item);
	}

	/**
	 * O(1)
	 * Remove and return the first element of this list
	 * pre: size() > 0
	 * post: size() = old size() - 1
	 * @return the old first element of this list
	 */
	public Object removeFirst () {
		assert (this.size() > 0) : "violation of precondition: removeFirst";
		
		_head = _head.getNext();
		_head.setPrev(null);
		_size--;
		
		return _head.getData();
	}

	/**
	 * O(1)
	 * Remove and return the last element of this list
	 * pre: size() > 0
	 * post: size() = old size() - 1
	 * @returnt he old last element of this list
	 */
	public Object removeLast () {
		assert (this.size() > 0) : "violation of precondition: removeLast";
		
		_tail = _tail.getPrev();
		_tail.setNext(null);
		_size--;
		
	    return _tail.getData();
	}

    /**
     * O(N)
     * Determine if this IList is equal to other. Two
     * ILists are equal if they contain the same elements
     * in the same order.
     * @return true if this IList is equal to other, false otherwise
     */
	public boolean equals (Object other) {
		IList list = (IList) other;
		Iterator it1 = this.iterator();
		Iterator it2 = list.iterator();
		boolean result = true;
		
		if (this.size() != list.size()) {
			result = false;
		}
		
		for (int i = 0; i < this.size() && result; i++) {
			if (this.get(i) != list.get(i)) {
				result = false;
			}
		}
		
	    return result;
	}

    /**
     * O(N)
     * Return a String version of this list enclosed in
     * square brackets, []. Elements are in
     * are in order based on position in the 
     * list with the first element
     * first. Adjacent elements are seperated by comma's
     * @return a String representation of this IList
     */
	public String toString () {
		StringBuffer result = new StringBuffer();
		Iterator it = this.iterator();
		DoubleListNode cursor = _head;
		
		result.append("[");
		
		while (it.hasNext()) {
			result.append(it.next());
			cursor = cursor.getNext();
			if (cursor.getNext() != _tail) {
				result.append(", ");
			}
		}
		
		result.append("]");
		
		return result.toString();
	}
}