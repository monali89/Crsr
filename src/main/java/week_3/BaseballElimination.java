package week_3;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Monali L on 7/3/2020
 */

public class BaseballElimination {

    int[] w;   // i's wins
    int[] l;   // i's losses
    int[] r;   // i's total remaining games
    int[][] g; // i's games left to play against team j
    Map<String, Integer> teams;
    Map<Integer, String> reverseTeams;

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
            reverseTeams = new HashMap<Integer, String>();

            for (int i = 0; i < lines; i++) {
                String line = bfr.readLine().replaceAll(" +", " ");
                String[] arr = line.split(" ");
                teams.put(arr[0], i);
                reverseTeams.put(i, arr[0]);
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

    private List<String> eliminationCertTeams;

    // is given team eliminated?
    public boolean isEliminated(String team) {

        eliminationCertTeams = new ArrayList<String>();

        // trivial elimination
        int index = teams.get(team);
        for (int i = 0; i < teams.size(); i++) {
            if (i == index) continue;
            if (w[index] + r[index] < w[i]) {
                System.out.println("Trivial Elimination");
                eliminationCertTeams.add(String.valueOf(i));
                return true;
            }
        }

        // non trivial elimination
        System.out.println("Nontrivial Elimination");

        // create a flow network without given team
        CreateGraph(team);

        int maxFlow = edk(capacity, flow, source, sink);

        if (maxFlow == 0) return false;

        System.out.println("Maxflow - " + maxFlow);

        // check flow from source to game vertices
        for (int i = 0; i < g.length; i++) {
            if (i == teams.get(team)) continue;
            for (int j = i+1; j < g.length; j++) {
                if (j == teams.get(team)) continue;
                if (capacity[source][teamToGraphIndex.get(i + "-" + j)] != flow[source][teamToGraphIndex.get(i + "-" + j)]) {
                    eliminationCertTeams.add(i + "-" + j);
                }
            }
        }

        getMinCut(capacity, flow, source, sink);

        return eliminationCertTeams.size() > 0;
        //return false;
    }

    // Graph representation
    //int[] vertices;
    int[][] capacity;
    int[][] flow;
    int source;
    int sink;
    // Map<Integer, String> indexMap;
    Map<String, Integer> teamToGraphIndex; // Mapping for graph integer index to actual vertex
    Map<Integer, String> graphToTeamIndex; // Mapping from graph int index to team representation

    private void CreateGraph(String team) {

        // total graph vertices = source + total game vertices + team vertices + sink
        int size = 1 + getComb(teams.size()-1) + (teams.size()-1) + 1;
        source = 0;
        sink = size - 1;

        teamToGraphIndex = new HashMap<String, Integer>();
        graphToTeamIndex = new HashMap<Integer, String>();

        teamToGraphIndex.put("source", source);
        teamToGraphIndex.put("sink", sink);
        graphToTeamIndex.put(source, "source");
        graphToTeamIndex.put(sink, "sink");

        int graphIndex = source+1;

        for (int i = 0; i < g.length; i++) { // game vertices
            if (i == teams.get(team)) continue;
            for (int j = i+1; j < g[i].length; j++) {
                if (j == teams.get(team)) continue;
                teamToGraphIndex.put(i + "-" + j, graphIndex);
                graphToTeamIndex.put(graphIndex, i + "-" + j);
                graphIndex++;
            }
        }

        for (int i = 0; i < g.length; i++) { // team vertices
            if (i == teams.get(team)) continue;
            teamToGraphIndex.put(String.valueOf(i), graphIndex);
            graphToTeamIndex.put(graphIndex, String.valueOf(i));
            graphIndex++;
        }

        // Initialize capacities
        capacity = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(capacity[i], -1);
        }

        graphIndex = 1;

        for (int i = 0; i < g.length; i++) {
            if (i == teams.get(team)) continue; // skip if given team node
            for (int j = i+1; j < g.length; j++) {
                if (j == teams.get(team)) continue; // skip if given team node

                capacity[source][graphIndex++] = g[i][j]; // from source to game vertex

                // from game vertex to team vertex
                capacity[teamToGraphIndex.get(i + "-" + j)][teamToGraphIndex.get(String.valueOf(i))] = Integer.MAX_VALUE;
                capacity[teamToGraphIndex.get(i + "-" + j)][teamToGraphIndex.get(String.valueOf(j))] = Integer.MAX_VALUE;
            }

            // from team vertex to sink
            int teamIndex = teamToGraphIndex.get(String.valueOf(i));
            capacity[teamIndex][sink] = w[teams.get(team)] + r[teams.get(team)] - w[i];
        }

        flow = new int[size][size]; // initialize remaining flow to be -1/null
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                flow[i][j] = 0;
            }
        }
    }

    private int getComb(int n) {
        return getFact(n) / (getFact(2) * getFact(n- 2));
    }

    private int getFact(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    private int edk(int[][] capacity, int[][] flow, int source, int sink) {
        int maxFlow = 0;
        int[] parent = bfs( capacity, flow, source, sink);

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

            parent = bfs(capacity, flow, source, sink);
        }
        return maxFlow;
    }

    private int[] bfs(int[][] capacity, int[][] flow, int source, int sink) {
        int[] pred = new int[capacity.length];
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
        return eliminationCertTeams;
    }

    private void getMinCut(int[][] capacity, int[][] flow, int source, int sink) {

        int[][] residual = new int[capacity.length][capacity.length];

        for (int i = 0; i < residual.length; i++) {
            for (int j = 0; j < residual.length; j++) {
                residual[i][j] = capacity[i][j] - flow[i][j];
            }
        }

        Set<String> certificateOfEliminationTeams = new HashSet<String>();
        boolean[] isVisited = new boolean[capacity.length];
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(source);
        isVisited[source] = true;

        while (!stack.isEmpty()) {
            int v = stack.pop();
            for (int i = 0; i < residual.length; i++) {
                if (residual[v][i] > 0 && !isVisited[i]) {
                    stack.push(i);
                    String[] tempTeams = graphToTeamIndex.get(i).split("-");
                    for (int j = 0; j < tempTeams.length; j++) {
                        certificateOfEliminationTeams.add(reverseTeams.get(Integer.parseInt(tempTeams[j])));
                    }
                    isVisited[i] = true;
                }
            }
        }
        System.out.println("certificateOfElimination - " + certificateOfEliminationTeams);
    }


    public static void main(String[] args) {

        String file = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\week_3\\teams5.txt";

        //BaseballElimination division = new BaseballElimination(args[0]);
        BaseballElimination division = new BaseballElimination(file);

        //System.out.println(division.isEliminated("Boston") ? "Eliminated" : " Not eliminated");

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
                System.out.println();
            }
            else {
                StdOut.println(team + " is not eliminated");
                System.out.println();
            }
        }
    }

}
