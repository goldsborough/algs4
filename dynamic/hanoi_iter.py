#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from collections import namedtuple

Move = namedtuple('Move', 'source, target, disc')


def incr(index, end=3):
	return (index + 1) % end


def hanoi(discs):
	rods = [[i for i in range(discs, 0, -1)], [], []]
	moves = []
	source = 0
	while len(rods[-1]) != discs:
		ok = True
		while not rods[source]:
			source = incr(source)
		disc = rods[source][-1]
		target = incr(source)
		while rods[target] and disc > rods[target][-1]:
			target = incr(target)
			if source == target:
				ok = False
		if ok and len(rods[target]) != discs or target == 2:
			del rods[source][-1]
			rods[target].append(disc)
			moves.append(Move(source, target, disc))
		source = incr(target)

	return moves

def main():
	print(len(hanoi(4)))

if __name__ == '__main__':
	main()
