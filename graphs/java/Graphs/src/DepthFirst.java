/**
 * Created by petergoldsborough on 09/30/15.
 */

public class DepthFirst
{

	public static boolean connected(Graph graph, int start, int target)
	{
		boolean[] visited = new boolean[graph.numberOfVertices()];

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

	private static boolean connected(Graph graph, int current, boolean[] visited, int target)
	{
		if (visited[current]) return false;

		if (current == target) return true;

		visited[current] = true;

		for (int vertex : graph.adjacent(current))
		{
			if (connected(graph, vertex, visited, target)) return true;
		}

		return false;
	}
}
