#include <iostream>

#include "ConnectedComponents.hpp"
#include "Graph.hpp"
#include "GraphOperations.hpp"

void print()
{
	std::cout << std::endl;
}

template<typename Head, typename... Tail>
void print(Head&& head, Tail&&... tail)
{
	std::cout << std::forward<Head>(head);
	
	print(std::forward<Tail>(tail)...);
}

int main(int argc, const char* argv[])
{
	Graph graph(10);
	
	graph.add_edge(0, 1);
	graph.add_edge(1, 2);
	graph.add_edge(1, 3);
	
	graph.add_edge(4, 5);
	graph.add_edge(4, 6);
	graph.add_edge(4, 7);
	
	graph.add_edge(8, 9);
	
	ConnectedComponents cc(graph);
	
	print(std::boolalpha);
	
	print(cc.count());
	print(cc.connected(0, 3));
	print(cc.id(0), " ", cc.id(3));
	print(cc.id(5));
	print(cc.id(8));

}
