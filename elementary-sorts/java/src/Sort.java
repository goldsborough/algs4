/**
 * Created by petergoldsborough on 08/17/15.
 */

public abstract class Sort
{
	public static void main(String[] args)
	{
		Integer[] array = new Integer[]{10, 2, 46, 3, 9, 4, 3, 3, 12, 11, 200};

		Insertion.sort(array);

		for (int i = 0; i < array.length; ++i)
		{
			System.out.println(array[i]);
		}
	}

	protected static boolean less(Comparable first, Comparable second)
	{
		return first.compareTo(second) < 0;
	}

	protected static void exchange(Comparable[] sequence, int first, int second)
	{
		Comparable temp = sequence[first];

		sequence[first] = sequence[second];

		sequence[second] = temp;
	}
}
