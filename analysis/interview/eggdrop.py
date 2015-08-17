#! /usr/bin/env python
# -*- coding: utf-8 -*-

"""
Egg drop.

Suppose that you have an N-story building (with floors 1 through N)
and plenty of eggs. An egg breaks if it is dropped from floor T or higher and
does not break otherwise. Your goal is to devise a strategy to determine the
value of T given the following limitations on the number of eggs and tosses:

Version 0: 1 egg, <= T tosses.
Version 1: ∼lg(N) eggs and ∼lg(N) tosses.
Version 2: ∼lg(T) eggs and ∼2lg(T) tosses.
Version 3: 2 eggs and ∼2√N tosses.
Version 4: 2 eggs and ≤c√T tosses for some fixed constant c.
"""

def egg_breaks(T):
	print(T)

def version0(N):
	T = 1
	for _ in range(N):
		if egg_breaks(T):
			return T
		T += 1

def version1(N, M=0):
	if M > N:
		return -1
	T = (N+1)//2
	if egg_breaks(T):
		return T
	U = version1(T-1)
	if U != -1:
		return U
	return version1(N, T+1)

def look(first, last):
	if first > last:
		return -1
	T = (first + last) / 2
	if egg_breaks(T):
		return T
	T = look(first, T-1)
	if T != -1:
		return T
	return look(T+1, last)

def version2(N):
	last = 1
	current = 1
	while current <= N:
		T = look(last, current)
		if T != -1:
			return T
		last = current
		current *= 2

def version3(N):
	first = 1
	last = 2
	while first <= N:
		if egg_breaks(first):
			return first
		if egg_breaks(last):
			mid = (first+last)/2
			if egg_breaks(mid):
				last = mid
			else:
				first = mid
		else:
			first = last
			last = first * 2


