#include "bottom-up.hpp"
#include "top-down.hpp"

#include <iostream>
#include <list>

int main(int argc, char * argv[])
{
	std::list<int> list {2, 3, 5, 2, 0, 10, 1, 4, 3, -1};
	
	bottom_up::sort(list.begin(), list.end());

	for (const auto& i : list)
	{
		std::cout << i << " ";
	}
	
	std::cout << std::endl;
}