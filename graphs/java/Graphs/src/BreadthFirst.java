import java.util.*;

/**
 * Created by petergoldsborough on 09/30/15.
 */

public class BreadthFirst
{
	public static boolean connected(Graph graph, int vertex, int target)
	{
		LinkedList<Integer> queue = new LinkedList<>();

		BitSet visited = new BitSet(graph.numberOfVertices());

		queue.add(vertex);

		visited.set(vertex);

		while (! queue.isEmpty())
		{
			vertex = queue.pop();

			if (vertex == target) return true;

			for (int adjacent : graph.adjacent(vertex))
			{
				if (! visited.get(adjacent))
				{
					queue.add(adjacent);

					visited.set(adjacent);
				}
			}
		}

		return false;

	}

	public static int shortestDistance(Graph graph, int vertex, int target)
	{
		int numberOfVertices = graph.numberOfVertices();

		if (vertex > numberOfVertices)
		{
			throw new IllegalArgumentException("Vertex out of range!");
		}

		if (target > numberOfVertices)
		{
			throw new IllegalArgumentException("Target out of range!");
		}

		BitSet visited = new BitSet(numberOfVertices);

		visited.set(vertex);

		LinkedList<Integer> queue = new LinkedList<>();

		queue.add(vertex);

		int lastOfLevel = vertex;

		int distance = 0;

		while (! queue.isEmpty())
		{
			vertex = queue.pop();

			if (vertex == target) break;

			for (int adjacent : graph.adjacent(vertex))
			{
				if (! visited.get(adjacent))
				{
					queue.add(adjacent);

					visited.set(adjacent);
				}
			}

			if (vertex == lastOfLevel)
			{
				++distance;

				lastOfLevel = queue.peekLast();
			}
		}

		return distance;
	}

	public static Iterable<Integer> shortestPath(Graph graph, Integer vertex, Integer target)
	{
		int numberOfVertices = graph.numberOfVertices();

		if (vertex.compareTo(numberOfVertices) > 0)
		{
			throw new IllegalArgumentException("Vertex out of range!");
		}

		if (target.compareTo(numberOfVertices) > 0)
		{
			throw new IllegalArgumentException("Target out of range!");
		}

		BitSet visited = new BitSet(numberOfVertices);

		visited.set(vertex);

		LinkedList<Integer> queue = new LinkedList<>();

		queue.add(vertex);

		HashMap<Integer, Integer> source = new HashMap<>();

		source.put(vertex, null);

		while (! queue.isEmpty())
		{
			vertex = queue.pop();

			if (vertex.equals(target)) break;

			for (int adjacent : graph.adjacent(vertex))
			{
				if (! visited.get(adjacent))
				{
					queue.add(adjacent);

					source.put(adjacent, vertex);

					visited.set(adjacent);
				}
			}
		}

		Stack<Integer> path = new Stack<>();

		while (vertex != null)
		{
			path.push(vertex);

			vertex = source.get(vertex);
		}

		return path;
	}

	public static void main(String[] args)
	{
		Graph graph = new Graph(10);

		graph.addEdge(0, 1);

		graph.addEdge(1, 5);

		graph.addEdge(5, 4);

		graph.addEdge(0, 4);

		for (int vertex : shortestPath(graph, 0, 5))
		{
			System.out.println(vertex);
		}
	}

}
