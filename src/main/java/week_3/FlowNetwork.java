package week_3;

import edu.princeton.cs.algs4.Bag;

/**
 * @author from lecture slides
 */

public class FlowNetwork {

    private final int V;
    private Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<FlowEdge>();
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<FlowEdge> adj(int v) { return adj[v]; }

    //all edges in this flow network
    Iterable<FlowEdge> edges()  {
        return null;
    }

    // number of vertices
    int V() { return V; }

    // number of edges
    int E() { return adj.length; }

}
