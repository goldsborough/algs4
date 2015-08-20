#ifndef EXTENDED_QUICK_SORT_HPP
#define EXTENDED_QUICK_SORT_HPP

#include <iostream>
#include <random>
#include <assert.h>

namespace extended
{
	class Quicksort
	{
	public:
		
		static const std::size_t CUTOFF = 10;
		
		Quicksort()
		: _generator(std::random_device()())
		{ }
		
		template<class Itr>
		void sort(Itr begin, Itr end)
		{
			_shuffle(begin, end);
			
			_sort(begin, end);
			
			_insertion_sort(begin, end);
		}
		
	private:
		
		using distribution_t = std::uniform_int_distribution<std::size_t>;
		
		template<class Itr>
		using boundary_t = std::pair<Itr, Itr>;
		
		template<class Itr>
		void _sort(Itr begin, Itr end)
		{
			if (std::distance(begin, end) <= CUTOFF) return;
			
			boundary_t<Itr> boundary = _partition(begin, end);
			
			_sort(begin, boundary.first);
			_sort(boundary.second, end);
		}
		
		template<class Itr>
		boundary_t<Itr> _partition(Itr begin, Itr end)
		{
			Itr median = _median(begin, --end);
			
			std::swap(*begin, *median);
			
			Itr pivot = begin++, next = begin;
			
			while (next != end)
			{
				if (*next < *pivot) std::swap(*(begin++), *(next++));
				
				else if (*next > *pivot) std::swap(*next, *(end--));
				
				else ++next;
			}
			
			std::swap(*pivot, *(--begin));
			
			return {begin, end};
		}
		
		template<class Itr>
		Itr _median(Itr begin, Itr end)
		{
			distribution_t distribution(0, std::distance(begin, end));
			
			Itr first = begin, second = begin, third = begin;
			
			std::advance(first, distribution(_generator));
			std::advance(second, distribution(_generator));
			std::advance(third, distribution(_generator));
			
			if (*second < *third)
			{
				if (*first < *second) return second;
				
				else if (*first < *third) return first;
				
				else return third;
			}
			
			else if (*second < *first) return second;
			
			else if (*first < *third) return third;
			
			else return first;
		}
		
		template<class Itr>
		void _shuffle(Itr itr, Itr end)
		{
			for (Itr begin = itr++; itr != end; ++itr)
			{
				distribution_t distribution(0, std::distance(begin, itr));
				
				Itr random = begin;
				
				std::advance(random, distribution(_generator));
				
				std::swap(*itr, *random);
			}
		}
		
		template<class Itr>
		void _insertion_sort(Itr begin, Itr end)
		{
			using T = typename std::iterator_traits<Itr>::value_type;
			
			Itr i = begin--;
			
			for (++i ; i != end; ++i)
			{
				T value = *i;
				
				Itr k = i, j = k--;
				
				for ( ; k != begin && *k >= value; --j, --k) *j = *k;
				
				*j = value;
			}
		}
		
		std::mt19937 _generator;
		
	};
	
	template<class Itr>
	void quicksort(Itr begin, Itr end)
	{
		Quicksort().sort(begin, end);
	}
}

#endif /* EXTENDED_QUICK_SORT_HPP */