# Analyzing Algorithms

## Reasons to Analyze Algorithms

- Predict performance.
- Compare algorithms.
- Provide guarantees.
- Understand theoretical basis.

## Scientific Method

- Observe some feature in the natural world.
- Hypothesize a model that is consisten with the observations.
- Predict events using the hypothesis.
- Verify the predictions by making further observations.
- Validate by repeating until the hypothesis and observations agree.

## Principles

- Experiments must be reproducible.
- Hypotheses must be falsifiable.

## Empirical Analysis

- Manual measurement (benchmarking) with a stopwatch or programmatic timeing method.

- Measure running time for different input sizes *N* (e.g. doubling time) and observe the relationship between the running times.

## Data Analysis

- Plot running time T(N) vs input size *N*.
- Plot as log-log plot, often get a straight line. lg(T(N)) vs lg(N). Plot tells you the exponent of *N*.
- Regression, power law: *a x N^b*
- Once you have the power b from the slope of the log-log plot, solve for *a* in the equation *T(N) = a x N^b*

## Doubling Hypothesis

- Run program, doubling the size of the input and observe ratios. Observe to what it converges, do not take the average!

|  N   | T(N) | Ratio | lg(Ratio) |
| ---- | ---- | ----- | --------  |
| 1000 | 0.1  |   -   |     -     |
| 2000 | 0.8  |  7.7  |    2.9    |
| 4000 | 6.4  |   8   |     3     |
| ...  | ...  |  ...  |    ...    |

                            ^
                       Converges to 3

- Hypothesis: Running time is about *a x N^b*, where *b = lg(Ratio)*
- Caveat: Cannot identify logarithmic factors with the doubling hypothesis.
- Calculate *a* by solving *T(N) = a x N^b* for a with all other variables now known.

## Experimental algorithmics

- System independent effects (determines constant *a* and exponent *b* in power law)

	+ Algorithm.
	+ Input data.

- System dependent effects (contribute only to constant *a* in power law)

	+ Hardware: CPU, memory, cache
	+ Software compiler, intepreter, garbage collector
	+ System: OS, network, other applications

## Mathematical Models

- Analyze individual operations to determine complexity
- Simplification 1

  Count only the most expensive ones, i.e. those that take the most time or where time x frequency is highest.

- Simplification 2

  Ignore lower order terms, e.g. in *5 x N^3 + 20 x N + 16*, ignore the term with N and the constant 16 (which is 16 x N^0) because they are less significant in comparison with the highest order term. We use *tilde notation* to say that *5 x N^3 + 20 x N + 16 __~ 5 x N^3__*. Technical definition is that for *f(N) ~ G(N)* when *N* goes towards infinity, the lower order terms become so insignificant that *f(N)/g(N) = 1*:

  *f(N) ~ g(N) => lim(N -> ∞) f(N)/g(N) = 1*

 # Order-of-Growth Classifications

 - A great number of algorithms (most) are described by the following order of growth functions (note that it is quite a small set):

 	+ 1 (constant)
 	+ log N (logarithmic)
 	+ N (linear)
 	+ N log N (linearithmic)
 	+ N^2 (quadratic)
 	+ N^3 (cubic)
 	+ 2^N (exponential)

- We say the algorithm "is proportional to" e.g. constant time

## Theory of Algorithms

### Types of analyses

1. Best case: Lower bound on cost.

	- Determined by *easiest* input
	- Provdes a goal for all inputs.

2. Worst case: Upper bound on cost.

	- Determined by *most difficult* input.
	- Provides a guarantee for all inputs.

3. Average case: Expected cost for random input.

	- A model for *random* input.
	- Provides a way to predict the worst case.

We focus on the worst case so that we can ignore the variability of input (sizes).

### Notation

- Big Theta: Describes the order of growth mathematically and thus classifies the algorithm. Example: Θ(Ν^2)

- Big Oh: Describes the upper bound (worst-case) of the algorithm. Example: O(N^2) = Θ(Ν^2) and smaller (better).

- Big Omega: Describes the lower bound (best case) of the algorithm. Example: Ω(Ν^2) = Θ(Ν^2) and greater (worse).

```Python

# Brute-force algorithm to check if
# any duplicates present in the sequence
for i in range(len(sequence)):
	for j in range(len(sequence)):
		if i != j and sequence[i] == sequence[j]:
			# Found a duplicate
			break
```

Here, to generally classify the algorithm we say that *its order of growth is quadratic*, thus Θ(Ν^2).

The lower bound and best case here is that the first comparison operation already triggers, therefore Ω(1).

The upper bound and worst case is that there are no duplicates and we perform N^2 comparison operations, therefore O(N^2).

## Memory

- Arrays in Java have an overhead of 24 bytes, i.e. if you have an array of N 32-bit (4-byte) integers the total array will have a space usage of 4N + 24 bytes.
- Object overhead in Java is 16 bytes. Nested classes *an extra* 8 bytes (pointer to enclosing class), unless it is declared static. Nested classes also have the object overhead of 16 bytes though.
- The size of a reference (pointer in C/C++) is 8 bytes.
- Padding mandates that each object be a multiple of 8 bytes (so extra bytes may be added).

# Introduction to Algorithms

## Loop Invariants

Loop invariants help us to understand whether and why an algorithm is correct. For insertion sort, the loop invariants reads:

1. For each position of the iterator in the for loop, all values to the left of the iterator are the elements that were initially in that subarray, but now in sorted order.

2. For each position of the iterator in the for loop, none of the values to the right of the iterator have yet been looked at.

We must show three things about a loop invariant:

1. Initialization: It is true prior to the first iteration of the loop. For a for loop, this point is immediately after the first assignment of an initial value to the loop variable and before the first test in the loop header.

2. Maintenance: If it is true before an iteration of the loop, it remains true before the next iteration (after the next assignment, before the next check).

3. Termination: When the loop termiantes, the invariant still holds true and gives us a useful property that helps show that the algorhtm is correct.
