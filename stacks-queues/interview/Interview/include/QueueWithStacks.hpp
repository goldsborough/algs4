#ifndef QUEUE_WITH_STACKS_HPP
#define QUEUE_WITH_STACKS_HPP

/*
Queue with two stacks.
 
Implement a queue with two stacks so that
each queue operations takes a constant
amortized number of stack operations.
 
[3, 2, 1, 0]
[0, 1, 2, 3]
*/

template<typename T>
class Stack
{
public:
	
	Stack(int capacity = 4)
	: _size(0)
	, _capacity(capacity)
	, _data(new T[capacity])
	{ }
	
	~Stack()
	{
		delete [] _data;
	}
	
	void push(const T& item)
	{
		_data[_size] = item;
		
		if (++_size == _capacity) _resize();
	}
	
	T pop()
	{
		T item = _data[--_size];
		
		if (_size == _capacity/4) _resize();
		
		return item;
	}
	
	int size()
	{
		return _size;
	}
	
	bool empty()
	{
		return _size == 0;
	}
	
private:
	
	void _resize()
	{
		T* old = _data;
		
		_capacity = _size * 2;
		
		_data = new T[_capacity];
		
		for (int i = 0; i < _size; ++i)
		{
			_data[i] = old[i];
		}
		
		delete [] old;
	}
	
	T* _data;
	
	int _size;
	
	int _capacity;
};

template<typename T>
class QueueWithStacks
{
public:
	
	void enqueue(const T& item)
	{
		_first.push(item);
		
		++_size;
	}
	
	T dequeue()
	{
		if (_second.empty())
		{
			while(! _first.empty())
			{
				_second.push(_first.pop());
			}
		}
		
		return _second.pop();
	}
	
	int size()
	{
		return _size;
	}
	
	bool empty()
	{
		return _size == 0;
	}
	
private:
	
	Stack<T> _first;
	Stack<T> _second;
	
	int _size;
	
};


#endif /* QUEUE_WITH_STACKS_HPP */
