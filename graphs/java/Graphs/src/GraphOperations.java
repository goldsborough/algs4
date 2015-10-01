import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * Created by petergoldsborough on 10/01/15.
 */

public class GraphOperations
{
	public static void main(String[] args)
	{
		Graph graph = new Graph(6);

		graph.addEdge(0, 3);
		graph.addEdge(3, 1);
		graph.addEdge(1, 4);
		graph.addEdge(4, 0);

		graph.addEdge(2, 2);

		System.out.println(isBipartite(graph, v -> v > 2));

	}

	public static int degree(Graph graph, int vertex)
	{
		if (vertex < 0 || vertex > graph.numberOfVertices())
		{
			throw new IllegalArgumentException("Vertex index out of range!");
		}

		return graph.adjacent(vertex).size();
	}

	public static int maxDegree(Graph graph)
	{
		int maximum = 0;

		for (int vertex = 0; vertex < graph.numberOfVertices(); ++vertex)
		{
			int adjacent = graph.adjacent(vertex).size();

			maximum = java.lang.Math.max(maximum, adjacent);
		}

		return maximum;
	}

	public static double averageDegree(Graph graph)
	{
		return (2.0 * graph.numberOfEdges()) / graph.numberOfVertices();
	}

	public static int selfLoops(Graph graph)
	{
		int loops = 0;

		for (int vertex = 0; vertex < graph.numberOfVertices(); ++vertex)
		{
			for (Graph.Edge adjacent : graph.adjacent(vertex))
			{
				if (adjacent.vertex == vertex) ++loops;
			}
		}

		return loops;
	}

	public static boolean isBipartite(Graph graph, Predicate<Integer> predicate)
	{
		ConnectedComponents cc = new ConnectedComponents(graph);

		BitSet visited = new BitSet(graph.numberOfVertices());

		for (ArrayList<Integer> vertices : cc.allComponents())
		{
			if (vertices.size() < 2) return false;

			int vertex = vertices.get(0);

			boolean is = ! predicate.test(vertex);

			if (! isBipartite(graph, vertex, is, predicate, visited))
			{
				return false;
			}
		}

		return true;
	}

	private static boolean isBipartite(Graph graph,
	                                   int vertex,
	                                   boolean was,
	                                   Predicate<Integer> predicate,
	                                   BitSet visited)
	{
		boolean is = predicate.test(vertex);

		if (was == is) return false;

		visited.set(vertex);

		for (Graph.Edge adjacent : graph.adjacent(vertex))
		{
			if (! visited.get(adjacent.vertex))
			{
				BitSet copy = (BitSet) visited.clone();

				if (! isBipartite(graph, adjacent.vertex, is, predicate, copy))
				{
					return false;
				}
			}
		}

		return true;
	}

	public static boolean eulerTourPossible(Graph graph)
	{
		ConnectedComponents cc = new ConnectedComponents(graph);

		if (cc.count() > 1) return false;

		for (int vertex = 0; vertex < graph.numberOfVertices(); ++vertex)
		{
			if (degree(graph, vertex) % 2 != 0) return false;
		}

		return true;
	}

	public static Iterable<Integer> eulerTour(Graph graph)
	{
		ArrayList<Integer> path = new ArrayList<>();

		HashSet<Integer> visited = new HashSet<>();

		return eulerTour(graph, 0, visited, path);
	}

	private static ArrayList<Integer> eulerTour(Graph graph,
	                                 int vertex,
	                                 HashSet<Integer> visited,
	                                 ArrayList<Integer> path)
	{
		path.add(vertex);

		if (visited.size() == graph.numberOfEdges()) return path;

		for (Graph.Edge adjacent : graph.adjacent(vertex))
		{
			if (! visited.contains(adjacent.id))
			{
				HashSet<Integer> visitedCopy = (HashSet<Integer>) visited.clone();

				visitedCopy.add(adjacent.id);

				ArrayList<Integer> pathCopy = (ArrayList<Integer>) path.clone();

				ArrayList<Integer> result = eulerTour(graph, adjacent.vertex, visitedCopy, pathCopy);

				if (result != null) return result;
			}
		}

		return null;
	}
}
