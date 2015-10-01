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

#include <set>
#include <vector>

class GraphOperations
{
public:
	
	using size_t = Graph::size_t;
	
	using vertex_t = Graph::vertex_t;
	
	using edge_t = Graph::edge_t;
	
	using component_t = std::vector<vertex_t>;
	
	using bitset_t = std::vector<bool>;
	
	
	static size_t degree(const Graph& graph, vertex_t vertex);
	
	static size_t max_degree(const Graph& graph);
	
	static size_t average_degree(const Graph& graph);
	
	static size_t self_loops(const Graph& graph);
	
	static bool is_connected(const Graph& graph,
							 vertex_t vertex,
							 vertex_t target);
	
	static size_t shortest_distance(const Graph& graph,
									vertex_t vertex,
									vertex_t target);
	
	static component_t shortest_path(const Graph& graph,
									 vertex_t vertex,
									 vertex_t target);
	
	static bool is_bipartite(const Graph& graph,
							 const std::function<bool(vertex_t)>& predicate);
	
	static bool euler_tour_possible(const Graph& graph);
	
	static component_t euler_tour(const Graph& graph);
	
private:
	
	static bool _is_connected(const Graph& graph,
							  vertex_t vertex,
							  vertex_t target,
							  bitset_t& visited);
	
	static bool _is_bipartite(const Graph& graph,
							  vertex_t vertex,
							  bool was,
							  const std::function<bool(vertex_t)>& check,
							  bitset_t& predicate);
	
	static bool _euler_tour(const Graph& graph,
							vertex_t vertex,
							component_t& path,
							std::set<size_t>& visited);
};

#endif /* GRAPH_OPERATIONS_HPP */
