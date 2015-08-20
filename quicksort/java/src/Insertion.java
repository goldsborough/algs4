/**
 * Created by petergoldsborough on 08/20/15.
 */

public class Insertion
{
	public static void sort(Comparable[] sequence)
	{
		for (int i = 1; i < sequence.length; ++i)
		{
			Comparable value = sequence[i];

			int j = i - 1;

			for ( ; j >= 0 && sequence[j].compareTo(value) >= 0; --j)
			{
				sequence[j + 1] = sequence[j];
			}

			sequence[j + 1] = value;
		}
	}
}
