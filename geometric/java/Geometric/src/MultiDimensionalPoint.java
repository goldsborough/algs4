/**
 * Created by petergoldsborough on 10/04/15.
 */

public interface MultiDimensionalPoint<Dimension extends Number & Comparable<Dimension>> extends Cloneable
{
	MultiDimensionalPoint clone();

	Dimension dimension(Integer k);

	void dimension(Integer k, Dimension value);

	Double distanceTo(MultiDimensionalPoint other);
}
