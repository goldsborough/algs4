#ifndef SELECTION_HPP
#define SELECTION_HPP

#include <random>
#include <iterator>
#include <utility>

template<class Itr>
void shuffle(Itr begin, Itr end)
{
	using dist_t = std::uniform_int_distribution<std::size_t>;
	
	std::mt19937 generator(std::random_device{}());
	
	for (std::size_t i = 1; ++begin != end; ++i)
	{
		dist_t distribution(0, i);
		
		Itr random = begin;
		
		std::advance(random, distribution(generator));
		
		std::swap(*begin, *random);
	}
}

template<class Itr>
Itr partition(Itr begin, Itr end)
{
	auto pivot = begin++;
	
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
Itr select(Itr begin, Itr end, std::size_t k)
{
	if (k > 0) --k; // 1-indexed to 0-indexed
	
	shuffle(begin, end);
	
	while (begin != end)
	{
		Itr middle = partition(begin, end);
		
		std::size_t position = std::distance(begin, middle);
		
		if (k < position) end = middle;
		
		else if (k > position) begin = ++middle;
		
		else return middle;
	}
	
	return end;
}

#endif /* SELECTION_HPP */