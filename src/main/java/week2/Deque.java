package week2;

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

/**
 * Date: 9/29/2019
 * @author: Monali
 */

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Node left;
        Item val;
        Node right;
        Node(Item item) {
            left = null;
			right = null;
            val = item;
        }
    }

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
		last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        size++;
        if (first == null) {
			Node newNode = new Node(item);
            first = newNode;
			last = newNode;
        } else {
            Node oldFirst = first;
            Node newFirst = new Node(item);
            first.left = newFirst;
            newFirst.right = oldFirst;
            first = newFirst;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        size++;
        if (last == null) {
            first = last = new Node(item);
        } else {
            Node oldLast = last;
            Node newLast = new Node(item);
            last.right = newLast;
            newLast.left = oldLast;
            last = newLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) throw new java.util.NoSuchElementException();
        size--;
        Item returnVal = first.val;
        first = first.right;
        return returnVal;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) throw new java.util.NoSuchElementException();
        size--;
        Item returnVal = last.val;
        last = last.left;
        return returnVal;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (current == null) throw new java.util.NoSuchElementException();
            Item item = current.val;
            current = current.right;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<String> dq = new Deque<String>();
        StdOut.println("Created new double ended queue");
        StdOut.println("Is deque empty? " + dq.isEmpty());

        StdOut.println("Adding ABC element at start");
        dq.addFirst("ABC");
        StdOut.println("Addin JDBg element at end");
        dq.addLast("JDB");
        StdOut.println("Adding NAH element at end");
        dq.addLast("NAH");
        StdOut.println("Adding OPS element at end");
        dq.addLast("OPS");
        StdOut.println("Adding ISG element at start");
        dq.addLast("ISG");

        Iterator<String> itr = dq.iterator();
        while (itr.hasNext()) {
            StdOut.println(itr.next());
        }

        StdOut.println("Is deque empty? " + dq.isEmpty());
        StdOut.println("week2.Deque size is " + dq.size());

        StdOut.println("Removing item at start: " + dq.removeFirst());
        StdOut.println("Removing item at end: " + dq.removeLast());
        StdOut.println("week2.Deque size is " + dq.size());

        itr = dq.iterator();
        while (itr.hasNext()) {
            StdOut.println(itr.next());
        }

    }

}
