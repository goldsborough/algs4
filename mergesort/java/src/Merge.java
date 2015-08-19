/**
 * Created by petergoldsborough on 08/19/15.
 */

public class Merge
{
	public static void topDown(Comparable[] sequence)
	{
		if (sequence.length < CUTOFF) Insertion.sort(sequence);

		else
		{
			Comparable[] auxiliary = new Comparable[sequence.length];

			for (int i = 0; i < auxiliary.length; ++i)
			{
				auxiliary[i] = sequence[i];
			}

			sort(sequence, auxiliary, 0, sequence.length);
		}
	}

	public static void bottomUp(Comparable[] sequence)
	{
		if (sequence.length < CUTOFF) Insertion.sort(sequence);

		else
		{
			Comparable[] auxiliary = new Comparable[sequence.length];

			Comparable[] temp;

			for (int bin = 1; bin < sequence.length; bin *= 2)
			{
				int first = 0, middle = bin, last = middle + bin;

				for ( ; first < sequence.length; first = last, middle = last + bin, last = middle + bin)
				{
					if (middle > sequence.length) middle = sequence.length;

					if (last > sequence.length) last = sequence.length;

					merge(auxiliary, sequence, first, middle, last);
				}

				temp = sequence;
				sequence = auxiliary;
				auxiliary = temp;
			}

			sequence = auxiliary;
		}
	}

	public static void main(String[] args)
	{
		Integer[] array = new Integer[]{4, 8, 3, 0, 5, 10, -1, 10, 11, 7, 4};

		Merge.bottomUp(array);

		for (int i = 0; i < array.length; ++i)
		{
			System.out.printf("%d ", array[i]);
		}
	}

	private static void sort(Comparable[] destination,
	                         Comparable[] source,
	                         int first,
	                         int last)
	{
		int size = last - first;

		if (size <= 1) return;

		int middle = first + size/2;

		sort(source, destination, first, middle);
		sort(source, destination, middle, last);

		if (less(source, middle, middle - 1))
		{
			merge(destination, source, first, middle, last);
		}

		assert isSorted(destination, first, last);
	}

	private static void merge(Comparable[] destination,
	                          Comparable[] source,
	                          int first,
	                          int middle,
	                          int last)
	{
		assert isSorted(source, first, middle);
		assert isSorted(source, middle, last);

		for (int i = first, j = middle; first < last; ++first)
		{
			int choice;

			if (i >= middle) choice = j++;

			else if (j >= last) choice = i++;

			else if (less(source, j, i)) choice = j++;

			else choice = i++;

			destination[first] = source[choice];
		}
	}

	private static boolean less(Comparable[] sequence, int first, int second)
	{
		return sequence[first].compareTo(sequence[second]) < 0;
	}

	private static boolean isSorted(Comparable[] sequence, int first, int last)
	{
		for (int i = first, j = i + 1; j < last; ++i, ++j)
		{
			if (less(sequence, j, i)) return false;
		}

		return true;
	}

	private static int CUTOFF = 8;
}

class Insertion
{
	static public void sort(Comparable[] sequence)
	{
		for (int i = 1; i < sequence.length; ++i)
		{
			Comparable item = sequence[i];

			int j = i - 1;

			for ( ; j >= 0 && sequence[j].compareTo(item) >= 0; --j)
			{
				sequence[j + 1] = sequence[j];
			}

			sequence[j + 1] = item;
		}
	}
};