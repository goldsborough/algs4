# Maximum Subarray

__Problem__:

Given stock prices for a company for a range of days 1 .. N, determine the day to buy stocks and the day to sell stocks such that the difference is most profitable for you.

This problem belongs to the class of *maximum-subarray* problems. For such problems, the goal is to find the sub-sequence with the largest sum of values within the larger sequence. It can be approached using the divide-and-conquer methodology, which is characterized by:

1. A *divide* step, where the problem, i.e. here the array of values, is broken down (divided) into smaller sub-problems, here smaller subarray.
2. A *conquer* step, where the sub-problems are solved recursively. There is one case -- the *base case* -- where the recursion terminates (bottoms-out).
3. A *combine* step, where the solutions of the sub-problems are combined into a solution for the larger problem.

For the maximum-subarray problem, it is thus necessary to think of first dividing the larger problem into smaller problems, i.e. to attempt to find the maximum-subarray in subarrays of the larger array. This could be done simply by dividing the array in half and performing some operation on both halves, with the base case occuring if there is only one element (or less) in the sequence:

```Python
def max_sub(array, first, last):
	if (last - first) <= 1:
		...
	middle = (first + last)//2
	max_sub(array, first, middle)
	max_sub(array, middle, last)
	...
```

However, the problem is that the subarray need not be solely in one of the two halves, but could just as well overlap the mid-point. Therefore, there must be some way of computing a maximum subarray that overlaps the mid-point. This procedure is in fact crucial, because it is suited perfectly for conquering subproblems, as the maximum overlapping subarray can be computed and returned for both of the two halves, up until its size is only two elements. Therefore, the goal is to compute the maximum overlapping subarray for all possible subarrays. The maximum sum of values (from the maximum-subarray) will bubble up through all stacks by always returning the maximum-subarray for each level of recursion.

This find-the-maximum-overlapping-subarray method should be analyzed further. Its goal is to find the maximum sum of values starting at the mid-point and moving to the left, and the maximum sum of values starting at the mid-point and moving to the right. This maximum sum is found on both sides by keeping a running sum and checking each time if the current running sum is the highest seen so far. When a new highest running sum is found, a pointer is updated to point to this boundary of the maximum subarray. To the left of the middle, this pointer is then the lower boundary of the overlapping subarray and to the right of the middle this pointer is then the end of the overlapping subarray. the final sum of the overlapping subarray if the sum of the left and right region.

     0  1 2 3  4
(0) [5 -2 1 6 -10]
	      i j

left-sum: 1
left-max: 1
i-max: 2
right-sum: 6
right-max: 6
j-max: 3

	 0  1 2 3  4
(1) [5 -2 1 6 -10]
	    i      j

left-sum: 1 + -2 = -1
left-max: still 1 (-1 < 1)
i-max: 2
right-sum: 6 - 10
right-max: 6 (-4 < 6)
j-max: 3

j stops.

(2) [5 -2 1 6 -10]
	 i         j

left-sum: 1 + -2 + 5 = 4
left-max: 4 (4 > 1)
i-max: 0
right-sum: 6
right-max: 6
j-max: 3

i stops.

Finally, the sum of the maximum overlapping subarray of this array is the sum of left-max and right-max, i.e. 10. The range of the maximum subarray is denoted by the points where the highest sums were found for each side, meaning i-max and j-max respectively.

The maximum sub array is thus [i-max .. j-max] and has a sum of 10.

In Code:

```Python
def find_overlapping(array, first, last):
	size = last - first
	middle = first + size//2

	i_max = middle
	left_sum = left_max = array[i_max]

	for i in range(i_max - 1, first - 1, -1):
		left_sum += array[i]
		if left_sum > left_max:
			left_max = left_sum
			i_max = i

	j_max = middle + 1

	if j_max >= size:
		return i_max, j_max, left_max + 0

	right_sum = right_max = array[j_max]

	for j in range(j_max + 1, last):
		right_sum += array[j]
		if right_sum > right_max:
			right_max = right_sum
			j_max = j

	return i_max, j_max, left_max + right_max
```

This method is run on half-arrays at each level of recursion, thereby finding the maximum possible overlapping subarray and thus the maximum overall subarray.

Now for the stocks, one would first compute the relative change or slope of the stocks over time, simply by iterating over two at a time and computing the difference between the stocks at a given day and the day before. For these changes, one then computes the maximum-subarray and thereby gets the range where the increase (the change) is greatest. The lower boundary is thus the buying-day, and the upper-boundary is the selling day. This is most profitable, because the stocks increased the most in price in this subarray.

## Shorter, better solution

```C+++
template<typename Iterator>
auto maximum_subarray(Iterator begin, Iterator end) -> decltype(*begin + *begin)
{
	using T = decltype(*begin + *begin);

	T sum = 0;

	T best = 0;

	auto largest = end;

	for ( ; begin != end; ++begin)
	{
		sum += *begin;

		if (largest == end || *begin > *largest) largest = begin;

		if (sum > best) best = sum;

		else if (sum < 0) sum = 0;
	}

	// in std::max(largest, best) best (at least 0)
	// would win if largest is negative
	if (*largest < 0) return *largest;

	return best;
}
```

The thing is that it never makes sense to have the current sum become negative. When you add a negative value from the current sum and the sum becomes negative, you might as well reset, because any positive value will be greater than that. Given a sequence like so:

`[2, -8, 3, -2, 4, 10]`

when you add -8 to 2, you get -6. Think of it that 2 + (-8) *reduces* to -6, would you add the -6 *at the front* to your maximum subarray? No, so we can just ignore the sequence up to -8 and start our search at 3. However when do we do care about negative values? When they don't make the sum negative. For 3 - 2, the sum is not negative. Again, think of 3 - 2 reducing to 1. We do want to include the 1 at the start, because it's positive. So the important part is whether or not the sum becomes negative. If it does, we reset to 0. The algorithm above implements this (and adds error handling for when there are only negative values -- all the `largest` stuff).
