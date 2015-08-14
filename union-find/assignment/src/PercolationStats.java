/**
 * Created by petergoldsborough on 08/13/15.
 */

import java.lang.Math;

public class PercolationStats 
{
    public PercolationStats(int N, int T)
	{
        if (N < 1)
        {
            throw new IllegalArgumentException("N must be greater 0!");
        }

        if (T < 1)
        {
            throw new IllegalArgumentException("T must be greater 0!");
        }

        this.T = T;

        ratios = new double[T];

        int size = N * N;

        for (int i = 0; i < T; ++i)
        {
            ratios[i] = _sample(N) / size;
        }
	}


    public double mean()
	{
        return StdStats.mean(ratios);
    }


    public double stddev()
	{
        return StdStats.stddev(ratios);
	}


    public double confidenceLo()
    {
        return mean() - ((1.96 * stddev()) / Math.sqrt(T));
    }

    public double confidenceHi()
    {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }

    public static void main(String[] args)
	{
        int N = Integer.parseInt(args[0]);

        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);

        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLo());
        System.out.println(ps.confidenceHi());
    }

    private double _sample(int N)
    {
        Percolation p = new Percolation(N);

        int count = 0;

        while (! p.percolates())
        {
            int row = StdRandom.uniform(1, N + 1);

            int column = StdRandom.uniform(1, N + 1);

            if (! p.isOpen(row, column))
            {
                p.open(row, column);

                count++;
            }
        }

        return count;
    }

    private double[] ratios;

    private int T;
};
