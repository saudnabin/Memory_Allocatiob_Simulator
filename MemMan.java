
//TO DO: Complete this class, add JavaDocs
import java.util.Iterator;
import java.util.Set;
//Do not add any more imports!
/**
 *  The Memory Management class.
 *
 *  @author Nabin Saud
 */
public class MemMan implements Iterable<MemBlock> {
	//******************************************************
	//****    IMPORTANT: DO NOT CHANGE/ALTER/REMOVE     ****
	//****    ANYTHING PROVIDED LIKE THESE INTS HERE... ****
	//******************************************************
	/**
	 * The placement policy for first fit.
	 */
	public static final int FIRST_FIT = 0;
	/**
	 * The placement policy for best fit.
	 */
	public static final int BEST_FIT = 1;
	/**
	 * The placement policy for worst fit.
	 */
	public static final int WORST_FIT = 2;
	/**
	 * The placement policy for next fit.
	 */
	public static final int NEXT_FIT = 3;
	//******************************************************
	//****    IMPORTANT: DO NOT CHANGE/ALTER/REMOVE     ****
	//****    ANYTHING PROVIDED LIKE THE INSTANCE       ****
	//****    VARIABLES IN THIS NESTED CLASS. ALSO      ****
	//****    DON'T REMOVE THE CLASS ITSELF OR ANYTHING ****
	//****    STRANGE LIKE THAT.                        ****
	//******************************************************
	/**
	 *  The BareNode class.
	 *
	 *  @author K. Raven Russell
	 */
	public static class BareNode {
		/**
		 * The block in a node.
		 */
		public MemBlock block;
		/**
		 * The next pointer for the node.
		 */
		public BareNode next;
		/**
		 * The previous pointer for the node.
		 */
		public BareNode prev;
		/**
		 * The boolean value to represent marked nodes.
		 */
		public boolean marked;

		/**
		 * A constructor for BareNode class.
		 * @param block a block that contains info like addr,size, istrue.
		 */
		public BareNode(MemBlock block) {
			this.block = block;
		}
	}

	/**
	 * The head of the LinkedList.
	 */
	private BareNode head;
	/**
	 * The placement policy type.
	 */
	private int type;

	/**
	 * A constructor for MemMan that takes in two parameters.
	 * @param type the placement policy type.
	 * @param head the head of the linked list.
	 */
	protected MemMan(int type, BareNode head){
		this.head = head;
		this.type = type;
	}
	/**
	 * This method give us back an instance that works how we want.
	 * @param type the placement policy type
	 * @param head the head node
	 * @return the object that works with given parameters
	 */
	public static MemMan factory(int type, BareNode head){
		if ((type <= 3)&& (type >=0)){
			if (head !=null){
				MemMan object = new MemMan(type,head);
				return object;
			}
		}
		return null;
	}

	/**
	 * This method simply returns the head of the LinkedList.
	 * @return head of the LinkedList
	 */
	public BareNode getHead(){
		return head;
	}
	/**
	 * This methods allocates memory of the correct size
	 * and returns the block of memory that's been allocated.
	 * @param size the size that needs to be allocated
	 * @return the memory block that has been allocated.
	 */

	public BareNode malloc(int size) {
		/* checking if the size is at least 1 byte */
		if (size < 1) {
			System.out.println("Invalid size....Size should be greater than 1 byte");
		}
		else {
			/* checking if the placement policy type is First Fit */
			if (FIRST_FIT == type) {
				BareNode start = head;
				MemBlock newblock;
				/* running a loop until the head is null */
				while (start != null) {
					/* checking if the head size is at least equal if not greater than the size and if head is free */
					if ((start.block.size >= size) && (start.block.isFree)) {
						newblock = new MemBlock(start.block.addr, size, false);
						/* checking if the free available space is same as the size */
						if (start.block.size == size) {
							start.block = newblock;
							return start;
						}
						else {
							/* creating a new node with block that represents new size and address */
							BareNode newNode = new BareNode(new MemBlock(start.block.addr + size, start.block.size - size, start.block.isFree));
							newNode.next = start.next;
							start.next = newNode;
							/* changing the block of head with newblock */
							start.block = newblock;
							newNode.prev = start;
						}
						return start;
					}
					start = start.next;
				}
			}
			/* checking if the placement policy type is Worst Fit */
			if (type == WORST_FIT){
				BareNode curr = head;
				int worst=0;
				while (curr !=null){
					if ((curr.block.isFree) && (curr.block.size>= size)){
						if (curr.block.size > worst){
							worst = curr.block.size;
						}
					}
					curr = curr.next;
				}
				BareNode start = head;
				MemBlock newblock;
				/* running a loop until the head is null */
				while (start != null) {
					/* checking if the head size is at least equal if not greater than the size and if head is free */
					if ((start.block.size == worst) && (start.block.isFree)) {
						newblock = new MemBlock(start.block.addr, size, false);
						/* checking if the free available space is same as the size */
						if (start.block.size == size) {
							start.block = newblock;
							return start;
						}
						else {
							/* creating a new node with block that represents new size and address */
							BareNode newNode = new BareNode(new MemBlock(start.block.addr + size, start.block.size - size, start.block.isFree));
							newNode.next = start.next;
							start.next = newNode;
							/* changing the block of head with newblock */
							start.block = newblock;
							newNode.prev = start;
						}
						return start;
					}
					start = start.next;
				}

			}
			/* checking if the placement policy type is Best Fit */
			if (type == BEST_FIT){
				BareNode curr = head;
				int best=100000;
				while (curr !=null){
					if ((curr.block.isFree) && (curr.block.size >= size)){
						if (curr.block.size < best){
							best = curr.block.size;
						}
					}
					curr = curr.next;
				}
				BareNode start = head;
				MemBlock newblock;
				/* running a loop until the head is null */
				while (start != null) {
					/* checking if the head size is at least equal if not greater than the size and if head is free */
					if ((start.block.size == best) && (start.block.isFree)) {
						newblock = new MemBlock(start.block.addr, size, false);
						/* checking if the free available space is same as the size */
						if (start.block.size == size) {
							start.block = newblock;
							return start;
						}
						else {
							/* creating a new node with block that represents new size and address */
							BareNode newNode = new BareNode(new MemBlock(start.block.addr + size, start.block.size - size, start.block.isFree));
							newNode.next = start.next;
							start.next = newNode;
							/* changing the block of head with newblock */
							start.block = newblock;
							newNode.prev = start;
						}
						return start;
					}
					start = start.next;
				}
			}
		}
		return null;
	}

	/**
	 * This methods checks if a node can be freed and returns a boolean value.
	 * @param node a node that needs to be freed
	 * @return true if the method successfully freed a node otherwise false
	 */
	public  boolean free(BareNode node){
		if ((!node.block.isFree) &&(node !=null)) {
			/* creating new block with modified parameters. */
			MemBlock empty = new MemBlock(node.block.addr,node.block.size, true);
			node.block = empty;
			/* checks if both left and right to the node is free */
			if ((node.prev !=null) && (node.next !=null)) {
				if ((node.prev.block.isFree)&&(node.next.block.isFree)){
					/* adding the size of all three nodes */
					int total = node.block.size+ node.prev.block.size +node.next.block.size;
					/* creating new block with modified parameters. */
					MemBlock both = new MemBlock(node.prev.block.addr,total,true);
					node.prev.block = both;
					node.prev.next= node.next.next;
					node.next.prev=node.prev;
					return true;
				}
			}
			/* checking if the previous node was also free */
			if ((node.prev !=null) && (node.prev.block.isFree)){
				/* creating new block with modified parameters. */
				MemBlock left = new MemBlock(node.prev.block.addr,node.block.size+node.prev.block.size, true);
				node.prev.block = left;
				node.next.prev = node.prev;
				node.prev.next = node.next;
				return true;
			}
			/* checking if the node after is also free */
			if ((node.next !=null) && (node.next.block.isFree)){
				/* creating new block with modified parameters. */
				MemBlock right = new MemBlock(node.block.addr,node.block.size+node.next.block.size, true);
				node.block = right;
				node.next = node.next.next;
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * This method reallocates a node based on the given parameters.
	 * @param node a node that needs to be reallocated.
	 * @param size the size that needs to be reallocated to.
	 * @return a node that has been reallocated.
	 */
	public BareNode realloc(BareNode node, int size){
		/*
		BareNode temp = null;
		int num=0;
		if(size<1) {
			System.out.println("Invalid size.Size needs to be at least 1 byte");
			return null;
		}
		if(temp.block.isFree){
			num+=temp.block.size;
		}
		if (num<size) {
			System.out.println("Memory is not available to reallocate!!!");
			return null;
		}
		malloc(size);
		free(temp);
		return(head);

		 */
		return null;
	}

	/**
	 * Perform mark-and-sweep. Return how many bytes of memory you recovered.
	 * @param addrs a set of unordered addresses for nodes
	 * @return the bytes of memory that was recovered
	 */
	public int garbageCollect(Set<Integer> addrs){
		BareNode curr = head;
		int recover = 0;
		for (Integer i: addrs){
			while (curr !=null){
				if (curr.block.addr == i){
					curr.marked= true;
				}
				curr = curr.next;
			}
		}
		while (curr !=null){
			if (!curr.marked){
				MemBlock change = new MemBlock(curr.block.addr,curr.block.size,true);
				curr.block = change;
				recover += curr.block.size;
			}
		}
		return recover;
	}

	/**
	 * this keeps track of the current location.
	 * @return iterator indication where the tracker is.
	 */
	public Iterator<MemBlock> iterator() {
		return new Iterator<>() {
			BareNode current = head;
			public boolean hasNext() {
				return current != null;
			}
			public MemBlock next() {
				MemBlock tracker = current.block;
				current = current.next;
				return tracker;

			}
		};
	}
}



