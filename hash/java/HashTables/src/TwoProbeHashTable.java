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

		System.out.println(table.size());
		System.out.println(table.isEmpty());
		System.out.println(table.get("one"));
		table.erase("two");
		System.out.println(table.size());
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

		int hash = (_sizes[first] < _sizes[second]) ? first : second;

		_nodes[hash] = new Node(key, value, _nodes[hash]);

		++_sizes[hash];

		if (++_size == _capacity) _resize();
	}

	private int _firstHash(Key key)
	{
		return _hash(key);
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
