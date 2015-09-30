//
//  GraphOperations.hpp
//  Graphs
//
//  Created by Peter Goldsborough on 10/01/15.
//  Copyright © 2015 Peter Goldsborough. All rights reserved.
//

#ifndef GRAPH_OPERATIONS_HPP
#define GRAPH_OPERATIONS_HPP

#include "Graph.hpp"

#include <vector>

class GraphOperations
{
public:
	
	using size_t = Graph::size_t;
	
	using vertex_t = Graph::vertex_t;
	
	using component_t = Graph::component_t;
	
	using bitset_t = std::vector<bool>;
	
	
	static Graph::size_t degree(const Graph& graph, vertex_t vertex);
	
	static Graph::size_t max_degree(const Graph& graph);
	
	static Graph::size_t average_degree(const Graph& graph);
	
	static Graph::size_t self_loops(const Graph& graph);
	
	static bool is_connected(const Graph& graph,
							 vertex_t vertex,
							 vertex_t target);
	
	static Graph::size_t shortest_distance(const Graph& graph,
										   vertex_t vertex,
										   vertex_t target);
	
	static Graph::component_t shortest_path(const Graph& graph,
											vertex_t vertex,
											vertex_t target);
	
private:
	
	static bool _is_connected(const Graph& graph,
							  vertex_t vertex,
							  vertex_t target,
							  bitset_t& visited);
};

#endif /* GRAPH_OPERATIONS_HPP */
