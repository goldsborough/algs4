/**
 * Created by petergoldsborough on 10/01/15.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;

public class ConnectedComponents
{
	public static void main(String[] args)
	{
		Graph graph = new Graph(10);

		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);

		graph.addEdge(4, 5);
		graph.addEdge(4, 6);
		graph.addEdge(4, 7);

		graph.addEdge(8, 9);

		ConnectedComponents cc = new ConnectedComponents(graph);

		System.out.println(cc.count());
		System.out.println(cc.connected(0, 3));
		System.out.println(cc.id(5));
		System.out.println(cc.id(8));
	}

	public ConnectedComponents(Graph graph)
	{
		int numberOfVertices = graph.numberOfVertices();

		BitSet visited = new BitSet(numberOfVertices);

		this.components = new ArrayList<>();

		this.count = 0;

		this.ids = new int[numberOfVertices];

		for (int vertex = 0; vertex < numberOfVertices; ++vertex)
		{
			if (! visited.get(vertex))
			{
				ArrayList<Integer> component = new ArrayList<>();

				dfs(graph, vertex, visited, component, count++);

				components.add(component);
			}
		}
	}

	public boolean connected(int first, int second)
	{
		return id(first) == id(second);
	}

	public ArrayList<ArrayList<Integer>> allComponents()
	{
		return this.components;
	}

	public ArrayList<Integer> component(int id)
	{
		return components.get(id);
	}

	public int count()
	{
		return count;
	}

	public int id(int vertex)
	{
		return ids[vertex];
	}

	private void dfs(Graph graph,
	                 int vertex,
	                 BitSet visited,
	                 ArrayList<Integer> component,
	                 int id)
	{
		visited.set(vertex);

		ids[vertex] = id;

		component.add(vertex);

		for (Graph.Adjacent adjacent : graph.adjacent(vertex))
		{
			if (! visited.get(adjacent.vertex))
			{
				dfs(graph, adjacent.vertex, visited, component, id);
			}
		}
	}

	private int count;
	private int[] ids;

	private ArrayList<ArrayList<Integer>> components;
}
