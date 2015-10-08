#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from collections import namedtuple

Box = namedtuple('Box', 'width, depth, height')

def volume(box):
	return box.width * box.depth * box.height


def stack_boxes(boxes):
	boxes.sort(key=volume)
	return boxes


def main():
	print(stack_boxes([Box(5, 5, 5), Box(3, 2, 10), Box(10, 10, 2)]))


if __name__ == '__main__':

	main()
