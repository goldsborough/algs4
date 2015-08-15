#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Queue:
	def __init__(self):
		self.queue = []

	def enqueue(self, item):
		self.queue.append(item)

	def dequeue(self, item):
		return self.queue.pop(0)