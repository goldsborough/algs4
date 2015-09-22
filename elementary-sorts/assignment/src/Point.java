/**
 * Created by petergoldsborough on 08/18/15.
 */

import java.util.Comparator;

/*
public class Point implements Comparable<Point> {
   public Point(int x, int y)                         // constructs the point (x, y)

   public   void draw()                               // draws this point
   public   void drawTo(Point that)                   // draws the line segment from this point to that point
   public String toString()                           // string representation

   public               int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
   public            double slopeTo(Point that)       // the slope between this point and that point
   public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
}
 */

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point>
{
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void draw()
	{
		StdDraw.point(x, y);
	}

	public void drawTo(Point that)
	{
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	public int compareTo(Point that)
	{
		if (that == null) throw new NullPointerException();

		if ((this.y < that.y) ||
		    (this.y == that.y && this.x < that.x)) return -1;

		if ((this.y > that.y) ||
			(this.y == that.y && this.x > that.x)) return +1;

		return 0;
	}

	public double slopeTo(Point to)
	{
		if (to == null) throw new NullPointerException();

		if (this.x == to.x && this.y == to.y) return Double.NEGATIVE_INFINITY;

		if (to.x == this.x) return Double.POSITIVE_INFINITY;

		if (to.y == this.y) return 0 / +1;

		double dy = to.y - this.y;

		return dy / (to.x - this.x);
	}

	public Comparator<Point> slopeOrder()
	{
		return new SlopeComparator();
	}

	private class SlopeComparator implements Comparator<Point>
	{
		@Override
		public int compare(Point first, Point second)
		{
			double slope1 = slopeTo(first);
			double slope2 = slopeTo(second);

			if (slope1 < slope2) return -1;

			if (slope1 > slope2) return +1;

			return 0;
		}
	};

	private final int x;
	private final int y;
};