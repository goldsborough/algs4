#ifndef MERGE_SORT_HPP
#define MERGE_SORT_HPP

#include <iterator>
#include <assert.h>

template<typename I, typename J>
void merge(I first, I last, J begin, J middle, J end)
{
	assert(is_sorted(begin, middle));
	assert(is_sorted(middle, end));
	
	for (J i = begin, j = middle; first != last; ++first)
	{
		if (i == middle) *first = *j++;
		
		else if (j == end) *first = *i++;
		
		else if (*i <= *j) *first = *i++;
		
		else *first = *j++;
	}
	
	assert(is_sorted(first, last));
}

template<typename Itr>
bool is_sorted(Itr next, Itr end)
{
	if (next != end)
	{
		for (Itr begin = next++; next != end; ++begin, ++next)
		{
			if (*next < *begin) return false;
		}
	}
	
	return true;
}

template<typename Itr>
void insertion_sort(Itr begin, Itr end)
{
	using T = typename std::iterator_traits<Itr>::value_type;
	
	Itr i = begin--;
	
	for (++i; i != end; ++i)
	{
		T value = *i;
		
		Itr k = i, j = k--;
		
		for ( ; k != begin && *k >= value; --k, --j)
		{
			*j = *k;
		}
		
		*j = value;
	}
}

#endif /* MERGE_SORT_HPP */