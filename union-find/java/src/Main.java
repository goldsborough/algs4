/**
 * Created by petergoldsborough on 08/12/15.
 */

import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter N: ");

        int N = scanner.nextInt();

        UnionFind uf = new WeightedQuickUnion(N);

        for ( ; ; )
        {
            int p = scanner.nextInt();

            int q = scanner.nextInt();

            if (uf.connected(p, q))
            {
                System.out.println("Already connected!");
            }

            else
            {
                uf.union(p, q);

                System.out.printf("New union: %d -> %d
", p, q);
            }

            uf.print();
        }
    }
};

