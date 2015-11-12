# Stacks and Queues

Fundamental data types

- Value: collection of objects.
- Operations: insert, remove, iterate, test if empty.

- Stack: Examine the most recently added: LIFO (last-in-first-out)
- Queues: Examine the first added: LIFO (first-in-first-out)

We want to de-couple the implementation from the interface (encapsulation).

## Stacks

Interface:

- Constructor(): creates an empty stack
- push(item): insert a new item *onto* the stack
- pop(): remove and return the element at the top of the stack
- isEmpty(): return whether or not the stack is empty
- size(): return the size of the stack

Implementations:

- Linked list
- Fixed size array

Fixed size array is problematic because the client must supply the size. Rather we would like to dynamically resize the stack.

We do so by re-allocating new space. If we re-allocate for each new item, the cost of inserting *N* items is proportional to *N^2*.

- Doubling strategy: double the capacity each time new space must be allocated (when size == capacity).

Applications:

- Back button in a Web browser.
- Recursion in a compiler.
- Undo in a word processor.

Often can model recursion with an explicit stack.

### Shrinking:

- Thrashing: if we halve the capacity when the stack is half-full, a push-pop-push-pop ... sequence may cause many re-allocations.

- Efficient solution: halve the size when the array is one-quarter full.

Invariant: The array is then between 25% and 100% full (lower-bound is exclusive, i.e. > 25% because then we shrink it)

### Amortized Time

Amortized time is essentially a measure of average time per operation for a sequence of operations. Inserting N elements into an array takes total time proportional to N, but the time per operation, the amortized const, is constant time. For example in a dynamic array, you double the size at certain intervals. But those periodic high costs of re-allocation can be "spread out" over all insertion operations and are then "diluted", such that the amortized time is still O(1). Consider that, when you double the size of the array, it takes twice as long to double again as before. At the same time, the doubling will take twice as long because there are twice as many elements. Re-allocation takes twice as long, but we can wait twice as long, so the cost is essentially diluted. If it costs X after X insertions, the amortized time is constant because $X/X = 1$. After, it costs 2X after 2X insertions, so still $2x/2x = 1$

### Array vs Linked List

Linked list:

- Every operation takes constant time in the worst case.
- Uses extra time and space to deal with the links.
- Usually slower than Array.
- Better guaranteed time (always constant).

Dynamic Array:

- Every operation takes constant *amortized* time (averaged -- the re-allocation cost is diluted)
- Less wasted space.

If I want to be sure that every operation will be constant time and cannot have occasional troughs in performance, the linked list will be better.

## Queues

Interface:

- Constructor(): create an empty queue
- enqueue(item): insert a new item onto the queue
- dequeue(): remove and return the oldest item
- isEmpty(): whether or not the queue is empty
- size(): returns the size of the queue

Implementations:

- Linked list: have nodes with pointers to the next element, enqueue by adding elements to the back and dequeue from the front.
- Array: Either enqueue by pushing back and dequeue by popping from the front and then moving all subsequent elements back by one index, or use a circular buffer with a first and last index, both wrapping around the array (increment modulo the capacity).

## Bag

Data structure where the order of retrieval is not important. We just add elements however we want and retrieve them however we want.

Interface:

- add(item): adds an item
- size(): returns the size
- iterator(): returns an iterator
