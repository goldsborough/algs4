#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Document search.

Design an algorithm that takes a sequence of N document words and a sequence of M query words and find the shortest interval in which the M query words appear in the document in the order given. The length of an interval is the number of words in that interval.
"""

from collections import namedtuple

Interval = namedtuple('Range', 'start, end')

def compute_interval(pattern, indices, last_index):
    first_index = last_index
    for letter in reversed(pattern[:-1]):
        champion = -1
        for index in indices[letter]:
            if index < first_index and index > champion:
                champion = index
        first_index = champion

    return Interval(first_index, last_index)

def distance(interval):
    return interval.end - interval.start + 1

def find_closest_interval(string, pattern):
    indices = {}
    closest_interval = None
    closest_distance = None
    for index, word in enumerate(string):
        if word in pattern:
            if word in indices:
                indices[word].append(index)
            elif word == pattern[-1]:
                if all(w in indices for w in pattern[:-1]):
                    interval = compute_interval(pattern, indices, index)
                    interval_distance = distance(interval)
                    if closest_interval is None:
                        closest_interval = interval
                        closest_distance = interval_distance
                    elif interval_distance < closest_distance:
                        closest_interval = interval
                        closest_distance = interval_distance
            else:
                indices[word] = [index]

    return closest_interval, closest_distance

def main():
    string = 'fxyazuvacfbgcijbkeabcxcbbcz'
    pattern = 'abc'
    print(find_closest_interval(string, pattern))

if __name__ == '__main__':
    main()
