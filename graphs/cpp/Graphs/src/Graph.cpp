//
//  Graph.cpp
//  Graphs
//
//  Created by Peter Goldsborough on 09/30/15.
//  Copyright © 2015 Peter Goldsborough. All rights reserved.
//

#include <queue>

#include "Graph.hpp"

Graph::Graph(vertex_t vertices)
: _vertices(vertices)
, _edges(0)
{ }

void Graph::add_edge(vertex_t first, vertex_t second)
{
	_vertices[first].push_back({second, _edges});
	_vertices[second].push_back({first, _edges});
	
	++_edges;
}

const Graph::adjacent_t& Graph::adjacent(vertex_t vertex) const
{
	return _vertices[vertex];
}

Graph::size_t Graph::number_of_edges() const
{
	return _edges;
}

Graph::size_t Graph::number_of_vertices() const
{
	return _vertices.size();
}