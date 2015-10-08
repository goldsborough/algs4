#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from enum import Enum, unique

from collections import namedtuple

Point = namedtuple('Point', 'x, y')


@unique
class Color(Enum):
	RED = 0,
	BLUE = 1,
	YELLOW = 2,
	GREEN = 3,
	BLACK = 4,
	WHITE = 5


def fill(screen, point, new_color):
	def _fill(screen, point, original_color, new_color):
		if point.y < 0 or point.x < 0:
			return False
		if screen[point.y][point.x] != original_color:
			return False
		screen[point.y][point.x] = new_color
		_fill(screen, Point(point.y - 1, point.x), original_color, new_color)
		for x in range(point.x + 1, len(screen[point.y])):
			if not _fill(screen, Point(point.y, x), original_color, new_color):
				break
		for x in range(point.x - 1, -1, -1):
			if not _fill(screen, Point(point.y, x), original_color, new_color):
				break
		return True

	_fill(screen, point, screen[point.y][point.x], new_color)


def print_screen(screen):
	for row in screen:
		for n, column in enumerate(row):
			if column == Color.BLUE:
				line = 'Blue'
			elif column == Color.RED:
				line = 'Red'
			elif column == Color.GREEN:
				line = 'Green'
			else:
				line = 'Other!'
			if n + 1 < len(row):
				line = '{0} | '.format(line)
			print(line.rjust(10), end='')
		print()


def main():
	screen = [
		[Color.BLUE] * 5,
		[Color.BLUE, Color.RED, Color.BLUE, Color.RED, Color.BLUE],
		[Color.BLUE, Color.RED, Color.RED, Color.RED, Color.BLUE],
		[Color.BLUE, Color.BLUE, Color.RED, Color.RED, Color.BLUE],
		[Color.BLUE] * 5,
	]
	point = Point(2, 2)

	print_screen(screen)

	fill(screen, point, Color.GREEN)

	print('\n')

	print_screen(screen)


if __name__ == '__main__':
	main()
