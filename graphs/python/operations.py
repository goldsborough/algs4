#!/usr/bin/env python
# -*- coding: utf-8 -*-

from collections import deque

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
		for other in graph.adjacent(vertex):
			if other == vertex:
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
			if dfs(graph, adjacent, target, visited):
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
			if adjacent not in visited:
				queue.append(adjacent)
				visited.add(adjacent)
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
			if adjacent not in visited:
				queue.append(adjacent)
				visited.add(adjacent)
				source[adjacent] = vertex

	path = deque()
	while vertex is not None:
		path.appendleft(vertex)
		vertex = source[vertex]

	return path

def main():
    graph = Graph(4)

    graph.add_edge(0, 1)
    graph.add_edge(1, 3)
    graph.add_edge(1, 2)

    print(connected(graph, 0, 5))
    print(shortest_distance(graph, 0, 2))
    print(shortest_path(graph, 0, 2))


if __name__ == '__main__':
	main()
