#ifndef HEAP_SORT_HPP
#define HEAP_SORT_HPP

#include <iterator>


#include <iostream>

template<class Itr>
class Heapsort
{
public:
	
	void sort(Itr stop, Itr end)
	{
		_size = std::distance(stop, end);
		
		Itr begin = stop--;
		
		for (Itr itr = begin + _size/2; itr != stop; --itr)
		{
			_sink(begin, itr, end);
		}
		
		for (Itr itr = end; itr != begin; )
		{
			--_size;
			
			_swap(begin, --itr);
			_sink(begin, begin, itr);
		}
	}
	
private:
	
	using T = typename std::iterator_traits<Itr>::value_type;
	
	void _sink(Itr begin, Itr itr, Itr end)
	{
		while (itr != end)
		{
			Itr left = _left(begin, itr), child = left;
			
			if (left == end) break;
			
			Itr right = _right(begin, itr);
			
			if (right != end && *right > *left) child = right;
			
			if (*child > *itr) _swap(child, itr);
			
			else break;
			
			itr = child;
		}
	}
	
	void _swap(Itr first, Itr second)
	{
		T temp = *first;
		*first = *second;
		*second = temp;
	}
	
	Itr _parent(Itr begin, Itr itr)
	{
		std::size_t distance = std::distance(begin, itr);
		
		return begin + distance/2;
	}
	
	Itr _left(Itr begin, Itr itr)
	{
		std::size_t distance = std::distance(begin, itr) * 2 + 1;
		
		if (distance >= _size) distance = _size - 1;
		
		return begin + distance;
	}
	
	Itr _right(Itr begin, Itr itr)
	{
		std::size_t distance = std::distance(begin, itr) * 2 + 2;
		
		if (distance >= _size) distance = _size - 1;
		
		return begin + distance;
	}
	
	std::size_t _size;
	
};

template<class Itr>
static void hsort(Itr begin, Itr end)
{
	Heapsort<Itr>().sort(begin, end);
}

#endif /* HEAP_SORT_HPP */