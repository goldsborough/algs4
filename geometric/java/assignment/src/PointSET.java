import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Created by petergoldsborough on 10/04/15.
 */

/*
public class PointSET {
   public         PointSET()                               // construct an empty set of points
   public           boolean isEmpty()                      // is the set empty?
   public               int size()                         // number of points in the set
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   public           boolean contains(Point2D p)            // does the set contain point p?
   public              void draw()                         // draw all points to standard draw
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty

   public static void main(String[] args)                  // unit testing of the methods (optional)
}
 */

public class PointSET
{
	public PointSET()
	{
		set = new TreeSet<>();
	}

	public boolean isEmpty()
	{
		return set.isEmpty();
	}

	public int size()
	{
		return set.size();
	}

	public void insert(Point2D point)
	{
		assertArgument(point);

		set.add(point);
	}

	public boolean contains(Point2D point)
	{
		assertArgument(point);

		return set.contains(point);
	}

	public void draw()
	{
		for (Point2D point : set) point.draw();
	}

	public Iterable<Point2D> range(RectHV rect)
	{
		assertArgument(rect);

		ArrayList<Point2D> points = new ArrayList<>(set.size());

		for (Point2D point : set)
		{
			if (rect.contains(point)) points.add(point);
		}

		return points;
	}

	public Point2D nearest(Point2D target)
	{
		assertArgument(target);

		Point2D champion = null;

		double best = Double.POSITIVE_INFINITY;

		for (Point2D point : set)
		{
			double distance = point.distanceTo(target);

			if (champion == null || distance < best)
			{
				champion = point;
				best = distance;
			}
		}

		return champion;
	}

	private void assertArgument(Object object)
	{
		if (object == null)
		{
			throw new NullPointerException("Argument cannot be null!");
		}
	}

	public static void main(String[] args)
	{
		PointSET set = new PointSET();

		set.insert(new Point2D(1, 1));
		set.insert(new Point2D(2, 2));
		set.insert(new Point2D(3, 3));

		System.out.println(set.nearest(new Point2D(1.5, 1.5)).toString());
	}

	private final TreeSet<Point2D> set;
}
