# Hash Tables

Save items in a key-indexed table (index is a function of the key).

Hash function: A Method for computing an array index given a key.

Issues:
- Compute the hash function.
- Equality: Method for checking whether two keys are *equal*.
- Collision resolution: Algorithm and data structure to handle two keys that hash to the same array index.

Classic space-time tradeoff:
- If no space limitation: trivial hash function with key as index.
- If no time limitation: trivial collision resolution with sequential search.
- Neither unlimited space nor unlimited time.

Idealistic goal:
- Scramble the keys uniformly to produce a table index.
- Efficiently computable.
- Each table index equally likely for each key (uniform probability).

All Java classes inherit a method hashCode(), which returns a 32-bit integer.

Such a method which returns only an integer is referred to as a *pre-hash* function. The hashing function itself then has to reduce the universe of all possible keys (e.g. 2^32 possible values for 32-bit integers) to the set of indices of your table:

- Pre-hash: return 100,000
- Hash: return 100,000 % M (the size of your hash table)

Default: Memory address of x (also *id* in Python).

For user-defined types, try to use all you data's bits. Generally create your value from base 32 numbers and use Horner's rule.

Standard recipe:

1. Combine each significant field using the 32x + y rule (Horner's method).
   - 32 or whatever the number of bits of your data type is (31 for signed integers!!!!)
2. If field is a primitive type, use the wrapper type's hashCode() method (in Java).
3. If field is a reference type, use hashCode().
4. If field is an array, apply to each entry.

For your hash-table, you would have an integer between 0 and M-1 where M is the size of your array and typically a prime or power of 2. For your array, you would then use modular arithmetic (i.e. hash code % M).

If you have signed integers, you have to use the absolute value of the integer.

But even then, you should make it positive by masking it with 0x7FFFFFFF. This mask leaves all bits except for the sign bit the way they were before, but always unsets the sign bit so that the value is positive.

Expect two values in the same bin (same hash value) after ~ sqrt(pi * M / 2) tosses.

Expect every bin has at least one value after ~ M ln M attempts.

## Hash Functions

- Division method: hash(k) = k mod M
- Multiplication method: hash(k) = [(a * k) mod 2^w] >> (w - r)
  + where a is any (random) integer (should be odd and not close to power of 2)
  + w is the word size (the number of bits of the integer)
  + r is the number of bits you want to return
- Universal hashing: hash(k) = [(a * k + b) mod p] mod m
  + a, b random
  + p prime larger than number of bits

## Separate chaining

Use an array of linked lists.
- Hash: map key to integer i between 0 and M - 1
- Insert: put at front of i-th chain (if not found).
- Search: linear search through i-th linked list (chain).

Proposition: Under a uniform hashing assumption, the probability that the number of keys in a list is within a constant factor of N / M is extremely close to 1. N is the number keys you want to insert, M is the size of the array. This says that the probability is very high that if N = M, there are N/M = 1 keys in each list (chain/bin).

Proof: The distribution of list sizes obeys a binomial distribution, because
- Every key is equally likely to be hashed to any slot of the table (uniformity)
- Independent of any other/previous/future keys (independence); i.e. a key is just as likely to map to a slot if another key is already there, as if there wasn't already a key there.

Considerations: given that the number of probes per search/insert is proportional to N/M:

- If M is too large => too many empty chains (wasted space).
- If M is too small => chains too long (linear search too expensive).
- Typical choice: M ~ N/5 (5 elements per chain); (you would resize when N becomes > M * 5) => constant-time operations.

N/M is denoted by alpha and is called the *load factor*.

The complexity is ultimately O(1 + alpha)

## Linear probing

Hash: Map key to integer i between 0 and M - 1.

Insert: Put at table index i if free; if not try i + 1, i + 2, etc. (search for next empty space).

Search: Look at table index i, if can't find look at i + 1, i + 2 etc.

For key-value pairs, use parallel arrays.

Usually M ~ 2 * N.

Mean displacement of new keys: 

- If M/2 space occupied, mean displacement is ~ 3/2.
- For full array, mean displacement is ~ sqrt(pi * M/8)

M should be significantly larger than N, typically a = N/M ~ 1/2 (M = 2N).

## Variations

Two-probe hashing:
- Hash to two positions, insert key in shorter of the two chains.
- Reduces expected length of the longest chain to lg lg N (from lg N).

Double hashing:
- Use linear probing, but skip a variable amount, not just 1 each time.
- Effectively eliminates clustering.
- Can allow table to become nearly full.
- More difficult to implement delete.

## Hash tables vs. Balanced Search Tree

Hash Tables:

- Simpler to code.
- No effective alternative for unordered keys.
- Faster for simple keys (a few arithmetic operations vs. lg N compares)

Balanced BSTs:

- Stronger performance guarantee.
- Support for ordered operations.
