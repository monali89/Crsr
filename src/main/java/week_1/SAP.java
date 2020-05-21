package week_1;

import edu.princeton.cs.algs4.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Monali L on 2/8/2020
 */
public class SAP {

    private Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.dg = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        vQueue.enqueue(v);
        wQueue.enqueue(w);

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

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        vQueue.enqueue(v);
        wQueue.enqueue(w);

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

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        for (int i: v) vQueue.enqueue(i);
        for (int i: w) wQueue.enqueue(i);

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

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        for (int i: v) {
            vQueue.enqueue(i);
        }
        for (int i: w) {
            wQueue.enqueue(i);
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

    // ML added
    private int getRoot() {
        boolean flag = false;
        int root = -1;
        for (int i = 0; i < dg.V(); i++) {
            if (dg.outdegree(i) == 0) {
                if (flag) return -1;
                flag = true;
                root = i;
            }
        }
        return root;
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
