#ifndef MERGE_SORT_BOTTOM_UP_HPP
#define MERGE_SORT_BOTTOM_UP_HPP

#include "mergesort.hpp"

#include <vector>
#include <iterator>


#include <iostream>


class bottom_up
{
	
public:
	
	static const std::size_t CUTOFF = 8;
	
	template<typename Itr>
	static void sort(Itr begin, Itr end)
	{
		std::size_t size = std::distance(begin, end);
		
		if (size < CUTOFF) insertion_sort(begin, end);
		
		else
		{
			using T = typename std::iterator_traits<Itr>::value_type;
			
			std::vector<T> auxiliary(begin, end);
			
			for (std::size_t bin = 1; bin < size; bin *= 2)
			{
				auto aux_begin = auxiliary.begin();
				auto aux_middle = auxiliary.begin();
				auto aux_end = auxiliary.begin();
				
				_advance(aux_middle, bin, auxiliary.end());
				_advance(aux_end, 2 * bin, auxiliary.end());
				
				auto first = begin, last = begin;
			
				_advance(last, 2 * bin, end);
				
				while (first != end)
				{
					std::copy(first, last, aux_begin);
					
					auto previous = aux_middle;
					
					if (*aux_middle < *(--previous))
					{
						merge(first, last, aux_begin, aux_middle, aux_end);
					}
					
					_advance(aux_begin, 2 * bin, auxiliary.end());
					_advance(aux_middle, 2 * bin, auxiliary.end());
					_advance(aux_end, 2 * bin, auxiliary.end());
					
					_advance(first, 2 * bin, end);
					_advance(last, 2 * bin, end);
				}
			}
		}
	}
	
private:
	
	template<typename Itr>
	static void _advance(Itr& itr, std::size_t distance, Itr last)
	{
		auto category = typename std::iterator_traits<Itr>::iterator_category();
		
		_advance(itr, distance, last, category);
	}
	
	template<typename RandomAccessItr>
	static void _advance(RandomAccessItr& itr,
						 std::size_t distance,
						 RandomAccessItr last,
						 std::random_access_iterator_tag)
	{
		itr += distance;
		
		if (itr > last) itr = last;
	}
	
	template<typename BidirectionalIterator>
	static void _advance(BidirectionalIterator& itr,
						 std::size_t distance,
						 BidirectionalIterator last,
						 std::bidirectional_iterator_tag)
	{
		std::size_t count = 0;
		
		while(itr != last && count < distance) ++count, ++itr;
	}
};

#endif /* MERGE_SORT_BOTTOM_UP_HPP */