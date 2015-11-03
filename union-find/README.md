# Dynamic Connectivity

## Implementations

Basic:

For *N* integer elements have an array of size *N* where each element (each *ID*) is an integer less than *N*.

1. QuickFind

  When calling union(p, q) for two elements p and q where both are less than *N*, you set the entry of p in the array to the entry of q, such that they have the same "ID" (you could also do vice-versa). More precisely, you iterate through the array and set each id that is equal to that of p (id[p]) to that of q (id[q]). To see if connected(p, q), you just check if their id's are the same in the array.

  - find operation is constant time: O(1)
  - union operation is linear in complexity: O(N)

2. QuickUnion

  Now the data structure takes the form of a *forest* (sequence of trees). Each element in the array has a *root*. Initially, each element is its own root. When you call union(p, q), q's root is set to p's root, so that their trees are merged. For example, at some point the array may look like so (indices on top, roots in the array):

   0  1  2  3  4  5  6  7  8  9
  [0, 2, 3, 4, 4, 5, 5, 6, 8, 9]

  Here, the first tree is (indices): 4 (root) <- 3 <- 2 <- 1 <- 0

  And another tree is: 5 (root) <- 6 <- 7

  To connect 7 and 2 when calling union(7, 2), you fetch 7's root, which is 5 and 2's root, which is 4. You then set 5's root equal to 4, so that 7 and 2 are connected. The whole structure then looks like so:

    0   4    8  9  
       / \  
      3   5  
      |   |  
      2   6  
      |   |  
      1   7  

  Here, 0, 8 and 9 are their own roots because they are not connected to any other elements. To see whether or not two elements are connected -- connected(p, q) -- you fetch their roots and compare them (either with while loop or recursively).

  - find operation has linear complexity: O(N)

  - union operation has linear complexity: O(N)

3. Weighted QuickUnion

  The main shortcoming of the above implementation is that trees can get very tall and skinny, increasing the time it takes to find the root of an element (worst-case is linear O(n), when each node has exactly one child and one parent). To address this issue, one can determine which tree to add to which root by analyzing the size -- or *weight* -- of the tree, and then attaching the smaller tree to the root of the larger (weightier) tree. Thereby, one would prevent the trees from getting too deep and would rather increase the depth of the forest. Consequently, find operations are less expensive. For the weight, an additional array is kept (or an additional data structure is created holding a weight and root ID) holding the weight of each node. Now in a union(p, q) operation, where p is 4 and q is 5, on the following list:

   0  1  2  3  4  5  6  7  8  9
  [0, 2, 4, 4, 4, 5, 5, 7, 8, 9]

  with this forest structure (initially):

  0   4     5   7 8 9  
     / \    |  
    2   3   6  
    |  
    1  

    it is first determined which tree has more weight. The tree with root 4 (p) has a weight of 4, while the tree with root 5 only has a weight of 2. Thus, where previously one root would have been assigned to the other randomly, here the root of 5 is set to point to 4, i.e. the lighter/smaller tree is attachted to the heavier/bigger one. This yields the following new forest:

    0   4 ___   7 8 9  
       / \   \  
      2   3   5  
      |       |  
      1       6  

    Here, the depth of the tree stays the same and thus the performance of a root-find operation. Had the tree with root 4 been attached to the tree with root 5, the depth would have increased and thus the efficiency of finding the root would have decreased.

    Further analysis shows here that the depth of the tree can be at most lg(N) -- where lg is the base-2 logarithm. The reason why is that when a tree T1 is merged with another tree T2, where the size s1 of tree T1 is less than the size s2 of tree T2, the size of T1 must double (if s1 = s2) or increase by a greater factor (if s2 > s1). For N elements, a tree with initial size 1 can only double lg(N) times.

    An example, starting out with N = 4:

    (0) 0 1 2 3

    The greatest depth can initally be achieved by halving the structure:

    (0) 0  2  
        |  |  
    (1) 1  3  

    And then again adding one tree to the other:

    (0)   0  
         / \  
    (1) 1   2  
            |  
    (2)     3  

    The depth here is 2 and lg(4) is 2. Proof complete.

    - find operation has logarithmic complexity: O(lg N)
    - union operation has logarithmic complexity: O(lg N)

4. Weighted QuickUnion with Path Compression

  A last alteration to the aforementioned implementation (3) is to further decrease the depth of the trees via *path compression*. The idea is to, when moving up a tree to find its root, flatten the tree by setting the root of each node to its grandparent. Best results would be to flatten it entirely, i.e. to set the root of each node to the root of the tree, but this is more complicated and given enough find operations the first implementation will suffice and produce flat trees in most cases.

  Take this (partial) structure:

      4 ___  
     / \   \  
    2   3    5__  
    |    |      \  
    1    6       7  
                / \  
               8   9  

    When looking for the root of 9, one would touch nodes 9, 7, 5 and lastly 4. While going upwards, this implementation would set each node's root to its grandparent, i.e. 9's new root will be 5 and 7's then 4:

      4 ___ ____   
     / \   \    \   
    2   3   5__  7   
    |    |     \  \   
    1    6      9  8   

    Here, the depth was decreased by 1 (the tree was *flattened*) and thus find
    operations will be less expensive.

# Notes

- O(N^2) (quadratic) algorithms get slower with newer computer architecture, as new computers usually have an equal increase in computation speed (number of operations per second they can process) as and memory they can hold. So when a new computer has 10x more memory and 10x more speed, an N^2 algorithm will run 10x slower (10x more memory -> 10^2=100 times more operations, but only 10x more speed, not 100x times) -- if the algorithm were to touch the entire space of memory.

- Scientific Method

  1. Model the problem
  2. Find an algorithm to solve it
  3. Fast enough? Fits in memory? (Time & Space Complexity)
  4. If not, figure out why
  5. Find a way to address the problem (improve algorithm)
  6. Iterate until satisfied.
