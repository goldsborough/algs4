/**
 * Created by petergoldsborough on 08/23/15.
 */

public class PriorityQueue<Key extends Comparable<Key>>
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
		PriorityQueue<Integer> heap = new PriorityQueue<>();

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

class MinHeap<Key extends Comparable<Key>>
{
	public static void main(String[] args)
	{
		MinHeap<Integer> heap = new MinHeap<>();
		
		heap.insert(5);
		heap.insert(2);
		heap.insert(0);
		heap.insert(10);
		
		System.out.println(heap.pop());
		System.out.println(heap.pop());
		System.out.println(heap.size());
		System.out.println(heap.top());
		System.out.println(heap.pop());
		System.out.println(heap.pop());
		System.out.println(heap.isEmpty());
	}
	
	public void insert(Key key)
	{
		if (++size == keys.length) resize();
		
		keys[size] = key;
		
		swim(size);
	}
	
	public Key top()
	{
		return keys[1];
	}
	
	public Key pop()
	{
		Key key = keys[1];
		
		swap(1, size);
		
		if (--size == keys.length/4) resize();
		
		sink(1);
		
		return key;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	private void swim(int index)
	{
		int parent = parent(index);
		
		while (less(index, parent))
		{
			swap(index, parent);
			
			index = parent;
			
			parent = parent(index);
		}
	}
	
	private void sink(int index)
	{
		while (index <= size)
		{
			int child;
			
			int left = left(index);
			
			if (left > size) break;
			
			int right = right(index);
			
			if (right > size || less(left, right)) child = left;
			
			else child = right;
			
			if (less(child, index)) swap(child, index);
			
			else break;
			
			index = child;
		}
	}
	
	private int parent(int index)
	{
		return index > 1 ? index/2 : 1;
	}
	
	private int left(int index)
	{
		return index * 2;
	}
	
	private int right(int index)
	{
		return index * 2 + 1;
	}
	
	private void swap(int first, int second)
	{
		Key temp = keys[first];
		
		keys[first] = keys[second];
		keys[second] = temp;
	}
	
	private boolean less(int first, int second)
	{
		return keys[first].compareTo(keys[second]) < 0;
	}
	
	private void resize()
	{
		int capacity = size * 2;
		
		if (capacity < MINIMUM_CAPACITY) return;
		
		Key[] old = keys;
		
		keys = (Key[]) new Comparable[capacity];
		
		for (int i = 0; i < size; ++i)
		{
			keys[i] = old[i];
		}
		
		old = null;
	}
	
	private static final int MINIMUM_CAPACITY = 8;
	
	Key[] keys = (Key[]) new Comparable[MINIMUM_CAPACITY];
	
	int size = 0;
}