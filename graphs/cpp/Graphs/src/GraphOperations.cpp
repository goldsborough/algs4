#include "GraphOperations.hpp"
#include "ConnectedComponents.hpp"

#include <queue>

GraphOperations::size_t GraphOperations::degree(const Graph& graph,
												vertex_t vertex)
{
	return graph.adjacent(vertex).size();
}

GraphOperations::size_t GraphOperations::max_degree(const Graph& graph)
{
	size_t maximum = 0;
	
	for (vertex_t vertex = 0; vertex < graph.number_of_vertices(); ++vertex)
	{
		maximum = std::max(degree(graph, vertex), maximum);
	}
	
	return maximum;
}

GraphOperations::size_t GraphOperations::average_degree(const Graph& graph)
{
	return 2.0 * graph.number_of_edges() / graph.number_of_vertices();
}

GraphOperations::size_t GraphOperations::self_loops(const Graph& graph)
{
	size_t loops = 0;
	
	for (vertex_t vertex = 0; vertex < graph.number_of_vertices(); ++vertex)
	{
		const auto& adjacent = graph.adjacent(vertex);
		
		loops += std::count_if(adjacent.begin(),
							   adjacent.end(),
							   [&] (const edge_t& edge)
							   { return edge.first == vertex; });
	}
	
	return loops;
}

bool GraphOperations::is_connected(const Graph& graph,
								   vertex_t vertex,
								   vertex_t target)
{
	bitset_t visited(graph.number_of_vertices(), false);
	
	return _is_connected(graph,
						 vertex,
						 target,
						 visited);
}

GraphOperations::size_t GraphOperations::shortest_distance(const Graph& graph,
														   vertex_t vertex,
														   vertex_t target)
{
	size_t number_of_vertices = graph.number_of_vertices();
	
	if (vertex > number_of_vertices)
	{
		throw std::invalid_argument("Start-vertex out of range!");
	}
	
	else if (target > number_of_vertices)
	{
		throw std::invalid_argument("Target-vertex out of range!");
	}
	
	std::vector<bool> visited(number_of_vertices, false);
	
	std::queue<vertex_t> queue;
	
	queue.push(vertex);
	
	vertex_t last_on_level = vertex;
	
	size_t distance = 0;
	
	while (! queue.empty())
	{
		vertex = queue.front();
		
		queue.pop();
		
		if (vertex == target) break;
		
		visited[vertex] = true;
		
		for (const auto& adjacent : graph.adjacent(vertex))
		{
			if (! visited[adjacent.first])
			{
				queue.push(adjacent.first);
			}
		}
		
		if (vertex == last_on_level)
		{
			++distance;
			
			last_on_level = queue.back();
		}
	}
	
	return distance;
}

GraphOperations::component_t GraphOperations::shortest_path(const Graph& graph,
															vertex_t vertex,
															vertex_t target)
{
	size_t number_of_vertices = graph.number_of_vertices();
	
	if (vertex > number_of_vertices)
	{
		throw std::invalid_argument("Start-vertex out of range!");
	}
	
	else if (target > number_of_vertices)
	{
		throw std::invalid_argument("Target-vertex out of range!");
	}
	
	std::vector<bool> visited(number_of_vertices, false);
	
	visited[vertex] = true;
	
	std::vector<vertex_t> source(number_of_vertices, vertex);
	
	std::queue<vertex_t> queue;
	
	queue.push(vertex);
	
	vertex_t last_on_level = vertex;
	
	size_t distance = 0;
	
	while (! queue.empty())
	{
		vertex = queue.front();
		
		queue.pop();
		
		if (vertex == target) break;
		
		for (const auto& adjacent : graph.adjacent(vertex))
		{
			if (! visited[adjacent.first])
			{
				queue.push(adjacent.first);
				
				visited[adjacent.first] = true;
				
				source[adjacent.first] = vertex;
			}
		}
		
		if (vertex == last_on_level)
		{
			++distance;
			
			last_on_level = queue.back();
		}
	}
	
	GraphOperations::component_t path(distance + 1);
	
	for (int i = static_cast<int>(distance); i >= 0; --i)
	{
		path[i] = vertex;
		
		vertex = source[vertex];
	}
	
	return path;
}

bool GraphOperations::_is_connected(const Graph &graph,
									Graph::vertex_t vertex,
									Graph::vertex_t target,
									bitset_t& visited)
{
	if (visited[vertex]) return false;
	
	if (vertex == target) return true;
	
	visited[vertex] = true;
	
	for (const auto& adjacent : graph.adjacent(vertex))
	{
		if (_is_connected(graph, adjacent.first, target, visited))
		{
			return true;
		}
	}
	
	return false;
}

bool GraphOperations::is_bipartite(const Graph &graph,
								   const std::function<bool(vertex_t)>& predicate)
{
	bitset_t visited(graph.number_of_edges(), false);
	
	ConnectedComponents cc(graph);
	
	for (const auto& component : cc.components())
	{
		vertex_t vertex = component[0];
		
		bool is = predicate(vertex);
		
		if (! _is_bipartite(graph, vertex, !is, predicate, visited))
		{
			return false;
		}
	}
	
	return true;
}

bool GraphOperations::_is_bipartite(const Graph &graph,
									vertex_t vertex,
									bool was,
									const std::function<bool(vertex_t)>& predicate,
									bitset_t &visited)
{
	bool is = predicate(vertex);
	
	if (was == is) return false;
	
	for (const auto& adjacent : graph.adjacent(vertex))
	{
		if (! visited[adjacent.second])
		{
			visited[adjacent.second] = true;
			
			if (! _is_bipartite(graph, adjacent.first, is, predicate, visited))
			{
				return false;
			}
		}
	}
	
	return true;
}

bool GraphOperations::euler_tour_possible(const Graph &graph)
{
	ConnectedComponents cc(graph);
	
	if (cc.count() > 1) return false;
	
	for (vertex_t vertex = 0; vertex < graph.number_of_vertices(); ++vertex)
	{
		if (degree(graph, vertex) % 2 != 0) return false;
	}
	
	return true;
}

GraphOperations::component_t GraphOperations::euler_tour(const Graph &graph)
{
	component_t path;
	
	std::set<size_t> visited;
	
	if (_euler_tour(graph, 0, path, visited)) return path;
	
	throw std::invalid_argument("Euler tour not possible for this graph!");
}

bool GraphOperations::_euler_tour(const Graph &graph,
								  vertex_t vertex,
								  component_t& path,
								  std::set<size_t>& visited)
{
	if (visited.size() == graph.number_of_edges())
	{
		path.push_back(vertex);
		
		return true;
	}
	
	for (const auto& adjacent : graph.adjacent(vertex))
	{
		if (! visited.count(adjacent.second))
		{
			auto copy = visited;
			
			copy.insert(adjacent.second);
			
			if (_euler_tour(graph, adjacent.first, path, copy))
			{
				path.push_back(vertex);
				
				return true;
			}
		}
	}
	
	return false;
}




