#!/usr/bin/env python
# -*- coding: utf-8 -*-

import random

def selection(seq):
	for i in range(len(seq)):
		minimum = i
		for j in range(i+1, len(seq)):
			if seq[j] < seq[minimum]:
				minimum = j
		seq[i], seq[minimum] = seq[minimum], seq[i]
	return seq

def insertion(seq):
	for i in range(1, len(seq)):
		value = seq[i]
		j = i - 1
		while j >= 0 and seq[j] >= value:
			seq[j+1] = seq[j]
			j -= 1
		seq[j + 1] = value
	return seq

def shell(seq):
	h = 1
	while h < len(seq)/3:
		h = h * 3 + 1
	while h >= 1:
		for i in range(h, len(seq), h):
			value = seq[i]
			j = i - h
			while j >= 0 and seq[j] >= value:
				seq[j + h] = seq[j]
				j -= h
			seq[j + h] = value
		h //= 3
	return seq

def r_shell(seq):
	h = 1
	while h < len(seq)/3:
		h = h * 3 + 1
	return do_r_shell(seq, h)

def do_r_shell(seq, h):
	if h == 0:
		return seq
	for i in range(h, len(seq), h):
		value = seq[i]
		j = i - h
		while j >= 0 and seq[j] >= value:
			seq[j + h] = seq[j]
			j -= h
		seq[j + h] = value
	return do_r_shell(seq, h//3)

def shuffle1(seq):
	for i in range(len(seq)):
		j = random.randrange(i + 1)
		seq[i], seq[j] = seq[j], seq[i]
	return seq

def shuffle2(seq):
	for n in range(len(seq)):
		i = random.randrange(n + 1)
		j = random.randrange(n + 1)
		seq[i], seq[j] = seq[j], seq[i]
	return seq

def shuffle3(seq):
	return random.shuffle(seq)

def main():
	
	l = [10, 2, 46, 3, 9, 4, 3, 3, 12, 11, 200, 5]

	l = shell(l)

	shuffle2(l)

	print(l)

if __name__ == '__main__':
	main()