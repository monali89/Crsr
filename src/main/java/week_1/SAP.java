package week_1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Monali L on 2/8/2020
 */

public class SAP {

    private final Digraph dg;
    private int ancestor;
    private int pathMinDist;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.dg = G;
        ancestor = -1;
        pathMinDist = -1;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        bfs(Collections.singletonList(v), Collections.singletonList(w));
        return pathMinDist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        bfs(Collections.singletonList(v), Collections.singletonList(w));
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        bfs(v, w);
        return pathMinDist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        bfs(v, w);
        return ancestor;
    }

    private void bfs(Iterable<Integer> v, Iterable<Integer> w) {

        ancestor = -1;
        pathMinDist = -1;

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        boolean[] vBfsMarked = new boolean[dg.V()];
        boolean[] wBfsMarked = new boolean[dg.V()];

        int[] vDistTo = new int[dg.V()];
        // int[] vEdgeTo = new int[dg.V()];
        int[] wDistTo = new int[dg.V()];
        // int[] wEdgeTo = new int[dg.V()];

        for (int i = 0; i < dg.V(); i++) {
            vDistTo[i] = Integer.MAX_VALUE;
            wDistTo[i] = Integer.MAX_VALUE;
            // vEdgeTo[i] = -1;
            // wEdgeTo[i] = -1;
        }

        for (int i: v) {
            vQueue.enqueue(i);
            vDistTo[i] = 0;
        }
        for (int i: w) {
            wQueue.enqueue(i);
            wDistTo[i] = 0;
        }

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        while (!vQueue.isEmpty() || !wQueue.isEmpty()) {
            if (!vQueue.isEmpty()) {
                int vCurr = vQueue.dequeue();
                vSet.add(vCurr);
                for (int i: w) {
                    if (vSet.contains(i)) {
                        ancestor = i;
                        pathMinDist = vDistTo[i];
                        return;
                    }
                }
                for (int n: dg.adj(vCurr)) {
                    if (!vBfsMarked[n]) {
                        if (vDistTo[n] > (vDistTo[vCurr] + 1)) {
                            vDistTo[n] = vDistTo[vCurr] + 1;
                            // vEdgeTo[n] = vCurr;
                        }
                        vQueue.enqueue(n);
                        vBfsMarked[n] = true;
                    }
                }
            }
            if (!wQueue.isEmpty()) {
                int wCurr = wQueue.dequeue();
                wSet.add(wCurr);
                for (int i: v) {
                    if (wSet.contains(i)) {
                        ancestor = i;
                        pathMinDist = wDistTo[i];
                        return;
                    }
                }
                for (int n: dg.adj(wCurr)) {
                    if (!wBfsMarked[n]) {
                        if (wDistTo[n] > (wDistTo[wCurr] + 1)) {
                            wDistTo[n] = wDistTo[wCurr] + 1;
                            // wEdgeTo[n] = wCurr;
                        }
                        wQueue.enqueue(n);
                        wBfsMarked[n] = true;
                    }
                }
            }
        }

        vSet.retainAll(wSet);
        List<Integer> list = new ArrayList<Integer>(vSet);

        int minDist = Integer.MAX_VALUE;
        int thisAncestor = -1;

        for (int l: list) {
            if (minDist > (vDistTo[l] + wDistTo[l])) {
                minDist = vDistTo[l] + wDistTo[l];
                thisAncestor = l;
            }
        }
        ancestor = thisAncestor;
        pathMinDist = minDist;
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
