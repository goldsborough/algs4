# Implementations

Basic:

For *N* integer elements have an array of size *N* where each element (each *ID*) is an integer less than *N*.

1. quick-find

	When calling union(p, q) for two elements p and q where both are less than *N*, you set the entry of p in the array to the entry of q, such that they have the same "ID" (you could also do vice-versa). More precisely, you iterate through the array and set each id that is equal to that of p (id[p]) to that of q (id[q]). To see if connected(p, q), you just check if their id's are the same in the array.

	- find operation is constant time: O(1)
	- union operation is linear in complexity: O(N)

2. quick-union

	Now the data structure takes the form of a *forest* (sequence of trees). Each element in the array has a *root*. Initially, each element is its own root. When you call union(p, q), q's root is set to p's root, so that their trees are merged. For example, at some point the array may look like so (indices on top, roots in the array):

	 0  1  2  3  4  5  6  7  8  9
	[0, 2, 3, 4, 4, 5, 5, 6, 8, 9]

	Here, the first tree is (indices): 4 (root) <- 3 <- 2 <- 1 <- 0

	And another tree is: 5 (root) <- 6 <- 7

	To connect 7 and 2 when calling union(7, 2), you fetch 7's root, which is 5 and 2's root, which is 4. You then set 5's root equal to 4, so that 7 and 2 are connected. The whole structure then looks like so:

      4    8  9
    /   \
	3   5
	|   |
	2   6
	|   |
	1   7
	|
	0

	Here, 8 and 9 are their own roots because they are not connected to any other elements. To see whether or not two elements are connected -- connected(p, q) -- you fetch their roots and compare them (either with while loop or recursively). 

	- find operation has (worst case) linear complexity: O(N)

	- union operation has (worst case) linear complexity: O(N)


# Notes

- O(N^2) (quadratic) algorithms get slower with newer computer architecture, as new computers usually have an equal increase in computation speed (number of operations per second they can process) as and memory they can hold. So when a new computer has 10x more memory and 10x more speed, an N^2 algorithm will run 10x slower (10x more memory -> 10^2=100 times more operations, but only 10x more speed, not 100x times) -- if the algorithm were to touch the entire space of memory.
