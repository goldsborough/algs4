#include <iostream>

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
	Graph graph(8);
	
	graph.add_edge(0, 2);
	
	graph.add_edge(2, 1);
	
	graph.add_edge(1, 3);
	
	graph.add_edge(0, 3);
	
	graph.add_edge(3, 2);
	
	graph.add_edge(3, 7);
	
	auto path = GraphOperations::shortest_distance(graph, 0, 3);
	
	print(path);
	

}
