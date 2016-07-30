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

Expect two values in the same bin (same hash value) after $\sqrt{
	\frac{\pi M}{2}}$ tosses.

Expect every bin has at least one value after $M \ln M$ attempts.

## Caching

If defining a hash function for your own user-defined type, consider caching the value, i.e. having it set to a null/invalid value upon-construction or keeping a boolean to indicate whether or not the method has already been called. Upon the first call of the method, compute the hash-code, store it and flip the "already-computed" flag if using a boolean. Upon subsequent calls, just return that value directly.

Note that this is only valid for immutable types as else the object/key would change but not its hash value.

## Hash Functions

- Division method: hash(k) = k mod M
- Multiplication method: hash(k) = [(a * k) mod 2^w] >> (w - r)
  + where a is any (random) integer (should be odd and not close to power of 2)
  + w is the word size (the number of bits of the integer)
  + r is the number of bits you want to return (lg m)
- Universal hashing: hash(k) = [(a * k + b) mod p] mod m
  + a, b random
  + p prime larger than number of bits

The hash function we implement or use falls into the __Uniform Hashing Assumption__, i.e. we can assume that for all practical purposes the function will or should scramble/hash keys uniformly among the indices between 0 and M - 1.

## Separate chaining

Use an array of linked lists.
- Hash: map key to integer i between 0 and M - 1
- Insert: put at front of i-th chain (if not found).
- Search: linear search through i-th linked list (chain).

Proposition: Under a uniform hashing assumption, the probability that the number of keys in a list is within a constant factor of N / M is extremely close to 1. N is the number keys you want to insert, M is the size of the array. This says that the probability is very high that if N = M, there are N/M = 1 keys in each list (chain/bin).

Proof: The distribution of list sizes obeys a binomial distribution, because
- Every key is equally likely to be hashed to any slot of the table (uniformity)
- Independent of any other/previous/future keys (independence); i.e. a key is just as likely to map to a slot if another key is already there, as if there wasn't already a key there.

i.e. The probability that $k$ keys hash to the same slot is: $\pmatrix{n\\k} \cdot (\frac{1}{m})^k \cdot (\frac{m - 1}{m})^{n - k}$

Considerations: given that the number of probes per search/insert is proportional to N/M:

- If M is too large => too many empty chains (wasted space).
- If M is too small => chains too long (linear search too expensive).
- Typical choice: M ~ N/5 (5 elements per chain); (you would resize when N becomes > M * 5) => constant-time operations.

N/M is denoted by $\alpha$ and is called the *load factor*.

The complexity is ultimately $O(1 + \alpha)$

## Open-addressing

An alternative to separate-chaining hash-tables are open-addressed hash-tables, where the table size $m$ is greater than the number of keys $n$, such that the load factor $\alpha$ is at most 1, $\alpha$ being equal to $\frac{n}{m}$ (it may be greater than 1 for separate-chaining). The generalized idea of an open-addressed hash-table is to continuously probe slots in the table according to some pattern until a free slot is found for insertion, or until the search key is found (if not, then terminate the search when reaching a free slot). For this, the hash function $h$ now no longer only takes the key $k$ to hash but also the probe index $i$ which modifies the returned value according to the progression in the probe sequence. For example, if $h(k, i)$ is called with $h(k, 0)$, the initial probe value will be returned (the same value as $h(k)$ returned previously). If that slot is taken, when inserting, or if it is not the wanted value, for search, i is incremented such that $h(k, i)$ then returns the next spot. This is the *generalized* idea.

Deletion in open-addressed hash-tables is rather complicated. There are two deletion methods. For the first, keys cannot be simply deleted by setting their spots to `null`, because when looking for other keys that also hash to this spot during their probe sequence, but that were inserted at a later spot because the to-be-deleted/cleared spot was taken, the search would terminate at this `null` spot. Thus, the search would be corrupted because the probe sequence should have continued to the real location of that *other key*, but didn't. In this case, keys to be deleted have to be marked as *tombstones* (dead/alive), such that searches skip them. The second method works only for linear probing, where the hash function is such that $h(k, i + 1) = h(k, i) + 1$ (i.e. where the following index is returned). Here, you can simply __re-hash all keys to the right in the current cluster (up to the next empty spot)__, i.e. all keys to the right up to the next NIL spot.

In an open-addressed hash-table, the expected number of probes at any point in time is:

$\frac{1}{1 - \alpha} = \frac{1}{1 - \frac{n}{m}} = \frac{1}{\frac{m - n}{m}} = \frac{m}{m - n} , \text{with } n \neq m \Leftrightarrow \alpha < 1$

## Linear probing

Hash: Map key to integer i between 0 and M - 1.

Insert: Put at table index i if free; if not try i + 1, i + 2, etc. (search for next empty space).

Search: Look at table index i, if can't find look at i + 1, i + 2 etc.

For key-value pairs, use parallel arrays (or nodes).

Usually $M \approx 2N$.

Mean displacement of new keys:

- If $M/2$ space occupied, mean displacement is $\approx 3/2$.
- For full array, mean displacement is $\approx \sqrt{\frac{\pi M}{8}}$

M should be significantly larger than N, typically $a = N/M ~ 1/2 (M = 2N)$.

One major problem of this method is clustering, here called *primary
clustering*, i.e. the formation of long chains of keys. The problem with
clusters is that the longer they are, the *more likely they are to get longer*,
since, given the uniform hashing assumption, the probability to hash into a
cluster of size $x$ in a table of size $m$ is $\frac{x + 1}{m}$. $x$ for the
values already in the cluster and plus $1$ for the spot to the right of the
cluster. Especially harmful are insertions into the only spot between two
clusters (i.e. joining them).

## Quadratic probing

Another method that causes clustering, but less of it, thus termed *secondary clustering*, is quadratic probing. Here the hash function follows the schema:

$h(k, i) = h'(k) + c_1i + c_2i^2 \mod m$

where $h'(k)$ is an auxiliary pre-hashing function, $c_1$ and $c_2$ are randomly
chosen constants and $i$ is the probe sequence index. Note that the key,
i.e. the initial probe value, determines the entire sequence, thus two keys
$k_1$ and $k_2$ where $h'(k_1) = h'(k_2)$ will hash to the same locations. This
method simply limits primary clustering (and replaces it with *secondary
clustering*).

## Double hashing

Double hashing greatly reduces clustering and is one of the best methods for
hashing in open-addressed schemes. It uses two auxiliary pre-hashing functions
$h_1(k)$ and $h_2(k)$, such that the hashing function $h(k, i)$ is of the form:

$h(k, i) = [h_1(k) + i \cdot h_2(k)] \mod m$

The initial probe goes to the position of $h_1(k)$ and all subsequent probes go
to integer multiples of $h_2(k)$ positions further. This method is effective
because for two keys $h_1$ and $h_2$ is is very unlikely that if already
$h_1(k_1) = h_1(k_2)$ also $h_2(k_1) = h_2(k_2)$.

__It is important that $h_2(k)$ be relatively prime to the table size $m$ for
the entire table to be searched.__ There are two ways to ensure this:

1. Make the table size $m$ prime and ensure that $h_2$ always returns a *positive*
   integer less than $m$.
2. __Let $m$ be a power of two__ and __make $h_2$ return an odd number__.

The second method is more sensical for doubling tables. $h_2$ could work in the
following way, where $x$ is the returned value:

```C++
return x % 2 ? x : x + 1;
```

```Python
return x if x % 2 else x + 1
```

Taking advantage of the fact that `x % 2` returns 1 for odd numbers which evaluates to `TRUE`.


## Separate-Chaining vs. Linear probing

+ Separate-Chaining:
	- Easier to implement delete.
	- Performance degrades gracefully.
	- Clustering less sensitive to poorly-designed hash function.

+ Linear probing:
	- Less wasted space.
	- Better cache performance.

## Variations

Two-probe hashing:
- Hash to two positions, insert key in shorter of the two chains.
- Reduces expected length of the longest chain to $\log \log N$ (from $\log N$).

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
- rehash -> for each old value, compute new hash and insert into new table

If we would make $m' = m + 1$, the complexity would be $\Theta(1 + 2 + 3 + ... + n) = \Theta(N^2)$

We really want $m' = 2m$.

If we do $m' = 2m$, then we'll have $\Theta(1 + 2 + 4 + 8 + 16 ... + n)$. Every time we rebuild in linear time, but we're only doing it log n times (log n doublings to get from 1 to n), thus we speak of *amortized cost* (like with arrays). I.e., a hash table is amortized constant time for insertion/deletion/search.

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

```
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
```

$O(|s| + |t| + \#matches * |s|)$

Use division method h(k) = k mod m

m must be a random prime!!! at least as big as |s| (>= length of s)

treat string x as multidigit number, with base a = alphabet size (ascii 256)

to append: $u = [u \cdot a + ord(c)] % p$

to skip: $u = u - ord(c) * a^{|u| - 1}$

r = r * a + ord(c) mod m

## k-wise independence

k-wise independent hash functions have the property that for every set of *k* keys *h(x)* returns a value that is independent from previous calls to that function for any of the *k* keys. For example, a 2-wise or pairwise-independent hash function is defined such that:

$Pr{h(x) = a_1 ∧ h(y) = a_1} = 1/m^2 = (1/m) x (1/m)$

i.e. that the call to $h(y)$ is independent from the call to $h(x)$. A hash
function to achieve this:

$$h(x) = \left[\left(\sum_{i=0}^{k-1} a_i x^i\right) \mod p\right] \mod m$$

i.e. a polynomial of order *k-1* with arbitrary constants *0 < a_i < p*.

## Other

- Avalanching hash functions: functions where small changes in the input cause
  great changes in the output: i.e. h("hel") and h("hell") produce entirely
  different hash values given only the very small change in the input.

- When storing passwords, it is a good idea to not associate the actual password
  with the user, but the output of a hash function for that password. That way,
  if a hacker runs off with your passwords/user data, he or she gets not the
  real passwords, but only the useless hash-values. When logging in, simply run
  the input through the same hash function and see if it hashes to the value
  associated with the user in the database. Just make sure the attacker cannot
  see/get your hash function.

## Additional

* Note you don't necessarily need to double the size if a chain gets too long,
  you could also choose a different hash-function.

(a,b)

keys   [0 | 1 | 2 | 3] h(key)
         |
	    [a]     [d]
		 |       |
	    [c]     [b]
		        |
values [0 | 1 | 2 | 3] h(value)
