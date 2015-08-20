#!/usr/bin/env python
# -*- coding: utf-8 -*-

import basic

CUTOFF = 10

def insertion_sort(sequence):
	for i in range(1, len(sequence)):
		value = sequence[i]
		j = i - 1
		while j >= 0 and sequence[j] >= value:
			sequence[j + 1] = sequence[j]
			j -= 1
		sequence[j + 1] = value

def median(sequence, i, j):
	first = i
	second = (i + j)//2
	third = j - 1
	if sequence[first] < sequence[second]:
		if sequence[second] < sequence[third]:
			return second
		elif sequence[first] < sequence[third]:
			return third
		else:
			return first
	elif sequence[first] < sequence[third]:
		return first
	elif sequence[third] < sequence[second]:
		return second
	else:
		return third


def partition(sequence, i, j):
	basic.swap(sequence, i, median(sequence, i, j))
	pivot = i
	i += 1
	k = i
	j -= 1
	while i < j:
		if sequence[i] < sequence[pivot]:
			basic.swap(sequence, k, i)
			i += 1
			k += 1
		elif sequence[i] > sequence[pivot]:
			basic.swap(sequence, i, j)
			j -= 1
		else:
			i += 1
	if sequence[j] == sequence[pivot]:
		j += 1
	basic.swap(sequence, pivot, k - 1)
	return k - 1, j


def actual_sort(sequence, i, j):
	if (j - i) <= CUTOFF:
		return
	left, right = partition(sequence, i, j)
	actual_sort(sequence, i, left)
	actual_sort(sequence, right, j)

def sort(sequence):
	basic.shuffle(sequence)
	actual_sort(sequence, 0, len(sequence))
	insertion_sort(sequence)


def main():
	l = [1, 4, 5, 3, 20, -1, 7, 2, 0, 5, 11, 10, -6, 5, 5, 5]
	sort(l)
	print(l)

if __name__ == '__main__':
	main()