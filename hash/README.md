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

## Caching

If defining a hash function for your own user-defined type, consider caching the value, i.e. having it set to a null/invalid value upon-construction or keeping a boolean to indicate whether or not the method has already been called. Upon the first call of the method, compute the hash-code, store it and flip the "already-computed" flag if using a boolean. Upon subsequent calls, just return that value directly.

Note that this is only valid for immutable types as else the object/key would change but not its hash value.

## Hash Functions

- Division method: hash(k) = k mod M
- Multiplication method: hash(k) = [(a * k) mod 2^w] >> (w - r)
  + where a is any (random) integer (should be odd and not close to power of 2)
  + w is the word size (the number of bits of the integer)
  + r is the number of bits you want to return
- Universal hashing: hash(k) = [(a * k + b) mod p] mod m
  + a, b random
  + p prime larger than number of bits

The hash function we implement or use falls into the Uniform Hashing Assumption, i.e. we can assume that for all practical purposes the function will or should scramble/hash keys uniformly among the indices between 0 and M - 1.

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

## Table Doubling

To grow a table of size m to m':

- Allocate table of size m'
- Build new hash h' (if m changes, h' must use a different value (matters if you don't use the division method for re-hashing))
- rehash -> for each old value, rehash and insert into new table

If we would make m' = m + 1, the complexity would be theta(1 + 2 + 3 + ... + n) = theta(N^2)

We really want m' = 2m.

If we do m' = 2m, then we'll have theta(1 + 2 + 4 + 8 + 16 ... + n). Every time we rebuild in linear time, but we're only doing it log n times (log n doublings to get from 1 to n), thus we speak of *amortized cost* (like with arrays). I.e., a hash table is amortized constant time for insertion/deletion/search.

Amortization: an operation takes *T(n) amortized*, if k operations take <= k * T(n) time. The occasional spike in running time is diluted.

Table doubling: k inserts take theta(k) time => theta(1) time amortized per insert.

Same is true for deletions, but don't shrink to half when n/2 elements left (table half empty), but rather shrink when n/4 elements left (when we're 3/4 empty). Amortized time is then O(1) again.

## String matching

Given two strings s and t, does s occur as a substring of t?

Obvious solution: go through t sequentially with moving substring of type len(s), check if at any time the current moving substring is equal to the substring you're looking at.

any(s==t[i:i+len(s)] for i in range(len(t)-len(s))

Running time: O(len(s) * (len(t) - len(s))) = O(|s| * |t|)

Hash s, then just hash every substring you're looking at and see if the hash values are equal.

## Rolling Hashes ADT

r is a string, we just wanna append the next char

r.append(c)

also want pop_left/skip, remove the first character (leftmost) (the one we don't need anymore).

r.__call__ (i.e. r()) will give you the hash value.

Called Karp-Rabin string matching algorithm.

# Compute the hash value of s (which we'll compare too)
rs = new Rolling Hash
rt = new Rolling Hash

for c in s:
	rs.append(c)
for c in t[:len(s)]:
		rt.append(c)
if rs() == rt():
	done
for i in range(len(s), len(t))
	rt.skip(t[i - len(s)])
	rt.append(t[i])
if rs() == rt():
	# potentially things match
	# so then do the check (linear/normal string comparison)
	if equal:
		found match
	else:
		# happens with probability <= 1/|s|

O(|s| + |t| + #matches * |s|)

Use division method h(k) = k mod m

m must be a random prime!!! at least as big as |s| (>= length of s)

treat string x as multidigit number, with base a = alphabet size (ascii 256)

to append: u = [u * a + ord(c)] % p

to skip: u = u - ord(c) * a^(|u| - 1)

r = r * a + ord(c) mod m

