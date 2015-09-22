/**
 * Created by petergoldsborough on 08/18/15.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.NoSuchElementException;

/*
public class FastCollinearPoints {
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   public           int numberOfSegments()        // the number of line segments
   public LineSegment[] segments()                // the line segments
}

 */

public class FastCollinearPoints
{
	public FastCollinearPoints(Point[] points)
	{
		if (points == null) throw new java.lang.NullPointerException();

		check(points);

		find(points);
	}

	public int numberOfSegments()
	{
		return segments.length;
	}

	public LineSegment[] segments()
	{
		return segments;
	}

	public static void main(String[] args)
	{
		In input = new In(args[0]);

		int N = input.readInt();

		Point[] points = new Point[N];

		for (int i = 0; i < N; ++i)
		{
			int x = input.readInt();
			int y = input.readInt();

			points[i] = new Point(x, y);
		}

		StdDraw.show(0);

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		for (Point p : points) p.draw();

		StdDraw.show();

		FastCollinearPoints brute = new FastCollinearPoints(points);


		for (LineSegment segment : brute.segments())
		{
			StdOut.println(segment);
			segment.draw();
		}
	}

	private void find(Point[] points)
	{
		Queue<LineSegment> queue = new Queue<>();

		for (int p = 0; p < points.length; ++p)
		{
			Point source = points[p];

			int q = p + 1;

			Arrays.sort(points, q, points.length, source.slopeOrder());

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

						queue.enqueue(new LineSegment(points[p], points[p + count]));
					}
				}

				q = i;
			}
		}

		segments = new LineSegment[queue.size()];

		for (int i = 0; i < segments.length; ++i)
		{
			segments[i] = queue.dequeue();
		}
	}

	private static void swap(Point[] points, int i, int j)
	{
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}

	private void check(Point[] points)
	{
		Arrays.sort(points);

		for (int i = 0, end = points.length - 1; i < end; ++i)
		{
			if (points[i] == null)
			{
				throw new NullPointerException();
			}

			if (points[i].compareTo(points[i + 1]) == 0)
			{
				throw new IllegalArgumentException();
			}
		}
	}

	private LineSegment[] segments;
}

class Queue<T>
{
	public void enqueue(T item)
	{
		if (item == null) throw new NullPointerException();

		Node node = new Node(item);

		if (last != null) last.next = node;

		last = node;

		if (first == null) first = node;

		++size;
	}

	public T dequeue()
	{
		if (size == 0) throw new NoSuchElementException();

		Node node = first;

		first = first.next;

		T item = node.item;

		node = null;

		--size;

		return item;

	}

	public T front()
	{
		if (size == 0) throw new NoSuchElementException();

		return first.item;
	}

	public T back()
	{
		if (size == 0) throw new NoSuchElementException();

		return last.item;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	private class Node
	{
		Node(T item)
		{
			this.item = item;
		}

		Node next = null;

		T item;
	}

	private Node first = null;

	private Node last = null;

	private int size = 0;

}