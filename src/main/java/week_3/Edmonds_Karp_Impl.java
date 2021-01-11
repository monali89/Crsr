package week_3;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

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
                capacity[i][j] = weights[i][j] == 0 ? -1 : weights[i][j];
                flow[i][j] = 0;
            }
        }
    }

    public int GetMaxFlow(){

        int maxFlow = 0;
        int[] parent = bfs();

        while (parent[sink] != -1) {

            int pathFlow = Integer.MAX_VALUE;

            // Calculate the minimum flow that can be sent on this path
            for (int startVertex = parent[sink], endVertex = sink;
                 startVertex != -1;
                 endVertex = startVertex, startVertex = parent[endVertex]) {
                pathFlow = Math.min(pathFlow, capacity[startVertex][endVertex] - flow[startVertex][endVertex]);
            }

            // Add current min path flow to overall flow
            maxFlow += pathFlow;

            // Update flow by the minimum capacity
            for (int startVertex = parent[sink], endVertex = sink;
                 startVertex != -1;
                 endVertex = startVertex, startVertex = parent[endVertex]) {
                flow[startVertex][endVertex] += pathFlow;
                flow[endVertex][startVertex] -= pathFlow;
            }
            parent = bfs();
        }

        return maxFlow;
    }

    public int[] bfs(){

        int[] pred = new int[vertices.length];
        Arrays.fill(pred, -1);

        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int i = 0; i < capacity[v].length; i++) {
                if (capacity[v][i] == -1) continue;
                if (pred[i] == -1 && capacity[v][i] > flow[v][i]) {
                    pred[i] = v;
                    queue.enqueue(i);
                }
            }
        }
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

        System.out.println("Max flow: " + obj.GetMaxFlow());
    }
}
