#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Dutch national flag.

Given an array of N buckets, each containing a Color.Red, Color.White, or
Color.Blue pebble, sort them by color. The allowed operations are:

swap(i,j): swap the pebble in bucket i with the pebble in bucket j.

color(i): color of pebble in bucket i.

The performance requirements are as follows:

At most N calls to color().
At most N calls to swap().

Constant extra space.
"""

from enum import Enum, unique

@unique
class Color(Enum):
	Red = 0
	White = 1
	Blue = 2

class Dutch(object):
	def __init__(self, seq):
		self.seq = seq
		self.color_count = 0
		self.swap_count = 0

	def swap(self, i, j):
		self.swap_count += 1
		self.seq[i], self.seq[j] = self.seq[j], self.seq[i]

	def color(self, i):
		self.color_count += 1
		return self.seq[i]

	def sort(self):
		i = j = 0
		n = len(self.seq) - 1
		while j <= n:
			color = self.color(j)
			if color == Color.Red:
				self.swap(i, j)
				i += 1
				j += 1
			elif color == Color.Blue:
				self.swap(j, n)
				n -= 1
			else:
				j += 1
		return self.seq

def three_way_partition(seq, left, right):
	"""
	Three-way-partisions a sequence.

	Partitions a sequence of values consisting of three distinct different
	types of elements such that the resulting sequence is sorted.

	Loop invariants:

	1. All values to the left of i are of type 'left'
	2. All values to the right of n are of type 'right'
	3. Values after j have not been looked at.
	4. j <= n for all iterations.

	Makes at most N swaps.

	Arguments:
		seq (iterable): The sequence to partition.
		left: The first category, will end up on the left.
		right: The third category, will end up on the right.

	Returns:
		The sorted (three-way-partitioned) sequence.
	"""
	i = j = 0
	n = len(seq) - 1
	while j <= n:
		value = seq[j]
		if value == left:
			seq[i], seq[j] = seq[j], seq[i]
			i += 1
			j += 1
		elif value == right:
			seq[j], seq[n] = seq[n], seq[j]
			n -= 1
		else:
			j += 1
	return seq

def partition(seq, left, middle, right, stop=False):
	i = 0
	j = len(seq) - 1
	while i < j:
		while i < j and seq[i] != right:
			i += 1
		while j > i and seq[j] != left:
		 	j -= 1
		seq[i], seq[j] = seq[j], seq[i]
	if not stop:
		a = partition(seq[:i], left, right, middle, True)
		b = partition(seq[i:], middle, left, right, True)
		return a + b
	return seq


def main():
	l = [Color.Red, Color.Blue, Color.White, Color.Red, Color.White,
	     Color.Blue, Color.Red, Color.Red, Color.White, Color.Blue]

	dutch = Dutch(l[:])

	print([i.value for i in dutch.sort()])

	result = three_way_partition(l[:], Color.Red, Color.Blue)

	print([i.value for i in result])

	result = partition(l[:], Color.Red, Color.White, Color.Blue)

	print([i.value for i in result])

if __name__ == '__main__':
	main()