#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

"""
Union-find with specific canonical element. 

Add a method find() to the union-
find data type so that find(i) returns the largest element in the connected
component containing i. The operations, union(), connected(), and find()
should all take logarithmic time or better.

For example, if one of the connected components is {1, 2, 6, 9}, then the
find() method should return 9 for each of the four elements in the connected
components because 9 is larger 1, 2, and 6.
"""

class UnionFind(object):

	def __init__(self, N):
		self.parent = [x for x in range(N)]
		self.size = [1 for x in range(N)]
		self.largest = [x for x in range(N)]

	def union(self, p, q):
		rootP = self.root(p)
		rootQ = self.root(q)
		if rootP != rootQ:
			largest = max([self.largest[rootP], self.largest[rootQ]])
			if self.size[rootP] >= self.size[rootQ]:
				self.parent[rootQ] = rootP
				self.size[rootP] += self.size[rootQ]
				self.largest[rootP] = largest
			else:
				self.parent[rootP] = rootQ
				self.size[rootQ] += self.size[rootP]
				self.largest[rootQ] = largest

	def connected(self, p, q):
		return self.root(p) == self.root(q)

	def root(self, i):
		while i != self.parent[i]:
			self.parent[i] = self.parent[self.parent[i]]
			i = self.parent[i]
		return i

	def find(self, i):
		return self.largest[self.root(i)]

def main():
	
	N = 10

	uf = UnionFind(N)

	uf.union(1, 2)
	uf.union(2, 6)
	uf.union(6, 9)

	print(uf.find(1))
	print(uf.find(6))
	print(uf.find(9))

if __name__ == "__main__":
	main()

