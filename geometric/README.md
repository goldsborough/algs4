# Geometric applications of BSTs

Geometric objects (e.g. points in a plane) are now of interest, not arbitrary keys.

Applications: CAD, games, movies, virtual reality, databases ...

## 1-D Range-Search

+ Insert key-value pair
+ Search for key `k`.
+ Delete key `k`.
+ __Range search__: find all keys between `k_1`  and `k_2`.
+ __Range count__: number of keys between `k_1` and `k_2`.

(Used for database queries).

Geometric interpretation:
+ Keys are points on a line.
+ Find/count points in a given 1-D interval.

Implementation:

+ Unordered array: Fast insert, slow range-search.
+ Ordered array: Slow insert (have to move elements), but fast binary search for `k_1` and `k_2`. Then `O(R)` complexity to collect all elements between `k_1` and `k_2` where `R` is the number of keys in that range.
+ 

| data structure  | insert  | range-count | range-search|
|-----------------|---------|-------------|-------------|
| unordered array |  O(1)   |    O(N)     |     O(N)    |
| ordered array   |  O(N)   |   O(lg N)   | O(R + lg N) |
| goal (BST)      | O(lg N) |   O(lg N)   | O(R + lg N) |

For range-count: `rank(k_2) - rank_(k_1) + 1`

## Line-segment intersection

Problem: Given `N` horizontal and vertical line segmetns, find all intersections.

Brute force: Quadratic algorithm, for every line segment, check if it intersects with any other line segment.

Solution: Sweep-line algorithm.

+ Sweep a vertical line from left to right and define each `x`-coordinate as an *event*. Upon reaching the left endpoint of a line-segment, insert its `y`-coordinate into a BST.

+ Upon reaching the right endpoint of a line, remove that `y`-coordinate from the BST.

+ When we hit a vertical line segment, do a range-search or range-count for all y-coordinates that it touches, and see if any of those are present.

## Kd-trees

Now using 2-D keys. Keys are points in the *plane*. When doing a range-search/count, the result is a `h x v` rectangle.

Grid implementation:
+ Divide space into `M x M` grid of squares.
+ Create a list of points contained in each square.
+ Use a 2-D array to directly index a relevant square.
+ Insert: `add(x, y)` to list for corresponding square.
+ Range search: examine only squares that intersect 2-D range query.

There is a space-time tradeoff:
+ Space required: `M^2 + N` (`M^2` for the `M x M` grid, `N` for the `N` points)
+ Time required is proportional to: `1 + N/M^2`

i.e. the more grids, the greater the granularity and the more precise the number of points we have to examine (if we have large grids, we have to store less grids, but have to examine more points which may lie outside the area of interest because they still intersect).

Rule of thumb: `sqrt(N) x sqrt(N)`, so that you have `N/M^2 = N/(sqrt(N)^2) = N/N = 1` points per square, on average.

If points are evenly distributed, the running time is:

+ `O(N)` to initialize the data structure.
+ `O(1)` to insert point.
+ `(R)` for range-search.

Problem: clustering. Makes lists of some squares very long, while others are short.

We need a data structure that more gracefully adapts to the data.

Like: `k`-D trees (space-partitioning trees).

Idea: Have a binary search tree for the points, but strictly alternate using the `x` and `y` coordinate as the key. i.e. the first node would partition according to its `x`-coordinate, such that all nodes in its right branch would have greater and all nodes to its left have smaller `x`-coordinates. Then on level 2 of the tree, partition according to `y` coordinates etc.
  
          5
     1
3       2

   4

           1:x
left of 1 /  \ right of 1
         3:y  2:y
below 3 /      \  above 2
       4:x      5:x

+ Search gives rectangle containing the point.
+ Insert further subdivides the plane.

The important thing is whether or not a level is odd or even.
+ Even levels (0, 2, 4 ...) mean using the `x`-coordinate to compare. 
+ Odd levels (1, 3, 5 ...) mean using the `y`-coordinate to compare.

Complexity: 
+ Typical case: `O(R + lg N)`, where `R` is the number of keys returned.
+ Worst case (assuming tree is balanced): `R + sqrt(N)`

Nearest neighbour search: find closest point to a query point.

Again follow k-D tree structure to get closer to the query point and repeatedly cut away parts of the full tree.

Typical case: `O(log N)` complexity
Worst case: `O(N)` complexity

Application from nature: Boids. Three simple rules lead to complex emergent flocking behaviour (e.g. of birds):
+ Collision avoidance: point away (a certain distance) from `k` *nearest* boids.
+ Flock centering: point towards the center of mass of `k` *nearest* boids.
+ Velocity matching: update the velocity to the average of `k` *nearest* boids.

To make a 2-D tree work as a `k`-D tree, recursively partition `k`-dimensional space into 2 halfspaces. Have a BST and cycle through `k` dimensions for each level rather than only 2. Generally, the dimension used for a level would thus be:

`d = i mod k` where `d` is then the d-th dimension of all `k` possible, to be used on level `i` as the partitioning factor.

## Interval search.

### 1-D interval search.

Have a data structure to hold a set of (overlapping) intervals, with the following supported operations:
+ Insert an interval (`i`, `j`)
+ Search for an interval between `i` and `j`.
+ Delete an interval beteen `i` and `j`.
+ Interval intersection query: Given an interval `i, j`, find all intervals that intersect that interval.

API of a class `IntervalST` (`IntervalST<Key extends Comparable<Key>, Value>`):

- `IntervalST()`: create a new interval-search-tree.
- `void put(Key lower, Key upper, Value value)`: insert interval-value pair.
- `Value get(Key lower, Key upper)`: return value paired with given interval.
- `void delete(Key lower, Key upper)`: delete given interval.
- `Iterable<Value> intersects(Key lower, Key upper)`: all interval-values that intersect the given interval.

Non-degeneracy assumption: No two interval have the same left endpoints.

Each node in the binary search tree stores the interval and also the *maximum endpoint in its tree*. The latter is used to determine whether or not to go down a tree when searching.

To search for any __single__ intersecting interval in the tree (only one):
+ If the interval in the current node intersects the query interval, return that intersecting interval.
+ Else if the left subtree is null, go right.
+ Else if the left subtree's maximum right endpoint is less than the left query endpoint, go right (no interval will intersect the query in the left subtree).
+ Else go left.

All operations are logarithmic.

## Rectangle intersection problem.

Goal: Find all intersections among a set of `N` orthogonal rectangles.

Quadratic algorithm: Check all pairs of rectangles for intersection.

Sweep vertical line from left to right.
+ `x`-coordinates of left and right endpoints define events.
+ Maintain set of rectangles that intersect the sweep line in an interval search tree, using `y`-intervals of rectangle (just like we inserted points for lines, we now insert intervals for rectangles).
+ Left endpoint: interval search for `y`-inerval of rectangle, return intersection or insert interval.
+ Right endpoint: remove `y`-interval of rectangle.

Sweep line algorithm takes time proportional to `N log N + R log N = (N + R) log N` to find `R` intersections among a set of `N` rectangles.

