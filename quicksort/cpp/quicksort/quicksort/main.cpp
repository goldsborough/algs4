#include "basic-quicksort.hpp"
#include "extended-quicksort.hpp"
#include "selection.hpp"

#include <iostream>
#include <list>

int main(int argc, char * argv[])
{
	std::list<int> list {1, 4, 5, 3, 20, -1, 7, 2, 0, 10, 8, 33, 100, 120, 101, -5, -10};
	
	std::cout << *select(list.begin(), list.end(), 1) << std::endl;
}