#ifndef HEAP_HPP
#define HEAP_HPP

#include <assert.h>

struct Less
{
	template<typename T, typename U>
	bool operator()(const T& first, const U& second)
	{
		return first < second;
	}
};

template<typename Key, typename Comparison = Less>
class Heap
{
public:
	
	typedef unsigned long index_t;
	
	static const int MINIMIUM_CAPACITY = 4 + 1;
	
	Heap(int capacity = MINIMIUM_CAPACITY,
		 const Comparison& compare = Comparison())
	: _keys(new Key[capacity])
	, _capacity(capacity)
	, _size(0)
	, _compare(compare)
	{ }
	
	~Heap()
	{
		delete [] _keys;
	}
	
	void insert(const Key& key)
	{
		_keys[++_size] = key;
		
		_swim(_size);
		
		if (_size == _capacity) _resize();
	}
	
	Key delete_max()
	{
		Key max = _keys[1];
		
		_swap(1, _size--);
		
		_sink(1);
		
		if (_size == _capacity/4) _resize();
		
		return max;
	}
	
	Key max() const
	{
		return _keys[1];
	}
	
	index_t size() const
	{
		return _size;
	}
	
	bool empty() const
	{
		return _size == 0;
	}
	
private:
	
	index_t _parent(index_t key)
	{
		assert(key > 0 && key <= _size);
		
		return key > 1 ? key/2 : 1;
	}
	
	index_t _left(index_t key)
	{
		assert(key > 0 && key <= _size);
		
		return 2 * key;
	}
	
	index_t _right(index_t key)
	{
		assert(key > 0 && key <= _size);
		
		return 2 * key + 1;
	}
	
	void _swap(index_t first, index_t second)
	{
		if (first == second) return;
		
		Key temp = _keys[first];
		_keys[first] = _keys[second];
		_keys[second] = temp;
	}
	
	void _swim(index_t key)
	{
		index_t parent = _parent(key);
		
		while (_keys[parent] < _keys[key])
		{
			_swap(parent, key);
			
			key = parent;
			
			parent = _parent(key);
		}
	}
	
	void _sink(index_t key)
	{
		while (key < _size)
		{
			index_t left = _left(key), child = left;
			
			if (left > _size) break;
			
			index_t right = _right(key);
			
			if (right <= _size && _keys[right] > _keys[left]) child = right;
			
			if (_keys[child] > _keys[key]) _swap(child, key);
			
			else break;
			
			key = child;
		}
	}
	
	void _resize()
	{
		index_t capacity = _size * 2;
		
		if (capacity < MINIMIUM_CAPACITY) return;
		
		Key* old = _keys;
		
		_keys = new Key[capacity];
		
		for (index_t i = 1; i <= _size; ++i)
		{
			_keys[i] = old[i];
		}
		
		delete [] old;
		
		_capacity = capacity;
	}
	
	Comparison _compare;
	
	index_t _size;
	
	index_t _capacity;
	
	Key* _keys;
};

#endif /* HEAP_HPP */