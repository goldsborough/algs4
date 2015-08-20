#!/usr/bin/env python
# -*- coding: utf-8 -*-

import basic

def partition(sequence, i, j):
	pivot = i
	i += 1
	j -= 1
	while i < j:
		while i < j and sequence[i] < sequence[pivot]:
			i += 1
		while i < j and sequence[j] > sequence[pivot]:
			j -= 1
		basic.swap(sequence, i, j)
	if sequence[i] >= sequence[pivot]:
		i -= 1
	basic.swap(sequence, pivot, i)
	return i

def select(sequence, k):
	if k > 0:
		k -= 1
	i = 0
	j = len(sequence)
	while i < j:
		middle = partition(sequence, i, j)
		if k < middle:
			j = middle
		elif k > middle:
			i = middle + 1
		else:
			break
	return sequence[k]

def main():
	l = [1, 4, 5, 3, 20, -1, -6, 2, 0, 5, 11, 10, -6]
	print(select(l, 2))

if __name__ == '__main__':
	main()