import edu.princeton.cs.algs4.Queue;

/**
 * Created by petergoldsborough on 09/30/15.
 */

public class BreadthFirst
{
	public static boolean connected(Graph graph, int vertex, int target)
	{
		Queue<Integer> queue = new Queue<>();

		boolean[] visited = new boolean[graph.numberOfVertices()];

		queue.enqueue(vertex);

		visited[vertex] = true;

		while (! queue.isEmpty())
		{
			vertex = queue.dequeue();

			if (vertex == target) return true;

			for (int adjacent : graph.adjacent(vertex))
			{
				if (! visited[adjacent])
				{
					queue.enqueue(adjacent);

					visited[adjacent] = true;
				}
			}
		}

		return false;

	}

	public static void main(String[] args)
	{
		Graph graph = new Graph(10);

		graph.addEdge(0, 1);

		graph.addEdge(1, 5);

		graph.addEdge(5, 4);

		System.out.println(connected(graph, 0, 30));
	}

}
