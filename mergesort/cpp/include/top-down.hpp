#ifndef MERGE_SORT_TOP_DOWN_HPP
#define MERGE_SORT_TOP_DOWN_HPP

#include "mergesort.hpp"

#include <vector>
#include <iterator>

#include <iostream>

class top_down
{
public:
	
	static const std::size_t CUTOFF = 8;
	
	template<typename I, typename J>
	static void _sort(I first, I last, J begin, J end)
	{
		std::size_t distance = std::distance(begin, end)/2;
		
		if (! distance) return;
		
		I i_middle = first;
		J j_middle = begin;
		
		std::advance(i_middle, distance);
		std::advance(j_middle, distance);
		
		_sort(begin, j_middle, first, i_middle);
		_sort(j_middle, end, i_middle, last);
		
		J previous = j_middle;
		
		if (*j_middle < *(--previous))
		{
			merge(first, last, begin, j_middle, end);
		}
	}
	
	template<typename Itr>
	static void sort(Itr begin, Itr end)
	{
		if (std::distance(begin, end) < CUTOFF)
		{
			std::clog << "insertion-sorting\n";
			
			insertion_sort(begin, end);
		}
		
		else
		{
			using T = typename std::iterator_traits<Itr>::value_type;
			
			std::clog << "merge-sorting\n";
			
			std::vector<T> auxiliary(begin, end);
			
			_sort(begin, end, auxiliary.begin(), auxiliary.end());
		}
	}
};


#endif /* MERGE_SORT_TOP_DOWN_HPP */
