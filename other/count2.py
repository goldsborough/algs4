#!/usr/bin/env python3
# -*- coding: utf-8 -*-

def count2(number):
	order = 10**(len(str(number)))
	count = 0
	while order > 1:
		order /= 10
		digit = number // order
		count += digit * (order // 10)
		if digit > 2:
			count += order
		elif digit == 2:
			count += number % order
		number %= order

	return int(count)

def main():
	print(count2(25))

if __name__ == '__main__':
	main()
