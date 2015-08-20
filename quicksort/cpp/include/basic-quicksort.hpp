#ifndef BASIC_QUICK_SORT_HPP
#define BASIC_QUICK_SORT_HPP

#include <iostream>
#include <random>
#include <assert.h>

namespace basic
{
	class Quicksort
	{
	public:
		
		Quicksort()
		: _generator(std::random_device()())
		{ }
		
		template<class Itr>
		void sort(Itr begin, Itr end)
		{
			_shuffle(begin, end);
			
			_sort(begin, end);
		}
		
	private:
		
		using distribution_t = std::uniform_int_distribution<std::size_t>;
		
		template<class Itr>
		void _sort(Itr begin, Itr end)
		{
			if (std::distance(begin, end) <= 1) return;
		
			Itr pivot = _partition(begin, end);
			
			_sort(begin, pivot);
			_sort(++pivot, end);
			
			assert(_is_sorted(begin, end));
		}
		
		template<class Itr>
		Itr _partition(Itr begin, Itr end)
		{
			Itr median = _median(begin, --end);
			
			std::swap(*begin, *median);
			
			Itr pivot = begin++;
			
			while(begin != end)
			{
				while (begin != end && *begin < *pivot) ++begin;
				
				while (begin != end && *end > *pivot) --end;
				
				std::swap(*begin, *end);
			}
			
			if (*end < *pivot) std::swap(*pivot, *end);
			
			else std::swap(*pivot, *(--end));
			
			return end;
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
		bool _is_sorted(Itr next, Itr end)
		{
			if (next != end)
			{
				for (Itr previous = next++; next != end; ++previous, ++next)
				{
					if (*next < *previous) return false;
				}
			}
			
			return true;
		}
		
		std::mt19937 _generator;
		
	};
	
	template<class Itr>
	void quicksort(Itr begin, Itr end)
	{
		Quicksort().sort(begin, end);
	}
}

#endif /* BASIC_QUICK_SORT_HPP */