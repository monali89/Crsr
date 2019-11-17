package week2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Date: 9/29/2019
 * @author: Monali
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
		queue = (Item[]) new Object[100];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
		//StdOut.println(first + " " + last + " " + size);
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resizeArray (int newSize) {
        Item[] newQueue = Arrays.copyOfRange(queue, 0, newSize);
        queue = newQueue;
    }

    // add the item
    public void enqueue (Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == queue.length) resizeArray(size*2);
        queue[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue () {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        if (size <= queue.length/4) resizeArray(queue.length/2);
        int randomIndex = StdRandom.uniform(size);
        Item toRemove = queue[randomIndex];
        queue[randomIndex] = queue[size-1];
        queue[size-1] = null;
        size--;
        return toRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new java.util.NoSuchElementException();
        int randomIndex = StdRandom.uniform(size);
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item>{
        private int currIndex = 0;
        public boolean hasNext() { return currIndex < size; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (currIndex >= size) throw new java.util.NoSuchElementException();
            return queue[currIndex++];
        }
    }

    // unit testing (required)
    public static void main(String[] args){

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        StdOut.println("Is queue empty? " + rq.isEmpty());
        StdOut.println("Queue size: " + rq.size());
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        rq.enqueue("E");
        rq.enqueue("F");
        rq.enqueue("G");
        rq.enqueue("H");

        StdOut.println("Is queue empty? " + rq.isEmpty());
        StdOut.println("Queue size: " + rq.size());

        StdOut.println("Iterating over queue");
        Iterator<String> itr = rq.iterator();
        while (itr.hasNext()){
            StdOut.println(itr.next());
        }

        StdOut.println("Getting random elements");
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());

        StdOut.println("Is queue empty? " + rq.isEmpty());
        StdOut.println("Queue size: " + rq.size());

        StdOut.println("Dequeuing random elements");
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());

        StdOut.println("Is queue empty? " + rq.isEmpty());
        StdOut.println("Queue size: " + rq.size());

        StdOut.println("Iterating over queue");
        itr = rq.iterator();
        while (itr.hasNext()){
            StdOut.println(itr.next());
        }

        StdOut.println("Dequeuing random elements");
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());

        StdOut.println("Is queue empty? " + rq.isEmpty());
        StdOut.println("Queue size: " + rq.size());

        StdOut.println("Iterating over queue");
        itr = rq.iterator();
        while (itr.hasNext()){
            StdOut.println(itr.next());
        }


    }

}