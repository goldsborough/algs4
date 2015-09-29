#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import division

class Graph(object):
	def __init__(self, vertices):
		self.vertices = [[] for _ in range(vertices)]
		self.edges = 0

	def add_edge(self, first, second):
		self.vertices[first].append(second)
		self.edges += 1

	def adjacent(self, vertex):
		return self.vertices[vertex]

	def degree(self, vertex):
		return len(self.vertices[vertex])

	def max_degree(self):
		return len(max(self.vertices, key=len))

	def average_degree(self):
		return (2 * self.edges) / len(self.vertices)

	def self_loops(self):
		loops = 0
		for vertex, adjacent in enumerate(self.vertices):
			for other in adjacent:
				if other == vertex:
					loops += 1

		return loops

	def number_of_edges(self):
		return self.edges

	def number_of_vertices(self):
		return len(self.vertices)

def main():
    pass

if __name__ == '__main__':
	main()
