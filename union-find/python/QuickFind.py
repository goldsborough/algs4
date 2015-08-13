#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import UnionFind

class QuickFind(UnionFind.UnionFind):

	def __init__(self, N):
		super().__init__(N)
		self.id = [x for x in range(N)]

	def union(self, p, q):
		p_id = self.id[p]
		q_id = self.id[q]

		if p_id != q_id:
			for n,i in enumerate(self.id):
				if i == p_id:
					self.id[n] = q_id

	def connected(self, p, q):
		return self.id[p] == self.id[q]

	def __repr__(self):
		return str(self.id)

def main():
	UnionFind.main(QuickFind)

if __name__ == "__main__":
	main()



