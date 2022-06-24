// Note: requires Java 11 or above

/*
TODO:
1. Write more efficient algorithm to check if a binary tree is a BST.
2. Write method to flatten array.
 */

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class BSTTree
{
	protected Node tree = null;

	protected static class Node
	{
		protected int val, balance;
		protected Node left, right;

		public Node(int val, Node left, Node right)
		{
			this.val = val;
			this.left = left;
			this.right = right;
		}

		public void setBalance(int leftHeight, int rightHeight) {
			balance = leftHeight - rightHeight;
		}

		public int getBalance() {
			return balance;
		}

		protected Node() {
			throw new Error("Arguments must be supplied to the constructor.");
		}

		// This is just a helper method for pretty printing a tree
		protected String toString(int indent)
		{
			String indentStr = "    ";
			
			String ret = "";
			if (left == null)
				ret += indentStr.repeat(indent + 1) + "null" + "\n";
			else
				ret += left.toString(indent + 1);
			ret += indentStr.repeat(indent) + val + "\n";
			if (right == null)
				ret += indentStr.repeat(indent + 1) + "null" + "\n";
			else
				ret += right.toString(indent + 1);
			return ret;
		}
	}

	public void flatten(Node t,LinkedList<Integer> flattenedTree) {
		if (!(t == null)) {
			flatten(t.left, flattenedTree);
			flattenedTree.add(t.val);
			flatten(t.right, flattenedTree);
		}
	}

	public void sort(int[] a) {
		tree=null;
		for (int i: a) {
			insert(i);
		}
		LinkedList<Integer> flattenedTree = new LinkedList<>();
		flatten(tree,flattenedTree);
		for (int i=0; i < a.length; i++) {
			a[i] = flattenedTree.get(i);
		}
	}

	public void delete(int v) {
		tree = delete(v,tree);
	}

	protected Node delete(int v, Node t) {
		if (t == null)
			return null;
		if (v == t.val) {
			if (t.left == null && t.right == null) {
				return null;
			}
			if (t.left == null) {
				return t.right;
			}
			if (t.right == null) {
				return t.left;
			}
			Node smallestRight = findSmallest(t.right);
			return new Node(smallestRight.val, t.left, removeSmallest(t.right));
		}
		if (v < t.val)
			return new Node(t.val, delete(v, t.left), t.right);
		return new Node(t.val, t.left, delete(v, t.right));
	}

	public int getMax() {
		return getMax(tree);
	}

	protected int getMax(Node t) {
		if (t.right == null) {
			return t.val;
		}
		return getMax(t.right);
	}

	protected Node removeSmallest(Node t) {
		if (t == null) {
			throw new Error("removeSmallest() run on null tree");
		}
		if (t.left==null) {
			return t.right;
		}
		return new Node(t.val, removeSmallest(t.left), t.right);
	}

	protected Node findSmallest(Node t) {
		if (t == null) {
			throw new Error("findSmallest() run on null tree");
		}
		if (t.left == null) {
			return t;
		}
		return findSmallest(t.left);
	}

	public boolean isIn(int v) {
		return isIn(v,tree);
	}

	protected boolean isIn(int v, Node t) {
		if (t == null)
			return false;
		if (v == t.val)
			return true;
		if (v < t.val)
			return isIn(v, t.left);
		return isIn(v,t.right);
	}

	/*public boolean isBst() {return isBst(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);}

	protected boolean isBst(Node t, int minVal, int maxVal) {
		if (t != null) {
			if (t.val < minVal || t.val > maxVal)
				return false;
			if (t.left != null) {
				if (t.left.val > t.val) {
					return false;
				}
				if (!isBst(t.left,minVal,t.val))
					return false;
			}
			if (t.right != null) {
				if (t.right.val < t.val) {
					return false;
				}
				return isBst(t.right,t.val,maxVal);
			}
		}
		return true;
	}*/

	boolean isBst() {
		return isBst(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	boolean isBst(Node n, int INT_MIN, int INT_MAX) {
		if (n == null) {
			return true;
		}
		if (n.val > INT_MIN && n.val < INT_MAX) {
			return isBst(n.left,INT_MIN,n.val) && isBst(n.right,n.val,INT_MAX);
		}
		return false;
	}


	public void insert(int v)
	{
		if (tree == null)
			tree = new Node(v, null, null);
		else
			insert(v, tree);
	}

	private void insert(int v, Node ptr)
	{
		if (v < ptr.val)
		{
			if (ptr.left == null)
				ptr.left = new Node(v, null, null);
			else
				insert(v, ptr.left);
		}
		else if (v > ptr.val)
		{
			if (ptr.right == null)
				ptr.right = new Node(v, null, null);
			else
				insert(v, ptr.right);
		}
		else
			throw new Error("Value already in tree");
	}

	public int size() {
		Deque<Node> nodes = new ArrayDeque<>();
		int size = 0;
		if(tree != null) nodes.push(tree);
		Node n;
		while(!nodes.isEmpty()) {
			n = nodes.pop();
			size++;
			if(n.left != null) nodes.push(n.left);
			if(n.right != null) nodes.push(n.right);
		}
		return size;
	}

	public void printTree ()
	{
		Deque<Node> nodes = new ArrayDeque<>();
		if (tree != null) {
			nodes.push(tree);
		}
		while (nodes.size() > 0) {
			Node n = nodes.pop();
			System.out.println(n.val);
			if (n.left != null) {
				nodes.push(n.left);
			}
			if (n.right != null) {
				nodes.push(n.right);
			}
		}
	}

	private int sum_rec(Node n) {
		if (n == null)
			return 0;
		return n.val+sum_rec(n.left)+sum_rec(n.right);
	}

	public int sum_rec() {
		return sum_rec(tree);
	}

	public int sum_itr() {
		Deque<Node> nodes = new ArrayDeque<>();
		int sum = 0;
		if(tree != null) nodes.add(tree);
		Node n;
		while (!nodes.isEmpty()) {
			n = nodes.pop();
			sum = sum + n.val;
			if(n.left != null) nodes.add(n.left);
			if(n.right != null) nodes.add(n.right);
		}
		return sum;
	}

	public String toString()
	{
		return tree == null ? "Empty" : tree.toString(0);
	}

	public static void main(String[] args)
	{
		BSTTree tree = new BSTTree();
		tree.insert(20);
		tree.insert(10);
		tree.insert(15);
		tree.insert(30);
		tree.insert(40);
		tree.insert(25);
		tree.insert(16);
		//tree.tree.left.val = 40;
		System.out.println(tree);
		tree.delete(20);
		System.out.println(tree);
		System.out.println(tree.isBst());
		System.out.println(tree.getMax());
		System.out.println(tree.isIn(12));
		System.out.println(tree.isIn(23));
		BSTTree sortTree = new BSTTree();
		int[] a = {67,54,89,100,23,12};
		sortTree.sort(a);
		for (int i: a) {
			System.out.printf("%d ",i);
		}
		System.out.println();
		System.out.println(tree.size());
		System.out.println(tree.sum_rec());
		System.out.println(tree.sum_itr());
		tree.printTree();
	}
}
