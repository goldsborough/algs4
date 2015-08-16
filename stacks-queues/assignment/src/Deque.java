/**
 * Created by petergoldsborough on 08/16/15.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.lang.Iterable;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item>
{
    public Deque()
    { }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public void addFirst(Item item)
    {
        if (item == null) throw new NullPointerException();

        Node node = new Node(item, null, first);

        if (first != null) first.left = node;

        else last = node;

        first = node;

        ++size;
    }

    public void addLast(Item item)
    {
        if (item == null) throw new NullPointerException();

        Node node = new Node(item, last, null);

        if(last != null) last.right = node;

        else first = node;

        last = node;

        ++size;
    }

    public Item removeFirst()
    {
        if (isEmpty()) throw new NoSuchElementException();

        Node node = first;

        Item item = node.item;

        first = first.right;

        node = null;

        --size;

        if (isEmpty()) last = null;

        return item;
    }

    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException();

        Node node = last;

        Item item = node.item;

        last = last.left;

        node = null;

        --size;

        if (isEmpty()) first = null;

        return item;
    }

    public Iterator<Item> iterator()
    {
        return new Itr();
    }

    static public void main(String[] args)
    {

    }

    private class Itr implements Iterator<Item>
    {
        public boolean hasNext()
        {
            return itr != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (! hasNext()) throw new NoSuchElementException();

            Item item = itr.item;

            itr = itr.right;

            return item;
        }

        private Node itr = first;
    };

    private class Node
    {
        Node(Item item, Node left, Node right)
        {
            this.item = item;
            this.left = left;
            this.right = right;
        }

        Item item;

        Node left;
        Node right;
    };

    private Node first = null;
    private Node last = null;

    private int size = 0;
}

class TestDeque
{
    public void testDequeInitialStatus()
    {
        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testDequeBatchFrontInsertion()
    {
        for (int i = 1; i <= 10; ++i)
        {
            deque.addFirst(i);
        }

        assert ! deque.isEmpty();

        assert deque.size() == 10;
    }

    public void testDequeBatchFrontRemoval()
    {
        for (int i = 10; i > 0; --i)
        {
            assert deque.removeFirst() == i;
        }

        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testInterspersedFrontInsertionAndRemoval()
    {
        deque.addFirst(1);

        deque.addFirst(2);

        deque.addFirst(3);

        assert deque.size() == 3;

        assert deque.removeFirst() == 3;

        assert deque.removeFirst() == 2;

        assert deque.size() == 1;

        deque.addFirst(7);

        assert deque.removeFirst() == 7;

        assert deque.removeFirst() == 1;

        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testDequeBatchBackInsertion()
    {
        for (int i = 1; i <= 10; ++i)
        {
            deque.addLast(i);
        }

        assert ! deque.isEmpty();

        assert deque.size() == 10;
    }

    public void testDequeBatchBackRemoval()
    {
        for (int i = 10; i > 0; --i)
        {
            assert deque.removeLast() == i;
        }

        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testInterspersedBackInsertionAndRemoval()
    {
        deque.addLast(1);

        deque.addLast(2);

        deque.addLast(3);

        assert deque.size() == 3;

        assert deque.removeLast() == 3;

        assert deque.removeLast() == 2;

        assert deque.size() == 1;

        deque.addLast(7);

        assert deque.removeLast() == 7;

        assert deque.removeLast() == 1;

        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testDequeBatchFrontAndBackInsertion()
    {
        for (int i = 1; i <= 10; ++i)
        {
            deque.addFirst(i);
            deque.addLast(i);
        }

        assert ! deque.isEmpty();

        assert deque.size() == 20;
    }

    public void testDequeBatchFrontAndBackRemoval()
    {
        for (int i = 10; i > 0; --i)
        {
            assert deque.removeFirst() == i;
            assert deque.removeLast() == i;
        }

        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testInterspersedFrontAndBackInsertionAndRemoval()
    {
        deque.addFirst(1);

        deque.addLast(2);

        deque.addFirst(3);

        deque.addLast(4);

        assert deque.size() == 4;

        assert deque.removeLast() == 4;

        assert deque.removeLast() == 2;

        assert deque.removeFirst() == 3;

        assert deque.size() == 1;

        deque.addLast(7);

        assert deque.removeFirst() == 1;

        assert deque.removeFirst() == 7;

        assert deque.isEmpty();

        assert deque.size() == 0;
    }

    public void testThrowsForNullOnFront()
    {
        try
        {
            deque.addFirst(null);

            assert false;
        }

        catch(NullPointerException e)
        { }
    }

    public void testThrowsForNullOnBack()
    {
        try
        {
            deque.addLast(null);

            assert false;
        }

        catch(NullPointerException e)
        { }
    }

    public void testThrowsForFrontRemovalOnEmptyDeque()
    {
        try
        {
            deque.removeFirst();

            assert false;
        }

        catch(NoSuchElementException e)
        { }

    }

    public void testThrowsForBackRemovalOnEmptyDeque()
    {
        try
        {
            deque.removeLast();

            assert false;
        }

        catch(NoSuchElementException e)
        { }
    }

    public void testIterationWorks()
    {
        deque.addFirst(1);
        deque.addLast(2);

        Iterator<Integer> itr = deque.iterator();

        int count = 1;

        while(itr.hasNext())
        {
            int value = itr.next();

            assert value == count++;
        }

        assert ! itr.hasNext();
    }

    public void testIterationThrowsForRemoval()
    {
        Iterator<Integer> itr = deque.iterator();

        try
        {
            itr.remove();

            assert false;
        }

        catch(UnsupportedOperationException e)
        {
            assert true;
        }
    }

    public static void main(String[] args)
    {
        TestDeque test = new TestDeque();

        test.testDequeInitialStatus();

        System.out.println("Initial: OK");


        test.testDequeBatchFrontInsertion();

        test.testDequeBatchFrontRemoval();

        test.testInterspersedFrontInsertionAndRemoval();

        System.out.println("Front: OK");


        test.testDequeBatchBackInsertion();

        test.testDequeBatchBackRemoval();

        test.testInterspersedBackInsertionAndRemoval();

        System.out.println("Back: OK");



        test.testDequeBatchFrontAndBackInsertion();

        test.testDequeBatchFrontAndBackRemoval();

        test.testInterspersedFrontAndBackInsertionAndRemoval();

        System.out.println("Front and Back: OK");


        test.testThrowsForNullOnFront();

        test.testThrowsForNullOnBack();

        System.out.println("Null: OK");


        test.testThrowsForFrontRemovalOnEmptyDeque();

        test.testThrowsForBackRemovalOnEmptyDeque();

        System.out.println("Bad Removal: OK");


        test.testIterationWorks();

        test.testIterationThrowsForRemoval();

        System.out.println("Iteration: OK");
    }

    Deque<Integer> deque = new Deque<>();
};