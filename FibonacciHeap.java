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
	public int totallinks;
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
		this.totallinks = 0;
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
		this.roots.add(insert_node); 
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
		HeapNode tmp = this.min.prev;
		this.min.prev = this.min.child.prev;
		tmp.next = this.min.child;
		this.min.prev.next = this.min;
		tmp.next.prev = tmp;
		cuts += this.min.rank;
		


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
		do { 
			cuts++; //need to check
			HeapNode parent = x.parent;
			if(x.next == x){
				parent.child = null;
			}
			else{
				x.next.prev = x.prev;
				x.prev.next = x.next;
				if(parent.child == x) {
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
			if(parent.parent != null) {parent.c++;}
			x = parent;
			numofchanges++;
		} while(x.c - 1 == this.c);
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
		return this.totallinks; 
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
		if(this.min == null){this.min = heap2.min;}
		else if(heap2.min == null){return;}
		else{
			HeapNode thisMinPrev = this.min.prev;
			HeapNode otherMinPrev = heap2.min.prev;
			this.min.prev = otherMinPrev;
			otherMinPrev.next = this.min;
			thisMinPrev.next = heap2.min;
			heap2.min.prev = thisMinPrev;	
		}
		heap2.min = null;
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

		public HeapNode(int key, String info) {
			this.c = 0;
			this.key = key;
			this.info = info;
			this.child = null;
			this.next = this;
			this.prev = this;
			this.parent = null;
			this.rank = 0;
		}
	}
}


