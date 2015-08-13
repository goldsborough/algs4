#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

class UnionFind(object):

	def __init__(self, N):
		self.N = N

	def union(self, p, q):
		raise NotImplementedError

	def connected(self, p, q):
		raise NotImplementedError

	def __repr__(self):
		raise NotImplementedError

def main(cls):
	N = int(input("Enter N: "))
	uf = cls(N)

	while True:
		nodes = [int(i) for i in input().split()]

		if not nodes:
			break
		else:
			p,q = nodes

		if uf.connected(p, q):
			print("{} and {} are already connected.".format(p, q))
		else:
			uf.union(p, q)
			print("{} -> {}".format(p, q))
		print(uf)