import java.util.ArrayList;

/**
 * Created by petergoldsborough on 10/03/15.
 */

public class BinarySearchTree1D
{
	public static void main(String[] args)
	{
		BinarySearchTree1D tree = new BinarySearchTree1D();

		for (int i = 0; i < 10; ++i)
		{
			tree.insert(i);
		}

		for (Integer i : tree.rangeSearch(3, 5))
		{
			System.out.println(i);
		}
	}

	public void insert(Integer coordinate)
	{
		root = insert(root, coordinate);
	}

	public boolean contains(Integer coordinate)
	{
		return contains(root, coordinate);
	}

	public void erase(Integer coordinate)
	{
		root = erase(root, coordinate);
	}

	public Integer rangeCount(Integer lower, Integer upper)
	{
		return rank(root, upper) - rank(root, lower) + 1;
	}

	public Iterable<Integer> rangeSearch(Integer lower, Integer upper)
	{
		ArrayList<Integer> range = new ArrayList<>();

		rangeSearch(root, lower, upper, range);

		return range;
	}

	private static class Node
	{
		public Node(Integer coordinate)
		{
			this.coordinate = coordinate;
		}

		public void resize()
		{
			size = 1;

			if (left != null) size += left.size;

			if (right != null) size += right.size;
		}

		Integer coordinate;

		Node left = null;
		Node right = null;

		Integer size = 1;
	}

	private Node insert(Node node, Integer coordinate)
	{
		if (node == null) return new Node(coordinate);

		if (coordinate < node.coordinate) node.left = insert(node.left, coordinate);

		else if (coordinate > node.coordinate) node.right = insert(node.right, coordinate);

		else node.coordinate = coordinate;

		node.resize();

		return node;
	}

	private boolean contains(Node node, Integer coordinate)
	{
		if (node == null) return false;

		if (coordinate < node.coordinate) return contains(node.left, coordinate);

		else if (coordinate > node.coordinate) return contains(node.right, coordinate);

		else return true;
	}

	private Node erase(Node node, Integer coordinate)
	{
		if (node == null) throw new IllegalArgumentException("No such coordinate!");

		if (coordinate < node.coordinate) node.left = erase(node.left, coordinate);

		else if (coordinate > node.coordinate) node.right = erase(node.right, coordinate);

		else
		{
			if (node.left == null) return node.right;

			if (node.right == null) return node.left;

			Node previous = null;
			Node successor = node.right;

			while (successor.left != null)
			{
				previous = successor;
				previous.size--;

				successor = successor.left;
			}

			swap(node, successor);

			if (previous != null) previous.left = successor.right;

			else node.right = successor.right;

			successor = null;
		}

		node.resize();

		return node;
	}

	private Integer rank(Node node, Integer coordinate)
	{
		if (node == null) return 0;

		if (coordinate < node.coordinate) return rank(node.left, coordinate);

		else if (coordinate > node.coordinate)
		{
			Integer size = 1;

			if (node.left != null) size += node.left.size;

			return size + rank(node.right, coordinate);
		}

		else return node.left == null ? 1 : node.left.size + 1;
	}

	private void rangeSearch(Node node, Integer lower, Integer upper, ArrayList<Integer> elements)
	{
		if (node == null) return;

		if (lower < node.coordinate)
		{
			rangeSearch(node.left, lower, upper, elements);
		}

		if (node.coordinate >= lower && node.coordinate <= upper)
		{
			elements.add(node.coordinate);
		}

		if (node.coordinate < upper)
		{
			rangeSearch(node.right, lower, upper, elements);
		}
	}

	private void swap(Node first, Node second)
	{
		Integer temp = first.coordinate;

		first.coordinate = second.coordinate;
		second.coordinate = first.coordinate;
	}

	Node root = null;

}
