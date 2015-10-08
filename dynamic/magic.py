#!/usr/bin/env python3
# -*- coding: utf-8 -*-

def magicIndex(values):
	def _magicIndex(lower, upper):
		if lower == upper:
			return None
		middle = (lower + upper)//2
		if middle < values[middle]:
			return _magicIndex(lower, middle)
		elif middle > values[middle]:
			return _magicIndex(middle + 1, upper)
		return middle
	return _magicIndex(0, len(values))

def main():
	print(magicIndex([-40, -20, 1, 2, 2, 3, 5, 7, 200]))


if __name__ == '__main__':
	main()
