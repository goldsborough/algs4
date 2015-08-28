import java.util.NoSuchElementException;

/**
 * Created by petergoldsborough on 08/28/15.
 */

public class TwoProbeHashTable<Key, Value> extends SeparateChainingHashTable<Key, Value>
{
	public static void main(String[] args)
	{
		TwoProbeHashTable<String, Integer> table = new TwoProbeHashTable<>();

		table.put("one", 1);

		table.put("two", 2);

		table.put("three", 3);

		table.put("four", 4);

		table.put("five", 5);

		table.put("six", 6);

		System.out.println(table.size());
		System.out.println(table.isEmpty());
		System.out.println(table.get("one"));
		table.erase("two");
		System.out.println(table.size());

		table.erase("one");
		table.erase("three");
		table.erase("four");

		System.out.println(table.get("six"));
	}

	public void put(Key key, Value value)
	{
		int first = _firstHash(key);

		for (Node node = _nodes[first]; node != null; node = node.next)
		{
			if (node.key.equals(key))
			{
				node.value = value;
				return;
			}
		}

		int second = _secondHash(first);

		for (Node node = _nodes[second]; node != null; node = node.next)
		{
			if (node.key.equals(key))
			{
				node.value = value;
				return;
			}
		}

		int hash = (_sizes[first] > _sizes[second]) ? second : first;

		_nodes[hash] = new Node(key, value, _nodes[hash]);

		++_sizes[hash];

		if (++_size == _capacity) _resize();
	}

	public Value get(Key key)
	{
		int first = _firstHash(key);

		for (Node node = _nodes[first]; node != null; node = node.next)
		{
			if (node.key.equals(key)) return (Value) node.value;
		}

		int second = _secondHash(first);

		for (Node node = _nodes[second]; node != null; node = node.next)
		{
			if (node.key.equals(key)) return (Value) node.value;
		}

		return null;
	}

	public void erase(Key key)
	{
		int first = _firstHash(key);

		Node node = _nodes[first], previous = null;

		for ( ; node != null; node = node.next)
		{
			if (node.key.equals(key))
			{
				if (previous != null)
				{
					previous.next = node.next;
				}

				node = null;

				--_sizes[first];

				if (--_size == _capacity/4) _resize();

				return;
			}
		}

		int second = _secondHash(first);

		node = _nodes[second];
		previous = null;

		for ( ; node != null; node = node.next)
		{
			if (node.key.equals(key))
			{
				if (previous != null)
				{
					previous.next = node.next;
				}

				node = null;

				--_sizes[second];

				if (--_size == _capacity/4) _resize();

				return;
			}
		}

		throw new NoSuchElementException("No such element '" + key.toString() + "'!");
	}

	private int _firstHash(Key key)
	{
		return super._hash(key);
	}

	private int _secondHash(int first)
	{
		return first ^ (first << 1) % _nodes.length;
	}

	protected void _resize()
	{
		super._resize();

		int[] old = _sizes;

		_sizes = new int[_nodes.length];

		for (int i = 0; i < _size; ++i)
		{
			_sizes[i] = old[i];
		}
	}

	private int[] _sizes = new int[MINIMUM_CAPACITY];
}
