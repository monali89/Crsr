package week_2;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Topological;

/**
 * @author Monali L on 3/3/2020
 */

public class ShortestPathDijkstra {

    private EdgeWeightedDigraph dg;
    private boolean[] visited;
    private double[] distTo;
    private int[] edgeTo;

    ShortestPathDijkstra(EdgeWeightedDigraph dg, int s) {

        this.dg = dg;
        visited = new boolean[dg.V()];
        distTo = new double[dg.V()];
        edgeTo = new int[dg.V()];
        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(dg.V());

        for (int i = 0; i < dg.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0.0;
        pq.insert(s, 0.0);

        while (!pq.isEmpty()) {
            int current = pq.delMin();
            Iterable<DirectedEdge> itr = dg.adj(current);
            for (DirectedEdge e: itr) {
                int v = e.from();
                int w = e.to();
                if (true) {
                    double dist = distTo[v] + e.weight();
                    if (distTo[w] > dist) {
                        distTo[w] = dist;
                        visited[w] = true;
                        edgeTo[w] = v;
                        if (pq.contains(w)) {
                            pq.decreaseKey(w, distTo[w]);
                        } else {
                            pq.insert(w, distTo[w]);
                        }
                    }
                }
            }
        }

        System.out.println("DEBUG: vertex - " + dg);

        System.out.println("DEBUG: Results - ");
        for (int i = 0; i < dg.V(); i++) {
            System.out.println("i - " + i + ", distTo - " + distTo[i] + ", edgeTo - " + edgeTo[i]);
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph ewdg = new EdgeWeightedDigraph(8);

        ewdg.addEdge(new DirectedEdge(0,1,5));
        ewdg.addEdge(new DirectedEdge(0,4,9));
        ewdg.addEdge(new DirectedEdge(0,7,8));

        ewdg.addEdge(new DirectedEdge(1,2,12));
        ewdg.addEdge(new DirectedEdge(1,3,15));
        ewdg.addEdge(new DirectedEdge(1,7,4));

        ewdg.addEdge(new DirectedEdge(2,3,3));
        ewdg.addEdge(new DirectedEdge(2,6,11));

        ewdg.addEdge(new DirectedEdge(3,6,9));

        ewdg.addEdge(new DirectedEdge(4,5,4));
        ewdg.addEdge(new DirectedEdge(4,6,20));
        ewdg.addEdge(new DirectedEdge(4,7,5));

        ewdg.addEdge(new DirectedEdge(5,2,1));
        ewdg.addEdge(new DirectedEdge(5,6,13));

        ewdg.addEdge(new DirectedEdge(7,5,6));
        ewdg.addEdge(new DirectedEdge(7,2,7));

        ShortestPathDijkstra spd = new ShortestPathDijkstra(ewdg, 0);

        Topological topological = new Topological(ewdg);
        System.out.println("DEBUG: T hasOrder - " + topological.hasOrder());
        System.out.println("DEBUG: T Order - " + topological.order());
    }
}
