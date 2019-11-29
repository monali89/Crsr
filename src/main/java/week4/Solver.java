package week4;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Date: 10/23/2019
 * @author: Monali
 */

public class Solver {

    private int moves;
    private MinPQ<Board> openList;
    //private MinPQ<Board> closeList;
    private MaxPQ<Board> closeList;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){

        if (initial == null) throw new IllegalArgumentException();

        // Initializing both open and closed list to empty
        openList = new MinPQ<Board>(new ByFValue());
        closeList = new MaxPQ<Board>(new ByFValue());
        moves = -1;
        openList.insert(initial);
        Board previous = null;

        // loop until the goal node is found
        while (!openList.isEmpty()) {

            moves++;

            // Get a node from the openlist with least f value (man dist + no. of moves made so far)
            Board curr = openList.delMin();
            closeList.insert(curr);

            // if goal node is found stop and backtrack to create the complete path from start until here
            if (curr.isGoal()) {
                return;
            }

            // Goal is not yet found
            // Get all the neighbors/child of the current node
            Iterable<Board> neighbors = curr.neighbors();

            // For each neighbor/child node do this
            for (Board child: neighbors) {

                // If child is in the closed list (already visited) skip it and check next
                Iterator<Board> closeItr = closeList.iterator();
                while (closeItr.hasNext()) {
                    if (closeItr.next().equals(child)) continue;
                }

                // Get the f value for this node
                //int g = moves + 1; // Distance from start to this node OR No. of moves made so far
                //int h = child.manhattan(); // Distance from this to goal node OR Manhattan distance
                //int f = g + h;

                // Check if child is already in open list
                // And if it exists compare the number of
                // Previous moves vs. current moves to reach to this node
                // If previous moves are less do not add current child to open list
                // If not add it to open list

                Iterator<Board> openItr = openList.iterator();
                while (openItr.hasNext()) {
                    Board child_in_open = openItr.next();
                    if (child_in_open.equals(child)) continue;
                    if (child_in_open.equals(previous)) continue;
                }
                openList.insert(child);
            }
            previous = curr;
        }
    }

    private static class ByFValue implements Comparator<Board> {
        private int m;
        public ByFValue() {
            m = 0;
        }
        public int compare(Board b1, Board b2) {
            m = m + 1;
            int f1 = m + b1.manhattan();
            int f2 = m + b2.manhattan();
            if (f1 < f2) return -1;
            else if (f1 > f2) return 1;
            else return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return false;
    }

    // min number of moves to solve initial board
    public int moves(){
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        return closeList;
    }

    // test client (see below)
    public static void main(String[] args){

        int[][] b = new int[][]{{0,1,3},{4,2,5},{7,8,6}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);

        System.out.println("Is Solvable? " + s.isSolvable());
        System.out.println("No. of moves: " + s.moves());

        Iterator<Board> itr = s.solution().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next().toString());
        }

        // TEMP - for un solvable board
        /*int[][] b = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        Iterator<Board> itr = s.solution().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next().toString());
        }*/
    }
}
