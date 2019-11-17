package week2;

import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

/**
 * Date: 11/1/2019
 * @author: Monali
 */

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
			String tempStr = StdIn.readString();
			if (tempStr.equals("exit")) break;
            rq.enqueue(tempStr);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
