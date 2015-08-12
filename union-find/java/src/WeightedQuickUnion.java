/**
 * Created by petergoldsborough on 08/12/15.
 */

public class WeightedQuickUnion extends QuickUnion
{
    WeightedQuickUnion(int N)
    {
        super(N);

        _size = new int[N];

        for (int i = 0; i < N; ++i) _size[i] = 1;
    }

    public void union(int p, int q)
    {
        int p_root = _root(p);

        int q_root = _root(q);

        if (p_root != q_root)
        {
            if (_size[p_root] > _size[q_root])
            {
                _id[q_root] = p_root;

                _size[p_root] += _size[q_root];
            }

            else
            {
                _id[p_root] = q_root;

                _size[q_root] += _size[p_root];
            }
        }

        for (int i : _id) System.out.printf("%d ", i);

        System.out.println();

        for (int i : _size) System.out.printf("%d ", i);
    }

    private int[] _size;
}
