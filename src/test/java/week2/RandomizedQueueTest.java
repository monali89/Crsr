package week2;

import org.junit.Assert;
import org.junit.Test;
import week2.RandomizedQueue;

import java.util.Iterator;

/**
 * Date: 10/6/2019
 * @author: Monali
 */

public class RandomizedQueueTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_enqueue_null_item(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue(null);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_sample_queue_empty(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.sample();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_dequeue_queue_empty(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.dequeue();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_iterator_next_no_items(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        Iterator<String> itr = rq.iterator();
        itr.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_iterator_remove(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        Iterator<String> itr = rq.iterator();
        itr.remove();
    }

    @Test
    public void test_queue_is_empty(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        Assert.assertEquals(true, rq.isEmpty());
    }

    @Test
    public void test_queue_size(){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        Assert.assertEquals(4, rq.size());
    }

    @Test
    public void test_queue_iterate(){
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(11);
        rq.enqueue(22);
        rq.enqueue(33);
        rq.enqueue(44);
        rq.enqueue(55);
        rq.enqueue(66);
        rq.enqueue(77);

        Iterator<Integer> itr = rq.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }

        Assert.assertTrue(1==1);
    }

    @Test
    public void test_queue_sample(){
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(11);
        rq.enqueue(22);
        rq.enqueue(33);
        rq.enqueue(44);
        rq.enqueue(55);
        rq.enqueue(66);
        rq.enqueue(77);

        System.out.println("Random item: " + rq.sample());
        System.out.println("Random item: " + rq.sample());
        System.out.println("Random item: " + rq.sample());
        System.out.println("Random item: " + rq.sample());

        Assert.assertTrue(1==1);
    }


}
