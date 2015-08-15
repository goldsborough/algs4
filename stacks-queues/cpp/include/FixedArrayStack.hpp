#ifndef FIXED_ARRAY_STACK_HPP
#define FIXED_ARRAY_STACK_HPP

template<typename T, int N>
class FixedArrayStack
{
public:
	
	FixedArrayStack()
	: _size(0)
	{ }
	
	void push(const T& item)
	{
		_array[_size++] = item;
	}
	
	T pop()
	{
		return _array[--_size];
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
	
	T _array [N];
	
	int _size;
};

#endif /* FIXED_ARRAY_STACK_HPP */