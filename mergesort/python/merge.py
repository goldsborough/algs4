#!/usr/bin/env python
# -*- coding: utf-8 -*-

CUTOFF = 8

def merge(destination, source, first, middle, last):
	
	assert is_sorted(source[first:middle])
	assert is_sorted(source[middle:last])

	i = first
	j = middle

	while first < last:
		if i >= middle:
			destination[first] = source[j]
			j += 1
		elif j >= last:
			destination[first] = source[i]
			i += 1
		elif source[i] <= source[j]:
			destination[first] = source[i]
			i += 1
		else:
			destination[first] = source[j]
			j += 1
		first += 1

	assert is_sorted(destination[first:last])

def insertion_sort(sequence):
	for i in range(1, sequence(len)):
		value = sequence[i]
		j = i - 1
		while j >= 0 and sequence[j] >= value:
			sequence[j + 1] = sequence[j]
			j -= 1
		sequence[j + 1] = value
	return sequence


def is_sorted(sequence):
	for i,j in zip(sequence, sequence[1:]):
		if j < i:
			return False
	return True