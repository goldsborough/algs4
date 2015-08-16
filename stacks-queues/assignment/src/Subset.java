/**
 * Created by petergoldsborough on 08/16/15.
 */

public class Subset
{
	public static void main(String[] args)
	{
		RandomizedQueue<String> queue = new RandomizedQueue<>();

		int k = Integer.parseInt(args[0]);

		while(k-- > 0) queue.enqueue(StdIn.readString());

		while(! queue.isEmpty()) StdOut.println(queue.dequeue());
	}
}
