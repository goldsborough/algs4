#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import copy

from collections import namedtuple

Position = namedtuple('Position', 'row, column')

def mark(possible, row, column):
	copied = copy.deepcopy(possible)

	# Mark the row
	copied[row] = [False for _ in range(len(possible))]
	# Mark the column
	for r in copied:
		r[column] = False

	# Mark the diagonals
	stop = False
	i = 1
	while not stop:
		stop = True
		if (row - i) >= 0:
			if (column - i) >= 0:
				stop = False
				copied[row - i][column - i] = False
			if (column + i) < len(possible):
				stop = False
				copied[row - i][column + i] = False
		if (row + i) < len(copied):
			if (column - i) >= 0:
				stop = False
				copied[row + i][column - i] = False
			if (column + i) < len(copied):
				stop = False
				copied[row + i][column + i] = False
		i += 1

	return copied

def arrangements(queens, size):
	initial = [[True for column in range(size)]for row in range(size)]
	def __arrangements(queens, row, possible):
		if row >= len(possible) or queens == 0:
			return [[]]
		positions = []
		for column, is_possible in enumerate(possible[row]):
			if is_possible:
				marked = mark(possible, row, column)
				for possibility in __arrangements(queens - 1, row + 1, marked):
					positions.append([Position(row, column)] + possibility)
		return positions
	return __arrangements(queens, 0, initial)

def main():
	print(arrangements(8, 8))


if __name__ == '__main__':
	main()
