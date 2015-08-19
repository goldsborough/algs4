#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Intersection of two sets.

Given two arrays a[] and b[], each containing N distinct 2D points in the
plane, design a subquadratic algorithm to count the number of points that
are contained both in array a[] and array b[].
"""

def find(seq, key):
	if not seq:
		return False
	mid = len(seq)//2
	if key < seq[mid]:
		return find(seq[:mid], key)
	elif key > seq[mid]:
		return find(seq[mid + 1:], key)
	return True

def count(a, b):
	b.sort()
	c = 0
	for i in a:
		if find(b, i):
			c += 1
	return c

def main():
	a = [1, 2, 3, 4, 8, 10, -1]
	b = [4, 2, 304, 3, 4, 5, 6, 11, 10, -2, -3, -1]
	print(count(a, b))

if __name__ == '__main__':
	main()