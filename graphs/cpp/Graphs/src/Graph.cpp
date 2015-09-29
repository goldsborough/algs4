//
//  Graph.cpp
//  Graphs
//
//  Created by Peter Goldsborough on 09/30/15.
//  Copyright © 2015 Peter Goldsborough. All rights reserved.
//

#include "Graph.hpp"

/*
 class Graph
 {
 public:
	
	using vertex_t = std::size_t;
	
	Graph(vertex_t vertices);
	
	void add_edge(vertex_t first, vertex_t second);
	
	size_t number_of_edges() const;
	
	size_t number_of_vertices() const;
	
 private:
	
	std::vector<std::vector<vertex_t>> _vertices;
 };
*/

Graph::Graph(vertex_t vertices)
: _vertices(vertices)
, _edges(0)
{ }

void Graph::add_edge(vertex_t first, vertex_t second)
{
	_vertices[first].push_back(second);
	
	++_edges;
}

const Graph::component_t& Graph::adjacent(vertex_t vertex) const
{
	return _vertices[vertex];
}

Graph::size_t Graph::degree(vertex_t vertex) const
{
	return _vertices[vertex].size();
}

Graph::size_t Graph::max_degree() const
{
	size_t maximum = 0;
	
	for (const auto& v : _vertices)
	{
		maximum = std::max(maximum, v.size());
	}
	
	return maximum;
}

Graph::size_t Graph::average_degree() const
{
	return (2.0 * _edges) / _vertices.size();
}

Graph::size_t Graph::self_loops() const
{
	size_t loops = 0;
	
	for (size_t vertex = 0; vertex < _vertices.size(); ++vertex)
	{
		loops += std::count(_vertices[vertex].begin(),
							_vertices[vertex].end(),
							vertex);
	}
	
	return loops;
}

Graph::size_t Graph::number_of_edges() const
{
	return _edges;
}

Graph::size_t Graph::number_of_vertices() const
{
	return _vertices.size();
}