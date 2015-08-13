/**
 * Created by petergoldsborough on 08/12/15.
 */

public abstract class UnionFind
{
    UnionFind(int N)
    {
        _N = N;

        _id = new int[N];

        for (int i = 0; i< N; ++i)
        {
            _id[i] = i;
        }
    }

    abstract public void union(int p, int q);

    abstract public boolean connected(int p, int q);

    public int N()
    {
        return _N;
    }

    public void print()
    {
        for (int i : _id) System.out.printf("%d ", i);

        System.out.println();
    }

    protected int[] _id;

    protected int _N;
}
