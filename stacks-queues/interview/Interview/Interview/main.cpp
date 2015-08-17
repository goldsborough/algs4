#include "QueueWithStacks.hpp"

#include <iostream>

int main(int argc, char * argv[])
{
	QueueWithStacks<int> queue;
	
	for (int i = 0; i < 10; ++i)
	{
		queue.enqueue(i);
	}
	
	std::cout << queue.dequeue() << std::endl;
	
	for (int i = 0; i < 9; ++i)
	{
		std::cout << queue.dequeue() << std::endl;
	}
}