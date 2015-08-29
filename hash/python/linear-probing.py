#!/usr/bin/env python
# -*- coding: utf-8 -*-

class HashTable(object):

	MINIMUM_CAPACITY = 4

	def __init__(self, capacity=MINIMUM_CAPACITY):
		self.buckets = [None for _ in range(capacity * 2)]
		self.size = 0
		self.alive = 0

	def insert(self, key, value):
		bucket = self.hash(key)
		while self.buckets[bucket]:
			if self.buckets[bucket]['key'] == key:
				if not self.buckets[bucket]['alive']:
					self.buckets[bucket]['alive'] = True
					self.alive += 1
				self.buckets[bucket]['value'] = value
				return
			bucket = (bucket + 1) % len(self.buckets)
		self.buckets[bucket] = {'key': key, 'value': value, 'alive': True}
		self.size += 1
		self.alive += 1
		if self.size == len(self.buckets):
			self.resize()

	def get(self, key):
		bucket = self.hash(key)
		while self.buckets[bucket]:
			if (self.buckets[bucket]['alive'] and 
			    self.buckets[bucket]['key'] == key):
				return self.buckets[bucket]['value']
			bucket = (bucket + 1) % len(self.buckets)
		raise KeyError("No such key '{0}'!".format(key))

	def erase(self, key):
		bucket = self.hash(key)
		while self.buckets[bucket]:
			if (self.buckets[bucket]['alive'] and 
			    self.buckets[bucket]['key'] == key):
				self.alive -= 1
				self.buckets[bucket]['alive'] = False
				if self.alive == len(self.buckets)//4:
					self.resize()
				return
			bucket = (bucket + 1) % len(self.buckets)
		raise KeyError("No such key '{0}'!".format(key))

	def contains(self, key):
		bucket = self.hash(key)
		while self.buckets[bucket]:
			if (self.buckets[bucket]['alive'] and 
			    self.buckets[bucket]['key'] == key):
				return True
			bucket = (bucket + 1) % len(self.buckets)
		return False

	def __getitem__(self, key):
		return self.get(key)

	def __setitem__(self, key, value):
		self.insert(key, value)

	def __len__(self):
		return self.alive

	def is_empty(self):
		return self.alive == 0

	def hash(self, key):
		return hash(key) % len(self.buckets)

	def resize(self):
		capacity = self.alive * 2
		if capacity >= self.MINIMUM_CAPACITY:
			old = self.buckets
			self.buckets = [None for _ in range(capacity)]
			i = 0
			for bucket in old:
				if bucket and bucket['alive']:
					self.buckets[i] = bucket
					i += 1
			self.rehash()

	def rehash(self):
		old = self.buckets[:]
		self.buckets = [None for _ in self.buckets]
		for bucket in old:
			if not bucket or not bucket['alive']:
				continue
			new_hash = self.hash(bucket['key'])
			while self.buckets[new_hash]:
				new_hash = (new_hash + 1) % len(self.buckets)
			self.buckets[new_hash] = bucket

def main():
	table = HashTable()

	table["one"] = 1
	table["two"] = 2
	table.insert("three", 3)
	table["four"] = 4
	table["four"] = 4
	table["five"] = 5

	table["six"] = 6
	table["seven"] = 7
	table["eight"] = 8

	
	print(len(table))

	print(table.is_empty())
	print(table["two"])

	table["one"] = 123

	print(table["one"])
	print(table["two"])

	table.erase("three")
	print(table["two"])
	table.erase("four")
	table.erase("five")
	
	print(len(table))
	print(table["two"])
	
	table.erase("one")
	table.erase("two")
	table.erase("six")

	print(table.is_empty())
	
	print(table["seven"])

	table.erase("seven")

	table.erase("eight")

	print(len(table))
	print(table.is_empty())


if __name__ == '__main__':
	main()
