#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from collections import namedtuple

Move = namedtuple('Move', 'source, target, disc')

def hanoi(discs):
	moves = []
	def __hanoi(discs, source=0, auxiliary=1, target=2):
		if discs <= 0:
			return
		__hanoi(discs - 1, source, target, auxiliary)
		moves.append(Move(source, target, discs))
		__hanoi(discs - 1, auxiliary, source, target)
	__hanoi(discs)

	return moves

def main():
	print(len(hanoi(6)))

if __name__ == '__main__':
	main()
