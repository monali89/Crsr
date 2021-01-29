package week_3;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Monali L on 7/3/2020
 */

public class BaseballElimination {

    int[] w;   // i's wins
    int[] l;   // i's losses
    int[] r;   // i's total remaining games
    int[][] g; // i's games left to play against team j
    Map<String, Integer> teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            int lines = Integer.parseInt(bfr.readLine());
            w = new int[lines];
            l = new int[lines];
            r = new int[lines];
            g = new int[lines][lines];
            teams = new HashMap<String, Integer>();

            for (int i = 0; i < lines; i++) {
                String line = bfr.readLine().replaceAll(" +", " ");
                String[] arr = line.split(" ");
                teams.put(arr[0], i);
                w[i] = Integer.parseInt(arr[1]);
                l[i] = Integer.parseInt(arr[2]);
                r[i] = Integer.parseInt(arr[3]);
                for (int j = 0; j < lines; j++) {
                    g[i][j] = Integer.parseInt(arr[j+4]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the team with the least possible wins (current wins + remaining games)
    private int getLowestTeam() {
        int minGames = w[0] + r[0];
        int minTeam = 0;
        for (int i = 1; i < teams.size(); i++) {
            if (minGames < (w[i] + r[i])) {
                minTeam = i;
                minGames = w[i] + r[i];
            }
        }
        return minTeam;
    }

    // number of teams
    public int numberOfTeams() {
        return r.length;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return w[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return g[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {

        // trivial elimination
        int index = teams.get(team);
        for (int i = 0; i < teams.size(); i++) {
            if (i == index) continue;
            if (w[index] + r[index] < w[i]) return true;
        }

        // non trivial elimination

        // create a flow network without given team

        // Graph representation
        int[] vertices;
        int[][] capacity;
        int[][] flow;
        int source;
        int sink;

        // Mapping for graph integer index to actual vertex
        // Map<Integer, String> indexMap;
        Map<String, Integer> indexMap;

        // Converting input into graph representation

        // START

        // total graph vertices = source + total game vertices + team vertices + sink
        int size = teams.size() * teams.size() + teams.size() + 1;
        source = 0;
        sink = size - 1;

        // create a mapping to refer to actual vertex from graph's integer vertex
        vertices = new int[size];
        for (int i = 0; i < size; i++) {
            vertices[i] = i;
        }

        //indexMap = new HashMap<Integer, String>();
        indexMap = new HashMap<String, Integer>();
        int graphIndex = source;
        indexMap.put("0", graphIndex++); // source vertex

        for (int i = 0; i < g.length; i++) { // game vertices
            for (int j = 0; j < g[i].length; j++) {
                indexMap.put(i + "-" + j, graphIndex++);
            }
        }

        for (int i = 0; i < g.length; i++) { // team vertices
            indexMap.put(String.valueOf(i), graphIndex++);
        }

        indexMap.put(String.valueOf(sink), graphIndex); // sink vertex

        if (graphIndex >= size) throw new IllegalStateException();

        // Initialize capacities

        capacity = new int[size][size];
        graphIndex = 1; // capacities from source to game vertices
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                capacity[source][graphIndex++] = g[i][j];
            }
        }

        for (int i = 0; i < g.length; i++) { // capacities from game vertices to team vertices
            for (int j = 0; j < g[i].length; j++) {
                capacity[indexMap.get(i + "-" + j)][i] = -1;
                capacity[indexMap.get(i + "-" + j)][j] = -1;
            }
        }

        for (int i = 0; i < g.length; i++) {
            int teamIndex = indexMap.get(String.valueOf(i));
            capacity[teamIndex][sink] = w[i] + r[i] - w[getLowestTeam()];
        }

        flow = new int[size][size]; // initialize remaining flow to be -1/null
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                flow[i][j] = -1;
            }
        }

        // END

        return false;
    }

    private int edk(int[] vertices, int[][] capacity, int[][] flow, int source, int sink) {
        int maxFlow = 0;
        int[] parent = bfs(vertices, capacity, flow, source, sink);

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
            parent = bfs(vertices, capacity, flow, source, sink);
        }

        return maxFlow;
    }

    private int[] bfs(int[] vertices, int[][] capacity, int[][] flow, int source, int sink) {
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

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {

        String file = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\week_3\\teams4.txt";

        //BaseballElimination division = new BaseballElimination(args[0]);
        BaseballElimination division = new BaseballElimination(file);

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}
