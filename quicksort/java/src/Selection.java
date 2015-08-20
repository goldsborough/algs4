/**
 * Created by petergoldsborough on 08/20/15.
 */

import java.util.NoSuchElementException;
import java.util.Random;

public class Selection
{
	public static Comparable select(Comparable[] sequence, int k)
	{
		if (k > sequence.length) throw new NoSuchElementException();

		else if (k > 0) --k;

		shuffle(sequence);

		for (int first = 0, last = sequence.length - 1; first < last; )
		{
			int middle = partition(sequence, first, last);

			if (k < middle) last = middle;

			else if (k > middle) first = middle + 1;

			else break;
		}

		return sequence[k];
	}

	public static void main(String[] args)
	{
		Integer[] array = new Integer[]{0, 1, -1, 3, 5, 0, 10};

		System.out.println(select(array, 5));
	}

	private static int partition(Comparable[] sequence, int first, int last)
	{
		int pivot = first++;

		while (first < last)
		{
			while (first < last && less(sequence, first, pivot)) ++first;

			while(first < last && less(sequence, pivot, last)) --last;

			swap(sequence, first, last);
		}

		if (less(sequence, first, pivot)) swap(sequence, pivot, first);

		else swap(sequence, pivot, --first);

		return first;
	}

	private static boolean less(Comparable[] sequence, int first, int second)
	{
		return sequence[first].compareTo(sequence[second]) < 0;
	}

	private static void shuffle(Comparable[] sequence)
	{
		for (int i = 1; i < sequence.length; ++i)
		{
			swap(sequence, i, random.nextInt(i + 1));
		}
	}

	private static void swap(Comparable[] sequence, int first, int second)
	{
		Comparable temp = sequence[first];

		sequence[first] = sequence[second];
		sequence[second] = temp;
	}

	private static Random random = new Random();
}
