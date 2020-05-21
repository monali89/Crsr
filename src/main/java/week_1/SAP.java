package week_1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Monali L on 2/8/2020
 */
public class SAP {

    private final Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.dg = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V()) throw  new IllegalArgumentException();
        return helperLength(Collections.singletonList(v), Collections.singletonList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V()) throw  new IllegalArgumentException();
        return helperAncestor(Collections.singletonList(v), Collections.singletonList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        if (v == null || w == null) throw new IllegalArgumentException();
        for (int vThis: v) if (vThis < 0 || vThis >= dg.V()) throw new IllegalArgumentException();
        for (int wThis: w) if (wThis < 0 || wThis >= dg.V()) throw new IllegalArgumentException();

        return helperLength(v, w);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        if (v == null || w == null) throw new IllegalArgumentException();
        for (int vThis: v) if (vThis < 0 || vThis >= dg.V()) throw new IllegalArgumentException();
        for (int wThis: w) if (wThis < 0 || wThis >= dg.V()) throw new IllegalArgumentException();

        return helperAncestor(v, w);

    }

    private int helperLength(Iterable<Integer> v, Iterable<Integer> w) {

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        for (int i: v) {
            vQueue.enqueue(i);
            vSet.add(i);
        }
        for (int i: w) {
            wQueue.enqueue(i);
            wSet.add(i);
        }

        boolean[] vIsVisited = new boolean[dg.V()];
        boolean[] wIsVisited = new boolean[dg.V()];

        int[] vDistTo = new int[dg.V()];
        int[] wDistTo = new int[dg.V()];

        while (!vQueue.isEmpty() || !wQueue.isEmpty()) {

            int vCurr = -1, wCurr = -1, vDist = 0, wDist = 0;
            if (!vQueue.isEmpty()) {
                vCurr = vQueue.dequeue();
                vDist = vDistTo[vCurr];
            }
            if (!wQueue.isEmpty()) {
                wCurr = wQueue.dequeue();
                wDist = wDistTo[wCurr];
            }

            if (vSet.contains(wCurr)) {
                return vDistTo[wCurr] + wDistTo[wCurr];
            } else if (wSet.contains(vCurr)) {
                return vDistTo[vCurr] + wDistTo[vCurr];
            }

            if (vCurr != -1) {
                for (int adj: dg.adj(vCurr)) {
                    if (!vIsVisited[adj]) {
                        vSet.add(adj);
                        vQueue.enqueue(adj);
                        vIsVisited[adj] = true;
                        if (vDistTo[adj] < vDist + 1) {
                            vDistTo[adj] = vDist + 1;
                        }
                    }
                }
            }
            if (wCurr != -1) {
                for (int adj: dg.adj(wCurr)) {
                    if (!wIsVisited[adj]) {
                        wSet.add(adj);
                        wQueue.enqueue(adj);
                        wIsVisited[adj] = true;
                        if (wDistTo[adj] < wDist + 1) {
                            wDistTo[adj] = wDist + 1;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private int helperAncestor(Iterable<Integer> v, Iterable<Integer> w) {

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        for (int i: v) {
            vQueue.enqueue(i);
            vSet.add(i);
        }
        for (int i: w) {
            wQueue.enqueue(i);
            wSet.add(i);
        }

        boolean[] vIsVisited = new boolean[dg.V()];
        boolean[] wIsVisited = new boolean[dg.V()];

        while (!vQueue.isEmpty() || !wQueue.isEmpty()) {

            int vCurr = vQueue.isEmpty() ? -1 : vQueue.dequeue();
            int wCurr = wQueue.isEmpty() ? -1 : wQueue.dequeue();

            if (vSet.contains(wCurr)) {
                return wCurr;
            } else if (wSet.contains(vCurr)) {
                return vCurr;
            }

            if (vCurr != -1) {
                for (int adj: dg.adj(vCurr)) {
                    if (!vIsVisited[adj]) {
                        vSet.add(adj);
                        vQueue.enqueue(adj);
                        vIsVisited[adj] = true;
                    }
                }
            }
            if (wCurr != -1) {
                for (int adj: dg.adj(wCurr)) {
                    if (!wIsVisited[adj]) {
                        wSet.add(adj);
                        wQueue.enqueue(adj);
                        wIsVisited[adj] = true;
                    }
                }
            }
        }
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
