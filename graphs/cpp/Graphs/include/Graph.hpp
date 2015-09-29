//
//  Graphs.hpp
//  Graphs
//
//  Created by Peter Goldsborough on 09/30/15.
//  Copyright © 2015 Peter Goldsborough. All rights reserved.
//

#ifndef GRAPH_HPP
#define GRAPH_HPP

#include <vector>

class Graph
{
public:
	
	using size_t = std::size_t;
	
	using vertex_t = std::size_t;
	
	using component_t = std::vector<vertex_t>;
	
	
	Graph(vertex_t vertices);
	
	
	void add_edge(vertex_t first, vertex_t second);
	
	const component_t& adjacent(vertex_t vertex) const;
	
	
	size_t degree(vertex_t vertex) const;
	
	size_t max_degree() const;
	
	size_t average_degree() const;
	
	size_t self_loops() const;
	
	
	size_t number_of_edges() const;
	
	size_t number_of_vertices() const;
	
private:
	
	std::vector<component_t> _vertices;
	
	size_t _edges;
};

#endif /* GRAPH_HPP */