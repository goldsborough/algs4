import java.util.ArrayList;
import java.util.Collection;

import edu.princeton.cs.algs4.*;

/**
 * Created by petergoldsborough on 10/05/15.
 */

/*
   public           boolean isEmpty()                      // is the set empty?
   public               int size()                         // number of points in the set
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   public           boolean contains(Point2D p)            // does the set contain point p?
   public              void draw()                         // draw all points to standard draw
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty

   public static void main(String[] args)                  // unit testing of the methods (optional)
 */

public class KdTree
{
	public KdTree()
	{
		root = null;
		size = 0;
	}

	public void insert(Point2D point)
	{
		assertArgument(point);

		root = insert(root, root, point, Coordinate.X);

		++size;
	}

	public boolean contains(Point2D point)
	{
		assertArgument(point);

		return contains(root, point, Coordinate.X);
	}

	public Iterable<Point2D> range(RectHV rect)
	{
		assertArgument(rect);

		ArrayList<Point2D> points = new ArrayList<>();

		if (root != null)
		{
			range(root, rect, Coordinate.X, points);
		}

		return points;
	}

	public Point2D nearest(Point2D point)
	{
		assertArgument(point);

		if (root == null) return null;

		Champion champion = new Champion();

		nearest(root, point, Coordinate.X, champion);

		return champion.point;
	}

	public void draw()
	{
		draw(root, Coordinate.X);
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	private enum Coordinate { X, Y }

	private class Node
	{
		public Node(Node root, Point2D point, Coordinate coordinate)
		{
			this.point = point;

			if (root == null) rectangle = new RectHV(0, 0, 1, 1);

			else
			{
				coordinate = other(coordinate);

				int comparison = compare(point, root.point, coordinate);

				double x, y;

				boolean isX = coordinate == Coordinate.X;

				if (comparison < 0)
				{
					x = isX ? root.point.x() : root.rectangle.xmax();
					y = isX ? root.rectangle.ymax() : root.point.y();

					rectangle = new RectHV(
							root.rectangle.xmin(),
							root.rectangle.ymin(),
							x,
							y
					);
				}

				else if (comparison > 0)
				{
					x = isX ? root.point.x() : root.rectangle.xmin();
					y = isX ? root.rectangle.ymin() : root.point.y();

					rectangle = new RectHV(
							x,
							y,
							root.rectangle.xmax(),
							root.rectangle.ymax()
					);
				}

				else rectangle = root.rectangle;
			}

		}

		public Node(Point2D point, RectHV rectangle)
		{
			this.point = point;
			this.rectangle = rectangle;
		}

		public final Point2D point;

		public final RectHV rectangle;

		public Node left = null;
		public Node right = null;
	}

	private static class Champion
	{
		public Champion()
		{
			this(null, Double.POSITIVE_INFINITY);
		}

		public Champion(Point2D point, double distance)
		{
			this.point = point;
			this.distance = distance;
		}

		public Point2D point;
		public double distance;
	}

	private static final class Pair<T, U>
	{
		public Pair(T first, U second)
		{
			this.first = first;
			this.second = second;
		}

		public final T first;
		public final U second;
	}

	private Node insert(Node root, Node node, Point2D point, Coordinate coordinate)
	{
		if (node == null) return new Node(root, point, coordinate);

		int comparison = compare(point, node.point, coordinate);

		if (comparison < 0)
		{
			node.left = insert(node, node.left, point, other(coordinate));
		}

		else if (comparison > 0)
		{
			node.right = insert(node, node.right, point, other(coordinate));
		}

		else --size; // Incremented again at the end (delta = 0)

		return node;
	}

	private boolean contains(Node node, Point2D point, Coordinate coordinate)
	{
		if (node == null) return false;

		int comparison = compare(point, node.point, coordinate);

		if (comparison < 0)
		{
			return contains(node.left, point, other(coordinate));
		}

		else if (comparison > 0)
		{
			return contains(node.right, point, other(coordinate));
		}

		else return true;
	}

	private void range(Node node,
	                   RectHV rectangle,
	                   Coordinate coordinate,
	                   Collection<Point2D> points)
	{
		if (node == null) return;

		Pair<Integer, Integer> comparison = compare(node.point, rectangle, coordinate);

		if (comparison.first <= 0)
		{
			range(node.left, rectangle, other(coordinate), points);
		}

		if (rectangle.contains(node.point))
		{
			points.add(node.point);
		}

		if (comparison.second <= 0)
		{
			range(node.right, rectangle, other(coordinate), points);
		}
	}

	private void nearest(Node node,
	                     Point2D point,
	                     Coordinate coordinate,
	                     Champion champion)
	{
		if (node == null) throw new NullPointerException();

		double distance = node.point.distanceSquaredTo(point);

		if (distance < champion.distance)
		{
			champion.point = node.point;
			champion.distance = distance;
		}

		Pair<Node, Node> closer = findCloserSide(node, point, coordinate);

		if (closerPointPossible(closer.first, point, champion))
		{
			nearest(closer.first, point, other(coordinate), champion);
		}

		if (closerPointPossible(closer.second, point, champion))
		{
			nearest(closer.second, point, other(coordinate), champion);
		}
	}

	private Pair<Node, Node> findCloserSide(Node node, Point2D point, Coordinate coordinate)
	{
		if (coordinate == Coordinate.X)
		{
			if (point.x() - node.point.x() > 0)
			{
				return new Pair<>(node.right, node.left);
			}
		}

		else if (point.y() - node.point.y() > 0)
		{
			return new Pair<>(node.right, node.left);
		}

		return new Pair<>(node.left, node.right);
	}

	private boolean closerPointPossible(Node node, Point2D point, Champion champion)
	{
		if (node == null) return false;

		return node.rectangle.distanceSquaredTo(point) < champion.distance;
	}

	private int compare(Point2D first, Point2D second, Coordinate coordinate)
	{
		if (coordinate == Coordinate.X)
		{
			int result = compare(first.x(), second.x());

			if (result != 0) return result;

			return compare(first.y(), second.y());
		}

		else
		{
			int result = compare(first.y(), second.y());

			if (result != 0) return result;

			return compare(first.x(), second.x());
		}
	}

	private Pair<Integer, Integer> compare(Point2D point, RectHV rectangle, Coordinate coordinate)
	{
		int minimumCompare;
		int maximumCompare;

		if (coordinate == Coordinate.X)
		{
			double x = point.x();

			minimumCompare = compare(rectangle.xmin(), x);
			maximumCompare = compare(x, rectangle.xmax());
		}

		else
		{
			double y = point.y();

			minimumCompare = compare(rectangle.ymin(), y);
			maximumCompare = compare(y, rectangle.ymax());
		}

		return new Pair<>(minimumCompare, maximumCompare);
	}

	private int compare(double first, double second)
	{
		if (first < second) return -1;

		else if (first > second) return +1;

		else return 0;
	}

	private Coordinate other(Coordinate coordinate)
	{
		if (coordinate == Coordinate.X) return Coordinate.Y;

		else return Coordinate.X;
	}

	private void draw(Node node, Coordinate coordinate)
	{
		if (node == null) return;

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);

		node.point.draw();

		StdDraw.setPenRadius();

		if (coordinate == Coordinate.X)
		{
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(
					node.point.x(),
					node.rectangle.ymin(),
					node.point.x(),
					node.rectangle.ymax()
			);
		}

		else
		{
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(
					node.rectangle.xmin(),
					node.point.y(),
					node.rectangle.xmax(),
					node.point.y()
			);
		}

		coordinate = other(coordinate);

		draw(node.left, coordinate);
		draw(node.right, coordinate);
	}

	private void assertPoint(Point2D point)
	{
		assertArgument(point);

		if (point.x() < 0 || point.x() > 1)
		{
			throw new IllegalArgumentException("x-coordinate of point not in unit square!");
		}

		if (point.y() < 0 || point.y() > 1)
		{
			throw new IllegalArgumentException("y-coordinate of point not in unit square!");
		}
	}

	private void assertArgument(Object object)
	{
		if (object == null)
		{
			throw new NullPointerException("Argument cannot be null!");
		}
	}

	private int memoryUsage()
	{
		int constant = 8 + 4; // root reference + size integer

		// Node cost
		// 16 bytes of object overhead
		// 8 bytes for reference to enclosing class
		// 8 bytes for node reference
		// 32 bytes for the point
		// 32 bytes for the rectangle
		// 8 + 8 bytes for the two left/right node references
		return constant + size * 112;
	}

	private double runningTime()
	{
		return Math.log(size) / Math.log(2);
	}

	private Node root;

	private int size;


	public static void main(String[] args)
	{
		KdTree tree = new KdTree();

		tree.insert(new Point2D(.5, .5));
		tree.insert(new Point2D(.5, .7));

		for (Point2D point : tree.range(new RectHV(0, 0, 1, 1)))
		{
			System.out.println(point);
		}
	}
}
