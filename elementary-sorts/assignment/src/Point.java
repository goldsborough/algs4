import java.util.Comparator;

/**
 * Created by petergoldsborough on 08/18/15.
 */

/*
public class Point implements Comparable<Point> {
   public final Comparator<Point> SLOPE_ORDER;        // compare points by slope to this point

   public Point(int x, int y)                         // construct the point (x, y)

   public   void draw()                               // draw this point
   public   void drawTo(Point that)                   // draw the line segment from this point to that point
   public String toString()                           // string representation

   public    int compareTo(Point that)                // is this point lexicographically smaller than that point?
   public double slopeTo(Point that)                  // the slope between this point and that point
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
		return "(" + (int)x + ", " + (int)y + ")";
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

	public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();

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