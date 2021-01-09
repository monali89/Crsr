package week_3;

import edu.princeton.cs.algs4.Queue;

/**
 * @author from lecture slides
 */

public class FordFulkerson {

    private boolean[] marked;   // true if s->v path in residual network
    private FlowEdge[] edgeTo;  // last edge on s->v path
    private double value;       // value of flow
    public FordFulkerson(FlowNetwork G, int s, int t)
    {
        value = 0.0;
        while (hasAugmentingPath(G, s, t))
        {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            value += bottle;
        }
    }
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty())
        {
            int v = queue.dequeue();
            for (FlowEdge e : G.adj(v))
            {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w])
                {
                    edgeTo[w] = e;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
        return marked[t];
    }

    public double value()
    { return value; }

    public boolean inCut(int v)
    { return marked[v]; }

    public static void main(String[] args) {

        FlowNetwork fn = new FlowNetwork(8);
        fn.addEdge(new FlowEdge(0, 1, 10));
        fn.addEdge(new FlowEdge(0, 1, 5));
        fn.addEdge(new FlowEdge(0, 2, 15));

        fn.addEdge(new FlowEdge(1, 2, 4));
        fn.addEdge(new FlowEdge(1, 4, 9));
        fn.addEdge(new FlowEdge(1, 5, 15));

        fn.addEdge(new FlowEdge(2, 3, 4));
        fn.addEdge(new FlowEdge(2, 5, 8));

        fn.addEdge(new FlowEdge(3, 6, 16));

        fn.addEdge(new FlowEdge(4, 5, 15));
        fn.addEdge(new FlowEdge(4, 7, 10));

        fn.addEdge(new FlowEdge(5, 6, 15));
        fn.addEdge(new FlowEdge(5, 7, 10));

        fn.addEdge(new FlowEdge(6, 7, 10));

        FordFulkerson obj = new FordFulkerson(fn, 0, 7);

        System.out.println(obj.value);
    }

}
