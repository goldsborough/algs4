/**
 * Created by petergoldsborough on 08/24/15.
 */
public class Heap
{
	public static void sort(Comparable[] sequence)
	{
		for (int i = sequence.length/2; i >= 0; --i)
		{
			sink(sequence, i, sequence.length);
		}

		for (int i = sequence.length - 1; i > 0; --i)
		{
			swap(sequence, 0, i);
			sink(sequence, 0, i);
		}
	}

	public static void main(String[] args)
	{
		Integer[] array = new Integer[]{4, -1, 3, 0, 10, 5, 8, -3};

		Heap.sort(array);

		for (int i = 0; i < array.length; ++i)
		{
			System.out.println(array[i]);
		}
	}

	private static void swap(Comparable[] sequence, int first, int second)
	{
		Comparable temp = sequence[first];
		sequence[first] = sequence[second];
		sequence[second] = temp;
	}

	private static boolean less(Comparable[] sequence, int first, int second)
	{
		return sequence[first].compareTo(sequence[second]) < 0;
	}

	private static void swim(Comparable[] sequence, int index)
	{
		int parent = parent(index);

		while (less(sequence, parent, index))
		{
			swap(sequence, parent, index);
			index = parent;
			parent = parent(index);
		}
	}

	private static void sink(Comparable[] sequence, int index, int last)
	{
		while (index < last)
		{
			int left = left(index), child = left;

			if (left >= last) break;

			int right = right(index);

			if (right < last && less(sequence, left, right)) child = right;

			if (less(sequence, index, child)) swap(sequence, index, child);

			else break;

			index = child;
		}
	}

	private static int parent(int index)
	{
		return index > 1 ? index/2 : 1;
	}

	private static int left(int index)
	{
		return 2 * index;
	}

	private static int right(int index)
	{
		return 2 * index + 1;
	}
}
