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
#include <utility>

class Graph
{
public:
	
	using size_t = std::size_t;
	
	using vertex_t = std::size_t;
	
	using edge_t = std::pair<vertex_t, size_t>;
	
	using adjacent_t = std::vector<edge_t>;
	
	
	Graph(vertex_t vertices);
	
	
	void add_edge(vertex_t first, vertex_t second);
	
	const adjacent_t& adjacent(vertex_t vertex) const;
	
	
	size_t number_of_edges() const;
	
	size_t number_of_vertices() const;
	
private:
	
	std::vector<adjacent_t> _vertices;
	
	size_t _edges;
};

#endif /* GRAPH_HPP */