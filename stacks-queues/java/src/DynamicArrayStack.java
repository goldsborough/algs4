/**
 * Created by petergoldsborough on 08/15/15.
 */

public class DynamicArrayStack<T>
{

    DynamicArrayStack()
    {
        _array = (T[]) new Object[1];
    }

    DynamicArrayStack(int capacity)
    {
        _array = (T[]) new Object[capacity];
    }

    public void push(T item)
    {
        _array[_size++] = item;

        if (_size == _array.length) _resize();
    }

    public T pop()
    {
        T item = _array[--_size];

        if (_size == _array.length/4) _resize();
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
        T[] old = _array;

        _array = (T[]) new Object[_size * 2];

        for (int i = 0; i < _size; ++i)
        {
            _array[i] = old[i];
        }
    }

    private T[] _array;

    private int _size = 0;
}
