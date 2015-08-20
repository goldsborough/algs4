/**
 * Created by petergoldsborough on 08/20/15.
 */

import java.util.Random;

public class BasicQuick
{
	public static void sort(Comparable[] sequence)
	{
		shuffle(sequence);

		sort(sequence, 0, sequence.length);
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

	protected static int median(Comparable[] sequence, int first, int last)
	{
		int range = last - first;

		int one = first + random.nextInt(range);
		int two = first + random.nextInt(range);
		int three = first + random.nextInt(range);

		if (less(sequence, one, two))
		{
			if (less(sequence, two, three))
			{
				return two;
			}

			else if (less(sequence, three, one))
			{
				return one;
			}

			else return three;
		}

		else if (less(sequence, three, two))
		{
			return two;
		}

		else if (less(sequence, three, one))
		{
			return three;
		}

		else return one;
	}

	protected static void shuffle(Comparable[] sequence)
	{
		for (int i = 1; i < sequence.length; ++i)
		{
			swap(sequence, i, random.nextInt(i + 1));
		}
	}

	protected static boolean less(Comparable[] sequence, int first, int second)
	{
		return sequence[first].compareTo(sequence[second]) < 0;
	}

	protected static void swap(Comparable[] sequence, int first, int second)
	{
		if (first == second) return;

		Comparable temp = sequence[first];

		sequence[first] = sequence[second];

		sequence[second] = temp;
	}

	protected static boolean isSorted(Comparable[] sequence, int first, int last)
	{
		for (int i = first, j = i + 1; j < last; ++i, ++j)
		{
			if (less(sequence, j, i)) return false;
		}

		return true;
	}

	private static void sort(Comparable[] sequence, int first, int last)
	{
		if (last - first <= 1) return;

		int pivot = partition(sequence, first, last);

		sort(sequence, first, pivot);
		sort(sequence, pivot + 1, last);

		assert isSorted(sequence, first, last);
	}

	private static int partition(Comparable[] sequence, int first, int last)
	{
		int median = median(sequence, first, last--);

		int pivot = first;

		swap(sequence, first++, median);

		for ( ; ; )
		{
			while(first <= last && less(sequence, first, pivot)) ++first;

			while (last >= first && less(sequence, pivot, last)) --last;

			if (first >= last) break;

			swap(sequence, first, last);
		}

		swap(sequence, pivot, last);

		return last;
	}

	protected static Random random = new Random();
}

