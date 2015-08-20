#!/usr/bin/env python
# -*- coding: utf-8 -*-

import random

def is_sorted(sequence):
	for i, j in zip(sequence, sequence[1:]):
		if j < i:
			return False
	return True


def swap(sequence, i, j):
	sequence[i], sequence[j] = sequence[j], sequence[i]


def shuffle(sequence):
	for i in range(1, len(sequence)):
		swap(sequence, i, random.randrange(i + 1))


def partition(sequence, i, j):
	pivot = i
	i += 1
	j -= 1
	while i < j:
		while i < j and sequence[i] < sequence[pivot]:
			i += 1
		while i < j and sequence[j] > sequence[pivot]:
			j -= 1
		swap(sequence, i, j)

	if sequence[i] >= sequence[pivot]:
		i -= 1

	swap(sequence, pivot, i)

	return i


def actual_sort(sequence, i, j):
	if (j - i) <= 1:
		return
	pivot = partition(sequence, i, j)
	actual_sort(sequence, i, pivot)
	actual_sort(sequence, pivot + 1, j)
	assert is_sorted(sequence[i:j])


def sort(sequence):
	shuffle(sequence)
	actual_sort(sequence, 0, len(sequence))


def main():
	l = [1, 4, 5, 3, 20, -1, 7, 2, 0]
	sort(l)
	print(l)

if __name__ == '__main__':
	main()