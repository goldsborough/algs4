/**
 * Created by petergoldsborough on 09/30/15.
 */

import java.util.ArrayList;

public class Graph
{
	public static class Adjacent
	{
		public Adjacent(int vertex, int edge)
		{
			this.vertex = vertex;
			this.edge = edge;
		}
		
		public int hashCode()
		{
			return vertex ^ edge;
		}
		
		public boolean equals(Object object)
		{
			if (! (object instanceof Adjacent)) return false;

			Adjacent other = (Adjacent) object;

			return this.edge == other.edge;
		}
		
		public final int vertex;
		public final int edge;
	}
	
	public Graph(int numberOfVertices)
	{
		this.vertices = (ArrayList<Adjacent>[]) new ArrayList[numberOfVertices];

		for (int vertex = 0; vertex < numberOfVertices; ++vertex)
		{
			vertices[vertex] = new ArrayList<>();
		}
	}

	public void addEdge(int first, int second)
	{
		assertIndex(first);
		assertIndex(second);

		vertices[first].add(new Adjacent(second, edges));
		vertices[second].add(new Adjacent(first, edges));

		++edges;
	}

	ArrayList<Adjacent> adjacent(int vertex)
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

	private ArrayList<Adjacent>[] vertices;

}
