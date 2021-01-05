package week_3;

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

    int[] w;
    int[] l;
    int[] r;
    int[][] g;
    List<String> teams;

    class Team {

        String name;
        int wins;
        int losses;
        int remaining;
        Map<String, Integer> gamesLeftMap;

        Team(String name, int wins, int losses, int remaining) {
            this.name = name;
            this.wins = wins;
            this.losses = losses;
            this.remaining = remaining;
            gamesLeftMap = new HashMap<String, Integer>();
        }

        public void addGamesLeft(String teamName, int gamesLeft) {
            gamesLeftMap.put(teamName, gamesLeft);
        }
    }

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            int lines = bfr.read();
            w = new int[lines];
            l = new int[lines];
            r = new int[lines];
            g = new int[lines][lines];
            teams = new ArrayList<String>();

            for (int i = 0; i < lines; i++) {
                String line = bfr.readLine();
                String[] arr = line.split(" ");
                teams.add(arr[0]);
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
        return teams;
    }

    // number of wins for given team
    public int wins(String team) {
        return -1;
    }

    // number of losses for given team
    public int losses(String team) {
        return -1;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return -1;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return -1;
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
