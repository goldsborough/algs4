import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Created by petergoldsborough on 10/04/15.
 */



public class KdTree<Dimension extends Number & Comparable<Dimension>, Point extends MultiDimensionalPoint<Dimension>>
{
	public static void main(String[] args)
	{
		Point2D<Double> lowerLeft = new Point2D<>(0.0, 0.0);
		Point2D<Double> upperRight = new Point2D<>(1.0, 1.0);

		KdTree<Double, Point2D<Double>> tree = new KdTree<>(2, lowerLeft, upperRight);

		tree.insert(new Point2D<>(.5, .5));

		tree.insert(new Point2D<>(.2, .1));

		tree.insert(new Point2D<>(.05, .3));

		tree.insert(new Point2D<>(.3, .6));

		tree.insert(new Point2D<>(.6, .6));

		tree.insert(new Point2D<>(.0, .0));

		tree.erase(new Point2D<>(.0, .0));

		System.out.println(tree.nearest(new Point2D<>(.02, .15)));

	}

	public KdTree(Integer dimensions, Point first, Point second)
	{
		this.dimensions = dimensions;

		this.globalFigure = new Figure(first, second);
	}

	public void insert(Point point)
	{
		root = insert(root, root, point, 0);
	}

	public boolean contains(Point point)
	{
		return contains(root, point, 0);
	}

	public void erase(Point point)
	{
		erase(root, point, 0);
	}

	public Integer rank(Point point)
	{
		return rank(root, point, 0);
	}

	public Integer rank(Point lower, Point upper)
	{
		return rank(root, lower, upper, 0);
	}

	public Iterable<Point> search(Point lower, Point upper)
	{
		ArrayList<Point> result = new ArrayList<>();

		search(root, lower, upper, 0, result);

		return result;
	}

	public Point nearest(Point point)
	{
		if (root == null)
		{
			throw new NoSuchElementException("Tree is empty!");
		}

		Champion champion = new Champion();

		nearest(root, point, 0, champion);

		return champion.node.point;
	}

	public Integer dimensions()
	{
		return dimensions;
	}

	public Integer size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	private class Figure
	{
		public Figure(Node root, Point point, Integer level)
		{
			if (root == null)
			{
				first = (Point) globalFigure.first.clone();
				second = (Point) globalFigure.second.clone();
				this.center = (Point) globalFigure.center.clone();
			}

			else
			{
				if (--level < 0) level = dimensions - level;

				Integer compare = root.compareTo(point, level);

				if (compare > 0)
				{
					this.first = (Point) root.figure.first.clone();
					this.second = (Point) root.figure.second.clone();

					this.second.dimension(0, root.point.dimension(0));
				}

				else if (compare < 0)
				{
					this.first = (Point) root.figure.first.clone();
					this.second = (Point) root.figure.second.clone();

					this.first.dimension(0, root.point.dimension(0));
				}

				else
				{
					this.first = (Point) point.clone();
					this.second = (Point) root.figure.second.clone();

					this.first.dimension(0, root.figure.first.dimension(0));
				}

				this.center = computeCenter();
			}
		}

		public Figure(Point first, Point second)
		{
			this.first = first;
			this.second = second;
			this.center = computeCenter();
		}

		public boolean contains(Point point)
		{
			for (int k = 0; k < dimensions; ++k)
			{
				Dimension coordinate = point.dimension(k);

				if (coordinate.compareTo(first.dimension(k)) < 0 &&
					coordinate.compareTo(second.dimension(k)) > 0)
				{
					return false;
				}
			}

			return true;
		}

		private Point computeCenter()
		{
			Point point = (Point) first.clone();

			for (int k = 0; k < dimensions; ++k)
			{
				Double mean = addDimensions(first, second, k) / 2.0;

				point.dimension(k, (Dimension) mean);
			}

			return point;
		}

		public final Point first;
		public final Point second;
		public final Point center;
	}

	private class Champion
	{
		public Champion()
		{
			this(null, Double.POSITIVE_INFINITY);
		}

		public Champion(Node node, Double distance)
		{
			this.node = node;
			this.distance = distance;
		}

		public Node node;
		public Double distance;

	}

	private class Node
	{
		public Node(Node root, Point point, Integer level)
		{
			this.point = point;
			this.figure = new Figure(root, point, level);

			this.left = null;
			this.right = null;
		}

		public int compareTo(Point other, Integer level)
		{
			Integer initial = level % dimensions;
			Integer k = initial;
			Integer compare;

			do
			{
				compare = this.point.dimension(k).compareTo(other.dimension(k));

				if (compare != 0) return compare;

				k = ++k % dimensions;
			}

			while (k.compareTo(initial) != 0);

			return 0;
		}

		public void resize()
		{
			size = 1;

			if (left != null) size += left.size;

			if (right != null) size += right.size;
		}

		public final Point point;

		public final Figure figure;

		public Node left;
		public Node right;

		public Integer size = 1;
	}

	private class Pair<T, U>
	{
		public Pair(T first, U second)
		{
			this.first = first;
			this.second = second;
		}

		public final T first;
		public final U second;
	}

	private static class PointError extends NoSuchElementException
	{
		public PointError(String point)
		{
			super("No such point '" + point + "'!");
		}
	}

	private Node insert(Node root, Node node, Point point, Integer level)
	{
		if (node == null)
		{
			++size;

			return new Node(root, point, level);
		}

		Integer compare = node.compareTo(point, level);

		if (compare > 0)
		{
			node.left = insert(root, node.left, point, ++level);
		}

		else if (compare < 0)
		{
			node.right = insert(root, node.right, point, ++level);
		}

		else /* Ignore */;

		node.resize();

		return node;
	}

	private boolean contains(Node node, Point point, Integer level)
	{
		if (node == null) return false;

		Integer compare = node.compareTo(point, level);

		if (compare > 0)
		{
			return contains(node.left, point, ++level);
		}

		else if (compare < 0)
		{
			return contains(node.right, point, ++level);
		}

		else return true;
	}

	private Node erase(Node node, Point point, Integer level)
	{
		if (node == null) throw new PointError(point.toString());

		Integer compare = node.compareTo(point, level);

		if (compare > 0)
		{
			node.left = erase(node.left, point, ++level);
		}

		else if (compare < 0)
		{
			node.right = erase(node.right, point, ++level);
		}

		else
		{
			if (node.left == null) return node.right;

			if (node.right == null) return node.left;

			Node previous = null;
			Node successor = node.right;

			while (successor.left != null)
			{
				previous = successor;
				previous.size--;

				successor = successor.left;
			}

			swap(node, successor);

			if (previous == null) successor.right = node.right;

			else previous.left = node.right;

			node = null;

			--size;

			// For the resize()
			node = successor;
		}

		node.resize();

		return node;
	}

	private Integer rank(Node node, Point point, Integer level)
	{
		if (node == null) return 0;

		Integer compare = node.compareTo(point, level);

		if (compare > 0) return rank(node.left, point, ++level);

		Integer size = 1;

		if (node.left != null) size += node.left.size;

		if (compare < 0) size += rank(node.right, point, ++level);

		return size;
	}

	private Integer rank(Node node, Point lower, Point upper, Integer level)
	{
		if (node == null) return 0;

		Integer number = 0;

		Integer compareLower = node.compareTo(lower, level);
		Integer compareUpper = node.compareTo(upper, level);

		if (compareLower > 0)
		{
			number += rank(node.left, lower, upper, level + 1);
		}

		if (compareLower >= 0 && compareUpper <= 0) ++number;

		if (compareUpper < 0)
		{
			number += rank(node.right, lower, upper, level + 1);
		}

		return number;
	}

	private void search(Node node,
	                    Point lower,
	                    Point upper,
	                    Integer level,
	                    Collection<? super Point> result)
	{
		if (node == null) return;

		Integer compareLower = node.compareTo(lower, level);
		Integer compareUpper = node.compareTo(upper, level);

		if (compareLower > 0)
		{
			search(node.left, lower, upper, level + 1, result);
		}

		if (compareLower >= 0 && compareUpper <= 0)
		{
			result.add(node.point);
		}

		if (compareUpper < 0)
		{
			search(node.right, lower, upper, level + 1, result);
		}
	}

	private void nearest(Node node, Point point, Integer level, Champion champion)
	{
		if (node == null) throw new NullPointerException();

		Double distance = node.point.distanceTo(point);

		if (distance.compareTo(champion.distance) < 0)
		{
			champion.node = node;
			champion.distance = distance;
		}

		Pair<Node, Node> closer = determineCloser(node, point, level);

		if (closerPointPossible(closer.first, point, champion))
		{
			nearest(closer.first, point, level + 1, champion);
		}

		if (closerPointPossible(closer.second, point, champion))
		{
			nearest(closer.second, point, level + 1, champion);
		}

	}

	private Pair<Node, Node> determineCloser(Node node, Point point, Integer level)
	{
		if (node.left == null || node.right == null)
		{
			return new Pair<>(node.left, node.right);
		}

		Double first = node.left.figure.center.distanceTo(point);
		Double second = node.right.figure.center.distanceTo(point);

		if (first.compareTo(second) <= 0)
		{
			return new Pair<>(node.left, node.right);
		}

		else return new Pair<>(node.right, node.left);
	}

	private boolean closerPointPossible(Node node, Point point, Champion champion)
	{
		if (node == null) return false;

		if (node.figure.contains(point)) return true;

		Dimension coordinate = point.dimension(dimensions - 1);

		if (coordinate.compareTo(node.figure.second.dimension(dimensions - 1)) > 0)
		{
			Point closest = (Point) node.point.clone();
			closest.dimension(dimensions - 1, node.figure.second.dimension(dimensions - 1));

			return closest.distanceTo(point).compareTo(champion.distance) < 0;
		}

		else if (coordinate.compareTo(node.figure.first.dimension(dimensions - 1)) < 0)
		{
			Point closest = (Point) node.point.clone();
			closest.dimension(dimensions - 1, node.figure.first.dimension(dimensions - 1));

			return closest.distanceTo(point).compareTo(champion.distance) < 0;
		}

		else
		{
			Double delta = addDimensions(point, node.point, 0);

			return Math.abs(delta) < champion.distance.doubleValue();
		}
	}

	private void swap(Node first, Node second)
	{
		Node temp = first.left;

		first.left = second.left;
		second.left = temp;

		temp = first.right;

		first.right = second.right;
		second.right = temp;
	}

	private Double addDimensions(Point first, Point second, Integer k)
	{
		return first.dimension(k).doubleValue() + second.dimension(k).doubleValue();
	}


	private Node root = null;
	private Integer size = 0;

	private final Integer dimensions;
	private final Figure globalFigure;
}
