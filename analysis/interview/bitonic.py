#! /usr/bin/env python
# -*- coding: utf-8 -*-

"""
Search in a bitonic array.

An array is bitonic if it is comprised of an increasing sequence of integers
followed immediately by a decreasing sequence of integers. Write a program that, 
given a bitonic array of N distinct integer values, determines whether a given
integer is in the array.

Standard version: Use ∼ 3lg(N) compares in the worst case.

Signing bonus: Use ∼ 2lg(N) compares in the worst case (and prove that no
algorithm can guarantee to perform fewer than ∼ 2lg(N) compares in the worst case).

[1, 5, 10, 15, 20, 3, 2, 0]
"""

def find_boundary(sequence):
	if len(sequence) < 3:
		return -1
	mid = len(sequence)//2
	if (sequence[mid - 1] < sequence[mid] and
	    sequence[mid + 1] < sequence[mid]):
		return mid
	elif sequence[mid - 1] < sequence[mid]:
		return find_boundary(sequence[mid:])
	else:
		return find_boundary(sequence[:mid + 1])

"""
First solution:

def find(sequence, x, comp=lambda x,y: x < y):
	if not sequence:
		return False
	mid = len(sequence)//2
	if sequence[mid] == x:
		return True
	elif comp(x, mid):
		return find(sequence[:mid], x, comp)
	else:
		return find(sequence[mid + 1:], x, comp)

def bitonic(sequence, x):

	boundary = find_boundary(sequence)

	if find(sequence[:boundary], x):
		return True

	return find(sequence[boundary:], x, lambda x,y: x > y)	
"""

def find(left, right, key):

	if not left and not right:
		return False

	if left:
		left_mid = len(left)//2

	if right:
		right_mid = len(right)//2

	if ((left and left[left_mid] == key) or
		(right and right[right_mid] == key)):
		return True

	if left:
		if key < left[left_mid]:
			left = left[:left_mid]
		else:
			left = left[left_mid+1:]

	if right:
		if key > right[right_mid]:
			right = right[:right_mid]
		else:
			right = right[right_mid+1:]

	return find(left, right, key)

def bitonic(sequence, x):

	boundary = find_boundary(sequence)

	return find(sequence[:boundary], sequence[boundary:], x)

def main():
	l = [1, 2, 3, 4, 5, 3, 2, 1]
	print(bitonic(l, 5))

if __name__ == "__main__":
	main()