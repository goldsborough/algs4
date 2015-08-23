/**
 * Created by petergoldsborough on 08/23/15.
 */

public class Heap<Key extends Comparable<Key>>
{
	public static int MINIMUM_CAPACITY = 4 + 1;

	public void insert(Key key)
	{
		_keys[++_size] = key;

		_swim(_size);

		if (_size == _keys.length) _resize();
	}

	public Key deleteMax()
	{
		Key key = _keys[1];

		_swap(1, _size--);
		_sink(1);

		if (_size == _keys.length/4) _resize();

		return key;
	}

	public Key max()
	{
		return _keys[1];
	}

	public int size()
	{
		return _size;
	}

	public boolean isEmpty()
	{
		return _size == 0;
	}

	public static void main(String[] args)
	{
		Heap<Integer> heap = new Heap<>();

		heap.insert(5);
		heap.insert(10);
		heap.insert(-2);
		heap.insert(0);

		System.out.println(heap.size());

		System.out.println(heap.deleteMax());

		System.out.println(heap.size());

		System.out.println(heap.deleteMax());
		System.out.println(heap.deleteMax());
		System.out.println(heap.deleteMax());

		System.out.println(heap.isEmpty());
	}

	private boolean _less(int first, int second)
	{
		return _keys[first].compareTo(_keys[second]) < 0;
	}

	private void _swim(int key)
	{
		int parent = _parent(key);

		while (_less(parent, key))
		{
			_swap(parent, key);

			key = parent;

			parent = _parent(key);
		}
	}

	private void _sink(int key)
	{
		while (key < _size)
		{
			int first = _child(key, 0), child = first;

			if (first >= _size) break;

			int second = _child(key, 1);

			if (second <= _size && _less(first, second)) child = second;

			if (_less(key, child)) _swap(key, child);

			else break;

			key = child;
		}
	}

	private void _swap(int first, int second)
	{
		Key temp = _keys[first];
		_keys[first] = _keys[second];
		_keys[second] = temp;
	}

	private void _resize()
	{
		int capacity = _size * 2;

		if (capacity < MINIMUM_CAPACITY) return;

		Key[] old = _keys;

		_keys = (Key[]) new Comparable[capacity];

		for (int i = 1; i <= _size; ++i)
		{
			_keys[i] = old[i];
		}

		old = null;
	}

	private int _parent(int key)
	{
		return key > 1 ? key/2 : 1;
	}

	private int _child(int key, int which)
	{
		return 2 * key + which;
	}

	private int _size = 0;

	private Key[] _keys = (Key[]) new Comparable[MINIMUM_CAPACITY];
}
