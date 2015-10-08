#!/usr/bin/env python3
# -*- coding: utf-8 -*-


def memoize(cache_index=0):
	def decorator(function):
		cache = {}
		def proxy(*args, **kwargs):
			key = args[cache_index]
			if key not in cache:
				cache[key] = function(*args, **kwargs)
			return cache[key]
		return proxy
	return decorator


@memoize()
def count_steps(n):
	if n == 0:
		return 1
	possibilities = 0
	for k in range(1, 4):
		if k <= n:
			possibilities += count_steps(n - k)

	return possibilities

@memoize(-1)
def count_steps_forward(n, i=0):
	if i == n:
		return 1
	possibilities = 0
	for k in range(1, 4):
		if i + k <= n:
			possibilities += count_steps_forward(n, i + k)

	return possibilities



def count_steps_bottom_up(n):
	cache = [0, 0, 1]
	for k in range(1, n + 1):
		this = cache[-1]
		if k >= 2:
			this += cache[-2]
		if k >= 3:
			this += cache[-3]
		cache[-3] = cache[-2]
		cache[-2] = cache[-1]
		cache[-1] = this

	return cache[-1]


def main():
	print(count_steps(100))

if __name__ == '__main__':
	main()
