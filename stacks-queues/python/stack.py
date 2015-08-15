#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Stack:
	def __init__(self):
		self.stack = []

	def push(self, item):
		self.stack.append(item)

	def pop(self, item):
		return self.stack.pop()

	def isEmpty(self):
		return self.size() == 0

	def size(self):
		return len(self.stack)