/**
 * Created by petergoldsborough on 08/12/15.
 */

public class QuickFind implements UnionFind
{
    QuickFind(int N)
    {
        this.N = N;

        _id = new int[N];

        for (int i = 0; i < N; ++i)
        {
            _id[i] = i;
        }
    }

    public void union(int p, int q)
    {
        if (! connected(p, q))
        {
            int p_id = _id[p];

            int q_id = _id[q];

            for (int i = 0; i < N; ++i)
            {
                if (_id[i] == p_id) _id[i] = q_id;
            }
        }
    }

    public boolean connected(int p, int q)
    {
        return _id[p] == _id[q];
    }

    private int N;

    private int[] _id;
};
