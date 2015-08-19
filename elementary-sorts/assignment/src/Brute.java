/**
 * Created by petergoldsborough on 08/18/15.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.util.Comparator;

public class Brute
{
	private static void sort(Comparable[] sequence)
	{
		int h = 1;
		while (h < sequence.length/3) h = 3 * h + 1;

		while (h >= 1)
		{
			for (int i = h; i < sequence.length; i += h)
			{
				Comparable item = sequence[i];

				int j = i - h;

				for ( ; j >= 0 && item.compareTo(sequence[j]) < 0; j -= h)
				{
					sequence[j + h] = sequence[j];
				}

				sequence[j + h] = item;
			}

			h /= 3;
		}
	}

	private static void find(Point[] points)
	{
		Point[] sorted = new Point[4];

		for (int i = 0; i < points.length; ++i)
		{
			Point source = points[i];

			for (int j = i + 1; j < points.length; ++j)
			{
				for (int k = j + 1; k < points.length; ++k)
				{
					for (int l = k + 1; l < points.length; ++l)
					{
						double slopeJ = source.slopeTo(points[j]);
						double slopeK = source.slopeTo(points[k]);
						double slopeL = source.slopeTo(points[l]);

						if (slopeJ == slopeK && slopeJ == slopeL)
						{
							sorted[0] = points[i];
							sorted[1] = points[j];
							sorted[2] = points[k];
							sorted[3] = points[l];

							sort(sorted);

							sorted[0].drawTo(sorted[3]);

							for (int p = 0; p < sorted.length; ++p)
							{
								System.out.print(sorted[p].toString());

								if (p + 1 < sorted.length)
								{
									System.out.print(" -> ");
								}
							}

							System.out.println();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args)
	{
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		In input = new In(args[0]);

		int N = input.readInt();

		Point[] points = new Point[N];

		for (int i = 0; i < N; ++i)
		{
			int x = input.readInt();
			int y = input.readInt();

			points[i] = new Point(x, y);

			points[i].draw();
		}

		find(points);
	}
}
