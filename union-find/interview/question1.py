#!/usr/local/bin/python3
#! -*- coding: utf-8 -*-

"""
Social network connectivity. 

Given a social network containing N members and a
log file containing M timestamps at which times pairs of members formed
friendships, design an algorithm to determine the earliest time at which all
members are connected (i.e., every member is a friend of a friend of a
friend ... of a friend). Assume that the log file is sorted by timestamp and
that friendship is an equivalence relation. The running time of your
algorithm should be M log N or better and use extra space proportional to N.
"""

import time

from collections import namedtuple

class Network(object):
	def __init__(self, N):
		self.parent = [x for x in range(N)]
		self.size = [1 for x in range(N)]
		self.roots = N

	def connect(self, p, q):
		rootP = self.root(p)
		rootQ = self.root(q)

		if rootP != rootQ:
			if self.size[rootP] >= self.size[rootQ]:
				self.parent[rootQ] = rootP
				self.size[rootP] += self.size[rootQ]
			else:
				self.parent[rootP] = rootQ
				self.size[rootQ] += self.size[rootP]
			self.roots -= 1

	def allConnected(self):
		return self.roots == 1

	def root(self, node):
		while node != self.parent[node]:
			self.parent[node] = self.parent[self.parent[node]]
			node = self.parent[node]
		return node

	def connected(self, p, q):
		return self.root(p) == self.root(q)

def main():

	N = 8

	Entry = namedtuple("Entry", ["timestamp", "p", "q"])

	entries = []

	with open("log.txt") as file:
		for entry in file.read().split("\n"):
			if entry:
				t, p, q = [int(i) for i in entry.split()]
				entries.append(Entry(t, p, q))

	network = Network(N)

	for entry in entries:
		network.connect(entry.p, entry.q)
		if network.allConnected():
			print(time.ctime(entry.timestamp))
			break
	else:
		print("Not all connected!")

if __name__ == "__main__":
	main()