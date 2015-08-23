#include "Heap.hpp"

#include <iostream>

int main(int argc, char * argv[])
{
	Heap<int> heap;
	
	heap.insert(5);
	heap.insert(10);
	heap.insert(-1);
	heap.insert(7);
	heap.insert(3);
	
	while (! heap.empty())
	{
		std::cout << heap.size() << " " << heap.delete_max() << "\n";
	}
}