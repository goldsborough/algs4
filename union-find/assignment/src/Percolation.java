/**
 * Created by petergoldsborough on 08/13/15.
 */

public class Percolation
{
    public Percolation(int N)
    {
        if (N < 1)
        {
            throw new IllegalArgumentException("N must be at least 1!");
        }

        this.N = N;

        int size = N * N;

        sites = new Site[N][N];

        uf1 = new WeightedQuickUnionUF(size + 2);

        uf2 = new WeightedQuickUnionUF(size + 1);

        top = size;

        bottom = size + 1;

        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                sites[i][j] = new Site(i, j);
            }
        }
    }

    public void open(int i, int j)
    {
        assertIndicesAreValid(i, j);

        sites[i - 1][j - 1].open();
    }

    public boolean isOpen(int i, int j)
    {
        assertIndicesAreValid(i, j);

        return sites[i - 1][j - 1].isOpen;
    }

    public boolean isFull(int i, int j)
    {
        assertIndicesAreValid(i, j);

        return uf2.connected(index(i, j), top);
    }

    public boolean percolates()
    {
        return uf1.connected(bottom, top);
    }

    public static void main(String[] args)
    {
        Percolation p = new Percolation(3);

        p.open(3, 1);

        p.open(1, 3);

        p.open(2, 3);

        p.open(3, 3);

        System.out.println(p.percolates());

        System.out.println(p.isFull(3, 1));

    }

    private void assertIndicesAreValid(int i, int j)
    {
        if (i < 1 || i > N)
        {
            throw new IndexOutOfBoundsException("Row index '" + i + "' is out of bounds!");
        }

        if (j < 1 || j > N)
        {
            throw new IndexOutOfBoundsException("Column index '" + j + "' is out of bounds!");
        }
    }

    private int index(int i, int j)
    {
        return (i - 1) * N + (j - 1);
    }

    private class Site
    {
        Site(int i, int j)
        {
            isOpen = false;

            row = i;

            column = j;

            index = row * N + column;

            left = (column > 0) ? index - 1 : -1;

            right = (column + 1 < N) ? index + 1 : -1;

            above = (row > 0) ? index - N : top;

            below = (row + 1 < N) ? index + N : bottom;
        }

        public void open()
        {
            if (isOpen) return;

            isOpen = true;

            if (above == top || sites[row - 1][column].isOpen)
            {
                uf1.union(index, above);
                uf2.union(index, above);
            }

            if (below == bottom || sites[row + 1][column].isOpen)
            {
                uf1.union(index, below);

                if (below != bottom) uf2.union(index, below);
            }

            if (left != -1 && sites[row][column - 1].isOpen)
            {
                uf1.union(index, left);
                uf2.union(index, left);
            }

            if (right != -1 && sites[row][column + 1].isOpen)
            {
                uf1.union(index, right);
                uf2.union(index, right);
            }
        }

        public boolean isOpen;

        public int row;

        public int column;

        public int index;

        public int left;

        public int right;

        public int above;

        public int below;
    };

    private WeightedQuickUnionUF uf1;

    private WeightedQuickUnionUF uf2;

    private Site[][] sites;

    private int bottom;

    private int top;

    private int N;
}
