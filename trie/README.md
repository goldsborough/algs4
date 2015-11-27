# Tries

String symbol table API, for a class `public class StringST<Value>`:

* `StringST()`: create an empty symbol table
* `void put(String key, Value value)`: put key-value pair into the symbol table
* `Value get(String key)`: return value paired with given key
* `void delete(String key)`: delete key and corresponding value

Goal: Faster than hashing, more flexible than BSTs.

Character accesses:

| Data Structure | Search hit     | Search miss    | Insert    | Space |
| :------------- | :------------- | :------------- | :-------- | :---- |
| Red-Black BST  | L + c lg^2(N)  |    c lg^2(N)   | c lg^2(N) | 4N    |
| Hash-Table     | L              | L              | L         | L     |

BST: $lg(N) + 1 = h$ to find the correct node (with few comparisons in between), but then additional $L$ to compare to the correct string.
Trie: Only $L$!

## R-way tries

trie -> re-*trie*-val

* Each node has $R$ children, one for each possible character (including null).
* Store the value in node corresponding to last character of a key.

For example for the strings "sea, sells, she, shells, sea":

```
      s
     / \
    /    \
  e        h
 /  \      | \
a(6) l  (0)e  o
     |     |  |
	 l     l  r
	 |     |  |
	 o(1)  l  e(7)
	       |
		   s(3)
```

There is a value associated with e, because it is the last character of the key "she".

Note that the existence of a key is determined entirely by wether or not it is linked to by another node, i.e. __there is no explicit `String key` in the Node__. The key is in the trie if there is a non-null node at that index for another node.

Search hit: `get("shells")`

Start at s, look at all (non-null) nodes associated with s, is there an 'h'? If so, go down that tree ...

Search miss: `get("shell")`

Go down the tree, till we hit the end of the string, i.e. s->h->e->l->l. Is ther a value associated with this node? If so, "shell" is a key in the tree, else it is not.

Insertion:

1. Go down the tree, if we hit a null node, create a new node there. If at last character, insert value.
2. If we find the key already present, replace value.

Store in each node an array of links for either 26 characters, or 36 with alphanumerics, or just simply 128/256 for extended/ASCII. Simply use the character itself as the index in the array to see if it is null or not.

Deletion:

Look for last node according to above rules. Then:

* If that last node has all null-links, it is no longer needed. Remove that node and recurse.
* If that last node has non-null links, it is still needed for other strings, so don't delete.

Bottom line: Fast search hit and even faster search miss, but wastes space!

R-way trie -> R = number of links in each node

| Data Structure | Search hit     | Search miss    | Insert    | Space |
| :------------- | :------------- | :------------- | :-------- | :---- |
| Red-Black BST  | L + c lg^2(N)  |    c lg^2(N)   | c lg^2(N) | 4N    |
| Hash-Table     | L              | L              | L         | L     |
| Trie           | L              | $\log_R(N)$    | L         |  $RN$ |
                                      ^
								height of R-way trie

Problem: way too huge memory use. Imagine spell-checking for Unicode (65536-way trie!!!!)

## Gotchas

* Don't forget that you do not store the key in the node.
* Don't forget that a search miss is not only if a node is null, but also if we've reached the end of the search string __and there is no value associated with the node__.
* Don't forget to make the character value of your string match your alphabet length (e.g. if using 26 letters a-z, decrement char value by 97)


## Comparison

Advantages of tries:

The basics:

* Predictable O(k) lookup time where k is the size of the key
* Lookup can take less than k time if it's not there
* Supports ordered traversal
* No need for a hash function
* Deletion is straightforward

New operations:

* You can quickly look up prefixes of keys, enumerate all entries with a given prefix, etc.

Advantages of hashtables:

* Everyone knows hashtables, right? Your system will already have a nice well-optimized implementation, faster than tries for most purposes.
* Your keys need not have any special structure.
* More space-efficient than the obvious linked trie structure
