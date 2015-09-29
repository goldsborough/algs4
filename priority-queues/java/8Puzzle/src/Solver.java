import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
	public static void main(String[] args)
	{
		In in = new In(args[0]);
		int N = in.readInt();

		int[][] blocks = new int[N][N];

		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				blocks[i][j] = in.readInt();
			}
		}

		Board initial = new Board(blocks);

		Solver solver = new Solver(initial);

		if (! solver.isSolvable())
		{
			StdOut.println("No solution possible");
		}

		else
		{
			StdOut.println("Minimum number of moves = " + solver.moves());

			for (Board board : solver.solution())
			{
				StdOut.println(board);
			}
		}
	}

	public Solver(Board initial)
	{
		if (initial == null) throw new NullPointerException();

		solution = solve(initial);
	}

	public boolean isSolvable()
	{
		return isSolvable;
	}

	public int moves()
	{
		return isSolvable ? moves : -1;
	}

	public Iterable<Board> solution()
	{
		return isSolvable ? solution : null;
	}

	private static class Node implements Comparable<Node>
	{
		Node(Board board, Node previous)
		{
			this.board = board;
			this.previous = previous;
			this.moves = previous != null ? previous.moves + 1 : 0;
			this.priority = board.manhattan() + this.moves;
		}

		public int compareTo(Node other)
		{
			if (priority < other.priority) return -1;
			if (priority > other.priority) return +1;
			else                           return  0;
		}

		public final Board board;

		public final Node previous;

		public final int priority;

		public final int moves;
	}

	private Stack<Board> solve(Board initial)
	{
		MinPQ<Node> original = new MinPQ<>();
		MinPQ<Node> twin = new MinPQ<>();

		original.insert(new Node(initial, null));
		twin.insert(new Node(initial.twin(), null));

		Node first, second;

		for( ; ; )
		{
			first = original.delMin();
			second = twin.delMin();

			if (first.board.isGoal())
			{
				isSolvable = true;
				moves = first.moves;
				return makeSolution(first);
			}

			else if (second.board.isGoal())
			{
				isSolvable = false;
				moves = second.moves;
				return makeSolution(second);
			}

			for (Board neighbor : first.board.neighbors())
			{
				if (first.previous == null || ! neighbor.equals(first.previous.board))
				{
					original.insert(new Node(neighbor, first));
				}
			}

			for (Board neighbor : second.board.neighbors())
			{
				if (second.previous == null || ! neighbor.equals(second.previous.board))
				{
					twin.insert(new Node(neighbor, second));
				}
			}
		}
	}

	private Stack<Board> makeSolution(Node node)
	{
		Stack<Board> stack = new Stack<>();

		for( ; node != null; node = node.previous)
		{
			stack.push(node.board);
		}

		return stack;
	}

	private boolean isSolvable = false;

	private Stack<Board> solution = new Stack<>();

	private int moves = 0;
}