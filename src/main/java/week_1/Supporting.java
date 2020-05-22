package week_1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Monali L on 3/18/2020
 */

public class Supporting {

    private boolean dfsMarked[];

    Supporting(Digraph dg) {
        dfsMarked = new boolean[dg.V()];
    }

    public void breadthFirstSearch(Digraph dg, int v) {

        System.out.println("BFS from start " + v);

        Queue<Integer> queue = new Queue<Integer>();
        boolean[] bfsMarked = new boolean[dg.V()];
        int[] distTo = new int[dg.V()];
        int[] edgeTo = new int[dg.V()];
        for (int i = 0; i < dg.V(); i++) {
            distTo[i] = Integer.MAX_VALUE;
            edgeTo[i] = -1;
        }
        distTo[v] = 0;
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int curr = queue.dequeue();
            System.out.println(curr);
            for (int w: dg.adj(curr)) {
                if (!bfsMarked[w]) {
                    if (distTo[w] > (distTo[curr] + 1)) {
                        distTo[w] = distTo[curr] + 1;
                        edgeTo[w] = curr;
                    }
                    queue.enqueue(w);
                    bfsMarked[w] = true;
                }
            }
        }
        for (int i = 0; i < dg.V(); i++) {
            System.out.printf("Vertex: %2d, Ancestor: %2d, Distance from %d: %s", i, edgeTo[i], v, (distTo[i] == Integer.MAX_VALUE ? "INFINITY" : distTo[i]));
            System.out.println();
        }
        System.out.println();
    }

    public void depthFirstSearch(Digraph dg, int v) {
        System.out.println(v);
        dfsMarked[v] = true;
        for (int w: dg.adj(v)) {
            if (!dfsMarked[w]) depthFirstSearch(dg, w);
        }
    }

    public void reachability(Digraph dg, int v, int w) {

        System.out.printf("Path between %d and %d using BFS\n", v, w);

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        boolean[] vBfsMarked = new boolean[dg.V()];
        boolean[] wBfsMarked = new boolean[dg.V()];

        int[] vDistTo = new int[dg.V()];
        int[] vEdgeTo = new int[dg.V()];
        int[] wDistTo = new int[dg.V()];
        int[] wEdgeTo = new int[dg.V()];

        for (int i = 0; i < dg.V(); i++) {
            vDistTo[i] = Integer.MAX_VALUE;
            wDistTo[i] = Integer.MAX_VALUE;
            vEdgeTo[i] = -1;
            wEdgeTo[i] = -1;
        }

        vDistTo[v] = 0;
        wDistTo[w] = 0;

        vQueue.enqueue(v);
        wQueue.enqueue(w);

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        while (!vQueue.isEmpty()) {
            int vCurr = vQueue.dequeue();
            vSet.add(vCurr);
            for (int n: dg.adj(vCurr)) {
                if (!vBfsMarked[n]) {
                    if (vDistTo[n] > (vDistTo[vCurr] + 1)) {
                        vDistTo[n] = vDistTo[vCurr] + 1;
                        vEdgeTo[n] = vCurr;
                    }
                    vQueue.enqueue(n);
                    vBfsMarked[n] = true;
                }
            }
        }

        while (!wQueue.isEmpty()) {
            int wCurr = wQueue.dequeue();
            wSet.add(wCurr);
            for (int n: dg.adj(wCurr)) {
                if (!wBfsMarked[n]) {
                    if (wDistTo[n] > (wDistTo[wCurr] + 1)) {
                        wDistTo[n] = wDistTo[wCurr] + 1;
                        wEdgeTo[n] = wCurr;
                    }
                    wQueue.enqueue(n);
                    wBfsMarked[n] = true;
                }
            }
        }

        System.out.println("DEBUG: vSet - " + vSet);
        System.out.println("DEBUG: wSet - " + wSet);

        System.out.println("BFS from start " + v);
        for (int i = 0; i < dg.V(); i++) {
            System.out.printf("Vertex: %2d, Ancestor: %2d, Distance from %d: %s", i, vEdgeTo[i], v, (vDistTo[i] == Integer.MAX_VALUE ? "INFINITY" : vDistTo[i]));
            System.out.println();
        }

        System.out.println("BFS from start " + w);
        for (int i = 0; i < dg.V(); i++) {
            System.out.printf("Vertex: %2d, Ancestor: %2d, Distance from %d: %s", i, wEdgeTo[i], w, (wDistTo[i] == Integer.MAX_VALUE ? "INFINITY" : wDistTo[i]));
            System.out.println();
        }

        if (vSet.contains(w)) {
            System.out.printf("Common ancestor of %d and %d: %d, Path Length: %d\n", v, w, v, vDistTo[w]);
            return;
        } else if (wSet.contains(v)) {
            System.out.printf("Common ancestor of %d and %d: %d, Path Length: %d\n", v, w, w, wDistTo[v]);
            return;
        }

        System.out.println("Not found in either sets so finding common");
        Set<Integer> common = new HashSet<Integer>(vSet);
        common.retainAll(wSet);
        List<Integer> list = new ArrayList<Integer>(common);
        System.out.println("List: " + list);

        int minDist = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int l: list) {
            if (minDist > (vDistTo[l] + wDistTo[l])) {
                minDist = vDistTo[l] + wDistTo[l];
                ancestor = l;
            }
        }

        System.out.printf("Common ancestor of %d and %d: %d, Path Length: %d\n", v, w, ancestor, minDist);

    }

    public static void main(String[] args) {

        Digraph digraph = getGraphFromFile("C:/Users/monal/IdeaProjects/Coursera/src/main/resources/week_1/digraph25.txt");
                //getFilledGraph();
        Supporting object = new Supporting(digraph);
        object.breadthFirstSearch(digraph, 13);
        object.breadthFirstSearch(digraph, 16);

        object.reachability(digraph, 13, 16);
    }

    // First line has vertex count
    // Second line has number of edges
    public static Digraph getGraphFromFile(String path) {

        Digraph dg = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            dg = new Digraph(Integer.parseInt(br.readLine()));
            int edges = Integer.parseInt(br.readLine());
            System.out.println("DEBUG: Vertexes - " + dg.V() + ", Edges - " + edges);
            String s = "";

            while ((s = br.readLine()) != null) {
                if (s.trim().equals("")) continue;
                String[] t = s.split(" ");
                int v = Integer.parseInt(t[0]);
                int w = Integer.parseInt(t[1]);
                dg.addEdge(v, w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dg;
    }

    private static Digraph getFilledGraph() {
        Digraph dg = new Digraph(13);

        dg.addEdge(0, 1);
        dg.addEdge(0, 5);

        dg.addEdge(1, 1);

        dg.addEdge(2, 3);

        dg.addEdge(3, 2);
        dg.addEdge(3, 5);

        dg.addEdge(4, 2);
        dg.addEdge(4, 3);

        dg.addEdge(5, 4);

        dg.addEdge(6, 0);
        dg.addEdge(6, 4);
        dg.addEdge(6, 8);
        dg.addEdge(6, 9);

        dg.addEdge(7, 6);
        dg.addEdge(7, 9);

        dg.addEdge(8, 6);

        dg.addEdge(9, 10);
        dg.addEdge(9, 11);

        dg.addEdge(10, 12);

        dg.addEdge(11, 4);
        dg.addEdge(11, 12);

        dg.addEdge(12, 9);

        return dg;
    }

}
