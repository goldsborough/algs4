/**
 * Created by petergoldsborough on 08/15/15.
 */

public class LinkedListQueue<T>
{
    public void enqueue(T item)
    {
        Node node = new Node(item);

        _last.next = node;

        _last = node;

        ++_size;
    }

    public T dequeue()
    {
        T item = _first.item;

        _first = _first.next;

        --_size;

        return item;
    }

    public boolean isEmpty()
    {
        return _size == 0;
    }

    public int size()
    {
        return _size;
    }

    private class Node
    {
        Node(T i)
        {
            item = i;
        }

        T item;
        Node next = null;
    };

    private Node _first = null;

    private Node _last = null;

    private int _size = 0;
}
