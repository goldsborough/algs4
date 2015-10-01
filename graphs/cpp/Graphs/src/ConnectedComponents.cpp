//
//  ConnectedComponents.cpp
//  Graphs
//
//  Created by Peter Goldsborough on 10/01/15.
//  Copyright © 2015 Peter Goldsborough. All rights reserved.
//

#include "ConnectedComponents.hpp"
#include "Graph.hpp"

ConnectedComponents::ConnectedComponents(const Graph& graph)
: _count(0)
, _ids(graph.number_of_vertices())
{
	bitset_t visited(graph.number_of_vertices(), false);
	
	for (vertex_t vertex = 0; vertex < graph.number_of_vertices(); ++vertex)
	{
		if (! visited[vertex])
		{
			component_t component;
			
			_dfs(graph, vertex, visited, component, _count++);
			
			_components.push_back(component);
		}
	}
}

bool ConnectedComponents::connected(vertex_t first, vertex_t second) const
{
	return id(first) == id(second);
}

const std::vector<ConnectedComponents::component_t>&
ConnectedComponents::all_components() const
{
	return _components;
}

const ConnectedComponents::component_t&
ConnectedComponents::component(id_t id) const
{
	return _components[id];
}

ConnectedComponents::id_t ConnectedComponents::count() const
{
	return _count;
}

ConnectedComponents::id_t ConnectedComponents::id(vertex_t vertex) const
{
	return _ids.at(vertex);
}

ConnectedComponents::id_t ConnectedComponents::operator[](vertex_t vertex)
{
	return _ids[vertex];
}

const ConnectedComponents::id_t
ConnectedComponents::operator[](vertex_t vertex) const
{
	return _ids[vertex];
}

void ConnectedComponents::_dfs(const Graph& graph,
							   vertex_t vertex,
							   bitset_t& visited,
							   component_t& component,
							   id_t id)
{
	visited[vertex] = true;
	
	_ids[vertex] = id;
	
	component.push_back(vertex);
	
	for (auto adjacent : graph.adjacent(vertex))
	{
		if (! visited[adjacent.first])
		{
			 _dfs(graph, adjacent.first, visited, component, id);
		}
	}
}