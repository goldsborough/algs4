#include "Heap.hpp"
#include "Heapsort.hpp"

#include <iostream>

template<typename T, std::size_t N>
T* begin(T (&array) [N])
{
	return array;
}

template<typename T, std::size_t N>
T* end(T (&array) [N])
{
	return array + N;
}

int main(int argc, char * argv[])
{
	int array [] = {0, 4, -2, 10, 7, -3, 20, 6, 5, 1};
	
	hsort(begin(array), end(array));
	
	for (std::size_t i = 0; i < 10; ++i)
	{
		std::cout << array[i] << " ";
	}
}