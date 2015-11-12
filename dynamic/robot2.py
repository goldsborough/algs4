#!/usr/bin/env python3
# -*- coding: utf-8 -*-

def memoize(number):
	def outer(function):
		cache = {}
		def inner(*args):
			key = tuple(args[:number])
			if key not in cache:
				cache[key] = function(*args)
			return cache[key]
		return inner
	return outer

@memoize(2)
def paths(x, y, blocked):
	if x < 0 or y < 0 or (x, y) in blocked:
		return []
	if x == 0 and y == 0:
		return [[(0, 0)]]
	result = []
	for path in paths(x, y - 1, blocked):
		result.append(path + [(x, y)])
	for path in paths(x - 1, y, blocked):
		result.append(path + [(x, y)])
	return result

def main():
	print(paths(1, 1, set([(1, 0)])))

if __name__ == '__main__':
	main()
