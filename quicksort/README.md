# Quicksort

- Random-shuffle the array
- Partition the array so that for some value i (invariants):
	+ entry a[i] is in place
	+ no larger entry is to the left of i
	+ no smaller entry is to the right of i
- Sort each piece recursively

Phase I: Repeat until i and j pointers cross.

- Pick a pivot point
- Scan i from left to right while a[i] < a[pivot] \
												   - and i < j
- Scan j from right to left while a[j] > a[pivot] /  
- Swap a[i] and a[j]

Quicksort sorts in-place, as opposed to mergesort which requires an additional auxiliary array to perform the merge (space cost N).

Terminating the loop: Testing whether the pointers cross is tricky, *especially with duplicate elements*.

Preserving randomness: Shuffling is needed for performance guarantee (if the array is sorted, performance is less -- perfect sorted order is extremely rare with random shuffle).

Duplicate keys: When duplicates are present, it is (counter-intuitively) better to stop on keys equal to the partioning item's key.

Worst case: O(N^2)
- More likely that your computer is struck by lightning bolt (random-shuffle)
- Negligible case.

Average case: Number of compares is ~ 1.39 N lg N.
- 39% more compares than mergesort
- But faster than mergesort in practice because of less data movement.

Random Shuffle
- Probabilistic guarantee against worst case.
- Basis for a mathematical model that can be validated with experiments.

Quicksort is not stable!

Practical improvements:

- Insertion sort for small subarrays of some size less than a cutoff size between 10 (<-) and 20 (improvement about 20%).
- You could also just stop the recursive function calls for small subarrays and then do one insertion sort after exiting the first stack (before returning from the main function), then you only have one pass. Recommended.

- Best choice of pivot item = median (which may be anywhere in the sequence).
- Estimate true median by taking median of sample of three elements. 
- Possible improvement: 10% when taking median of three.

## Selection

Given an array of *N* items, find the k-th largest.

Quick-select: Partition the array to find its median, then go left if you need a k lower than the position of the median or right if you need a greater k. The position median of the median always indicates the how-many-eth largest value it is.

Complexity:
 - Probably N lg N upper bound
 - Probably N upper bound for k = 1, 2, 3 (fixed number of passes)
 - Probably N lower bound ^

## Duplicate Keys

Often, purpose of sort is to bring items with equal keys together.

- Sort population by age.
- Sort job applicants by college attended.

Mergesort: Always uses between 1/2 N lg N and N lg N compares for files with duplicate keys.

Quicksort: Takes quadratic time unless partitioning stops on equal keys!

Mistake: Put all items equal to the partitioning item on one side.
Consequence: ~ 1/2 N^2 compares when all keys are equal.

Recommended: Stop scans on items equal to the partiioning item.
Consequence: ~ N lg N compares when all keys are equal.

Desirable: Put all items equal to the partitioning item in place.

### Three-way partitioning

- Partition array into three parts so that:
	+ Entries between i and j are equal to the partition v.
	+ No larger entries to the left of i.
	+ No smaller entries to the right of j.

Thereby can have quicksort move elements that are equal to the key into the middle, smaller values to the left and greater values to the right, like normal partitioning. Then, you only have to sort values between the beginning and the first equal value, and between the last equal value and the end.

Sorting lower bound: If there are *n* distinct keys and the i-th one occurs x_i times, an compare-based asorting algorithm must use at least

*lg(N!/(x_1! x_2! ... x_n!)) ~ -sum_{i=1}^{n} x_i lg(x_i/N)*

compares in the worst case (N lg N when all are distinct; linear when only a constant number of distinct keys).

Bottom line: Randomized quicksort with 3-way partitioning reduces running time from *linearithmic to linear* in broad class of applications.

Questions to consider for picking or implementing a sorting algorithm:

- Stability?
- Parallel?
- Deterministic (always produces the same results when run multiple times, i.e. no randomness like in randomized quicksort)?
- Keys all distinct?
- Multiple key types?
- Linked list or arrays?
- Large or small items?
- Is your array randomly ordered?
- Need guaranteed performance?


Elementary sorts may be the method of choice for some combination of the above attributes.

__No algorithm can cover all combinations of attributes__.

The system sort is usually good enough, though.

Algorithm | In-place? | Stable? | Worst | Average | Best  | Remarks
----------|-----------|---------|-------|---------|-------|-------------------------------------
Selection |    Yes    |    No   | N^2/2 |  N^2/2  | N^2/2 | N exchanges
Insertion |    Yes    |   Yes   | N^2/2 | N^2/4   |   N   | Good for small N or partially ordered
Shell     |    Yes    |   No    |  ?    |    ?    |   N   | Tight code, subquadratic
Merge     |    No     |   Yes   | N lgN |  N lgN  | N lgN | N lg N guarantee, stable
Quick     |   Yes     |   No    | N^2/2 |  2N lgN | N lgN | Fastest in practice, usually N lgN
Quick3    |   Yes     |   No    | N^2/2 |  2N lgN |   N   | Improves Quicksort for duplicates
???       |   Yes     |  Yes    | N lgN |  N lgN  | N lgN | Holy sorting grail



