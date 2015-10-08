#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from collections import namedtuple

def memoize(*indices):
	Key = namedtuple('Key', [chr(65 + i) for i in range(len(indices))])
	def decorator(function):
		cache = {}
		def proxy(*args, **kwargs):
			key = Key(*[args[i] for i in indices])
			if key not in cache:
				cache[key] = function(*args, **kwargs)
			return cache[key]
		return proxy
	return decorator


@memoize(0, 1)
def paths(x, y):
	if x == 0 and y == 0:
		return 1
	possibilities = 0
	if x > 0:
		possibilities += paths(x - 1, y)
	if y > 0:
		possibilities += paths(x, y - 1)

	return possibilities

@memoize(0, 1)
def find_path(x, y, bad):
	if x == 0 and y == 0:
		return [(0, 0)]
	path = None
	if x > 0 and (x - 1, y) not in bad:
		path = find_path(x - 1, y, bad)
	if y > 0 and (x, y - 1) not in bad:
		result = find_path(x, y - 1, bad)
		if result is not None:
			if path is None or len(result) < len(path):
				path = result

	if path is not None:
		return [(x, y)] + path
	return None


def main():
	print(find_path(100, 100, [(0, 1)]))


if __name__ == '__main__':
	main()
