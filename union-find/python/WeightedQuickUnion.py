#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import UnionFind

from QuickUnion import QuickUnion

class WeightedQuickUnion(QuickUnion):

	def __init__(self, N):
		super().__init__(N)
		self.sizes = [1 for x in range(N)]

	def union(self, p, q):
		p_root = self.getRoot(p)
		q_root = self.getRoot(q)
		
		if p_root != q_root:
			if self.sizes[p_root] >= self.sizes[q_root]:
				self.roots[q_root] = p_root
				self.sizes[p_root] += self.sizes[q_root]
			else:
				self.roots[p_root] = q_root
				self.sizes[q_root] += self.sizes[p_root]

	def __repr__(self):
		return "{}\n{}".format(self.roots, self.sizes)

def main():
	UnionFind.main(WeightedQuickUnion)

if __name__ == "__main__":
	main()
