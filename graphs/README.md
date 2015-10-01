# Graph Algorithms

Undirected graph: a set of *vertices* (nodes) connected pairwise by *edges* (lines).

| graph     | vertex | edge |
|-----------|--------|------|
| circuit   | component | wire |
| financial | stock | transaction |
| internet  | router | connection |
| social    | person | friendship/relation|

__Path__: A sequence of vertices connected by edges.
__Cycle__: A path whose first and last vertices are the same.

Two vertices are connected if there is a path between them.

__Component__: A set of connected vertices within a graph.

One vertex, many vertices.

Problem examples:

+ Is there a path between `s` and `t`?
+ What is the shortest path between `s` and `t`?
+ Is there a cycle in the graph?
+ Euler tour: is there a cycle that uses each edge exactly once?
+ Hamilton tour: is there a cycle that uses each vertex exactly once?
+ Minimum Spanning Tree (MST): What is the best way to connect all of these vertices?
+ Connectivity: is there a way to connect all of the vertices?

## Graph API

Graph Representation as a drawing can be misleading, as the same graph can be drawn in different ways.

Vertex Representation:
+ Integers between `0` and `V-1`.
+ Why: We can convert between naames and integers with a symbol table.

API:

- `Graph(int V)`: create an empty graph with `V` vertices.
- `Graph(File in)`: create graph from an input string.
- `void addEdge(int v, int w)`: add an edge between `v` and `w`.
- `Iterable<Integer> adjacent(int v)`: Get an iterator to all vertices adjacent to `v`.
- `int numberOfEdges()`: The number of edges in the graph.
- `int numberOfVertices()`: the number of vertices in the graph.

Non-member methods for graph-interaction:

- `int degree(Graph G, int v)`: Compute the *degree* of `v`:

```Java
public static int degree(Graph G, int v)
{
	int degree = 0;
	for (int w : G.adjacent(v)) ++degree;
	return degree;
}
```

- `int maxDegree(Graph G)`: Compute the maximum degree of a graph.

```Java
public static int maxDegree(Graph G)
{
	int max = 0;
	for (int v = 0; v < G.V(); ++v)
	{
		int degree = degree(G, v);
		if (degree > max) max = degree;
	}
	return max;
}
```

- `int averageDegree(Graph G)`: Compute the average degree of each vertex in a graph.

```Java
public static int averageDegree(Graph G)
{
	return (2.0 * G.E()) / G.V();	
}
```

- `int selfLoops(Graph G)`: Count the number of self-loops.

```Java
public static int selfLoops(Graph G)
{
	int loops = 0;

	for (int v = 0; v < G.V(); ++v)
	{
		for (int w : G.adjacent(w))
		{
			if (v == w) ++loops;
		}
	}

	return count/2.0;
}
```

Graph representation:

+ Set-of-edges representation: Keep linked-list/array for the edges (with the vertices they connect).

+ Adjacency-matrix representation: Maintain a two-dimensional `V`-by-`V` boolean array specifying whether or not any vertex is connected to any other vertex.Better if the graph is *dense*, i.e. few vertices, many edges. For each edge `v-w` in the graph: `adjacency[v][w] == adjacency[w][v] == true`

+ Adjancency-list representation: vertex-indexed array of linked-lists (generally a *bag*), where each linked-list (chain) contains all the vertices that vertex is connected to. Better if the graph is *sparse*, i.e. many vertices, few edges.


Complexity of the aforementioned representations:

| Representation | Space | add edge | are v and w connected? | iterate over adjacent vertices |
|----------------|-------|----------|------------------------|--------------------------------|
| List of edges  |   E   |   O(1)   |         O(E)           |               O(E)		      |
| Adj. matrix    |  V^2  |   O(1)   |         O(1)		     |               O(V)             |
| Adj. list      | E + V |   O(1)   |      O(degree(V))      |            O(degree(V))		  |

## Depth-First Search

Model graph as a maze:
+ Vertex = intersection.
+ Edge = passage.

Goal: Explore every intersection in the maze.

Trémaux maze exploration:

+ Unroll a ball of string behind you.
+ Mark each visited intersection and each visited passage.
+ Retrace steps when no unvisited options (go back to find other way).

Algorithm:

To visit vertex `v`
+ mark `v` as visited
+ Recursively visit all unmarked vertices `w` adjacent to `v`.

Design Pattern for Graph Processing:

+ Decouple the graph data type from graph processing.
+ Create a `Graph` object.
+ Pass the `Graph` object to a graph-processsing routine.
+ Query the graph-processing routine for information.

### Analysis

Proposition: DFS marks all vertices connected to `s` in time proportional to the sum of their degrees.

Proof:

+ If `w` is marked, then `w` is connected to `s`.
+ If `w` is connected to `s`, then `w` is marked.
+ Each vertex connected to `s` is visited once.

## Breadth-First Search

+ Start with queue with only one element: the starting vertex.
+ Remove vertex `v` from queue.
+ Add to queue all unmarked vertices adjacent to `v` and mark them.
+ Repeat until queue is empty.

+ Can also very easily keep a `distance` data structure that records the distance of each visited vertex to the starting vertex. Because of the way breadth-first search is structured, the first time a target is hit, it is in the shortest path to it, i.e. if we keep the distance of each vertex to the start, it is equal to that distance + 1 for the last vertex connecting to the target.

### Analysis

Proposition: BFS computes the shorted paths (fewest number of edges) from `s` to all other vertices in a graph in time proportional to `E + V`.

Proof: The Queue always consists of zero or more vertices of distance `k` to `s`, followed by zero or more vertices of distance `k + 1`.

Note that DFS uses recursion, therefore a stack, while BFS uses a queue.

## Connected Components

Definition: Vertices `v` and `w` are *connected* if there is a path between them.

Goal: Preprocess a graph to answer queries of the form "is `v` connected to `w`?"

Connected component API:

+ `ConnectedComponent(Graph graph)`: process graph.
+ `boolean connected(int v, int w)`: are `w` and `v` connected?
+ `int count()`: number of connected components
+ `int id(v)`: Component identifier for `v`?

The relation "is connected to" is an *equivalence relation*:

+ Reflexive: `v` is connected to `v`.
+ Symmetric: if `v` is connected to `w`, then `w` is connected to `v`.
+ Transitive: if `v` is connected to `w` and `w` is connected to `x`, then `v` is connected to `x`.

Definition: A connected component is a maximal set of connected vertices.

Idea: assign each connected component an ID, and each vertex the ID of the component in which it is connected. To see if two vertices are connected, check if their IDs are equal (like for dynamic-connectivity problems).

## Graph challenges

Problem: Is a graph bipartite?

Bipartite: If the graph can be divided into two sets of vertices, such that every edge connects an edge from the one class to an edge of the other class.

Problem: Euler Tour -> Is there a general cycle that uses each edge exactly once?
Answer: Yes if connected and all vertices have *even* degree.

Problem: Travelling Salesman -> Is there a cycle that visits every vertex exactly once? (NP-complete)

Problem: Are two graphs identical except for vertex names? (Graph-isomorphism problem).

Problem: Can you lay out the graph in the plane without crossing edges?


