/**
 * Created by petergoldsborough on 08/18/15.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	public FastCollinearPoints(Point[] source)
	{
		if (source == null) throw new java.lang.NullPointerException();

		Point[] points = new Point[source.length];

		for (int i = 0; i < points.length; ++i)
		{
			points[i] = source[i];
		}

		check(points);

		find(points);
	}

	public int numberOfSegments()
	{
		return segments.length;
	}

	public LineSegment[] segments()
	{
		LineSegment[] copy = new LineSegment[segments.length];

		for (int i = 0; i < segments.length; ++i)
		{
			copy[i] = segments[i];
		}

		return copy;
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

		StdOut.println(brute.numberOfSegments());


		for (LineSegment segment : brute.segments())
		{
			StdOut.println(segment);
			segment.draw();
		}
	}

	private void find(Point[] points)
	{
		Queue<LineSegment> queue = new Queue<>();

		HashMap<Double, ArrayList<Point>> slopes = new HashMap<>();

		for (int p = 0; p < points.length; ++p)
		{
			Point source = points[p];

			int q = p + 1;

			Arrays.sort(points, q, points.length, source.slopeOrder());

			while (q < points.length - 2)
			{
				double slope = source.slopeTo(points[q]);

				int smallest = p;
				int largest = p;
				int count = 0;

				for ( ; q < points.length && source.slopeTo(points[q]) == slope; ++q)
				{
					if (points[q].compareTo(points[smallest]) < 0) smallest = q;

					else if (points[q].compareTo(points[largest]) > 0) largest = q;

					++count;
				}

				if (count >= 3)
				{
					if (! alreadySeen(slopes, source, slope))
					{
						LineSegment segment = new LineSegment(points[smallest], points[largest]);

						queue.enqueue(segment);

						ArrayList<Point> slopePoints = slopes.get(slope);

						if (slopePoints == null)
						{
							slopePoints = new ArrayList<>();

							slopes.put(slope, slopePoints);
						}

						slopePoints.add(source);
					}
				}
			}
		}

		segments = new LineSegment[queue.size()];

		for (int i = 0; i < segments.length; ++i)
		{
			segments[i] = queue.dequeue();
		}
	}

	private static boolean alreadySeen(HashMap<Double, ArrayList<Point>> slopes, Point p, double slope)
	{
		ArrayList<Point> previous = slopes.get(slope);

		if (previous == null) return false;

		for (Point q : previous)
		{
			if (p.slopeTo(q) == slope) return true;
		}

		return false;
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