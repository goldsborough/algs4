#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import random

CUTOFF = 8


def __insertion_sort(sequence, start, end):
	stop = start - 1
	start += 1
	while start < end:
		value = sequence[start]
		i = start - 1
		while i > stop and sequence[i] > value:
			sequence[i + 1] = sequence[i]
			i -= 1
		sequence[i + 1] = value
		start += 1

	return sequence


def __is_sorted(sequence, start, end):
	start += 1
	while start < end:
		if sequence[start - 1] > sequence[start]:
			return False
		start += 1

	return True


def __swap(sequence, first, second):
	sequence[first], sequence[second] = sequence[second], sequence[first]


def __partition3(sequence, start, end):
	pivot = start
	start += 1
	end -= 1
	itr = start
	while itr <= end:
		if sequence[itr] == sequence[pivot]:
			itr += 1
		elif sequence[itr] < sequence[pivot]:
			__swap(sequence, start, itr)
			start += 1
			itr += 1
		else:
			__swap(sequence, itr, end)
			end -= 1
	start -= 1
	__swap(sequence, pivot, start)

	return start, itr


def __shuffle(sequence, start, end):
	for i in range(start + 1, end):
		random_index = random.randrange(start, i + 1)
		__swap(sequence, random_index, i)

	return sequence


def __partition(sequence, start, end):
	pivot = start
	start += 1
	end -= 1
	while start < end:
		while start < end and sequence[start] < sequence[pivot]:
			start += 1
		while start < end and sequence[end] > sequence[pivot]:
			end -= 1
		if start == end:
			break
		__swap(sequence, start, end)
		start += 1
		end -= 1
	if sequence[start] > sequence[pivot]:
		start -= 1
	__swap(sequence, pivot, start)

	return start


def __sort(sequence, start, end):
	if (end - start) < CUTOFF:
		return __insertion_sort(sequence, start, end)

	equal_range = __partition3(sequence, start, end)
	__sort(sequence, start, equal_range[0])
	__sort(sequence, equal_range[1], end)

	#pivot = __partition(sequence, start, end)
	#__sort(sequence, start, pivot)
	#__sort(sequence, pivot + 1, end)

	assert __is_sorted(sequence, start, end)

	return sequence


def sort(sequence, start=0, end=None):
	end = end or len(sequence)
	__shuffle(sequence, start, end)
	return __sort(sequence, start, end)


def main():
	l = [4, 0, 10, 3, 4, 6, 2, 9, 7, 5]
	print(sort(l))

if __name__ == '__main__':
	main()
