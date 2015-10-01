//
//  ConnectedComponents.hpp
//  Graphs
//
//  Created by Peter Goldsborough on 10/01/15.
//  Copyright © 2015 Peter Goldsborough. All rights reserved.
//

#ifndef CONNECTED_COMPONENTS_HPP
#define CONNECTED_COMPONENTS_HPP

#include <vector>

class Graph;

class ConnectedComponents
{
public:
	
	using id_t = std::size_t;
	
	using vertex_t = std::size_t;
	
	
	ConnectedComponents(const Graph& graph);
	
	bool connected(vertex_t first, vertex_t second) const;
	
	id_t count() const;
	
	id_t id(vertex_t vertex) const;
	
	id_t operator[](vertex_t vertex);
	
	const id_t operator[](vertex_t vertex) const;
	
	
private:
	
	using bitset_t = std::vector<bool>;
	
	void _dfs(const Graph& graph,
			  vertex_t vertex,
			  bitset_t& visited,
			  id_t id);
	
	id_t _count;
	
	std::vector<id_t> _ids;
};

#endif /* CONNECTED_COMPONENTS_HPP */
