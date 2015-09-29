import edu.princeton.cs.algs4.Bag;

import java.util.Arrays;
import java.util.HashSet;

public class Board
{
	public static void main(String[] args)
	{
		int[][] array = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};

		Board board = new Board(array);

		System.out.println(board.toString());

		for (Board b : board.neighbors())
		{
			System.out.println(b.toString());
		}

		System.out.println(board.isGoal());
	}

	public Board(int[][] blocks)
	{
		if (blocks == null) throw new java.lang.NullPointerException();

		HashSet<Integer> set = new HashSet<>();

		this.dimension = blocks.length;

		board = new int[dimension * dimension];

		for (int row = 0; row < blocks.length; ++row)
		{
			for (int column = 0; column < blocks.length; ++column)
			{
				if (set.contains(blocks[row][column]))
				{
					throw new IllegalArgumentException("Found duplicates in board!");
				}

				else set.add(blocks[row][column]);

				int index = index(row, column);

				board[index] = blocks[row][column];

				if (board[index] == 0) blank = index;
			}
		}
	}

	public int dimension()
	{
		return this.dimension;
	}

	public int hamming()
	{
		int error = 0;

		for (int i = 0; i < board.length; ++i)
		{
			if (board[i] != 0 && board[i] != i + 1) ++error;
		}

		return error;
	}

	public int manhattan()
	{
		int error = 0;

		for (int i = 0; i < board.length; ++i)
		{
			if (board[i] != 0)
			{
				error += Math.abs(i / dimension - (board[i] - 1) / dimension);
				error += Math.abs(i % dimension - (board[i] - 1) % dimension);
			}
		}

		return error;
	}

	public boolean isGoal()
	{
		if (blank != board.length - 1) return false;

		for (int i = 0; i < board.length; ++i)
		{
			if (board[i] != 0 && board[i] != i + 1) return false;
		}

		return true;
	}

	public Board twin()
	{
		Board other = new Board(this);

		if (dimension == 1) return other;

		// Don't swap with the blank block;
		int first = 1, second = 2;

		if (board[first] == 0) first = 0;

		else if (board[second] == 0) second = 3;

		swap(other, first, second);

		return other;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == this) return true;

		if (object == null) return false;

		if (! (object instanceof Board)) return false;

		Board other = (Board) object;

		if (this.blank != other.blank) return false;

		return java.util.Arrays.equals(this.board, other.board);
	}

	public Iterable<Board> neighbors()
	{
		Bag<Board> bag = new Bag<>();

		// Left neighbour
		if (blank % dimension > 0)
		{
			Board other = new Board(this);
			swap(other, blank, blank - 1);
			bag.add(other);
		}

		// Right neighbour
		if ((blank + 1) % dimension > 0)
		{
			Board other = new Board(this);
			swap(other, blank, blank + 1);
			bag.add(other);
		}

		// Top neighbour
		if (blank >= dimension)
		{
			Board other = new Board(this);
			swap(other, blank, blank - dimension);
			bag.add(other);
		}

		// Bottom neighbour
		if (blank + dimension < board.length)
		{
			Board other = new Board(this);
			swap(other, blank, blank + dimension);
			bag.add(other);
		}

		return bag;
	}

	public String toString()
	{
		String display = Integer.toString(dimension) + "\n";

		for (int i = 0; i < board.length; )
		{
			display += board[i];

			if (++i % dimension == 0) display += "\n";

			else display += "  ";
		}

		return display;
	}

	private Board(Board other)
	{
		this.board = Arrays.copyOf(other.board, other.board.length);
		this.dimension = other.dimension;
		this.blank = other.blank;

	}

	private int index(int row, int column)
	{
		return row * dimension + column;
	}

	private static void swap(Board source,
	                         int first,
	                         int second)
	{
		if (source.board[first] == 0) source.blank = second;

		int temp = source.board[first];
		source.board[first] = source.board[second];
		source.board[second] = temp;
	}

	private final int[] board;

	private final int dimension;
	private int blank;

}
