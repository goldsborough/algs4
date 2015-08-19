#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Permutation.

Given two integer arrays of size N, design a subquadratic algorithm to
determine whether one is a permutation of the other. That is, do they
contain exactly the same entries but, possibly, in a different order.
"""

def solution1(a, b):
	# O(2N lg N + N) = O(N(2lgN + 1)) = O(N lg N)
	a.sort() # O(N lg N)
	b.sort() # O(N lg N)
	return a == b # O(N)

def find(seq, key):
	if not seq:
		return False
	mid = len(seq)//2
	if key < seq[mid]:
		return find(seq[:mid], key)
	if key > seq[mid]:
		return find(seq[mid + 1:], key)
	return True

def solution2(a, b):
	# O(N lg N + N lg N) = O(2N lg N) = O(N lg N)
	b.sort() # O(N lg N)
	count = 0
	for i in a: # O(N)
		if find(b, i): # O(lg N)
			count += 1
	return count == len(a)

def are_permutations(a, b):
	return solution2(a, b)

def main():
	a = [1, 2, 3, 4]
	b = [2, 1, 4, 3]
	c = [1, 4, 3, 2]
	print(are_permutations(a, b), are_permutations(b, c))
	d = [0, 2, 3, 4]
	e = [1, 2, 2, 3]
	print(are_permutations(a, d), are_permutations(a, e))

if __name__ == '__main__':
	main()