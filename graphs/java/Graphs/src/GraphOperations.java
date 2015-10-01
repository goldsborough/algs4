import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * Created by petergoldsborough on 10/01/15.
 */

public class GraphOperations
{
	public static void main(String[] args)
	{
		Graph graph = new Graph(4);

		graph.addEdge(0, 2);
		graph.addEdge(2, 1);
		graph.addEdge(1, 3);
		graph.addEdge(3, 0);
		graph.addEdge(0, 1);


		System.out.println(isBipartite(graph, v -> v >= 2));

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
			for (Graph.Adjacent adjacent : graph.adjacent(vertex))
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

		for (Integer vertex: cc.allComponents())
		{
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

		for (Graph.Adjacent adjacent : graph.adjacent(vertex))
		{
			if (! visited.get(adjacent.edge))
			{
				visited.set(adjacent.edge);

				if (! isBipartite(graph, adjacent.vertex, is, predicate, visited))
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
		Stack<Integer> path = new Stack<>();

		BitSet visited = new BitSet(graph.numberOfEdges());

		if (eulerTour(graph, 0, visited, path)) return path;

		return null;
	}

	private static boolean eulerTour(Graph graph,
	                                 int vertex,
	                                 BitSet visited,
	                                 Stack<Integer> path)
	{
		if (visited.cardinality() == graph.numberOfEdges())
		{
			path.push(vertex);

			return true;
		}

		for (Graph.Adjacent adjacent : graph.adjacent(vertex))
		{
			if (! visited.get(adjacent.edge))
			{
				BitSet visitedCopy = (BitSet) visited.clone();

				visitedCopy.set(adjacent.edge);

				if (eulerTour(graph, adjacent.vertex, visitedCopy, path))
				{
						path.push(vertex);

						return true;
				}
			}
		}

		return false;
	}
}
