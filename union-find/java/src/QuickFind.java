/**
 * Created by petergoldsborough on 08/12/15.
 */

public class QuickFind extends UnionFind
{
    QuickFind(int N)
    {
        super(N);
    }

    public void union(int p, int q)
    {
        if (! connected(p, q))
        {
            int p_id = _id[p];

            int q_id = _id[q];

            for (int i = 0; i < _N; ++i)
            {
                if (_id[i] == p_id) _id[i] = q_id;
            }
        }
    }

    public boolean connected(int p, int q)
    {
        return _id[p] == _id[q];
    }
};
