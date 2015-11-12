#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from collections import namedtuple

Move = namedtuple('Move', 'disc, source, to')

def hanoi(discs):
	def __recurse(discs, source=0, helper=1, target=2):
		if discs <= 0:
			return []
		moves = __recurse(discs - 1, source, target, helper)
		moves.append(Move(discs, source, target))
		moves += __recurse(discs - 1, helper, source, target)
		return moves
	return __recurse(discs)

def main():
	print(hanoi(3))

if __name__ == '__main__':
	main()
