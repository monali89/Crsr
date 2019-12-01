package week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Solver {

    private class Node {
        Board board;
        Node previous;
        int manhattanDist;
        int m;
        Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.manhattanDist = board.manhattan();
            this.m = moves;
        }
    }

    private boolean solvable;
    private Iterable<Board> solution;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        Board twin = initial.twin();
        MinPQ<Node> openListOrig = new MinPQ<Node>(new ByFValue());
        MinPQ<Node> openListTwin = new MinPQ<Node>(new ByFValue());

        openListOrig.insert(new Node(initial, null, 0));
        openListTwin.insert(new Node(twin, null, 0));

        while (!openListOrig.isEmpty() && !openListTwin.isEmpty()) {

            Node currNodeOrig = openListOrig.delMin();
            Node currNodeTwin = openListTwin.delMin();
            moves = currNodeOrig.m;

            if (currNodeOrig.board.isGoal() && moves > 0) {
                Stack<Board> stack = new Stack<Board>();
                Node curr = currNodeOrig;
                while (curr != null) {
                    stack.push(curr.board);
                    curr = curr.previous;
                }
                solvable = true;
                solution = stack;
                return;
            }
            if (currNodeTwin.board.isGoal()) {
                solvable = false;
                solution = null;
                moves = -1;
                return;
            }

            Iterable<Board> neighborsOrig = currNodeOrig.board.neighbors();
            for (Board child: neighborsOrig) {
                Node childNode = new Node(child, currNodeOrig, currNodeOrig.m+1);
                if (currNodeOrig.previous != null && child.equals(currNodeOrig.previous.board)) continue;
                openListOrig.insert(childNode);
            }

            Iterable<Board> neighborsTwin = currNodeTwin.board.neighbors();
            for (Board child: neighborsTwin) {
                Node childNode = new Node(child, currNodeTwin, currNodeTwin.m+1);
                if (currNodeTwin.previous != null && child.equals(currNodeTwin.previous.board)) continue;
                openListTwin.insert(childNode);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solution;
    }

    // Custom comparator by F value of a node (moves + manhattan distance)
    private static class ByFValue implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            int f1 = n1.m + n1.manhattanDist;
            int f2 = n2.m + n2.manhattanDist;
            if (f1 < f2) return -1;
            else if (f1 > f2) return 1;
            else return 0;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}