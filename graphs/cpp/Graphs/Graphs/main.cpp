#include "ConnectedComponents.hpp"
#include "Graph.hpp"
#include "GraphOperations.hpp"

#include <chrono>
#include <iostream>

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

template<typename Function, typename... Args>
auto benchmark(const std::size_t runs, Function function, Args&&... args)
{
	using duration_t = std::chrono::duration<double, std::milli>;
	
	using clock = std::chrono::high_resolution_clock;
	
	
	duration_t duration(0);
	
	for (std::size_t i = 0; i < runs; ++i)
	{
		auto start = clock::now();
		
		function(std::forward<Args>(args)...);
		
		duration += (clock::now() - start);
	}
	
	return (duration/runs).count();
}

template<typename Return, typename... All, typename... Args>
auto benchmark(Return (&function) (All...), Args&&... args)
{
	return benchmark(1e6, function, std::forward<Args>(args)...);
}

int main(int argc, const char* argv[])
{
	Graph graph(5);
	
	graph.add_edge(0, 1);
	graph.add_edge(1, 2);
	graph.add_edge(2, 1);
	graph.add_edge(2, 3);
	graph.add_edge(3, 2);
	graph.add_edge(3, 0);
	
	graph.add_edge(1, 4);
	graph.add_edge(3, 4);
	
	print(std::boolalpha);
	
	for (const auto& vertex : GraphOperations::euler_tour(graph))
	{
		print(vertex);
	}

}
