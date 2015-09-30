/**
 * Created by petergoldsborough on 09/30/15.
 */

import edu.princeton.cs.algs4.Bag;

import java.lang.Math;
/*

- `Graph(int V)`: create an empty graph with `V` vertices.
- `Graph(File in)`: create graph from an input string.
- `void addEdge(int v, int w)`: add an edge between `v` and `w`.
- `Iterable<Integer> adjacent(int v)`: Get an iterator to all vertices adjacent to `v`.
- `int numberOfEdges()`: The number of edges in the graph.
- `int numberOfVertices()`: the number of vertices in the graph.

 */

public class Graph
{
	public Graph(int numberOfVertices)
	{
		this.vertices = (Bag<Integer>[]) new Bag[numberOfVertices];

		for (int vertex = 0; vertex < numberOfVertices; ++vertex)
		{
			vertices[vertex] = new Bag<>();
		}
	}

	public void addEdge(int first, int second)
	{
		assertIndex(first);
		assertIndex(second);

		vertices[first].add(second);
		vertices[second].add(first);

		++edges;
	}

	Iterable<Integer> adjacent(int vertex)
	{
		assertIndex(vertex);

		return vertices[vertex];
	}

	public int degree(int vertex)
	{
		assertIndex(vertex);

		return vertices[vertex].size();
	}

	public int maxDegree()
	{
		int maximum = 0;

		for (Bag<Integer> vertex : vertices)
		{
			maximum = java.lang.Math.max(maximum, vertex.size());
		}

		return maximum;
	}

	public double averageDegree()
	{
		return (2.0 * edges) / vertices.length;
	}

	public int selfLoops()
	{
		int loops = 0;

		for (int vertex = 0; vertex < vertices.length; ++vertex)
		{
			for (int other : vertices[vertex])
			{
				if (other == vertex) ++loops;
			}
		}

		return loops;
	}

	public int numberOfEdges()
	{
		return edges;
	}

	public int numberOfVertices()
	{
		return vertices.length;
	}

	private void assertIndex(int vertex)
	{
		if (vertex < 0 || vertex > vertices.length)
		{
			throw new IllegalArgumentException("Vertex index out of range!");
		}
	}

	private int edges = 0;

	private Bag<Integer>[] vertices;

}
