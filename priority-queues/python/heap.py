#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Heap(object):
	def __init__(self):
		self.keys = [None]

	def insert(self, key):
		self.keys.append(key)
		self.swim(len(self))

	def delete_max(self):
		key = self.keys[1]
		self.swap(1, -1)
		del self.keys[-1]
		self.sink(1)
		return key

	def max(self):
		return self.keys[1]

	def __len__(self):
		return len(self.keys) - 1

	def empty(self):
		return len(self) == 0

	def swap(self, i, j):
		self.keys[i], self.keys[j] = self.keys[j], self.keys[i]

	def parent(self, key):
		return key//2 if key > 1 else key

	def left(self, key):
		return 2 * key

	def right(self, key):
		return 2 * key + 1

	def swim(self, key):
		parent = self.parent(key)
		while self.keys[parent] < self.keys[key]:
			self.swap(parent, key)
			key = parent
			parent = self.parent(key)

	def sink(self, key):
		while key < len(self):
			child = left = self.left(key)
			if left > len(self):
				break
			right = self.right(key)
			if right <= len(self):
				child = max([left, right], key=lambda k: self.keys[k])
			if self.keys[child] <= self.keys[key]:
				break
			self.swap(child, key)
			key = child

def main():
	heap = Heap()
	heap.insert(5)
	heap.insert(-1)
	heap.insert(7)
	heap.insert(0)
	while not heap.empty():
		print(len(heap), heap.delete_max())

if __name__ == '__main__':
	main()