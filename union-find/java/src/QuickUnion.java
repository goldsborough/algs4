/**
 * Created by petergoldsborough on 08/12/15.
 */

public class QuickUnion extends UnionFind
{
    QuickUnion(int N)
    {
        super(N);
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
}
