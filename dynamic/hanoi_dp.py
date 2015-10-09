#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import math
import copy

from collections import namedtuple

Move = namedtuple('Move', 'source, target, disc')

def hanoi(discs):
	seen = set()
	def __solve(rods, depth=0):
		if len(rods[2]) == discs:
			return []
		if rods in seen:
			return None
		seen.add(rods)
		best = None
		least_moves = math.inf
		for source in range(len(rods)):
			if not rods[source]:
				continue
			disc = rods[source][-1]
			for target in range(len(rods)):
				if ((source == target) or
					(rods[target] and disc > rods[target][-1])):
					continue
				copied = []
				for i, p in enumerate(rods):
					if i == source:
						copied.append(p[:-1])
					elif i == target:
						copied.append(tuple(list(p) + [disc]))
					else:
						copied.append(p)
				moves = __solve(tuple(copied), depth + 1)
				if moves is not None and len(moves) < least_moves:
					best = [Move(source, target, disc)] + moves
					least_moves = len(moves)
		return best
	return __solve(tuple([
		tuple(n for n in range(discs - 1, -1, -1)),
		tuple([]),
		tuple([])
	]))



def main():
	print(len(hanoi(3)))


if __name__ == '__main__':
	main()
