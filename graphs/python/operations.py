#!/usr/bin/env python
# -*- coding: utf-8 -*-

from collections import deque

from components import ConnectedComponents
from graph import Graph

def degree(graph, vertex):
	return len(graph.adjacent(vertex))


def max_degree(graph):
	return len(max([graph.adjacent(v) for v in graph], key=len))


def average_degree(graph):
	return 2 * graph.number_of_edges / graph.number_of_vertices


def self_loops(graph):
	loops = 0
	for vertex in graph:
		for adjacent in graph.adjacent(vertex):
			if adjacent.vertex == vertex:
				loops += 1

	return loops

def connected(graph, vertex, target):
	if vertex >= graph.number_of_vertices():
		raise RuntimeError('Vertex is out of range!')
	elif target >= graph.number_of_vertices():
		return False

	def dfs(graph, vertex, target, visited):
		if vertex in visited:
			return False
		if vertex == target:
			return True

		visited.add(vertex)
		for adjacent in graph.adjacent(vertex):
			if dfs(graph, adjacent.vertex, target, visited):
				return True

		return False

	return dfs(graph, vertex, target, set())

def shortest_distance(graph, vertex, target):
	visited = set()
	visited.add(vertex)

	queue = deque()
	queue.append(vertex)

	last_of_level = vertex
	old = -1
	distance = 0

	while len(queue) > 0:
		vertex = queue.popleft()
		if old == last_of_level:
			distance += 1
			last_of_level = vertex
		if vertex == target:
			break
		for adjacent in graph.adjacent(vertex):
			if adjacent.vertex not in visited:
				queue.append(adjacent.vertex)
				visited.add(adjacent.vertex)
		old = vertex

	return distance

def shortest_path(graph, vertex, target):
	visited = set()
	visited.add(vertex)

	queue = deque()
	queue.append(vertex)

	source = {vertex: None}

	while len(queue) > 0:
		vertex = queue.popleft()
		if vertex == target:
			break
		for adjacent in graph.adjacent(vertex):
			if adjacent.vertex not in visited:
				queue.append(adjacent.vertex)
				visited.add(adjacent.vertex)
				source[adjacent.vertex] = vertex

	path = deque()
	while vertex is not None:
		path.appendleft(vertex)
		vertex = source[vertex]

	return path

def is_bipartite(graph, predicate):
	cc = ConnectedComponents(graph)
	for component in cc.components:
		vertex = component[0]
		previous = not predicate(vertex)
		if not is_bipartite(graph,
							vertex,
							previous,
							predicate,
							set()):
			return False

	return True

def is_bipartite(graph, vertex, was, predicate, visited):
	state = predicate(vertex)
	if state == was:
		return False
	elif len(visited) == graph.number_of_edges:
		return True

	for adjacent in graph.adjacent(vertex):
		if adjacent.edge not in visited:
			visited.add(adjacent.edge)
			if not is_bipartite(graph,
							    adjacent.vertex,
							    state,
							    predicate,
							    visited):
				return False

	return True


def euler_tour_possible(graph):
	cc = ConnectedComponents(graph)
	if cc.count > 1:
		return False
	for vertex in graph:
		if degree(graph, vertex) % 2 != 0:
			return False

	return True

def euler_tour(graph):
	path = []
	def make_euler_tour(graph, vertex, visited):
		if len(visited) == graph.number_of_edges():
			path.append(vertex)
			return True
		for adjacent in graph.adjacent(vertex):
			if adjacent.edge not in visited:
				copy = visited.copy()
				copy.add(adjacent.edge)
				if make_euler_tour(graph, adjacent.vertex, copy):
					path.append(vertex)
					return True
		return False

	if make_euler_tour(graph, 0, set()):
		return path
	raise RuntimeError('Euler tour not possible for this graph!')


def main():
	graph = Graph(5)

	graph.add_edge(0, 1)
	graph.add_edge(1, 2)
	graph.add_edge(2, 1)
	graph.add_edge(2, 3)
	graph.add_edge(3, 2)
	graph.add_edge(3, 0)

	graph.add_edge(1, 4)
	graph.add_edge(3, 4)

	print(euler_tour(graph))


if __name__ == '__main__':
	main()
