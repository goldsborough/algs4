#! /usr/bin/env python
# -*- coding: utf-8 -*-

"""
3-SUM in quadratic time.

Design an algorithm for the 3-SUM problem that takes
time proportional to N^2 in the worst case. You may assume that you can sort
the N integers in time proportional to N^2 or better.
"""

def threesum(sequence):

	count = 0
	sequence.sort()
	for n, i in enumerate(sequence):
		rest = sequence[n + 1:]
		left = 0
		right = len(rest) - 1
		while left < right:
			value = rest[left] + rest[right]
			if value == -i:
				count += 1
				left += 1
			elif value < -i:
				left += 1
			else:
				right -= 1

	return count


def main():
	l = [5, 3, -8, 8, 7, -2, 0, -10, 10]
	print(threesum(l))

if __name__ == "__main__":
	main()