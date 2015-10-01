#!/usr/bin/env python
# -*- coding: utf-8 -*-

from graph import Graph

class ConnectedComponents(object):

	def __init__(self, graph):
		self.count = 0
		self.id = {}
		visited = set()
		for vertex in graph:
			if vertex not in visited:
				self.dfs(graph, vertex, visited, self.count)
				self.count += 1

	def connected(self, first, second):
		return self.id[first] == self.id[second]

	def dfs(self, graph, vertex, visited, count):
		visited.add(vertex)
		self.id[vertex] = count
		for adjacent in graph.adjacent(vertex):
			if adjacent not in visited:
				self.dfs(graph, adjacent, visited, count)

def main():
	graph = Graph(10)
	
	graph.add_edge(0, 1)
	graph.add_edge(1, 2)
	graph.add_edge(1, 3)
	
	graph.add_edge(4, 5)
	graph.add_edge(4, 6)
	graph.add_edge(4, 7)
	
	graph.add_edge(8, 9)
	
	cc = ConnectedComponents(graph)
	
	print(cc.count)
	print(cc.connected(0, 3))
	print(cc.id[0], cc.id[3])
	print(cc.id[5])
	print(cc.id[8])

if __name__ == '__main__':
	main()