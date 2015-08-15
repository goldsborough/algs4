#ifndef DYNAMIC_ARRAY_STACK_HPP
#define DYNAMIC_ARRAY_STACK_HPP

template<typename T>
class DynamicArrayStack
{
public:
	
	DynamicArrayStack(int capacity = 1)
	: _data(new T[capacity])
	, _capacity(capacity)
	, _size(0)
	{ }
	
	~DynamicArrayStack()
	{
		delete [] _data;
	}
	
	void push(const T& item)
	{
		_data[_size++] = item;
		
		if (_size == _capacity) _resize(_size * 2);
	}
	
	T pop()
	{
		T item = _data[--_size];
		
		if (_size == _capacity/4) _resize(_capacity/2);
		
		return item;
	}
	
	bool isEmpty() const
	{
		return _size == 0;
	}
	
	int size() const
	{
		return _size;
	}
	
private:
	
	void _resize(int capacity)
	{
		T* old = _data;
		
		_data = new T[capacity];
		
		for(int i = 0; i < _size; ++i)
		{
			_data[i] = old[i];
		}
		
		_capacity = capacity;
		
		delete [] old;
	}
	
	T* _data;
	
	int _size;
	
	int _capacity;
};

#endif /* DYNAMIC_ARRAY_STACK_HPP */