package week_3;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Monali L on 12/28/2020
 */

public class Edmonds_Karp_Impl {

    public static void main(String[] args) {

        EdgeWeightedDigraph dg = new EdgeWeightedDigraph(8);

        dg.addEdge(new DirectedEdge(0, 1, 10));
        dg.addEdge(new DirectedEdge(0, 2, 5));
        dg.addEdge(new DirectedEdge(0, 3, 15));

        dg.addEdge(new DirectedEdge(1, 4, 9));
        dg.addEdge(new DirectedEdge(1, 5, 15));
        dg.addEdge(new DirectedEdge(1, 2, 4));

        dg.addEdge(new DirectedEdge(2, 5, 8));
        dg.addEdge(new DirectedEdge(2, 3, 4));

        dg.addEdge(new DirectedEdge(3, 6, 16));

        dg.addEdge(new DirectedEdge(4, 7, 10));
        dg.addEdge(new DirectedEdge(4, 5, 15));

        dg.addEdge(new DirectedEdge(5, 7,10));
        dg.addEdge(new DirectedEdge(5, 6,15));

        dg.addEdge(new DirectedEdge(6, 2,6));
        dg.addEdge(new DirectedEdge(6, 7, 10));

        System.out.println("Max Flow: " + GetMaxFlow(dg, 0, 7));
    }

    public static int GetMaxFlow(EdgeWeightedDigraph g, int source, int sink){

        int maxFlow = 0;
        List<DirectedEdge> parent = new ArrayList<DirectedEdge>();
        Map<DirectedEdge, Integer> flow = new HashMap<DirectedEdge, Integer>();

        // this will true until path doesn't exist
        while (bfs(g, source, sink, parent)) {

            // Calculate current path's (returned by bfs in parent) value
            int s = sink;
            int currentFlow = 0;
            while (s != source) {
                //currentFlow = Math.min();

            }

        }

        return maxFlow;
    }

    public static boolean bfs(EdgeWeightedDigraph g, int s, int t, List<DirectedEdge> parent){
        return false;
    }
}
