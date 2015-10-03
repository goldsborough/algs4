import java.util.ArrayList;

/**
 * Created by petergoldsborough on 10/03/15.
 */

public class IntersectionSearch
{
	public static class Point implements Comparable<Point>
	{
		Point(Integer x, Integer y)
		{
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point other)
		{
			if (this.x < other.x) return -1;

			else if (this.x > other.x) return +1;

			else if (this.y > other.y) return +1;

			else if (this.y < other.y) return -1;

			else return 0;
		}

		public final Integer x;
		public final Integer y;

		public Line line;
	}

	public static class Line
	{
		Line(Point start, Point end)
		{
			this.start = start;
			this.start.line = this;

			this.end = end;
			this.end.line = this;
		}

		public final Point start;
		public final Point end;
	}

	public static void main(String[] args)
	{
		ArrayList<Line> lines = new ArrayList<>();

		lines.add(new Line(new Point(0, 1), new Point(5, 1)));
		lines.add(new Line(new Point(3, 2), new Point(6, 2)));

		lines.add(new Line(new Point(1, 0), new Point(1, 4)));
		lines.add(new Line(new Point(4, 0), new Point(4, 3)));

		for (Point point : IntersectionSearch.find(lines))
		{
			System.out.printf("(%d, %d) ", point.x, point.y);
		}
	}

	public static Iterable<Point> find(ArrayList<Line> lines)
	{
		ArrayList<Point> points = new ArrayList<>();

		for (Line line : lines)
		{
			points.add(line.start);
			points.add(line.end);
		}

		points.sort(Point::compareTo);

		ArrayList<Point> intersections = new ArrayList<>();

		BinarySearchTree1D tree = new BinarySearchTree1D();

		boolean skip = false;

		for (Point point : points)
		{
			if (skip)
			{
				skip = false;
				continue;
			}

			if (isVertical(point.line))
			{
				for (Integer y : tree.rangeSearch(point.y, point.line.end.y))
				{
					intersections.add(new Point(point.x, y));
				}

				skip = true;
			}

			else if (point.equals(point.line.start))
			{
				tree.insert(point.y);
			}

			else tree.erase(point.y);
		}


		return intersections;
	}

	public static boolean isVertical(Line line)
	{
		return line.start.x.equals(line.end.x);
	}


}
