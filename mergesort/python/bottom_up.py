#!/usr/bin/env python
# -*- coding: utf-8 -*-

import merge

def sort(sequence):
	size = len(sequence)

	if size < merge.CUTOFF:
		merge.insertion_sort(sequence)
		return
	
	auxiliary = sequence[:]
	bucket = 1
	while bucket < size:
		first = 0
		middle = bucket
		last = middle + bucket
		while first < size:
			middle = middle if middle <= size else size
			last = last if last <= size else size
			merge.merge(auxiliary, sequence, first, middle, last)
			first = last
			middle = first + bucket
			last = middle + bucket
		sequence, auxiliary = auxiliary, sequence
		bucket *= 2
	return sequence

def main():
	seq = [2, 3, 5, 2, 0, 10, 1, 4, 3, -1]

	seq = sort(seq)

	print(seq)

if __name__ == '__main__':
	main()
