#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import re

from collections import namedtuple


def memoize(*indices):
	def decorator(function):
		cache = {}
		def proxy(*args, **kwargs):
			key_args = [args[i] for i in indices] if indices else args
			key = tuple(key_args)
			if key not in cache:
				cache[key] = function(*args, **kwargs)
			return cache[key]
		return proxy
	return decorator


def parse(expression):
	pattern = re.compile(r'(\d+)\s*(\W)\s*(\d+)')
	values = []
	operators = []
	match = pattern.search(expression)
	if not match:
		raise RuntimeError('Invalid expression!')
	values.append(match.group(1))
	while match:
		operators.append(match.group(2))
		values.append(match.group(3))
		match = pattern.search(expression, match.start(3))

	return tuple(values), tuple(operators)


def stringify(values, operators, put_left=False):
	if not values:
		return ''
	expression = [str(values[0])]
	if len(values) == 1:
		if operators:
			if put_left:
				return ' {0} {1}'.format(operators[0], expression[0])
			return '{0} {1} '.format(expression[0], operators[0])
		return values[0]
	for first, operator, second in zip(values, operators, values[1:]):
		expression += [operator, str(second)]
	return ' '.join(expression)


def determine_expected(value, operator, result):
	if len(result) == 2:
		return result
	result = result[0]
	if operator == '|':
		if result == 1:
			if value == 1:
				return (0, 1)
			else:
				return (1,)
		else:
			if value == 1:
				return None
			else:
				return (0,)
	elif operator == '&':
		if result == 1:
			if value == 1:
				return (1,)
			else:
				return None
		else:
			return None
	elif operator == '^':
		if result == 1:
			if value == 1:
				return (0,)
			else:
				return (1,)
		else:
			if value == 1:
				return (1,)
			else:
				return (0,)

	raise RuntimeError('Invalid operator!')


def get_expected(before, after, result):
	if after[0]:
		after_value = eval(stringify(after[0], after[1][1:]))
		result = determine_expected(
			after_value,
			after[1][0],
			result
		)
	if result is not None and before[0]:
		before_value = eval(stringify(before[0], before[1][:-1]))
		result = determine_expected(
			before_value,
			before[1][-1],
			result
		)

	return result


def evaluate(values, operators, expected):
	expression = stringify(values, operators)
	if expression and eval(expression) in expected:
		return [expression]
	else:
		return []

def parenthesization_possibilities(expression, result):
	@memoize()
	def __find(values, operators, expected):
		possibilities = evaluate(values, operators, expected)
		if len(values) == 2 and len(operators) == 1:
			return possibilities
		for opening in range(len(values) - 1):
			for closing in range(1, len(values)):
				if ((opening == closing) or
					(opening == 0 and closing == len(values) - 1)):
					continue
				before = (values[:opening], operators[:opening])
				after = (values[closing + 1:], operators[closing:])
				v = values[opening:closing + 1]
				o = operators[opening:closing]
				e = get_expected(before, after, expected)

				if e is None:
					continue

				before = stringify(*before, False)
				after = stringify(*after, True)

				for possibility in __find(v, o, e):
					possibility = before + '({0})'.format(possibility) + after
					possibilities.append(possibility)

		return possibilities
	p = __find(*parse(expression), (result,))
	if not p:
		return 'Not possible.'
	return '{0} ways: {1}'.format(len(p), ', '.join(p))


def main():
	print(parenthesization_possibilities('0 | 0', 1))


if __name__ == '__main__':
	main()
