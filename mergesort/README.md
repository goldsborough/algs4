# Mergesort

Basic Plan:

- Divide array into two halves.
- Recursively sort each half.
- Merget the two halves.

Goal: Given two sorted subarrays a[lo] to a[mid] and a[mid+1] to a[hi], replace them with a sorted array to a[lo] to a[hi].

Need three indices:

- i (left half)
- j (right half)
- k (current pointer in the destination array)

Assertions: Statements to test axsumptions about your program.

- Helps detect logic bugs.
- Documents code.
- Use assertions to check internal invariants.

Proposition: Mergesort uses at most *N lg N* compares and 6 *N lg N* array accesses to sort any aray of size N.

Proof: The number of compares *C(N)* and array accesses *A(N)* to mergesort an array of size *N* satisfy the recurrences:

*C(N) <= C(ceil(N/2)) + C(floor(N/2)) + N* for *N > 1*, with *C(1) = 0*
			^				^			^
		 left half		 right half	  merge
*A(N) <= A(ceil(N/2)) + A(floor(N/2)) + 6N* for *N > 1*, with *C(1) = 0*

## Memory

Proposition: Mergesort uses extra space proportional to *N*.
Proof: The auxiliary array needs to be of size *N* for the merging operation.

## Practical Improvements

- Use insertion sort for small subarrays.

	+ Mergesort has too much overhead for tiny subarrays.
	+ Cutoff to insertion sort ~ 7 items.

- Stop sorting if already sorted.

	+ Occurs when the biggest item in the first half is less than the smallest item in the second half.
	+ Improves partially-ordered arrays.

	In detail, if you have the subarrays [A B C] and [D E F] you would, after having sorted those, normally proceed to merge them by iterating over the two subarrays and always picking the smaller value in the two subarrays at the current index. But if the greatest value in the left half is less than *or equal* the smallest value in the right half, then this would just mean that the `merge` operation would first move all elements from the first subarray into the destination array and then all elements from the second subarray, because every value in the first subarray is smaller than the first element in the second subarray.

	- Eliminate the copy to the auxiliary array. Save time (but not space) by switching the role of the input and auxiliary array in each recursive call.

## Bottom-Up mergesort

Bottom-up mergesort is a variant of the mergesort algorithm that does not require recursion, but rather only operates with a for loop. This time, instead of starting with the full array, at the *top*, we start at the *bottom*, with subarrays of size one. Those subarrays are then merged into the auxiliary array. The subarray size is then doubled and the process repeats in the next iteration of the for loop.

## Sorting complexity

- Computational complexity: A framework to study the efficiency of algorithms that solve a particular problem *X*.

- Model of computation: The allowed operations.

- Cost model: The number of operations (comparisons for sorting algorithms).

- Upper bound: The cost guarantee provided by some algorithm to solve the problem *X*.

- Lower bound: Proven limit on cost guarantee of all algorithms for *X* (no algorithm can do better).

- Optimal algorithm: Algorithm with best possible cost guarantee for *X*.

Proposition: Any compare-based sorted algorithm  must use at least lg (N!) ~ N lg N compares in the worst case.

## Stability

Stability is the discussion of how or whether sorting algorithms maintain the order of elements in the original sequence, when re-ordering. For example, when first sorting the sequence of strings ["cd", "abe", "ape" "e"] lexicographically, the result would be ["abe", "ape", "cd", "e"]. Stability comes into play when now asking, "if we now sort the resulting sequence by length, will keys that were equal according to the previous order, stay in their arrangment if they are also equal according to the new order?" Some sorting algorithms will re-order ["abe", "ape", "cd", "e"] to ["e", "cd", "abe", "ape"] when asked to sort by length. Here, "abe" and "ape" remained in their previous order. Another sorting algorithm may not be stable, and result in the newly ordered sequence ["e", "cd", "ape", "abe"].

- A stable sort preserves the relative order of items with equal keys.

What sorts are stable?

Insertion sort and mergesort are stable, but not selection sort and shellsort.

One question to ask is: does the algorithm check "less than" or "less than or equal". "less than" will result in stable sort for insertion sort.

Proof by counter-example: Long-distance exchange might move an item past some equal item. Selection sort is not stable. Just like shell sort: long-distance exchange.

Mergesort: Stability of mergesort depends on the merge operation, which depends on the comparison operation. To be precise, mergesort is stable if we pick the value from *the left subarray* when comparing values from the left and right subarrays that are equal (because the left value was also before the right value before sorting the subarrays).


