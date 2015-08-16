/**
 * Created by petergoldsborough on 08/15/15.
 */

public class ArrayQueue<T>
{
    public ArrayQueue()
    {
        _data = (T[]) new Object[1];
    }

    public ArrayQueue(int capacity)
    {
        _data = (T[]) new Object[capacity];
    }

    public void enqueue(T item)
    {
        _data[_last] = item;

        _last = ++_last % _data.length;

        if (++_size == _data.length) _resize();
    }

    public T dequeue()
    {
        T item = _data[_first];

        _first = ++_first % _data.length;

        if (--_size == _data.length/4) _resize();

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

    private void _resize()
    {
        T[] old = _data;

        _data = (T[]) new Object[_size * 2];

        for(int i = 0; _first != _last; _first = ++_first % _data.length, ++i)
        {
            _data[i] = old[_first];
        }

        _first = 0;
        _last = _size;
    }

    private T[] _data;

    private int _size = 0;

    private int _first = 0;

    private int _last = 0;
}
