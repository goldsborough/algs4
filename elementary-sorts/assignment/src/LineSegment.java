/**
 * Created by petergoldsborough on 09/20/15.
 */

import edu.princeton.cs.algs4.StdDraw;

/*
public class LineSegment {
   public LineSegment(Point p, Point q)        // constructs the line segment between points p and q
   public   void draw()                        // draws this line segment
   public String toString()                    // string representation
}

 */

public class LineSegment
{
	public LineSegment(Point p, Point q)
	{
		this.p = p;
		this.q = q;
	}

	public void draw()
	{
		p.drawTo(q);
	}

	public String toString()
	{
		return p + " -> " + q;
	}


	private final Point p;
	private final Point q;
}
