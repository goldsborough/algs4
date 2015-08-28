import java.util.NoSuchElementException;

/**
 * Created by petergoldsborough on 08/28/15.
 */

public class LinearProbingHashTable<Key, Value>
{
	public static final int MINIMUM_CAPACITY = 4;

	public static void main(String[] args)
	{
		SeparateChainingHashTable<String, Integer> table = new SeparateChainingHashTable<>();

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
		int i = _hash(key);

		for ( ; _elements[i] != null; i = ++i % _elements.length)
		{
			if (_elements[i].key.equals(key))
			{
				_elements[i].value = value;
				return;
			}
		}

		_elements[i] = new Pair(key, value);

		if (++_size == _elements.length) _resize();
	}

	public Value get(Key key)
	{
		int i = _hash(key);

		for ( ; _elements[i] != null; i = ++i % _elements.length)
		{
			if (_elements[i].key.equals(key))
			{
				return _elements[i].value;
			}
		}

		return null;
	}

	public void erase(Key key)
	{
		int i = _hash(key);

		for ( ; _elements[i] != null; i = ++i % _elements.length)
		{
			if (_elements[i].key.equals(key))
			{
				_elements[i] = null;

				if (--_size == _elements.length/4) _resize();

				return;
			}
		}

		throw new NoSuchElementException("Invalid key '" + key.toString() + "'!");
	}

	public int size()
	{
		return _size;
	}

	public boolean isEmpty()
	{
		return _size == 0;
	}

	private int _hash(Key key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % _elements.length;
	}

	private void _resize()
	{
		int capacity = _size * 2;

		if (capacity < MINIMUM_CAPACITY) return;

		Pair[] old = _elements;

		_elements = (Pair[]) new Object[capacity];

		for (int i = 0; i < _size; ++i)
		{
			_elements[i] = old[i];
		}

		old = null;
	}

	private class Pair
	{
		Pair(Key key, Value value)
		{
			this.key = key;
			this.value = value;
		}

		Key key;
		Value value;
	};

	private Pair[] _elements = (Pair[]) new Object[MINIMUM_CAPACITY];

	private int _size = 0;
}
