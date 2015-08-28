import java.util.NoSuchElementException;

/**
 * Created by petergoldsborough on 08/28/15.
 */

public class SeparateChainingHashTable<Key, Value>
{
	public static int MINIMUM_CAPACITY = 4;

	public static int BIN_SIZE = 1;

	public static void main(String[] args)
	{
		SeparateChainingHashTable<String, Integer> table = new SeparateChainingHashTable<>();

		table.put("one", 1);

		table.put("two", 2);

		table.put("three", 3);

		table.put("four", 4);

		table.put("five", 5);

		System.out.println(table.size());
		System.out.println(table.isEmpty());
		System.out.println(table.get("one"));
		table.erase("two");
		System.out.println(table.size());
	}

	public void put(Key key, Value value)
	{
		int hash = _hash(key);

		for (Node node = _nodes[hash]; node != null; node = node.next)
		{
			if (node.key.equals(key))
			{
				node.value = value;
				return;
			}
		}

		_nodes[hash] = new Node(key, value, _nodes[hash]);

		if (++_size == _capacity) _resize();
	}

	public Value get(Key key)
	{
		int hash = _hash(key);

		for (Node node = _nodes[hash]; node != null; node = node.next)
		{
			if (node.key.equals(key)) return (Value) node.value;
		}

		return null;
	}

	public void erase(Key key)
	{
		int hash = _hash(key);

		Node node = _nodes[hash], previous = null;

		for ( ; node != null; previous = node, node = node.next)
		{
			if (node.key.equals(key))
			{
				if (previous != null)
				{
					previous.next = node.next;
				}

				node = null;

				if (--_size == _capacity/4) _resize();

				return;
			}
		}

		throw new NoSuchElementException("No such element '" + key.toString() + "'!");
	}

	public int size()
	{
		return _size;
	}

	public boolean isEmpty()
	{
		return _size == 0;
	}

	protected static class Node
	{
		public Node(Object key, Object value, Node next)
		{
			this.key = key;
			this.value = value;
			this.next = next;
		}

		Object key;
		Object value;

		Node next = null;
	}

	protected int _hash(Key key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % _nodes.length;
	}

	protected void _resize()
	{
		int capacity = _size * 2;

		if (capacity < MINIMUM_CAPACITY * BIN_SIZE) return;

		Object[] old = _nodes;

		int bins = _capacity / BIN_SIZE;

		_nodes = new Node[bins * 2];

		_move(old, _nodes, bins);

		_capacity = capacity;

		old = null;
	}

	protected void _move(Object[] source, Object[] destination, int bins)
	{
		int half = BIN_SIZE / 2;

		for (int bin = 0; bin < bins; ++bin)
		{
			Node node = (Node) source[bin];

			for (int n = 0; n < half; )
			{
				Node next = node.next;

				if (++n >= half) node.next = null;

				node = next;
			}

			destination[bin] = source[bin];

			destination[bins + bin] = node;
		}
	}

	protected int _size = 0;

	protected int _capacity = MINIMUM_CAPACITY * BIN_SIZE;

	protected Node[] _nodes = new Node[MINIMUM_CAPACITY];
}
