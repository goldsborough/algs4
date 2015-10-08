#!/usr/bin/env python3
# -*- coding: utf-8 -*-


def permutate(string, i=0):
	if i == len(string):
		return ['']
	subsequent = permutate(string, i + 1)
	permutations = []
	for s in subsequent:
		for index in range(len(s) + 1):
			permutations.append(s[:index] + string[i] + s[index:])

	return permutations


def main():
	print(permutate('abc'))


if __name__ == '__main__':
	main()
