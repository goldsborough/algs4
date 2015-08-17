/**
 * Created by petergoldsborough on 08/17/15.
 */

public class Insertion extends Sort
{
	public static void sort(Comparable[] sequence)
	{
		for (int i = 1; i < sequence.length; ++i)
		{
			Comparable value = sequence[i];

			int j = i - 1;

			for ( ; j >= 0 && !less(sequence[j], value); --j)
			{
				sequence[j+1] = sequence[j];
			}

			sequence[j+1] = value;
		}
	}
}
