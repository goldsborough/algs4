#!/usr/bin/env python
# -*- coding: utf-8 -*-

from collections import namedtuple

Range = namedtuple('Range', ['left', 'right', 'sum'])

def compute_changes(array):
	changes = []
	for i,j in zip(array, array[1:]):
		changes.append(j - i)
	return changes

def find_crossing(changes, first, last):
	size = last - first
	middle = first + size//2

	i_max = middle
	left_sum = left_max = changes[i_max]

	for i in range(i_max - 1, first - 1, -1):
		left_sum += changes[i]
		if left_sum > left_max:
			left_max = left_sum
			i_max = i

	j_max = middle + 1

	if j_max >= size:
		return Range(i_max, j_max, left_max + 0)

	right_sum = right_max = changes[j_max]

	for j in range(j_max + 1, last):
		right_sum += changes[j]
		if right_sum > right_max:
			right_max = right_sum
			j_max = j
			
	return Range(i_max, j_max + 1, left_max + right_max)


def find_max_sub(changes, first, last):
	if (last - first) <= 1:
		return Range(first, last, changes[first] if changes else 0)
	middle = (first + last)//2
	
	left = find_max_sub(changes, first, middle)
	right = find_max_sub(changes, middle, last)
	crossing = find_crossing(changes, first, last)

	return max((left, right, crossing), key=lambda sub: sub.sum)

def max_sub(array):
	changes = compute_changes(array)
	result = find_max_sub(changes, 0, len(changes))
	return result.left, result.right - 1

def main():
	array = [100, 113, 110, 85, 105, 102, 86, 63,
			 81, 101, 94, 106, 101, 79, 94, 90, 97]
	print(max_sub(array))

if __name__ == '__main__':
	main()