#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

def threesumA(sequence):
	count = 0
	length = len(sequence)
	for i in range(length):
		for j in range(i + 1, length):
			for k in range(j + 1, length):
				if sequence[i] + sequence[j] + sequence[k] == 0:
					count += 1
	return count

def find(sequence, value):
	if not sequence:
		return False
	mid = len(sequence)//2
	if value < sequence[mid]:
		return find(sequence[:mid], value)
	elif value > sequence[mid]:
		return find(sequence[mid + 1:], value)
	else:
		return True

def threesumB(sequence):
	count = 0
	length = len(sequence)
	sequence.sort()
	for i in range(length):
		for j in range(i + 1, length):
			value = sequence[i] + sequence[j]
			if find(sequence[j + 1:], -value):
				count += 1
	return count

def main():
	l = [5, 3, -8, 8, 7, -2, 0, -10, 10]
	print(threesumA(l))
	print(threesumB(l))

if __name__ == "__main__":
	main()