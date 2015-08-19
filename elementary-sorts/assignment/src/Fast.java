/**
 * Created by petergoldsborough on 08/18/15.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

public class Fast
{
	private static void find(Point[] points)
	{
		for (int p = 0; p < points.length; ++p)
		{
			Point source = points[p];

			int q = p + 1;

			Arrays.sort(points, q, points.length, points[p].SLOPE_ORDER);

			while (q < points.length - 2)
			{
				double slope = source.slopeTo(points[q]);

				swap(points, p + 1, q);

				int i = q + 1;

				int count = 1;

				for ( ; i < points.length && source.slopeTo(points[i]) == slope; ++i)
				{
					swap(points, p + count + 1, i);

					++count;
				}

				if (count >= 3)
				{
					boolean seen = false;

					for (int j = 0; j < p; ++j)
					{
						if (points[j].slopeTo(source) == slope)
						{
							seen = true;
							break;
						}
					}

					if (! seen)
					{
						Arrays.sort(points, p, p + count + 1);

						points[p].drawTo(points[p + count]);

						for (int j = p, end = p + count; j <= end; ++j)
						{
							System.out.print(points[j].toString());

							if (j < end) System.out.print(" -> ");

							if (points[j].compareTo(source) == 0)
							{
								swap(points, j, p);
							}
						}

						System.out.println();
					}
				}

				q = i;
			}
		}
	}

	private static void swap(Point[] points, int i, int j)
	{
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
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
