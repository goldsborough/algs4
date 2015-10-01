/**
 * Created by petergoldsborough on 09/30/15.
 */

import java.util.BitSet;

public class DepthFirst
{

	public static boolean connected(Graph graph, int start, int target)
	{
		BitSet visited = new BitSet(graph.numberOfVertices());

		return connected(graph, start, visited, target);
	}

	public static void main(String[] args)
	{
		Graph graph = new Graph(10);

		graph.addEdge(0, 1);

		graph.addEdge(1, 5);

		graph.addEdge(5, 4);

		System.out.println(connected(graph, 0, 5));
	}

	private static boolean connected(Graph graph, int current, BitSet visited, int target)
	{
		if (visited.get(current)) return false;

		if (current == target) return true;

		visited.set(current);

		for (Graph.Adjacent adjacent : graph.adjacent(current))
		{
			if (connected(graph, adjacent.vertex, visited, target)) return true;
		}

		return false;
	}
}
