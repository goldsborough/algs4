# Priority Queues

Collections: Insert and delete item. Which do we delete?

Stack: Remove the item most-recently added (LIFO)
Queue: Remove the item least-recently added (FIFO)
Randomized Queue: Remove a random item.
Priority Queue: Remove the largest (or smallest) item.

Interface:

- Constructor: Create an empty priority queue (or with initializer list).
- insert(key): Insert a key into the priority queue.
- delMax(): Return and remove the largest key
- isEmpty(): Is the priority queue empty?
- size(): The number of elements in the queue.

Challenge: Find the largest M items in a stream of N items.

- Fraud detection: isolate very large transactions.
- File maintenace: find biggest file or directories.

Constraint: Not enough memory to store all N items.

Trivial possible implementations:

1. Ordered array with logarithmic lookup time (to find the place to insert a new random key) but linear complexity to move elements one further; linked list for constant-time insertion but linear lookup-time (can't do a binary search).

2. Unordered array with constant-time insertion but linear lookup-time for the maximum (go through the whole array and look for the maximum); same with linked list.

## Binary Heap

Binary Tree: Empty or node with links to left and right binary trees.

Complete Tree: Perfectly balanced (no empty nodes on a level), except for bottom level.

Property: Height of complete tree with *N* nodes is floor(lg N).
Proof: Height only increases when N is a power of 2.

Binary Heap: Array representation of a heap-ordered complete binary tree.

Heap-ordered binary tree (heap-property):

- Keys stored in each node.
- Parent's key is greater than or equal (not less) than any child's key.

Array representation
- Indices start at 1.
- Take nodes in level order.
- No explicit links needed.

Tree representation (no parent less than any child):

```
(0)       X  
        /   \  
(1)    C     O  
      / \   / \  
(2)  A   B M   K  
```

Array representation:

[_ X C O A B M K]
 ^ 0 1 1 2 2 2 2
 |
Index 0 is empty for easier calculations (start at index 1).

To change indices we do arithmetic.

Properties:

- Largest key is a[1], which is root of binary tree.
- Parent of node at k is k/2 (integer-divide/floor) except for root (no parent and k/2 would give 0 for index 1, which is false).
- Children of node at *k* are at *2k* and *2k + 1*

Scenario: Child's key becomes larger key than it's parent's key.

To eliminate the violation:
- Exchange (swap) key in child with key in parent
- Until the heap order is restored.

This is called the *swim* operation.

Insertion:

- Add a node at the end of the array
- Then swim it up.
- Cost: At most lg N + 1 compares

Scenario: Parent's key becomes smaller than one or both of its children's.

To eliminate the violation:
- Exchange key in parent with key in larger of the two children.
- Repeat until heap oder restored.

Called *sink* (as opposed to *swim*).

To delete the maximum in a priority queue:

- Exchange the root node (maximum) with a node at the end, so that the root node is no longer part of the heap.
- The new root now violates the heap property, so we sink it.

Complexity:

- Insert: log N
- delMax: log N
- max: 1

Make sure heap nodes are immutable (const/final).

# Heapsort

## Never forget that sorting can be done by counting

Plan:
1. Create max-heap with all *N* keys
	- Iterate through the array backwards, starting at the midpoint *N/2* (all nodes after that are single heaps)
	- Sink each node
2. Repeadetly remove the maximum key
	- Delete maximum operation: swap max (at root) with last, then sink new root.

Mathematical analysis:

- N sink operations, each performing a maximum of lg N steps -> N lg N
- Heap construction uses <= 2 N compares and exchanges.

Significance: In-place and N lg N worst case.

- Mergesort: Guaranteed N lg N, but not in-place.
- Quicksort: In-place, but quadratic time in worst case.
- Heapsort: Yes!

Practically:

- Inner loop longer than quicksort (index arithmetic, child comparisons (which greater)).
- Makes poor use of cache memory (references to memory are all over the place); quicksort always looks at nearby addresses in memory.
- Not stable.

## Event-Driven Simulation

- Simulate the motion of N moving particles that behave according to the laws of elastic collision.

Hard disc model:
- Moving particles interact via eleastic collisions with each other and walls.
- Each particle is a disc with known position, vlcoity, mass and radius.
- No other forces (like gravity).

### Gotchas

* Careful that when doing heapsort, your iterators don't start at 1. So to compute the left child, you'd have to use 2 * (std::distance(begin, iterator) + 1) - 1 and without the - 1 for the right child.
