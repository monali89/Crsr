package week2;

import org.junit.Assert;
import org.junit.Test;
import week2.Deque;

import java.util.Iterator;

/**
 * Date: 9/29/2019
 * @author: Monali
 */

public class DequeTest {

    class Student{
        int id;
        String name;
        double marks;
        Student(int id, String name, double marks){
            this.id = id;
            this.name = name;
            this.marks = marks;
        }
    }

    @Test
    public void test_IsEmpty(){
        Deque<Student> dq = new Deque<Student>();
        Assert.assertEquals(true, dq.isEmpty());
    }

    @Test
    public void test_size(){
        Deque<Student> dq = new Deque<Student>();
        dq.addLast(new Student(12, "PQR", 45.35));
        dq.addLast(new Student(13, "MNO", 35.21));
        dq.addLast(new Student(14, "XYZ", 55.77));
        dq.addFirst(new Student(11, "ABC", 56.92));
        Assert.assertEquals(4, dq.size());
    }

    @Test
    public void test_addFirst(){
        Deque<Student> dq = new Deque<Student>();
    }

    @Test
    public void test_addLast(){
        Deque<Student> dq = new Deque<Student>();
    }

    @Test
    public void test_removeFirst(){
        Deque<Student> dq = new Deque<Student>();
    }

    @Test
    public void test_removeLast(){
        Deque<Student> dq = new Deque<Student>();
    }

    @Test
    public void test_iterator(){
        Deque<Student> dq = new Deque<Student>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_null_object_first(){
        Deque<Student> dq = new Deque<Student>();
        dq.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_null_object_last(){
        Deque<Student> dq = new Deque<Student>();
        dq.addLast(null);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_remove_first_from_empty_deque(){
        Deque<Student> dq = new Deque<Student>();
        dq.removeFirst();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_remove_last_from_empty_deque(){
        Deque<Student> dq = new Deque<Student>();
        dq.removeLast();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_empty_deque_iterator_next(){
        Deque<Student> dq = new Deque<Student>();
        Iterator<Student> itr = dq.iterator();
        itr.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_empty_deque_iterator_remove(){
        Deque<Student> dq = new Deque<Student>();
        Iterator<Student> itr = dq.iterator();
        itr.remove();
    }

}
