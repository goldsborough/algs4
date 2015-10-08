
# Dynamic programming

+ "Careful brute force"
+ subproblems + reuse

Lecture: https://www.youtube.com/watch?v=OQ5jsbhAv_M

## Fibonacci numbers

Recurrence relation:

F_1 = F_2 = 1
F_n = F_n-1 + F_n-2

Naive recursive algorithm:

```Python
def fib(n):
	if n <= 2:
		return n
	return fib(n - 1) + fib(n - 2)
```

Complexity: exponential

T(N) = T(N - 1) + T(N - 2) + O(1)

F_n is approximately equal to the golden ratio phi to the n-th power: φ^n

T(N) >= 2T(N - 2) = 2^(N/2) -> O(2^(N/2))

## Memoization

Put values we compute into a dictionary. If we see that value again in the problem, we already solved it, so take its value from the dictionary.

```Python
memo = {}
def fib(n):
	if n in memo:
		return memo[n]
	if n <= 2:
		f = 1
	else:
		f = fib(n - 1) + fib(n - 2)
	memo[n] = f
	return f
```

or

```Python
cache = {0:1, 1:1, 2:2}
def fib(n):
	if n not in cache:
		cache[n] = fib(n - 1) + fib(n - 2)
	return cache[n]
```

Can also use an array (even more efficient, especially if no hash-tables available -> C++):

```Python
def fib(n):
    	cache = [None for _ in range(n + 1)]
    	def _fib(n):
    		if cache[n] is None:
    			if n <= 2:
    				cache[n] = 1
    			else:
    				cache[n] = _fib(n - 1) + _fib(n - 2)
    		return cache[n]
    	return _fib(n)
```

```C++
std::size_t fib(std::size_t n, std::vector<std::size_t>& cache)
{
	if (! cache[n])
	{
		if (n <= 2) cache[n] = 1;

		else cache[n] = fib(n - 1, cache) + fib(n - 2, cache);
	}

	return cache[n];
}

std::size_t fib(std::size_t n)
{
	std::vector<std::size_t> cache(n + 1, 0);

	return fib(n, cache);
}
```

fib(k) only recurses the first time it's kalled for all k.

Memoized call is constant time: `O(1)`.

The number of non-memoized calls is N.

Complexity, finally, is then `O(N)`.

In general, in DP, memoize (remember) solutions & reuse solutions. The challenge is to find out what the subproblems are.

DP ~ recursion + memoization.

The time you spend on a DP problem is: #subproblems * time/subproblem
Recursive calls are then (amortized) Θ(1).

```Python
def memoize(function):
	cache = {}
	def proxy(key, *args, **kwargs):
		if key not in cache:
			cache[key] = function(key, *args, **kwargs)
		return cache[key]
	return proxy

@memoize
def ...
```

Remember for the above that applying a decorator

```Python
@decorate
def function(...):
	...
```

is equivalent to assigning the decorated function to itself, i.e. to replace the function by the return value of the decorator such that also recursive calls return that returned value (function):

```Python
function = decorate(function)

# now calls to function inside function will reference the decorated function
```

## Bottom-up DP algorithm

```Python
memo = {}
for k in range(1, n):
	if k <= 2:
		memo[k] = 1
	else:
		memo[k] = memo[k - 1] + memo[k - 2]

return memo[n]
```

+ Exactly same computation
+ Topological sort of subproblem dependency (DAG)

Bottom-up DP can save space.

## Shortest-path

d(s, v) for all v

Guessing: don't know the answer? guess.

Algorithmic approach: try all guesses (and take the best one).

+ Guess last vertex before `v`, call it `u`
+ Compute distance d(s, u) between `s` and `u`
+ Do that for all `u` (all vertices before `v`)
+ Take the minimum distance
+ Do that recursively, i.e. for all vertices `t` before `u`

s -> O -> O -> O -> v

Memoized version: check if d(s, v) is in the memo-table, take it if yes, else compute it.

Careful, could be infinite on graphs with cycles.

<(a)> means the edges are going out from a and >b< towards b.

   <(a)>
  / |  \
s   ^   v
  \ | /
   >b<							d(s, v) (infinite! -> d(s, a) ...)
							 /
d(s, v) -> d(s, a) -> d(s, b)
							\  d(s, s)

On acyclic graphs, the time is O(V + E).

Number of subproblems: V
Time for subproblem: indgree(V) -> number of incoming edges to v

Total time is thus: sum_{v \in V} indegree(v) = O(E + V)

__Lesson learned: subproblem dependencies must be acyclic!__

5 "easy" steps to DP:

1. Define subproblems.
2. Guess something. After the first guess, I want a subproblem *of the original type*.
3. Relate subproblem solutions (combine)
4. Build the algorithm: recurse & memoize or DP table (bottom-up approach)
5. Solve original problem (combine)

## Text justification

Given text, split them into "good" lines.

text = list of words

badness(i, j) -> how bad it is to use words[i:j] as line

if the words don't fit -> infinity badness (math.inf)
if the words do fit ->  (page width - total width)^3

1. Subproblems: suffixes -> words[i:]
2. Guess where to start the next line.
   + # choices <= n - 1 = O(N)
3. Recurrence: DP(i) = min(DP(j) + badness(i, j) for j in range(i + 1, n + 1))
   + with DP(N) = 0
   + I.e. DP(i) = the badness to have a line between i and j + the mininum badness of any way to break subsequent lines.
   + j of minimum badness sum gives you the index j of the next line i-j (argmin); the best j value
   + time/subproblem = O(N)
4. Topological order: i = n, n - 1, ..., 0
   + total time N * O(N) = O(N * N)
5. The original problem is solved by: DP(0)

Parent-pointers: remember which guess was best -> j (the argmin)

parent[i] = argmin(...) -> (best j value)

break at:
0 -> parent[0] -> parent[parent[0]] -> parent[parent[parent[0]]]
0 -> j_1 -> j_2 -> j_3 ...

## Perfect-information Blackjack

+ Deck, a sequence of cards: c_0, c_1, ..., c_{n-1}
+ 1 player vs dealer
+ $1 bet/hand

1. Subproblem: suffix [c_i:]
   + #Subproblem = n
2. Guess: How many times should I hit during the first play?
   + #choices = n
3. Recurrence: BJ(i) = max(outcome \in {-1, 0, 1} + BJ(j)
						   for #hits in range(0, n)
						   if not yet busted)
    + with j = i + 4 + #player_hits + #dealer_hits (= number of cards left)

Complexity O(N^3) worst case.

## Sumproblems for strings

+ Suffixes `x[i:]` for all `i`
+ Prefixes `x[:i]` for all i
+ Substrings `x[i:j]` for all i <= j O(N^2)

## Parenthesization

Optimal evaluation of associative expression: A_0 * A_1 * A_n-1 for n matrices

I choose where the parentheses are: (A_0 * A_1) * A_2)

Some parenthesis-configuration is more efficient than others.

For matrices: A, B, C -> A * B * C

(   _     __n__  )     _
(n | | * [_____] )* n | | = first a square n*n matrix, then * n = O(N^2)
(  |_|           )    |_|_

If you compute the latter two matrices first, though, it's every column in row 1 of matrix B times every row in column 1 of matrix C = 1 number, and then multiply all values in A by this value = O(N). More efficient.

2. Guessing: what is the last multiplication we're doing.

1. Subproblem: optimal evaluation of A_i ... A_{j-1}

This would be a substring problem. You rarely can combine prefix with suffix problems, it's then usually a substring problem.

3. Recurrence: DP(i, j) = min(DP(i, k) + DP(k, j) + cost of multiplication of the two resulting matrices, for k in range(i + 1, j))

Should have a function to compute the cost of matrix multiplication given the dimensions.

4. Time: O(N^3) -> O(N^2) for substring problems, O(N) for each subproblem

Base case: substring-length 1

## Edit-distance

Given two strings `x & y`. What's the cheapest possible sequence of character edits to turn x into y.

Allowed character-edit:
+ Insert character
+ Delete character
+ Replace character c -> c'

Used for spelling-correction and DNA-processing.

### Longest common subsequence

x = hieroglyphology
y = michaelangelo

What's the longest common subsequence? Not substring, you can drop any characters, edit, replace etc.

1. subproblem = solve edit distance on x[i:] & y[j:] for all i, j
2. Guess what to do with x and y, given indices i and j into those, respectively
   + replace x[i] with y[j]
   + insert y[j]
   + delete x[i]
3. recurrence: DP(i, j) = min(
		cost(replace) + DP(i + 1, j + 1),
		cost(insert) + DP(i, j + 1),
		cost(delete) + DP(i + 1, j)
	)
Complexity: O(|x| * |y|), for every letter in x, for every letter in y
4. Topological diagram: matrix from |x| to 0 and |y| to 0
Naive approach:
for i = |x| ... O:
	for j |y| ... O:
		...

5. Original problem: DP(0, 0)

Running time: subproblem -> constant (insert, delete, replace all constant), thus O(|x| * |y|)

Only need to store the last characters.

## Knapsack problem

You're going camping, have only one backpack. You wanna bring as much as possible with you.

+ You have a list of items, each with size s_i (integer) & value v_i (how much you care about it)
+ Knapsack of size S
+ Choose items to maximize the sum of the value (to you), while keeping the sum of the sizes less than S.

1. Subproblem = suffix [i:] of items & remaining capacity X <= S
   + # of subproblems O(N * S)
2. Guessing: is item `i` in the subset of not? Yes or no
3. DP(i, X) = max(DP(i + 1, X), DP(i + 1, X - s_i) + v_i) consume and ignore or consume and add size

Pseudopolynomial time, betweeen polynomial and exponential time.
