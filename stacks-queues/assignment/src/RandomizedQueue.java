/**
 * Created by petergoldsborough on 08/16/15.
*/

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.lang.Iterable;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    public RandomizedQueue()
    { }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public void enqueue(Item item)
    {
        if (item == null) throw new NullPointerException();

        items[size] = item;

        if (++size == items.length) resize();
    }

    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException();

        int index = randomIndex();

        Item item = items[index];

        items[index] = items[--size];

        if (size == items.length/4) resize();

        return item;
    }

    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException();

        return items[randomIndex()];
    }

    public Iterator<Item> iterator()
    {
        return new Itr();
    }

    private class Itr implements Iterator<Item>
    {
        public Itr()
        {
            indices = new int[size];

            for (int i = 0; i < size; ++i)
            {
                indices[i] = i;
            }

            StdRandom.shuffle(indices);
        }

        public boolean hasNext()
        {
            return index < size;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();

            return items[indices[index++]];
        }

        private int index = 0;

        private int[] indices;
    }

    private int randomIndex() {
        return StdRandom.uniform(size);
    }

    private void resize()
    {
        int capacity = size * 2;

        if (capacity < minimumCapacity) return;

        Item[] old = items;

        items = (Item[]) new Object[capacity];

        System.arraycopy(old, 0, items, 0, size);

        old = null;
    }

    private Item[] items = (Item[]) new Object[minimumCapacity];

    private int size = 0;

    private static int minimumCapacity = 4;
}

class TestRandomizedQueue
{
    public void testInitialStatus()
    {
        assert queue.isEmpty();

        assert queue.size() == 0;
    }

    public void testInsertionWorks()
    {
        for (int i = 1; i <= 10; ++i)
        {
            queue.enqueue(i);

            assert queue.size() == i;
        }

        assert ! queue.isEmpty();
    }

    public void testRemovalWorks()
    {
        for (int i = 0; i < 10; ++i)
        {
            queue.dequeue();

        }

        assert queue.isEmpty();

        assert queue.size() == 0;
    }

    public void testRandomnessOfRemoval()
    {
        int[] values = new int[10];

        for (int i = 1; i <= 10; ++i)
        {
            values[i - 1] = i;

            queue.enqueue(i);
        }

        for (int i = 9; i >= 0; --i)
        {
            if (queue.dequeue() != values[i])
            {
                return;
            }
        }

        assert false;
    }

    public void testIterationWorks()
    {
        for(int i = 0; i < 10; ++i)
        {
            queue.enqueue(i);
        }

        try
        {
            Iterator<Integer> itr = queue.iterator();

            while (itr.hasNext())
            {
                itr.next();
            }
        }

        catch(Exception e)
        {
            assert false;
        }

        assert true;
    }

    public void testRandomnessOfIteration()
    {
        int[] values = new int[10];

        for (int i = 0; i < 10; ++i)
        {
            values[i] = i;
        }

        for (int i = 0; i < 10; ++i)
        {
            int count = 0;

            for (int value : queue)
            {
                if (value != values[count++]) return;
            }
        }

        assert false;
    }

    public void testIndependenceOfIterators()
    {
        for (int i = 0; i < 10; ++i)
        {
            Iterator<Integer> itr = queue.iterator();

            for (int value : queue)
            {
                if (value != itr.next()) return;
            }
        }

        assert false;
    }

    public void testThrowsForNullInsertion()
    {
        try
        {
            queue.enqueue(null);

            assert false;
        }

        catch(NullPointerException e)
        { }
    }

    public void testThrowsForSamplingOnEmptyQueue()
    {
        while(! queue.isEmpty())
        {
            queue.dequeue();
        }

        try
        {
            queue.sample();

            assert false;
        }

        catch(NoSuchElementException e)
        { }
    }

    public void testThrowsForRemovalOnEmptyQueue()
    {
        try
        {
            queue.dequeue();

            assert false;
        }

        catch(NoSuchElementException e)
        { }
    }

    public void testThrowsForIteratorRemoval()
    {
        try
        {
            Iterator<Integer> itr = queue.iterator();

            itr.remove();

            assert false;
        }

        catch(UnsupportedOperationException e)
        { }
    }

    public static void main(String[] args)
    {
        TestRandomizedQueue test = new TestRandomizedQueue();

        test.testInitialStatus();

        System.out.println("Initial: OK");


        test.testInsertionWorks();

        System.out.println("Insertion: OK");


        test.testRemovalWorks();

        System.out.println("Removal: OK");


        test.testRandomnessOfRemoval();

        System.out.println("Randomness of Removal: OK");


        test.testIterationWorks();

        System.out.println("Iteration: OK");


        test.testRandomnessOfIteration();

        System.out.println("Randomness of Iteration: OK");


        test.testIndependenceOfIterators();

        System.out.println("Independence of Iterators: OK");


        test.testThrowsForNullInsertion();

        test.testThrowsForSamplingOnEmptyQueue();

        test.testThrowsForRemovalOnEmptyQueue();

        test.testThrowsForIteratorRemoval();

        System.out.println("Exceptions: OK");
    }

    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
};