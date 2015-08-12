/**
 * Created by petergoldsborough on 08/12/15.
 */

public class QuickUnion implements UnionFind
{
    QuickUnion(int N)
    {
        _id = new int[N];
        
        for (int i = 0; i < N; ++i)
        {
            _id[i] = i;
        }
    }
    
    public void union(int p, int q)
    {
        int p_root = _root(p);

        int q_root = _root(q);

        if (p_root != q_root)
        {
            _id[_root(q)] = _root(p);
        }
    }
    
    public boolean connected(int p, int q)
    {
        return _root(p) == _root(q);
    }
    
    protected int _root(int i)
    {
        while(_id[i] != i) i = _id[i];
        
        return i;
    }

    protected int _alternative_root(int i)
    {
        return (i == _id[i]) ? i : _root(_id[i]);
    }
    
    
    protected int[] _id;
}
