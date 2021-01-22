package week_3;

import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    int[][] flow;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            int lines = bfr.read();
            w = new int[lines];
            l = new int[lines];
            r = new int[lines];
            g = new int[lines][lines];
            teams = new HashMap<String, Integer>();

            flow = new int[lines][lines];

            for (int i = 0; i < lines; i++) {
                String line = bfr.readLine();
                String[] arr = line.split(" ");
                teams.put(arr[0], i);
                w[i] = Integer.parseInt(arr[1]);
                l[i] = Integer.parseInt(arr[2]);
                r[i] = Integer.parseInt(arr[3]);
                for (int j = 0; j < lines; j++) {
                    g[i][j] = Integer.parseInt(arr[j+4]);
                    flow[i][j] = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int GetMaxFlow() {

        // Graph representation

        return -1;
    }

    private int[] bfs() {

        Map<String, String> pred = new HashMap<String, String>();

        // initialize predecessor for all vertices to null
        pred.put("0", null);

        for (int i = 0; i < g.length; i++) {
            pred.put(String.valueOf(i), null);
            for (int j = 0; j < g.length; j++) {
                pred.put(i + "" + j, null);
            }
        }

        int sink = teams.size()*teams.size() + teams.size() + 1;
        pred.put(String.valueOf(sink), null);

        // source to game vertex
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                // check how many games still remaining to play
                if (g[i][j] <= 0) continue;
                if (g[i][j] > flow[i][j]) {
                    pred.put(i + "" + j, "0");
                }
            }
        }

        // game vertex to team vertex
        

        // team vertex to sink

        return null;
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
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
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
