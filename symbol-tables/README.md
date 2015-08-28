# Symbol Tables

Key-value pair abstraction
- Insert a value with specified key
- Given a key, search for  the corresponding value.

Associative array abstraction: associate one value with each key.

Interface:

- Constructor()
- put(key, value): put key-value pair into the table
- get(key): retrieve value paired with key (null if not found)
- delete(key): remove key and its value from the table
- contains(key): is there a value paired with key?
- isEmpty(): whether or not the table is empty
- size(): the number of elements in the table
- keys(): all the keys in the table (iterable)

Regarding *null*:

- Exception for null values.
- get() returns null if key is not present (contains() -> get(key) != null)
- put() overwrites old value with new value (delete() -> put(key, null)).

Value type: any generic type.

Key type: extend Comparable for compareTo, otherwise built-in equals().

Equality:
- Reflexive: x.equals(x) is true.
- Symmetric: x.equals(y) if y.equals(x)
- Transitive: x.equals(y) and y.equals(z), then x.equals(z)
- Non-null: x.equals(null) is false.

You should test equality of fields that usually differ first.

## Elemenatary Implementations:

1. Sequential search in a linked list:
	- Linked list of key-value pairs (nodes).
	- Keep unordered.
	- Search: Scan through all keys until find a match O(N).
	- Insert: Scan trhough all keys until find a match; if no match add to front O(N).
2. Binary search in an ordered array.
	- Parallel arrays, one for keys and one for values
	- Keys in sorted order, do binary search for key and retrieve value at parallel index.
	- If unsucessful search, insert new value/pair at last index found.
	- Problem: data shift.
	- Search: O(lg N)
	- Insert: O(N)

## Ordered Operations

Interface:

- Constructor(): Create an ordered symbol table
- put(key, value): put key-value pair into the table
- get(key): retrieve value paired with key (null if not found)
- delete(key): remove key and its value from the table
- contains(key): is there a value paired with key?
- isEmpty(): whether or not the table is empty
- size(): the number of elements in the table
- min(): Return the smallest key.
- max(): Return the largest key.
- floor(key): Return largest key less than or equal to the key.
- ceiling(key): Return smallest key greater than or equal to the key.
- rank(key): Number of keys less than key.
- select(k): Key of rank k.
- deleteMin(): delete the smallest key.
- deleteMax(): delete the largest key.
- size(min, max): return the number of keys between min and max ranks.
- keys(min, max): return all the keys in the table between min and max ranks.
- keys(): all the keys in the table (iterable)

So many operations usually not good, but here they're all efficient.

## Binary Search Tree

A binary tree is either:
- empty
- two disjoint binary trees (left and right)

Symmetrics order: Each node has a key, and every node's key is:
- Larger than all keys in left subtree.
- Smaller than all keys in right subtree.

For put/insert, use recursive method that returns a new node if the current key is null, else the key again after performing another step of recursion (usually the node/key returned will be the same one).

Cost is always lg(depth of node) + 1 = h (height).

Tree is like a quicksort-partition: all elements on the left than the pivot are less, all to the right are greater.

- Best case: the tree is perfectly balanced, such that a search is always logarithmic. E.g. for insertions 4, 2, 6, 1, 3, 5, 7 you get:
     
     4
   /   \
  2     6
 / \   / \
1   3 5   7

- Worst case: Always add greater or always smaller value, then like linked list -- search is linear O(N). E.g. for insertions 1, 2, 3, 4:

1
 \
  2
   \
    3
     \
      4

Proposition: If *N* keys are inserted into a BST in *random* order, the expected number of compares for a search/insert is ~ 1.39 lg N (for random order).
Proof: Exactly like quicksort partitioning.

## Ordered Operations

- Minimum: Go all the way left (or keep link).
- Maximum: Go all the way right (or keep link).

- Floor (largest key, less than some key *k*):
	+ if key in tree: right-most child of left sub-tree
	+ else: first parent where the current node is the right child (such that the parent is the largest element less than the node.)

Keep an extra field in each node to store the size of the subtree.

- size() is then the size of the root
- rank(key), similar to floor()
	+ base case the node is null, return 0
	+ if the key is greater than the current node, return the size of the left subtree (all less than node), + 1 for the current node and add the result of a further recursive call to the right
	+ if the key is less than the current node, do a recursive call to the left
	+ if the key is found (equal to the node), return the size of left subtree
- select()

Iteration: Inorder traversal:
- Recursive, pass around queue by reference.
- Go left as far as possible, until the node is null (then return).
- Then enqueue the current node.
- Then go right, such that in the next stack you go as far left as possible again.
- Note that this is recursive and would return a full container with the keys.
- For stepwise iteration:
	+ start at minimum
	+ if has right, go right and all the way to the left
	+ else, if current node is left child of parent, set to and return parent
	+ else, if current node is right child of parent, set to and return first greater parent (first parent to the right)

Lazy approach: set value to null, leave as tombstome to guide searches (bad memory usage).

DeleteMin:
- Go left while possible (until find a node with left null)
- Replace that node by its right link.
- Update subtree counts (still 1 + size of left + size of right).

Deletion: O(sqrt(N))

## Balanced Trees

### 2-3 tree

Allow 1 or 2 keys per node
- 2-node: one key, two children
- 3-node: two keys, three children

Perfect balance: Every path from root to null link has same length.
Symmetric order: Inorder traversal yields keys in ascending order.

Insert into a 2-node at bottom:
- Search for key, as usual.
- Replace 2-node with 3-node.

Insert into a 3-node at bottom:
- Add new key to 3-node to create temporary 4-node.
- Move middle key in 4-node into parent.

To move Z into this tree (P is a 2-node, S/Z is a 3-node):
  
   R
  / \
 P  S/X
/ \ /|\

Z is temporarily moved into the S/X three node (creates a 4-node):

   R
  / \
 P  S/X/Z
/ \ /| |\

Then the middle of the three keys is moved up to the parent node to create a three-node between R/X, and the smallest of S/X/Z is made the middle node of the newly created three-node at R/X. Z remains as a two-node to the right of the R/X three node:

   R/X
  / | \
 P  S  Z
/ \/ \/ \

Note that this is possible because when the middle of the original 4-node is moved up, the lower of the 4-node fits between the upper key (R) and the middle key (X), because S, X and Z were greater than R, while S is less than X. Z remains larger than all.

To insert into a 3-node below a 3-node:
- Add new key to lower 3-node to create temporary 4-node.
- Move middle key in 4-node into parent.
- Repeat up the tree, as necessary.
- If you reach the root and it ends up being a 4-node, split the 4-node into three 2-nodes, so that a 4-node like this:

A/B/C
/| |\

would turn into three 2-nodes:
   
   B
  / \
 A   C
/ \ / \

Note that the height increases by one -- the only time it does so for a 2-3-tree.

Invariants: Maintains symmetric order and perfect balance.

Every path from root to null link has the same length.

Tree height:
- Worst case: lg N (all 2-nodes)
- Best case: log_3 N = 0.631 lg N (all 3-nodes)
- Between 12 and 20 for a million nodes.
- Between 18 and 30 for a billion nodes.

Guaranteed logarithmic performance for search and insert.

Practically speaking the implementation is complicated.

## Red-Black trees

- Represent 2-3 tree as a BST.
- Use internal left-leaning links as "glue" for 3-nodes.

- No node has two red links connected to it.
- Every path from root to null link has the same number of black links.
  + The reason why is that the red links are just "proxy-links" to simplify
  	the 2-3 tree structure. In a 2-3 tree, all paths from the root to null have the same length (the tree is balanced). A red-black tree is not directly balanced like that because the 3-nodes are split up into two 2-nodes, so the red-black tree could be higher. However, if you don't count the red paths (because they would be part of the same node in a 2-3 tree, and we're modelling/simplifying that), the distance from root to null is always the same, i.e. every node on one level has the same number of *black links*. Therefere there is *black balance*: every path from root to null has the same number of black links (don't count the red ones!).
- Red links lean left.

Sometimes we need to rotate a right-leaning link to lean left:

    E
   / \ (RED)
(< E) \
       S
      / \
 (E < S) (< S)

 So we set:

 - E.right to S.left
 - S.left to E
 - S' color to E's color (BLACK)
 - E's color to RED
      
       S
      / \
     E   (< S)
(R) /  \
 (< E) (E < S)


Invariant: 
- Perfect black balance.
- Maintains symmetric order.

Color Flip: re-color to split a (temporary) 4-node.

Proposition: Height of tree is <= 2 lg N in the worst case.

Proof: 
- Every path from root to null link has the same number of *black links*.
- Never two red links in-a-row (we'd rotate then).
- Best case: only black: lg(N)
- Worst case: one red for every black: 2 * lg(N)


















