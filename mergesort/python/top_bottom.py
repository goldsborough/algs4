#!/usr/bin/env python
# -*- coding: utf-8 -*-

import merge

def sort(sequence):
	if len(sequence) < merge.CUTOFF:
		merge.insertion_sort(sequence)
	else:
		_sort(sequence, sequence[:], 0, len(sequence))

def _sort(destination, source, first, last):
	length = last - first
	if length <= 1: return
	middle = first + length//2

	_sort(source, destination, first, middle)
	_sort(source, destination, middle, last)

	if source[middle - 1] > source[middle]:
		merge.merge(destination, source, first, middle, last)

def main():
	seq = [2, 3, 5, 2, 0, 10, 1, 4, 3, -1]

	sort(seq)

	print(seq)

if __name__ == '__main__':
	main()