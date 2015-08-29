#!/usr/bin/env python
# -*- coding: utf-8 -*-

class HashTable(object):

	def __init__(self):
		self.bins = []
		self.size = 0

	def insert(self, key, value):
		pass

	def at(self, key, value):
		pass

	def erase(self, key):
		pass

	def hash(self, key):
		return hash(key) & 0x7FFFFFFF % len(self.bins)

	def contains(self, key):
		pass

	def __len__(self):
		return self.size

	def is_empty(self):
		pass

def main():
	pass

if __name__ == '__main__':
	main()