package week_3;

import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Queue;

/**
 * @author Monali L on 12/28/2020
 */

public class Edmonds_Karp_Impl {

    int[] vertices;
    int[][] capacity;
    int[][] flow;
    int source;
    int sink;

    Edmonds_Karp_Impl(int size, int[][] weights, int source, int sink) {

        if (weights.length == 0 || weights[0].length != size) throw new IllegalArgumentException();

        vertices = new int[size];
        capacity = new int[size][size];
        flow = new int[size][size];
        this.source = source;
        this.sink = sink;

        for (int i = 0; i < size; i++) {
            vertices[i] = i;
            for (int j = 0; j < size; j++) {
                capacity[i][j] = weights[i][j];
                flow[i][j] = 0;
            }
        }
    }

    public static int GetMaxFlow(EdgeWeightedDigraph g, int source, int sink){

        int maxFlow = 0;

        return maxFlow;
    }

    public int[] bfs(){

        int[] pred = new int[vertices.length];
        Queue<Integer> queue = new Queue<Integer>();
        boolean[] isVisited = new boolean[vertices.length];
        queue.enqueue(source);
        isVisited[source] = true;

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int i = 0; i < capacity[v].length; i++) {
                if (capacity[v][i] == 0) continue;
                if (pred[i] == 0 && capacity[v][i] > flow[v][i]) {
                    pred[i] = v;
                    queue.enqueue(i);
                }
            }
        }

        int s = sink;
        System.out.print(s);
        while (s != source) {
            System.out.print(" " + pred[s]);
            s = pred[s];
        }
        System.out.println();

        return pred;
    }

    public static void main(String[] args) {

        int[][] weights = {
                {0, 10,  5, 15,  0,  0,  0,  0},
                {0,  0,  4,  0,  9,  0, 15,  0},
                {0,  0,  0,  4,  8,  0,  0,  0},
                {0,  0,  0,  0,  0,  0, 16,  0},
                {0,  0,  0,  0,  0, 15,  0, 10},
                {0,  0,  0,  0,  0,  0,  0, 10},
                {0,  0,  0,  0,  0,  0, 15, 10},
                {0,  0,  0,  0,  0,  0,  0,  0}
        };

        Edmonds_Karp_Impl obj = new Edmonds_Karp_Impl(8, weights, 0, 7);
        int[] bfsPath = obj.bfs();
    }
}
