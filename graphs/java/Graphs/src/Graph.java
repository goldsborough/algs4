/**
 * Created by petergoldsborough on 09/30/15.
 */

import java.util.ArrayList;

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
	public static class Edge
	{
		public Edge(int vertex, int id)
		{
			this.vertex = vertex;
			this.id = id;
		}
		
		public int hashCode()
		{
			return vertex ^ id;
		}
		
		public boolean equals(Object object)
		{
			if (! (object instanceof Edge)) return false;
			
			Edge other = (Edge) object;

			return this.id == other.id;
		}
		
		public final int vertex;
		public final int id;
	}
	
	public Graph(int numberOfVertices)
	{
		this.vertices = (ArrayList<Edge>[]) new ArrayList[numberOfVertices];

		for (int vertex = 0; vertex < numberOfVertices; ++vertex)
		{
			vertices[vertex] = new ArrayList<>();
		}
	}

	public void addEdge(int first, int second)
	{
		assertIndex(first);
		assertIndex(second);

		vertices[first].add(new Edge(second, edges));
		vertices[second].add(new Edge(first, edges));

		++edges;
	}

	ArrayList<Edge> adjacent(int vertex)
	{
		assertIndex(vertex);

		return vertices[vertex];
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

	private ArrayList<Edge>[] vertices;

}
