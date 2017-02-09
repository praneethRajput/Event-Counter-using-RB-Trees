
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


/*
 *  Author: Praneeth Rajput : 61591691
 *  Implementation of an event counter using red-black tree. Each event has two
fields: ID and count, where count is the number of active events with the given ID.
The event counter stores only those ID’s whose count is > 0. Once a count drops
below 1, that ID is removed. Initially the program builds red-black tree from
a sorted list of n events (i.e., n pairs (ID, count) in ascending order of ID) in O(n)
time. All the required operations are supported in the specified time complexity

Acronym : rbBST = Red Black Binary Search Tree will be used throughout the code
 */

public class bbst {

	public static final String DEFAULT_DUAL_OUTPUT = "0 0";
	public static final String DEFAULT_SINGLE_OUTPUT = "0";
	public static final String DELIMITER = " ";
	private RbBST rbBST;

	public bbst(){
		rbBST = new RbBST();
	}

	static class Event {

		private int id;
		private int count;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public Event(int id, int count) {
			this.id = id;
			this.count = count;
		}

	}

	/*
	 * Node class with attributes id, count related to event, leftNode, rightNode, parentNode and boolean value red referencing 
	 * if the node is RED or BLACK
	 */
	class Node {

		int id;
		int count;
		Node left;
		Node right;
		Node parent;
		boolean red;

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}
		public Node getParent() {
			return parent;
		}
		public void setParent(Node parent) {
			this.parent = parent;
		}
		public boolean isRed() {
			return red;
		}
		public void setRed(boolean red) {
			this.red = red;
		}

		public Node(int id, int count, Node parent) {
			this.id = id;
			this.count = count;
			this.setParent(parent);
			this.red = false;
		}
	}

	/*
	 * Enum file for operations defined for the Red Black Binary Search Tree, as part of initialization a static map is initialized with value to enum
	 * mapping which is later invoked to map the command line argument to the appropriate enum
	 * 1. increase - increase the count
	 * 2. reduce - reduce the count
	 * 3. count - retrieve count of an event through the Id
	 * 4. inrange - retrieve the aggregate count of the events within the range
	 * 5. next - return the next smallest event with id greater than the current id
	 * 6. previous - return the next greatest even with id smaller than the current id
	 * 7. quit - to quit the program - invokes System.exit(0)
	 */
	public enum OperationsEnum {

		INCREASE("increase"),
		REDUCE("reduce"),
		COUNT("count"),
		INRANGE("inrange"),
		NEXT("next"),
		PREVIOUS("previous"),
		QUIT("quit");

		private String name;
		private static Map<String, OperationsEnum> map = new HashMap<String, OperationsEnum>();

		static {
			for(OperationsEnum opEnum : OperationsEnum.values()){
				map.put(opEnum.name, opEnum);
			}
		}

		private OperationsEnum(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static OperationsEnum getEnum(String value){
			return map.get(value);
		}
	}


	/**
	 * 
	 * @param id
	 * @param count
	 * Increase the count of the event theID by m. If theID is not present, insert it. Print the count
		of theID after the addition.
	 */
	public void increase (int id, int count) {

		Node node = rbBST.increase(id, count);
		if (null!=node)	System.out.println(node.getCount());
		else System.out.println(DEFAULT_SINGLE_OUTPUT);
	}

	/**
	 * 
	 * @param id
	 * @param count
	 * Decreases the count of the event Id by count, if the count reduces to 0 or lesser then the event is removed
	 * The count is printed after operation and if id is removed or is not present then the output is 0
	 */
	public void reduce (int id, int count) {

		Node node = rbBST.reduce(id, count);
		if (null!=node)	System.out.println(node.getCount());
		else			System.out.println(DEFAULT_SINGLE_OUTPUT);
	}

	/**
	 * 
	 * @param id
	 * Returns the count of the Event with Id = id, returns 0 if the id is not present
	 */
	public void count (int id) {

		Node node = rbBST.findNode(id);
		if (null!=node) System.out.println(node.getCount());
		else			System.out.println(DEFAULT_SINGLE_OUTPUT);		
	}

	/**
	 * 
	 * @param id1
	 * @param id2
	 * Returns the aggregate count of all the events with Ids in the range (id1,id2), returns 0 in case no Ids are present
	 */
	public void inRange (int id1, int id2) {
		System.out.println(rbBST.inRange(id1, id2));
	}

	/**
	 * 
	 * @param id
	 * Returns the Id and count of the event which has the lowest Id greater than the input Id, returns 0 0 incase such an Id does not exist
	 */
	public void next (int id) {

		Node node = rbBST.next(id);
		if (null!=node)	System.out.println(node.getId() + DELIMITER + node.getCount());
		else			System.out.println(DEFAULT_DUAL_OUTPUT);
	}

	/**
	 * 
	 * @param id
	 * Returns the Id and count of the event which has the greatest Id lower than the input Id, returns 0 0 in case such an Id does not exist
	 */
	public void previous (int id) {

		Node node = rbBST.previous(id);
		if (null!=node)	System.out.println(node.getId() + DELIMITER + node.getCount());
		else			System.out.println(DEFAULT_DUAL_OUTPUT);
	}


	/**
	 * 
	 * @param args
	 *  Main program: Takes file name as input along with command line arguments for the operations
	 *  to be performed on the rbBST
	 */
	public static void main(String args[]){

		Scanner scanner = null;
		bbst eventCounter = new bbst();

		if(args.length < 1){
			System.err.println("Missing argument for Input File, exiting");
			return;
		}

		try{

			// Reading the Event Ids and Count from the text file
			List<Event> eventList = getEventsFromFile(args[0]);
			// Building tree from the read input 
			eventCounter.rbBST.buildTree(eventList.size(), 
					eventList.iterator());
			String commandLine = null;
			scanner = new Scanner(System.in);
			StringTokenizer tokenizer;
			//System.out.println("Event list retreived succesfully, proceed with operations");			

			/*
			 *  Accepting commands as input arguments and routing the operations to corresponding methods in the 
			 *  implementation: Using Switch Case: Quit to exit
			 */
			while((commandLine = scanner.nextLine()) != null){
				tokenizer = new StringTokenizer(commandLine);

				switch(OperationsEnum.getEnum(tokenizer.nextToken())){

				case INCREASE:
					eventCounter.increase(Integer.parseInt(tokenizer.nextToken())
							, Integer.parseInt(tokenizer.nextToken()));
					break;

				case REDUCE:
					eventCounter.reduce(Integer.parseInt(tokenizer.nextToken())
							, Integer.parseInt(tokenizer.nextToken()));
					break;

				case COUNT:
					eventCounter.count(Integer.parseInt(tokenizer.nextToken()));
					break;

				case INRANGE:
					eventCounter.inRange(Integer.parseInt(tokenizer.nextToken())
							, Integer.parseInt(tokenizer.nextToken()));
					break;

				case NEXT:
					eventCounter.next(Integer.parseInt(tokenizer.nextToken()));
					break;

				case PREVIOUS:
					eventCounter.previous(Integer.parseInt(tokenizer.nextToken()));
					break;

				case QUIT:
					System.exit(0);

				default:
					System.err.println("Undefined Operation: Allowed Operations: Incerase, Reduce, Count, Inrange, Next, Previous");
				}
			}
		}catch(Exception e){
			System.err.println("Exception encountered " + e);
		}finally{
			// closing the connection
			scanner.close();
		}

	}


	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IllegalArgumentException
	 *  Private Utility method to retrieve the list of events from the input file
	 *  Tokenizer is used to split the ID and Count values, the method throws an IllegalArgumentException 
	 *  in case of any exception encountered during the parsing of the input file
	 *  All the exception cases have been handled in the module
	 */
	private static List<Event> getEventsFromFile(String fileName) throws IllegalArgumentException{

		List<Event> eventList = null;
		String line = null;
		BufferedReader bufferedReader = null;
		int size = 0;

		try{
			FileReader fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			if((line = bufferedReader.readLine()) != null){
				size = Integer.parseInt(line);
				eventList = new ArrayList<Event>(size);
			}else{
				System.err.println("Could not retrieve size to initialise the Event List");
				throw new IllegalArgumentException();
			}
			// Tokenizer to split the input lines based on default delimiter
			StringTokenizer tokenizer;
			while((line = bufferedReader.readLine()) != null){
				tokenizer = new StringTokenizer(line);
				eventList.add(new Event(Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken())));
			}
		}catch(FileNotFoundException e){
			System.err.println("File not found exception, exiting " + e);
			throw new IllegalArgumentException();
		}catch(IOException e){
			System.err.println("IO Exception, exiting " + e);
			throw new IllegalArgumentException();
		}catch(NumberFormatException e){
			System.err.println("Exception while parsing the ID and Count fileds, exiting " + e);
			throw new IllegalArgumentException();
		}finally{

			// Closing the connection
			try{
				bufferedReader.close();
			}catch(Exception e){
				System.err.println("Error while closing the Buffer Reader " + e);
			}
		}

		return eventList;
	}


	/**
	 * 
	 * @author Praneeth
	 *
	 */
	public class RbBST {

		private Node root;
		private int size;

		public Node getRoot() {
			return root;
		}
		public void setRoot(Node root) {
			this.root = root;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}

		/**
		 *         
		 * @param id
		 * @return
		 * Traversal to find the node with id equal to the input argument
		 * Invoked by Count function to retrieve the count of a particular event
		 */
		Node findNode (int id) {
			Node node = root;

			while (node != null) {
				if (id < node.getId())
					node = node.left;
				else if (id > node.getId())
					node = node.right;
				else
					return node;
			}
			return null;
		}

		/**
		 * 
		 * @param id
		 * @return
		 */
		Node next (int id) {
			Node node = root;
			while (node != null) {
				if (id < node.getId()) {
					if (node.left != null)
						node = node.left;
					else
						return node;
				} else {
					if (node.right != null) {
						node = node.right;
					} else {
						Node parent = node.parent;
						Node child = node;
						while (parent != null && child == parent.right) {
							child = parent;
							parent = parent.parent;
						}
						return parent;
					}
				}
			}
			return null;
		}

		/**
		 * 
		 * @param id
		 * @return
		 */
		Node previous (int id) {
			Node node = root;
			while (node != null) {
				if (id > node.getId()) {
					if (node.right != null)
						node = node.right;
					else
						return node;
				} else {
					if (node.left != null) {
						node = node.left;
					} else {
						Node parent = node.parent;
						Node child = node;
						while (parent != null && child == parent.left) {
							child = parent;
							parent = parent.parent;
						}
						return parent;
					}
				}
			}
			return null;

		}

		/**
		 * 
		 * @param id
		 * @param count
		 * @return
		 */
		Node increase (int id, int count) {
			Node a = root;

			/*
			 * Condition check for root == null, if so initialize the root node and return
			 */
			if (a == null) {
				size = 1;
				root = new Node(id, count, null);
				return root;
			}

			Node parent = null;
			while (a != null) {
				parent = a;
				// Comparison with Id for Traversal
				if (id < a.getId())
					a = a.left;
				else if (id > a.getId())
					a = a.right;
				// Handling equality condition for Id, incrementing the count by value of count and returning the node
				else {
					a.setCount(count + a.getCount());
					return a;
				}
			}
			Node b = new Node(id, count, parent);
			if (id <  parent.id)
				parent.left = b;
			else
				parent.right = b;
			RBInsertFixUp(b);
			size++;
			return b;
		}

		/**
		 * 
		 * @param a
		 */
		private void RBInsertFixUp (Node a) {
			a.red = Boolean.TRUE;
			while (a != null && a != root && a.parent.red == Boolean.TRUE) {
				if (findParent(a) == findLeft(findParent(findParent(a)))) {
					Node b = findRight(findParent(findParent(a)));
					if (isRed(b) == Boolean.TRUE) {
						setRed(findParent(a), Boolean.FALSE);
						setRed(b, Boolean.FALSE);
						setRed(findParent(findParent(a)), Boolean.TRUE);
						a = findParent(findParent(a));
					} else {
						if (a == findRight(findParent(a))) {
							a = findParent(a);
							leftRotate(a);
						}
						setRed(findParent(a), Boolean.FALSE);
						setRed(findParent(findParent(a)), Boolean.TRUE);
						rightRotate(findParent(findParent(a)));
					}
				} else { 
					Node b = findLeft(findParent(findParent(a)));
					if (isRed(b) == Boolean.TRUE) {
						setRed(findParent(a), Boolean.FALSE);
						setRed(b, Boolean.FALSE);
						setRed(findParent(findParent(a)), Boolean.TRUE);
						a = findParent(findParent(a));
					} else {
						if (a == findLeft(findParent(a))) {
							a = findParent(a);
							rightRotate(a);
						}
						setRed(findParent(a), Boolean.FALSE);
						setRed(findParent(findParent(a)), Boolean.TRUE);
						leftRotate(findParent(findParent(a)));
					}
				}
			}
			root.red = Boolean.FALSE;
		}

		/**
		 * 
		 * @param id
		 * @param count
		 * @return
		 */
		Node reduce (int id, int count) {
			// Retrieving node with id and returning in case it does not exist
			Node node = findNode(id);
			if (node == null)
				return null;
			// If the reduction count  is less than the count of the retrieved event then the reduction is done
			if(count < node.getCount()) {
				node.setCount(node.getCount() - count);
				return node;
			}	
			// Else the entry is to be removed as the count of an event does not go below 1 according to the problem statement
			removeNode(node);
			// Return null as there is no longer an entry with that Id
			return null;
		}

		/**
		 * 
		 * @param node
		 */
		private void removeNode (Node node) {

			if (node.left != null && node.right != null) {
				Node successor = getSuccessor(node);
				node.id = successor.id;
				node.count = successor.count;
				node = successor;
			} 


			Node repNode = (node.left != null ? node.left : node.right);
			if (repNode != null) {

				repNode.parent = node.parent;
				if (node.parent == null)
					root = repNode;
				else if (node == node.parent.left)
					node.parent.left  = repNode;
				else
					node.parent.right = repNode;

				node.left = node.right = node.parent = null;
				if (node.red == Boolean.FALSE)
					RBDeleteFixUp(repNode);
			} else if (node.parent == null) { 
				root = null;
			} else { 
				if (node.red == Boolean.FALSE)
					RBDeleteFixUp(node);
				if (node.parent != null) {
					if (node == node.parent.left)
						node.parent.left = null;
					else if (node == node.parent.right)
						node.parent.right = null;
					node.parent = null;
				}
			}
			size = size - 1;
		}


		/**
		 * 
		 * @param idLow
		 * @param idHigh
		 * @return
		 * Begins by passing the root node to the supporting module
		 */
		int inRange (int id1, int id2) {
			return inRange(root, id1, id2);
		}

		/**
		 * 
		 * @param node
		 * @param low
		 * @param high
		 * @return
		 * The method returns the sum of counts of for Ids between input ID range, the count is
		 * accumulated recursively. In case the node is null then ) is returned. The calculation is done assuming that
		 * both the extreme range Ids are inclusive in the calculation - Recursive
		 */
		private int inRange (Node node, int id1, int id2) {
			if(node == null)
				return 0;
			
			if (node.getId() < id1)
				return inRange(node.right, id1, id2);
			else if(node.getId() >= id1 && node.getId() <= id2)
				return  node.getCount() + 
						inRange(node.right, id1, id2) + inRange(node.left, id1, id2);
			else
				return inRange(node.left, id1, id2);
			
		}

		/**
		 * 
		 * @param node
		 * @return
		 */
		private Boolean isRed (Node node) {
			return (node == null ? Boolean.FALSE : node.red);
		}

		/**
		 * 
		 * @param node
		 * @return
		 * Returns the parent of node or null in case the node is null
		 * Calling function to handle the null check
		 */
		private Node findParent (Node node) {
			return (node == null ? null: node.parent);
		}

		/**
		 * 
		 * @param node
		 * @param red
		 * Setting the boolean value of color for the node provided it is not null
		 */
		private void setRed (Node node, Boolean red) {
			if (node != null)
				node.red = red;
		}


		/**
		 * 
		 * @param node
		 * Rotate Right transform the configurations of the node by changing a constant number of pointers
		 * Based on the pseudo code Reference
		 */
		private void rightRotate (Node node) {
			if (node != null) {
				// Setting left node
				Node leftNode = node.left;
				// Setting left node's right subtree to the current node's left subtree
				node.left = leftNode.right;			
				if (leftNode.right != null) 
					leftNode.right.parent = node;
				// Linking current node's parent to the original left node
				leftNode.parent = node.parent;
				if (node.parent == null)
					root = leftNode;
				else if (node.parent.right == node)
					node.parent.right = leftNode;
				else node.parent.left = leftNode;
				// Putting current node on the original left node's right
				leftNode.right = node;
				node.parent = leftNode;
			}
		}


		/**
		 * 
		 * @param node
		 * Based on the Pseudo Code Reference from Cormen
		 */
		private void RBDeleteFixUp (Node node) {
			// Checking for !root and node color is black condition
			while (node != root && isRed(node) == Boolean.FALSE) {
				if (node == findLeft(findParent(node))) {
					Node sibling = findRight(findParent(node));

					// Checking if the sibling node is Red
					if (isRed(sibling) == Boolean.TRUE) {
						// Setting it to Black
						setRed(sibling, Boolean.FALSE);
						// Changing the parent to Red
						setRed(findParent(node), Boolean.TRUE);
						leftRotate(findParent(node));
						sibling = findRight(findParent(node));
					}

					if (isRed(findLeft(sibling))  == Boolean.FALSE &&
							isRed(findRight(sibling)) == Boolean.FALSE) {
						setRed(sibling, Boolean.TRUE);
						node = findParent(node);
					} else {
						if (isRed(findRight(sibling)) == Boolean.FALSE) {
							setRed(findLeft(sibling), Boolean.FALSE);
							setRed(sibling, Boolean.TRUE);
							rightRotate(sibling);
							sibling = findRight(findParent(node));
						}
						setRed(sibling, isRed(findParent(node)));
						setRed(findParent(node), Boolean.FALSE);
						setRed(findRight(sibling), Boolean.FALSE);
						leftRotate(findParent(node));
						node = root;
					}
				} else { 
					Node sibling = findLeft(findParent(node));

					if (isRed(sibling) == Boolean.TRUE) {
						setRed(sibling, Boolean.FALSE);
						setRed(findParent(node), Boolean.TRUE);
						rightRotate(findParent(node));
						sibling = findLeft(findParent(node));
					}

					if (isRed(findRight(sibling)) == Boolean.FALSE &&
							isRed(findLeft(sibling)) == Boolean.FALSE) {
						setRed(sibling, Boolean.TRUE);
						node = findParent(node);
					} else {
						if (isRed(findLeft(sibling)) == Boolean.FALSE) {
							setRed(findRight(sibling), Boolean.FALSE);
							setRed(sibling, Boolean.TRUE);
							leftRotate(sibling);
							sibling = findLeft(findParent(node));
						}
						setRed(sibling, isRed(findParent(node)));
						setRed(findParent(node), Boolean.FALSE);
						setRed(findLeft(sibling), Boolean.FALSE);
						rightRotate(findParent(node));
						node = root;
					}
				}
			}

			// Setting the color to Black
			setRed(node, Boolean.FALSE);
		}

		/**
		 * 
		 * @param level
		 * @param low
		 * @param high
		 * @param rl
		 * @param iterator
		 * @return
		 */
		private final Node buildTree (int level, int low, int high,int rl,Iterator<Event> iterator) {

			if (high < low) 
				return null;

			int mid = (low + high)>>>1;

			Node left  = null;
			if (low < mid)
				left = buildTree(level+1, low, mid - 1, rl,iterator);


			Event event = iterator.next();
			int id = event.getId();
			int count = event.getCount();

			Node middleNode =  new Node(id, count, null);

			if (level == rl)
				middleNode.setRed(Boolean.TRUE);

			if (left != null) {
				middleNode.setLeft(left);
				left.setParent(middleNode);
			}

			if (mid < high) {
				Node right = buildTree(level+1, mid+1, high, rl, iterator);
				middleNode.setRight(right);
				right.setParent(middleNode);
			}

			return middleNode;
		}


		/**
		 * 
		 * @param node
		 * Rotate Left transforms the configurations of the node by changing a constant number of pointers
		 * Required that node.right != Sentinel Nil, Based on Pseudo Code Reference
		 * 
		 */
		private void leftRotate (Node node) {
			if (node != null) {
				// Setting right node
				Node rightNode = node.right;
				// Setting right nodes left subtree into the current node's right subtree
				node.right = rightNode.left;
				if (rightNode.left != null)
					rightNode.left.parent = node;
				// Linking current node's parent to the original right node
				rightNode.parent = node.parent;
				if (node.parent == null)
					root = rightNode;
				else if (node.parent.left == node)
					node.parent.left = rightNode;
				else
					node.parent.right = rightNode;
				// Putting current node on the original right node's left
				rightNode.left = node;
				node.parent = rightNode;
			}
		}


		/**
		 * 
		 * @param node
		 * @return
		 */
		Node getSuccessor(Node node) {
			if (node == null)
				return null;
			else if (node.right == null) {
				Node parentNode = node.parent;
				Node child = node;
				while (parentNode != null && child == parentNode.right) {
					child = parentNode;
					parentNode = parentNode.parent;
				}
				return parentNode;
			} else {
				Node rightNode = node.right;
				while (rightNode.left != null)
					rightNode = rightNode.left;
				return rightNode;
			}
		}	

		/**
		 * 
		 * @param size
		 * @param it
		 * Building Tree from the sorted input that is fed into the program, solution arrived at as part of a study group, citing as reference here
		 * 
		 *
		 */
		void buildTree (int size, Iterator<Event> it) {
			this.size = size;
			int rl = calculateRedLevel(size);
			root = buildTree(0, 0, size-1, rl, it);
		}

		/**
		 * 
		 * @param node
		 * @return
		 * Return the Node.left value, calling function to handle null check condition on node
		 */
		private Node findLeft (Node node) {
			return (node == null) ? null: node.left;
		}

		/**
		 * 
		 * @param node
		 * @return
		 * Return the Node.right value, calling function to handle null check condition on node
		 */
		private Node findRight (Node node) {
			return (node == null) ? null: node.right;
		}

		/**
		 * 
		 * @param size
		 * @return
		 */
		private int calculateRedLevel(int size) {

			int res = 0;
			for (int i = size - 1; i >= 0; i = i / 2 - 1)
				res++;
			return res;

		}

	}
}


