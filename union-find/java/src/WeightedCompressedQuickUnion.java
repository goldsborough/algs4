/**
 * Created by petergoldsborough on 08/12/15.
 */

public class WeightedCompressedQuickUnion extends WeightedQuickUnion
{
    WeightedCompressedQuickUnion(int N)
    {
        super(N);
    }

    protected int _root(int i)
    {
        while (i != _id[i])
        {
            // Have root point to grandparent
            _id[i] = _id[_id[i]];

            i = _id[i];
        }

        return i;
    }
}
