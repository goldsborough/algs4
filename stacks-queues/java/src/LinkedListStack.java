/**
 * Created by petergoldsborough on 08/15/15.
 */

public class LinkedListStack<T>
{
    public void push(T item)
    {
        Node node = new Node(item, first);

        first = node;
    }

    public T pop()
    {
        T item = first.item;

        first = first.next;

        return item;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public int size()
    {
        int count = 0;

        Node node = first;

        while(node != null) ++count;

        return count;
    }

    private class Node
    {
        Node(T i, Node node)
        {
            item = i;
            next = node;
        }

        T item;
        Node next;
    }

    private Node first = null;
}
