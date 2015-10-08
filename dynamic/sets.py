#!/usr/bin/env python3
# -*- coding: utf-8 -*-


def subsets(values):
	if not values:
		return [[]]
	value = values[0]
	without = subsets(values[1:])
	with_value = [[value] + i for i in without]

	return [i for i in without + with_value if i]


def main():
	print(subsets([0, 1, 2, 3, 4]))


if __name__ == '__main__':
	main()
