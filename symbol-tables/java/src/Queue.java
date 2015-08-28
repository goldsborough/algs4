import java.util.Iterator;
import java.util.NoSuchElementException;

class Queue<T> implements Iterable<T>
{
	public void enqueue(T item)
	{
		Node node = new Node(item);

		if (first == null) first = node;

		else last.next = node;

		last = node;

		++size;
	}

	public T dequeue()
	{
		Node node = first;

		first = first.next;

		if (first == null) last = null;

		T item = node.item;

		node = null;

		--size;

		return item;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public Iterator<T> iterator()
	{
		return new Itr();
	}

	protected class Node
	{
		public Node(T item)
		{
			this.item = item;
		}

		T item;

		Node next;
	}

	protected class Itr implements Iterator<T>
	{
		public boolean hasNext()
		{
			return node != null;
		}

		public T next()
		{
			if (node == null) throw new NoSuchElementException();

			T item = node.item;

			node = node.next;

			return item;
		}

		Node node = first;
	}

	Node first = null;
	Node last = null;

	int size = 0;
}