package week_3;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Monali L on 7/3/2020
 */

public class BaseballElimination {

    private int[] w;   // i's wins
    private int[] l;   // i's losses
    private int[] r;   // i's total remaining games
    private int[][] g; // i's games left to play against team j

    // String team name and integer index mapping
    private Map<Integer, String> intToStrsTeams;
    private Map<String, Integer> strToIntTeams;

    // FlowNetwork's vertex to input node mapping
    private Map<String, Integer> strToIntNode;

    // Custom flow network for running the algo
    private FordFulkerson fordFulkerson;
    private FlowNetwork flowNetwork;

    // Additional things required
    private int source;
    private int sink;

    private boolean isEliminated;
    private List<String> eliminationCertTeams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            int lines = Integer.parseInt(bfr.readLine());

            w = new int[lines];
            l = new int[lines];
            r = new int[lines];
            g = new int[lines][lines];

            intToStrsTeams = new HashMap<Integer, String>();
            strToIntTeams = new HashMap<String, Integer>();

            for (int i = 0; i < lines; i++) {
                String line = bfr.readLine().replaceAll(" +", " ").trim();
                String[] arr = line.split(" ");

                intToStrsTeams.put(i, arr[0]);
                strToIntTeams.put(arr[0], i);
                w[i] = Integer.parseInt(arr[1]);
                l[i] = Integer.parseInt(arr[2]);
                r[i] = Integer.parseInt(arr[3]);

                for (int j = 0; j < lines; j++) {
                    g[i][j] = Integer.parseInt(arr[j+4]);
                }
            }
            isEliminated = false;
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
        return strToIntTeams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return w[strToIntTeams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return l[strToIntTeams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return r[strToIntTeams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return g[strToIntTeams.get(team1)][strToIntTeams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {

        eliminationCertTeams = new ArrayList<String>();

        // trivial elimination
        int index = strToIntTeams.get(team);
        for (int i = 0; i < w.length; i++) {
            if (i == index) continue;
            if (w[index] + r[index] < w[i]) {
                isEliminated = true;
                eliminationCertTeams.add(intToStrsTeams.get(i));
                return isEliminated;
            }
        }

        // non trivial elimination

        // create a flow network without given team
        fillFlowNetwork(team);
        fordFulkerson = new FordFulkerson(flowNetwork, source, sink);

        // if some edges are not full, this team cannot win and is eliminated
        Iterable<FlowEdge> sourceToGameEdges = flowNetwork.adj(source);
        for (FlowEdge edge: sourceToGameEdges) {
            if (edge.capacity() != edge.flow()) {
                isEliminated = true;
                break;
            }
        }

        return isEliminated;
    }

    private void fillFlowNetwork(String team) {

        // total graph vertices = source + total game vertices + team vertices + sink
        int size = 1 + getComb(strToIntTeams.size()-1) + (strToIntTeams.size()-1) + 1;
        source = 0;
        sink = size - 1;

        flowNetwork = new FlowNetwork(size);

        strToIntNode = new HashMap<String, Integer>();

        strToIntNode.put("source", source);
        strToIntNode.put("sink", sink);

        int graphIndex = source+1;

        for (int i = 0; i < g.length; i++) { // game vertices
            if (i == strToIntTeams.get(team)) continue;
            for (int j = i+1; j < g[i].length; j++) {
                if (j == strToIntTeams.get(team)) continue;
                strToIntNode.put(i + "-" + j, graphIndex);
                graphIndex++;
            }
        }

        for (int i = 0; i < g.length; i++) { // team vertices
            if (i == strToIntTeams.get(team)) continue;
            strToIntNode.put(String.valueOf(i), graphIndex);
            graphIndex++;
        }

        graphIndex = 1;

        for (int i = 0; i < g.length; i++) {
            if (i == strToIntTeams.get(team)) continue; // skip if given team node
            for (int j = i+1; j < g.length; j++) {
                if (j == strToIntTeams.get(team)) continue; // skip if given team node

                FlowEdge sourceToGameVertex = new FlowEdge(source, graphIndex++, g[i][j]);
                flowNetwork.addEdge(sourceToGameVertex);

                // from game vertex to team vertex
                FlowEdge gameToTeamVertex;
                gameToTeamVertex = new FlowEdge(strToIntNode.get(i + "-" + j), strToIntNode.get(String.valueOf(i)), Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(gameToTeamVertex);
                gameToTeamVertex = new FlowEdge(strToIntNode.get(i + "-" + j), strToIntNode.get(String.valueOf(j)), Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(gameToTeamVertex);

            }

            // from team vertex to sink
            int teamIndex = strToIntNode.get(String.valueOf(i));
            FlowEdge teamToSink = new FlowEdge(teamIndex, sink, w[strToIntTeams.get(team)] + r[strToIntTeams.get(team)] - w[i]);
            flowNetwork.addEdge(teamToSink);
        }

    }

    private int getComb(int n) {
        return (((n-1)*n)/2);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {

        if (!isEliminated) return null;

        if (eliminationCertTeams.size() > 0) return eliminationCertTeams;
        eliminationCertTeams = new ArrayList<String>();
        for (int i = 0; i < g.length; i++) {
            if (i == strToIntTeams.get(team)) continue; // skip if given team node
            if (fordFulkerson.inCut(strToIntNode.get(String.valueOf(i)))) {
                eliminationCertTeams.add(intToStrsTeams.get(i));
            }
        }
        return eliminationCertTeams;
    }

    public static void main(String[] args) {

        String file = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\week_3\\teams4.txt";

        // BaseballElimination division = new BaseballElimination(args[0]);
        BaseballElimination division = new BaseballElimination(file);

        // System.out.print(division.isEliminated("Philadelphia") ? "Eliminated" : " Not eliminated");
        // System.out.println(" by subset " + division.certificateOfElimination("Philadelphia"));

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
