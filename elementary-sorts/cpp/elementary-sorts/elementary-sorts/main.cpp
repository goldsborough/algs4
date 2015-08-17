#include "elementary-sorts.hpp"

#include <iostream>

template<typename T, std::size_t N>
constexpr T* begin(T (&array)[N])
{
	return array;
}

template<typename T, std::size_t N>
constexpr T* end(T (&array) [N])
{
	return array + N;
}

template<typename T, std::size_t N>
constexpr std::size_t size(T (&array)[N])
{
	return N;
}

int main(int argc, char * argv[])
{
	int values[] = {10, 2, 46, 3, 9, 4, 3, 3, 12, 11, 200};
	
	shell(std::begin(values), std::end(values));
	
	for (int i = 0, end = size(values); i < end; ++i)
	{
		std::cout << values[i] << " ";
	}
}