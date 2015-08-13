#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import UnionFind

from WeightedQuickUnion import WeightedQuickUnion

class WeightedQuickUnionPathCompression(WeightedQuickUnion):

	def __init__(self, N):
		super().__init__(N)

	def root(self, i):
		while i != self.roots[i]:
			self.roots[i] = self.roots[self.roots[i]]
			i = self.roots[i]
		return i

def main():
	UnionFind.main(WeightedQuickUnionPathCompression)

if __name__ == "__main__":
	main()
