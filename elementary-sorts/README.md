# Elementary Sorts

 - Re-arrange an array of *N* items according to a certain key

Question: How can sort() know hot to compare data of type Double, String and File without any information about the type of an item's key?

Answer: Callback -> reference to executable code

- Client passes array of objects to sort() function.
- The sort() function calls back object's compareTo() method as needed.

Callbacks:

- Java: interface
- C: function pointer
- C++: class-type functors (or lambdas)
- Python: first-class functions

Total order is a binary relation <= that satisfies
- Antisymmetry: if v <= w and w <= v, then v = w
- Transitivity: if v <= w and w <= x, v <= x
- Totality: either v <= w or w <= v or both

Comparable API (Java):

Inherit a class for a comparable object from *Comparable* class.

Implement compareTo() so that v.compareTo(w)

- Is a total order
- Returns a negative integer, zero or positive integer if v is less than, equal to or greater than w, respectively
- Throws an exception if incompatible types (or either is null)

## Selection Sort

- In iteration i, find index *min* of smallest remaining entry
- Swap a[i] and a[min]

Selection sort works by taking a sequence of *N* integers in any order and iterating over it sequentially. There is an iterator, index or pointer i that is initially equal to the first position in the array and iterates until its end. The first invariant states that, to the left of the iterator, all values are in non-decreasing order and will no longer be modified (looked at) -- they are fixed. For each iteration, the iterator occupies the spot of the next smallest value, which it seeks to find in the remaining sequence, i.e. at iteration i, the i-th smallest value is searched for. The second invariant of selection sort is that no entry to the right of the iterator is smaller than any entry to the left of the iterator. At each iteration, the goal is to find the next smallest value. Initially, this minimum value is said to be at the index of the first iterator. Then, a second iterator traverses the sequence starting at one position past the first iterator, looking for the smallest remaning value in the sequence. For this, it must traverse the entire remaining set of values. Once the remaining values have been traversed, the values of the first iterator and the value where the minimum was found are swapped. This procedure is performed until the first iterator has been at every position. In fact, the last iteration can be skipped, because there are no following values.

```python
def selection(seq):
	for i in range(len(seq)):
		minimum = i
		for j in range(i+1, len(seq)):
			if seq[j] < seq[minimum]:
				minimum = j
		seq[i], seq[minimum] = seq[minimum], seq[i] 
```

Invariants:

- Entries to the left of the iterator are fixed and in ascending order.
- No entry to the right of the iterator is smaller than any entry to the left of it.

Proposition:

- Selection sort uses (N-1) + (N-2) + ... + 1 + 0 ~ N^2/2 compares and N exchanges.

- The running time is insensitive to input: Quadratic time, even if input is sorted.

- Data movement is minimal: Linear number of insertions.

## Insertion sort

- In iteration i, swap a[i] with each larger entry to its left.

Insertion sort is the method one most often uses to sort a deck of cards. For every value in the sequence, one moves backward through the previous set of values and inserts the current value at the position of the first value that is greater, such that the current value is inserted at the position where the previous value is smaller and the next value is greater than or equal to the current value. In more detail, the algorithm utilizes an iterator i starting at the second position (i.e. index 1 for 0-indexed sequences). The value of iterator i shall be henceforth denoted by x. During each iteration, another iterator j traverses all values *before* the i, looking for the first value y that is smaller than the value x of the first iterator i. This means that iterator j iterates *while* y its value is *greater than or equal* to the value x of the first iterator i. As soon as a value for y is encountered that does not satisfy the above condition, i.e. that is smaller than x, iterator j stops. Now it is important to mention that while iterator j moves backwards, its values must be shifted forward, such that when j stops iterating all values after j are at one position further than they were at the start of the current iteration, this can be done by setting the value at index [j + 1] equal to the value at index [j] for every iteration of j. At last, once j hits a value smaller than x, the value at [j + 1] should hold the value of x. For this, x must be saved prior to the iteration of j as it will otherwise have been overriden by the value at [i - 1] when j + 1 = i.

```python
def insertion(seq):
	for i in range(1, len(seq)):
		value = seq[i]
		j = i - 1
		while j >= 0 and seq[j] >= value:
			seq[j+1] = seq[j]
			j -= 1
		seq[j + 1] = value
	return seq
```

Invariants:

- Entries to the left of the index are in ascending order.
- Entries to the right have not been looked yet.

Complexity:

To sort a randomly-ordered array with distinct keys, isnertion sort uses ~ 1/4 N^2 compares and ~1/4 N^2 exchanges on average.

Insertion sort does depend on the order of the sequence (unlike selection sort).

Best case: if the array is in non-decreasing order (correct), insertion sort makes N - 1 compares and 0 exchanges.

Worst case: If the array is in descending order with no duplicates, insertion sort makes ~ 1/2 N^2 compares and ~1/2 N^2 exchanges. This is worse than selection sort in the worst case because it must do more exchanges (selection sort always does one exchange per iteration and thus a linear number of exchanges in total).

Definition: An inversion is a pair of keys tha tare out of order. 

In a reversed sequence of values, each key is out of order with the next.

Definition: An array is *partially sorted* if the number of inversions is linear, i.e. <= *c N* for some constant c.

- Example: A subarray of size 10 is appended to a sorted subarray of size N (the whole array is partially sorted).

Proposition: For partially-sorted arrays, insertion sort runs in linear time.

Proof: Number of exhcanges equals the number of inversions.

## Shell sort

- "h-sorting" the array, h interleaved sorted subsequences.

How to h-sort an array? Insertion sort but moving back *h* indices.

- big increments -> small subarray
- small increments -> already in order

Shell-sort addresses the fact that for insertion sort, the iterator may need to travel back very many steps in the sequence. It does so by sorting in multiple steps, and with a different *h*-value each time. The value of *h* is the increment used for going back -- the *stride*. For insertion sort, the *h*-value is always 1, but for shell sort initially, this value is large. During each step, shell sort will act like insertion sort, but going back in steps of h and thus transporting values further distances each time, achieving the same thing insertion sort with less granularity but with greater efficiency. For example, to transport a value from the end of a sequence of *N* integers to its front, insertion sort would take *N* steps. However, shell sort would have a greater *h* value such as *N/3* and thus travel the same distance in only three steps. After each stage, the *h*-value is made smaller so that the shell-sorting operation is performed with more granularity, i.e. for smaller increments the values will be transported closer to their final position in the sorted sequence, while at the same time they won't have to travel as far as in insertion sort, because the greater grain of the prevoius *h-value* brought them closer to the real position. The increment value of *h* is an ongoing topic of research, but a popular increment sequence is one by Donald Knuth: 3x + 1. This means that for the first stage of sorting, one would look for the greatest value in the sequence by setting h equal to h * 3 + 1 while h is still less than N/3, where N is the length of the sequence. Then, after sorting with this h-value, h is brought back to the finer, smaller increment value by floor-/integer-dividing h by 3. Other increment sequences exist, with different degrees of efficiency. Note that shell sort is only an adaption of insertion sort to make it more efficient, the core idea is the same.

```python
def shell(seq):
	h = 1
	while h < len(seq)/3:
		h = h * 3 + 1
	while h >= 1:
		for i in range(h, len(seq), h):
			value = seq[i]
			j = i - h
			while j >= 0 and seq[j] >= value:
				seq[j + h] = seq[j]
				j -= h
			seq[j + h] = value
		h //= 3
	return seq
```

Proposition: The worst-case number of compares used by shellsort with the 3x+1 increments is O(N^(3/2)).

Property: The number of compares used by shellsort with the 3x+1 increments is at most a small multiple of N times the number of increments used. 

- Fast unless array size is huge.
- Tiny, fixed footprint for code (little amount of code to write).
- __Often used in embedded systems__.
- Hardware sort prototype.

For an array that is already sorted, the complexity is linearithmic. Since successive increment values of h differ by at least a factor of 3, there are ∼log_3(N) increment values. For each increment value h, the array is already h-sorted so it will make ∼N compares.

# Shuffling

How to random-shuffle a sequence?

- Generate a random real number for each array entry.
- Sort the array acording to those numbers.

Proposition: Shuffle sort produces a uniformly random permutation of the input array, provided no duplicate values.

Drawback: Extra cost of sorting.

Better: Knuth shuffle

- Iterate over the array and generate a random index between __0 and i__
- Swap the values at the current index and the random index

The random index must be between 0 and i, where i is the current iterator position. The reason why, as can be read [here](https://en.wikipedia.org/wiki/Fisher–Yates_shuffle#Implementation_errors) is that for N values, choosing a random index between 0 and N - 1 would yield N^N possibilites for different arrangements. However, there can only be N! different permutations of N. Because N! is less than N^N, using an index betwen 0 and N-1 would mean that some of the N^N possibilites are duplicates and thus the shuffling algorithm would not be uniform. The solution ist to pick the random index between 0 and i or i and N-1, so that the N! possibilities are not oversaturated.

## Convex Hull

A convex hull of a set of N points is the smallest polygon that encloses all points.

 . . 
 .  . .
. . . . .
 . . .
.   .  .

-->
 ___
|. .\___ 
/.  . . \__
|. . . . .|
| . . .   /
|.   .  .|
|________|

The output of a program to compute such a convex hull should be the sequence of vertices in counter-clockwise order.

Fact: We can traverse the convex hull by making only counter-clockwise turns.

Fact: The vertices of the convex hull appear in increasing order of polar angle with respect to the point *p*, which has the lowest *y*-coordinate.

Graham Scan algorithm:

- Pick point *p* with smallest *y*-coordinate.
- Sort points by polar angle with with *p*.
- Consider points in order, discard all points that do not create a counter-clockwise turn.

- How to find point *p* with smallest *y*-coordinate? Sorting.

- How to sort ponits by polar angle with respect to *p*. Define a total order.

- How to determine whether p_1 -> p_2 -> p_3 is a counter-clockwise turn?

- How to sort efficiently? Mergesort.


