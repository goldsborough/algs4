#!/usr/bin/env python
# -*- coding: utf-8 -*-

def swap(sequence, i, j):
	sequence[i], sequence[j] = sequence[j], sequence[i]

def right_child(i):
	return 2 * i + 2

def left_child(i):
	return 2 * i + 1

def sink(sequence, i, n):
	while i < n:
		child = left = left_child(i)
		if left >= n:
			break
		right = right_child(i)
		if right < n and sequence[right] > sequence[left]:
			child = right
		if sequence[child] > sequence[i]:
			swap(sequence, child, i)
		else:
			break
		i = child

def sort(sequence):
	for n in range(len(sequence)//2, -1, -1):
		sink(sequence, n, len(sequence))
	for n in range(len(sequence) - 1, -1, -1):
		swap(sequence, 0, n)
		sink(sequence, 0, n)
	return sequence

def main():
	l = [0, 4, -2, 10, 7, -3, 20, 6, 5, 1]
	print(sort(l))

if __name__ == '__main__':
	main()