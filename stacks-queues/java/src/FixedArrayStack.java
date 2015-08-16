/**
 * Created by petergoldsborough on 08/15/15.
 */

public class FixedArrayStack<T>
{
    public FixedArrayStack(int capacity)
    {
        array = (T[]) new Object[capacity];
        size = capacity;
    }

    public void push(T item)
    {
        array[size++] = item;
    }

    public T pop()
    {
        T item = array[--size];

        array[size] = null;

        return item;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    private T[] array;

    private int size;
}
