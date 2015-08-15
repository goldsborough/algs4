#include "LinkedListStack.hpp"
#include "FixedArrayStack.hpp"
#include "DynamicArrayStack.hpp"
#include "LinkedListQueue.hpp"
#include "ArrayQueue.hpp"

#include <iostream>

int main(int argc, char * argv[])
{
	ArrayQueue<int> q;
	
	q.enqueue(1);
	
	q.enqueue(2);
	
	q.enqueue(3);
	
	std::cout << q.size() << std::endl;
	
	std::cout << q.dequeue() << "\n"
			  << q.dequeue() << "\n"
			  << q.dequeue() << "\n";
	
	std::cout << std::boolalpha << q.isEmpty() << std::endl;
}