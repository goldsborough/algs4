/**
 * Created by petergoldsborough on 08/20/15.
 */

public class ExtendedQuick extends BasicQuick
{
	public static final int CUTOFF = 10;

	public static void sort(Comparable[] sequence)
	{
		shuffle(sequence);

		sort(sequence, 0, sequence.length);

		Insertion.sort(sequence);
	}

	public static void main(String[] args)
	{
		Integer[] values = new Integer[]{1, 4, 5, 3, 20, -1, 7, 2, 0};

		BasicQuick.sort(values);

		for (int i = 0; i < values.length; ++i)
		{
			System.out.printf("%d ", values[i]);
		}
	}

	private static void sort(Comparable[] sequence, int first, int last)
	{
		if (last - first <= CUTOFF) return;

		Boundary boundary = partition(sequence, first, last);

		sort(sequence, first, boundary.left);
		sort(sequence, boundary.right, last);

		assert isSorted(sequence, first, last);
	}

	private static Boundary partition(Comparable[] sequence, int first, int last)
	{
		int median = median(sequence, first, last);

		swap(sequence, first, median);

		int pivot = first, itr = ++first;

		while (itr < last)
		{
			if (less(sequence, itr, pivot)) swap(sequence, first++, itr++);

			else if (less(sequence, pivot, itr)) swap(sequence, itr, --last);

			else ++itr;
		}

		swap(sequence, pivot, itr - 1);

		return new Boundary(first, last);
	}

	private static class Boundary
	{
		Boundary(int left, int right)
		{
			this.left = left;
			this.right = right;
		}

		public int left;
		public int right;
	};
};