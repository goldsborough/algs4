#include <iostream>

#include "Graph.hpp"

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
	Graph graph(4);
	
	graph.add_edge(0, 2);
	
	graph.add_edge(0, 0);
	
	print(graph.number_of_edges(), " ", graph.number_of_vertices());
	
	for (const auto& v : graph.adjacent(0)) print(v);
	
	print(graph.max_degree(), " ", graph.degree(0), " ", graph.average_degree());
	
	print(graph.self_loops());
}
