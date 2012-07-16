/**
 * A class that represents a node to be used in a linked list.
 * These nodes are doubly linked.
 *
 * @author Mike Scott
 * @version July 25, 2005
 */

 public class DoubleListNode
 {
	 // instance variables

	// the data to store in this node
	private Object myData;

	// the link to the next node (presumably in a list)
	private DoubleListNode myNext;

	// the reference to the previous node (presumambly in a list)
	 private DoubleListNode myPrev;

	/**
	 * default constructor.
	 * <br>pre: none
	 * <br>post: getData() = null, getNext() = null, getPrev() = null
	 * <br>O(1)
	 */
	public DoubleListNode()
	{	this(null, null, null);
	}

	/**
	 * create a DoubleListNode that holds the specified data
	 * and refers to the specified next and previous elements.
	 * <br>pre: none
	 * <br>post: getData() = item, getNext() = next, getPrev() = prev
	 * <br>O(1)
	 * @param item the  data this DoubleListNode should hold
	 * @param next the next node
	 * @param prev the previous node
	 */
	public DoubleListNode(Object data, DoubleListNode next, DoubleListNode prev)
	{	myData = data;
		myNext = next;
		myPrev = prev;
	}

	/**
	 * return the data in this node.
	 * <br>pre: none
	 * <br>O(1)
	 * @return the data this DoubleListNode holds
	 */
	public Object getData()
	{	return myData;	}

	/**
	 * return the DoubleListNode this ListNode refers to.
	 * <br>pre: none
	 * <br>O(1)
	 * @return the DoubleListNode this DoubleListNode refers to (normally the next one in a list)
	 */
	public DoubleListNode getNext()
	{	return myNext;	}

	/**
	 * return the DoubleListNode this DoubleListNode refers to.
	 * <br>pre: none
	 * <br>O(1)
	 * @return the DoubleListNode this DoubleListNode refers to (normally the previous one in a list)
	 */
	public DoubleListNode getPrev()
	{	return myPrev;	}

	/**
	 * set the data in this node.
	 * The old data is over written.
	 * <br>pre: none
	 * <br>post: getData() == data
	 * <br>O(1)
	 * @param data the new data for this DoubleListNode to hold
	 */
	public void setData(Object data)
	{	myData = data;	}

	/**
	 * set the next node this DoubleListNode refers to.
	 * <br>pre: none
	 * <br>post: getNext() = next
	 * <br>O(1)
	 * @param next the next node this DoubleListNode should refer to
	 */
	public void setNext(DoubleListNode next)
	{	myNext = next;	}

	/**
	 * set the previous node this DoubleListNode refers to.
	 * <br>pre: none
	 * <br>post: getPrev() = next
	 * <br>O(1)
	 * @param prev the previous node this DoubleListNode should refer to
	 */
	public void setPrev(DoubleListNode prev)
	{	myPrev = prev;	}
 }