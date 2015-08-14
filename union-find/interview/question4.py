#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

"""
Union-by-size.

Develop a union-find implementation that uses the same basic
strategy as weighted quick-union but keeps track of tree height
and always links the shorter tree to the taller one. Prove a lg(N)
upper bound on the height of the trees for N sites with your algorithm.
"""

class UnionFind(object):

	def __init__(self, N):
		self.parent = [x for x in range(N)]
		self.height = [1 for x in range(N)]

	def union(self, p, q):
		rootP = self.root(p)
		rootQ = self.root(q)
		if rootP != rootQ:
			if self.height[rootP] == self.height[rootQ]:
				self.parent[rootQ] = rootP
				self.height[rootP] += 1
			if self.height[rootP] > self.height[rootQ]:
				self.parent[rootQ] = rootP
			else:
				self.parent[rootP] = rootQ

	def connected(self, p, q):
		return self.root(p) == self.root(q)

	def root(self, i):
		while i != self.parent[i]:
			self.parent[i] = self.parent[self.parent[i]]
			i = self.parent[i]
		return i

def main():
	
	N = 5

	uf = UnionFind(N)

	print(uf.parent, uf.height)

	uf.union(0, 1)

	print(uf.parent, uf.height)

	uf.union(2, 3)

	print(uf.parent, uf.height)

	uf.union(1, 3)

	print(uf.parent, uf.height)

	print(uf.connected(0, 2))


if __name__ == "__main__":
	main()