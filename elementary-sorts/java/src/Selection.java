/**
 * Created by petergoldsborough on 08/17/15.
 */

public class Selection extends Sort
{
	public static void sort(Comparable[] sequence)
	{
		for (int i = 0, N = sequence.length - 1; i < N; ++i)
		{
			int min = i;

			for (int j = i + 1; j < sequence.length; ++j)
			{
				if (less(sequence[j], sequence[min]))
				{
					min = j;
				}
			}

			exchange(sequence, i, min);
		}
	}
}
