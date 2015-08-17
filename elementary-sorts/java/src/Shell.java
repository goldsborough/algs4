/**
 * Created by petergoldsborough on 08/17/15.
 */

public class Shell extends Sort
{
	public static void sort(Comparable[] sequence)
	{
		int N = sequence.length;

		int h = 1;

		while (h < N/3) h = 3 * h + 1;

		while (h >= 1)
		{
			for (int i = h; i < sequence.length; i += h)
			{
				Comparable value = sequence[i];

				int j = i - h;

				for ( ; j >= 0 && !less(sequence[j], value); j -= h)
				{
					sequence[j + h] = sequence[j];
				}

				sequence[j + h] = value;
			}

			h /= 3;
		}
	}
}
