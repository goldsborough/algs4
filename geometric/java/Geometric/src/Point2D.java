import java.lang.reflect.Constructor;

/**
 * Created by petergoldsborough on 10/04/15.
 */

public class Point2D<Numeric extends Number & Comparable<Numeric>> implements MultiDimensionalPoint<Numeric>
{
	public Point2D(Numeric x, Numeric y)
	{
		this.x = x;
		this.y = y;
	}

	public Point2D clone()
	{
		return new Point2D<>(x, y);
	}

	public Numeric dimension(Integer k)
	{
		return k == 0 ? x : y;
	}

	public void dimension(Integer k, Numeric value)
	{
		if (k == 0) x = value;

		else y = value;
	}

	public Double distanceTo(MultiDimensionalPoint other)
	{
		Double deltaX = x.doubleValue() - other.dimension(0).doubleValue();
		Double deltaY = y.doubleValue() - other.dimension(1).doubleValue();

		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	public String toString()
	{
		return "(" + x.toString() + ", " + y.toString() + ")";
	}

	Numeric x;
	Numeric y;
}
