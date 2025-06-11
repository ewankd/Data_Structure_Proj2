import java.util.LinkedList;

/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
	private int cuts;
	private LinkedList<HeapNode> roots;
	public int size;
	public HeapNode min;
	public int totalliknks;
	public final int POS_VALUE = 1;
	public int c;
	
	/**
	 *
	 * Constructor to initialize an empty heap.
	 * pre: c >= 2.
	 *
	 */
	public FibonacciHeap(int c)
	{
		this.min = null;
		this.size = 0;
		this.totalliknks = 0;
		this.c = c;
		this.cuts = 0;
		this.roots = new LinkedList<>();
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) 
	{    	
		HeapNode insert_node = new HeapNode(key, info);
		if(this.min == null){
			this.min = insert_node;
			this.min.next = this.min.prev = this.min;
		}
		else{
			HeapNode oldprev = this.min.prev;
			insert_node.next = this.min;
			this.min.prev = insert_node;
			insert_node.prev = oldprev;
			oldprev.next = insert_node;
			if(this.min.key > key) {this.min = insert_node;}
		}
		this.size++;
		return insert_node; 
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin()
	{
		return this.min;
	}

	/**
	 * 
	 * Delete the minimal item.
	 * Return the number of links.
	 *
	 */
	public int deleteMin()
	{
		
		return 46; // R+W
	}
	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap.
	 * Return the number of cuts.
	 * 
	 */
	public int decreaseKey(HeapNode x, int diff) 
	{    
		int numofchanges = 0;
		x.key -= diff;
		if (x.key < this.min.key) {this.min = x;}
		if(x.parent == null || x.key >= x.parent.key){return 0;}
		boolean oldx_mark;
		do { 
			cuts++; //need to check
			HeapNode parent = x.parent;
			if(x.next == x){
			parent.child = null;
		}
		else{
			x.next.prev = x.prev;
			x.prev.next = x.next;
			 if (parent.child == x) {
       			parent.child = x.next;
   			}
		}
		HeapNode oldprev = this.min.prev;
		x.next = this.min;
		this.min.prev = x;
		x.prev = oldprev;
		oldprev.next = x;
		parent.rank--;
		x.parent = null;
		x.mark = false;
		parent.c++;
		oldx_mark = parent.mark;
		if(parent.c == this.c-1){parent.mark = true;}
		numofchanges++;
		x = parent;
		} while(oldx_mark);
		return numofchanges; 
	}

	/**
	 * 
	 * Delete the x from the heap.
	 * Return the number of links.
	 *
	 */
	public int delete(HeapNode x) 
	{   
		this.decreaseKey(x, POS_VALUE + x.key); // we are going to keep it for now
		return this.deleteMin();
	}

	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
		return this.totalliknks; 
	}

	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
		return this.cuts; 
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2)
	{
		this.roots.addLast(heap2.roots.get(0));
		heap2.roots = new LinkedList<>();	
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size; 
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return this.roots.size(); 
	}

	/**
	 * Class implementing a node in a Fibonacci Heap.
	 *  
	 */
	public static class HeapNode{
		public int c;
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public boolean mark;

		public HeapNode(int key, String info) {
			this.c = 0;
			this.key = key;
			this.info = info;
			this.child = null;
			this.next = null;
			this.prev = null;
			this.parent = null;
			this.rank = 0;
			this.mark = false;
		}
	}
}


