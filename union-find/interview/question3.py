#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

"""
Successor with delete.

Given a set of N integers S={0,1,...,N−1} and a 
sequence of requests of the following form:

- Remove x from S
- Find the successor of x: the smallest y in S such that y≥x.

Design a data type so that all operations (except construction)
should take logarithmic time or better.

S = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }

"""

def delete(S, x):
	del S[x]
	return S[x] if x < len(S) else S[x - 1]

def main():
	
	S = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

	print(delete(S, 8))


if __name__ == "__main__":
	main()