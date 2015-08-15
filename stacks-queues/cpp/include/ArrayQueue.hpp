#ifndef ARRAY_QUEUE_HPP
#define ARRAY_QUEUE_HPP

template<typename T>
class ArrayQueue
{
public:
	
	ArrayQueue(int capacity = 1)
	: _data(new T[capacity])
	, _capacity(capacity)
	, _size(0)
	, _first(0)
	, _last(0)
	{ }
	
	~ArrayQueue()
	{
		delete [] _data;
	}
	
	void enqueue(const T& item)
	{
		_data[_last] = item;
		
		_last = ++_last % _capacity;
		
		if (++_size == _capacity) _resize();
	}
	
	T dequeue()
	{
		T item = _data[_first];
		
		_first = ++_first % _capacity;
		
		if (--_size == _capacity/4) _resize();
		
		return item;
	}
	
	bool isEmpty()
	{
		return _size == 0;
	}
	
	int size()
	{
		return _size;
	}
	
private:
	
	void _resize()
	{
		T* old = _data;
		
		_data = new T[_size * 2];
		
		for (int i = 0; i < _size; _first = ++_first % _capacity, ++i)
		{
			_data[i] = old[_first];
		}
		
		delete old;
		
		_first = 0;
		
		_last = _size;
		
		_capacity = _size * 2;
	}
	
	T* _data;
	
	int _size;
	
	int _capacity;
	
	int _first;
	
	int _last;
};

#endif /* ARRAY_QUEUE_HPP */