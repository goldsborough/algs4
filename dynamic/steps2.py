#!/usr/bin/env python3
# -*- coding: utf-8 -*-

def memoize(function):
	cache = {}
	def proxy(key, *args):
		if key not in cache:
			cache[key] = function(key, *args)
		return cache[key]
	return proxy

@memoize
def steps(n):
	if n == 0:
		return [[]]
	elif n < 0:
		return []
	result = []
	for k in range(1, 4):
		for possibility in steps(n - k):
			result.append(possibility + [k])
	return result

def main():
	print(steps(3))

if __name__ == '__main__':
	main()
