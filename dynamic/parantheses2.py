#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from timeit import timeit

def memoize(function):
	cache = {}
	def proxy(opening, closing, *args):
		if (opening, closing) not in cache:
			result = function(opening, closing, *args)
			cache[opening, closing] = result
			return result
		return cache[opening, closing]
	return proxy

def parantheses(number):
	def __recurse(opening, closing):
		if closing == 0 and opening == 0:
			return [['']]
		if closing < opening:
			return []
		result = []
		if opening > 0:
			for possibility in __recurse(opening - 1, closing):
				result.append(['('] + possibility)
		for possibility in __recurse(opening, closing - 1):
			result.append([')'] + possibility)
		return result
	return [''.join(i) for i in __recurse(number, number)]


def main():
	#print(parantheses(3))
	print(timeit('parantheses(3)', globals=globals()))

if __name__ == '__main__':
	main()
