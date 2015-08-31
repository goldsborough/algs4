#!/usr/bin/env python
# -*- coding: utf-8 -*-

import math
import random

from collections import namedtuple

def is_prime(n):
	"""Primality test"""
	if n <= 1:
		return False
	if n <= 3:
		return True
	if n % 2 == 0 or n % 3 == 0:
		return False
	for k in range(5, int(math.sqrt(n)), 6):
		if n % k == 0 or n % (k + 2) == 0:
			return False
	return True

def prime(n):
	""" Finds a random prime greater than or equal n. """
	upper = n * 10 + 100
	k = random.randrange(n, upper)
	while not is_prime(k):
		k = random.randrange(n, upper)
	return k

class RollingHash(object):
	"""
	A rolling hash data structure.

	Attributes:
		self.hash (int): The last (cached) hash value.
		self.base (int): The base (size of the alphabet).
		self.p (int): A random prime >= len(substring).
		self.u (int): The length of the substring
	"""

	def __init__(self, p, s):
		self.hash = 0
		self.base = 256
		self.p = p
		self.u = len(s)
		for c in s:
			self.append(c)

	def append(self, c):
		"""
		Appends a new character to the rolling hash value.

		Appending involves multiplying the current hash
		value by the base value (the alphabet size), as per
		Rabin's fingerprint/Horner's method, then adding the
		new constant, the 'ord' (usually ascii) value of
		the character. (The string itself is therefore treated
		as a multidigit number, where each character is seen
		as a number). The resulting value is then modulo-divided
		by the randomly chosen prime to yield the resulting
		hash value. This prime-modulo-division is done to prevent
		integer overflow.

		Arguments:
			c (char): The character value to append.
		"""
		self.hash = self.hash * self.base + ord(c)
		self.hash %= self.p

	def pop(self, c):
		"""
		Pop/removes a character from the rolling hash value.

		Rabin's fingerprint is basically based on Horner's
		method. Thus if we have three digits a, b, c, the value
		of the hash, disregarding the modulo operation, is 
		a * base^2 + b * base^1 + c * base^0. As one can see,
		the last/highest power is n - 1, where n is the number
		of digits and ultimately the length of a substring.
		Therefore, if we wanted to remove/pop the last digit
		a, we would subtract from the sum a * base^(n-1). That's
		what we do here for the digits.

		Arguments:
			c (char): The character to pop.
		"""
		self.hash -= ord(c) * (self.base**(self.u - 1) % self.p)
		self.hash %= self.p

	def __call__(self):
		"""
		Returns the current hash value.

		You can also directly access the hash attribute.

		Returns:
			The current (cached) hash value.
		"""
		return self.hash

def find(s, t):
	"""
	Tries to find the substring s in the string t.

	Returns:
		The start and end of the first match found, else None.
	"""

	Result = namedtuple('Result', ['start', 'end'])

	# Get a random prime
	p = prime(len(s))

	# Initialize the first rolling hash
	# for the substring we're looking
	# *for* (stays constant thereafter)
	hs = RollingHash(p, s)

	# Initialize the second rolling hash
	# for the substring we're looking *at*
	# Note the prime must be the same!!!
	ht = RollingHash(p, t[:len(s)])

	# Initial check, note that we first check
	# the hash values (cheap), and only then
	# actual strings (hash collisions may happen)
	if hs() == ht() and s == t[:len(s)]:
		return Result(0, len(s))

	# Roll through the string ...
	for n in range(len(s), len(t)):

		# Pop the left-most character
		ht.pop(t[n - len(s)])

		# Add the new character
		ht.append(t[n])

		# The substring includes t[n], so go up to
		# n + 1; therefore also shift up the lower
		# boundary by one (to ignore the skipped char)
		if hs() == ht() and s == t[n-len(s)+1:n+1]:
			return Result(n-len(s)+1, n+1)
	return None

def main():
	print(find('pqrst', 'abcdefghijklmnopqrstuvwxyz'))

if __name__ == '__main__':
	main()
