#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from timeit import timeit

def parantheses(n):
	def _parantheses(opened, closed):
		if opened == 0 and closed == 0:
			return [[]]
		strings = []
		if opened > 0 and opened <= closed:
			rest = _parantheses(opened - 1, closed)
			strings += [['('] + i for i in rest]
		if closed > 0 and closed >= opened:
			rest = _parantheses(opened, closed - 1)
			strings += [[')'] + i for i in rest]
		return strings
	return [''.join(i) for i in _parantheses(n, n)]


def main():
	#print(parantheses(3))
	print(timeit('parantheses(3)', globals=globals()))


if __name__ == '__main__':
	main()
