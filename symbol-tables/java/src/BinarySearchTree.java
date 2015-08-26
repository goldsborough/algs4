import java.util.Iterator;
import java.util.NoSuchElementException;

/*
- Constructor(): Create an ordered symbol table
- put(key, value): put key-value pair into the table
- get(key): retrieve value paired with key (null if not found)
- delete(key): remove key and its value from the table
- contains(key): is there a value paired with key?
- isEmpty(): whether or not the table is empty
- size(): the number of elements in the table
- min(): Return the smallest key.
- max(): Return the largest key.
- floor(key): Return largest key less than or equal to the key.
- ceiling(key): Return smallest key greater than or equal to the key.
- rank(key): Number of keys less than key.
- select(k): Key of rank k.
- deleteMin(): delete the smallest key.
- deleteMax(): delete the largest key.
- size(min, max): return the number of keys between min and max ranks.
- keys(min, max): return all the keys in the table between min and max ranks.
- keys(): all the keys in the table (iterable)
 */

public class BinarySearchTree<Key extends Comparable, Value> implements Iterable<BinarySearchTree<Key, Value>.Pair>
{
	public class Pair
	{
		public Pair(Key key, Value value)
		{
			this.key = key;
			this.value = value;
		}

		Key key;
		Value value;
	}

	public static void main(String[] args)
	{
		BinarySearchTree<String, Integer> map = new BinarySearchTree<>();

		map.put("B", 2);
		map.put("A", 1);
		map.put("Llama", 7);
		map.put("A", 10);

		for (BinarySearchTree<String, Integer>.Pair pair : map)
		{
			System.out.printf("%s : %d\n", pair.key, pair.value);
		}
	}

	public void put(Key key, Value value)
	{
		root = put(root, key, value);
	}

	public Value get(Key key)
	{
		Node node = get(root, key);

		return (node == null) ? null : node.value;
	}

	public void delete(Key key)
	{
		root = delete(root, key);
	}

	public boolean contains(Key key)
	{
		return get(key) != null;
	}

	public boolean isEmpty()
	{
		return root == null;
	}

	public int size()
	{
		return (root == null) ? 0 : root.size;
	}

	public Key minimum()
	{
		if (root == null) return null;

		Node node = minimum(root);

		return node.key;
	}

	public Key maximum()
	{
		if (root == null) return null;

		Node node = root;

		while (node.right != null) node = node.right;

		return node.key;
	}

	public Key floor(Key key)
	{
		return floor(root, key);
	}

	public Key ceiling(Key key)
	{
		return ceiling(root, key);
	}

	public int rank(Key key)
	{
		return rank(root, key);
	}

	public Key select(int rank)
	{
		if (rank == 0) return minimum();

		return select(root, rank);
	}

	public void deleteMinimum()
	{
		delete(minimum());
	}

	public void deleteMaximum()
	{
		delete(maximum());
	}

	public int size(int lower, int upper)
	{
		return size(root, lower, upper);
	}

	public Iterable<Key> keys(int lower, int upper)
	{
		Queue<Key> queue = new Queue<>();

		keys(root, lower, upper, queue);

		return queue;
	}

	public Iterable<Key> keys()
	{
		Queue<Key> queue = new Queue<>();

		keys(root, queue);

		return queue;
	}

	public Iterable<Value> values(int lower, int upper)
	{
		Queue<Value> queue = new Queue<>();

		values(root, lower, upper, queue);

		return queue;
	}

	public Iterable<Value> values()
	{
		Queue<Value> queue = new Queue<>();

		values(root, queue);

		return queue;
	}


	public Iterator<BinarySearchTree<Key,Value>.Pair> iterator()
	{
		return new Itr();
	}

	public Iterator<BinarySearchTree<Key,Value>.Pair> iterator(int lower, int upper)
	{
		return new Itr(lower, upper);
	}

	private class Node implements Comparable<Key>
	{
		Node(Key key, Value value)
		{
			this.key = key;
			this.value = value;
		}

		public int compareTo(Key other)
		{
			return key.compareTo(other);
		}

		Key key;
		Value value;

		Node left = null;
		Node right = null;

		int size = 1;
	}

	private class Itr implements Iterator<Pair>
	{
		public Itr()
		{
			fill(root);
		}

		public Itr(int lower, int upper)
		{
			fill(root, lower, upper);
		}

		public boolean hasNext()
		{
			return ! queue.isEmpty();
		}

		public Pair next()
		{
			if (! hasNext()) throw new NoSuchElementException();

			return queue.dequeue();
		}

		private void fill(Node node, int lower, int upper)
		{
			if (node == null) return;

			int rank = (node.left == null) ? 0 : node.left.size;

			if (rank >= lower && rank < upper)
			{
				queue.enqueue(new Pair(node.key, node.value));
			}

			if (rank >= lower)
			{
				fill(node.left, lower, upper);
			}

			if (rank < upper)
			{
				lower -= rank - 1;
				upper -= rank - 1;
				fill(node.right, lower, upper);
			}
		}

		private void fill(Node node)
		{
			if (node == null) return;

			fill(node.left);

			queue.enqueue(new Pair(node.key, node.value));

			fill(node.right);
		}

		private Queue<Pair> queue = new Queue<>();
	}

	private Node minimum(Node node)
	{
		if (node != null)
		{
			while (node.left != null) node = node.left;
		}

		return node;
	}

	private Node put(Node node, Key key, Value value)
	{
		if (node == null) return new Node(key, value);

		if (node.compareTo(key) < 0)
		{
			node.right = put(node.right, key, value);
			node.size = 1 + node.right.size;
			if (node.left != null) node.size += node.left.size;
		}

		else if (node.compareTo(key) > 0)
		{
			node.left = put(node.left, key, value);
			node.size = 1 + node.left.size;
			if (node.right != null) node.size += node.right.size;
		}

		else node.value = value;

		return node;
	}

	private Node get(Node node, Key key)
	{
		if (node == null) return null;

		if (node.compareTo(key) < 0) return get(node.right, key);

		else if (node.compareTo(key) > 0) return get(node.left, key);

		else return node;
	}

	private Node delete(Node node, Key key)
	{
		if (node == null) return null;

		if (node.compareTo(key) > 0)
		{
			node.left = delete(node.left, key);

			node.size = 1;
			if (node.left != null) node.size += node.left.size;
			if (node.right != null) node.size += node.right.size;
		}

		else if (node.compareTo(key) < 0)
		{
			node.right = delete(node.right, key);

			node.size = 1;
			if (node.left != null) node.size += node.left.size;
			if (node.right != null) node.size += node.right.size;
		}

		else
		{
			if (node.right == null) return node.left;

			if (node.left == null) return node.right;

			Node previous = null;
			Node successor = node.right;

			while (successor.left != null)
			{
				previous = successor;
				successor = successor.left;
			}

			if (previous != null)
			{
				previous.left = successor.right;
				previous.size--;
			}

			node.key = successor.key;
			node.value = successor.value;
		}

		return node;
	}

	private Key floor(Node node, Key key)
	{
		if (node == null) return null;

		if (node.compareTo(key) < 0)
		{
			Key result = floor(node.right, key);

			return (result == null) ? node.key : result;
		}

		else return floor(node.left, key);
	}

	private Key ceiling(Node node, Key key)
	{
		if (node == null) return null;

		if (node.compareTo(key) > 0)
		{
			Key result = ceiling(node.left, key);

			return (result == null) ? null : result;
		}

		else return ceiling(node.right, key);
	}

	private int rank(Node node, Key key)
	{
		if (node == null) return 0;

		int comparison = node.compareTo(key);

		if (comparison > 0) return rank(node.left, key);

		else if (comparison < 0)
		{
			int size = 1;

			if (node.left != null) size += node.left.size;

			return size + rank(node.right, key);
		}

		else if (node.left == null) return 0;

		else return node.left.size;
	}

	private Key select(Node node, int rank)
	{
		if (node == null) return null;

		if (rank == 0) return node.key;

		if (node.left == null)
		{
			return select(node.right, rank - 1);
		}

		else if (node.left.size < rank)
		{
			return select(node.right, rank - 1 - node.left.size);
		}

		else if (node.left.size > rank)
		{
			return select(node.left, rank);
		}

		else return node.key;
	}

	private int size(Node node, int lower, int upper)
	{
		int count = 0;

		if (node != null && node.left != null)
		{
			int rank = node.left.size;

			if (rank >= lower && rank < lower) count++;

			if (rank >= lower) count += size(node.left, lower, upper);

			if (rank < upper)
			{
				lower -= rank + 1;
				upper -= rank + 1;
				count += size(node.right, lower, upper);
			}
		}

		return count;
	}

	private void keys(Node node, int lower, int upper, Queue<Key> queue)
	{
		if (node == null) return;

		int rank = (node.left == null) ? 0 : node.left.size;

		if (rank >= lower && rank < upper) queue.enqueue(node.key);

		if (rank >= lower) keys(node.left, lower, upper, queue);

		if (rank < upper)
		{
			lower -= rank + 1;
			upper -= rank + 1;
			keys(node.right, lower, upper, queue);
		}
	}

	private void keys(Node node, Queue<Key> queue)
	{
		if (node == null) return;

		keys(node.left, queue);

		queue.enqueue(node.key);

		keys(node.right, queue);
	}

	private void values(Node node, int lower, int upper, Queue<Value> queue)
	{
		if (node == null) return;

		int rank = (node.left == null) ? 0 : node.left.size;

		if (rank >= lower && rank < upper) queue.enqueue(node.value);

		if (rank >= lower) values(node.left, lower, upper, queue);

		if (rank < upper)
		{
			lower -= rank + 1;
			upper -= rank + 1;
			values(node.right, lower, upper, queue);
		}
	}

	private void values(Node node, Queue<Value> queue)
	{
		if (node == null) return;

		values(node.left, queue);

		queue.enqueue(node.value);

		values(node.right, queue);
	}

	private Node root = null;
}

class Queue<T> implements Iterable<T>
{
	public void enqueue(T item)
	{
		Node node = new Node(item);

		if (first == null) first = node;

		else last.next = node;

		last = node;

		++size;
	}

	public T dequeue()
	{
		Node node = first;

		first = first.next;

		if (first == null) last = null;

		T item = node.item;

		node = null;

		--size;

		return item;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public Iterator<T> iterator()
	{
		return new Itr();
	}

	private class Node
	{
		public Node(T item)
		{
			this.item = item;
		}

		T item;

		Node next;
	}

	private class Itr implements Iterator<T>
	{
		public boolean hasNext()
		{
			return node != null;
		}

		public T next()
		{
			if (node == null) throw new NoSuchElementException();

			T item = node.item;

			node = node.next;

			return item;
		}

		Node node = first;
	}

	Node first = null;
	Node last = null;

	int size = 0;
}