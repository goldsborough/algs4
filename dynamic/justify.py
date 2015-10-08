#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import math
import re


class Justifier(object):
	def __init__(self, text):
		self.text = text
		self.words = []
		word = re.compile(r'\w+')
		match = word.search(text)
		while match:
			self.words.append(match.start())
			match = word.search(text, match.end())

	def justify(self, width):
		cache, breakpoints = self.break_lines(width, {}, {})
		return self.reconstruct(breakpoints)

	def break_lines(self, width, cache, breakpoints, index=0):
		if index not in cache:
			champion = None
			if index + 1 == len(self.words):
				minimum_badness = 0
			else:
				minimum_badness = math.inf
				for next_index in range(index + 1, len(self.words)):
					word_index = self.words[next_index]
					badness, breakpoints = self.break_lines(
												width,
												cache,
												breakpoints,
												next_index
											)
					badness += self.line_badness(index, next_index, width)
					if badness < minimum_badness:
						minimum_badness = badness
						champion = word_index
			cache[index] = minimum_badness
			breakpoints[self.words[index]] = champion
		return cache[index], breakpoints

	def reconstruct(self, breakpoints):
		justified = []
		index = 0
		while index is not None:
			next_index = breakpoints[index]
			justified.append(self.text[index:next_index])
			index = next_index

		return justified

	def line_badness(self, first, second, width):
		line = self.text[self.words[first]:self.words[second]]
		line = re.sub(r'\s', '', line)
		if len(line) > width:
			return math.inf
		return (width - len(line))**3


def main():
	#text = 'the quick brown fox smoked pot in the spaceship with Darth Vader'
	text = 'the quick brown fox'
	justifier = Justifier(text)
	print(justifier.justify(10))

if __name__ == '__main__':
	main()
