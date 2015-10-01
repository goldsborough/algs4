#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import division


class Graph(object):
	def __init__(self, vertices):
		self.vertices = [[] for _ in range(vertices)]
		self.edges = 0

	def add_edge(self, first, second):
		self.vertices[first].append(second)
		self.vertices[second].append(first)
		self.edges += 1

	def adjacent(self, vertex):
		return self.vertices[vertex]

	def number_of_edges(self):
		return self.edges

	def number_of_vertices(self):
		return len(self.vertices)

	def __iter__(self):
		return iter(range(len(self.vertices)))

def main():
	pass

if __name__ == '__main__':
	main()
