package week4;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

/**
 * Date: 10/23/2019
 * @author: Monali
 */

public class Board {

    private final int[][] board;
    private int r1, c1, r2, c2;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        board = getArrayCopy(tiles);

        do {
            r1 = StdRandom.uniform(board.length);
            c1 = StdRandom.uniform(board.length);
            r2 = StdRandom.uniform(board.length);
            c2 = StdRandom.uniform(board.length);
        } while (board[r1][c1] == 0 || board[r2][c2] == 0 || board[r1][c1] == board[r2][c2]);
    }

    // string representation of this board
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(board.length);
        sb.append('\n');
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int expectedValue = getExpectedCellValue(i, j);
                int actualValue = board[i][j];
                if (actualValue != expectedValue) {
                    if (expectedValue == 0) continue;
                    else hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int manDistance = 0;

        int[] row = new int[board.length*board.length];
        int[] col = new int[board.length*board.length];
        int ctr = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                row[ctr] = i;
                col[ctr] = j;
                ctr++;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int expectedValue = getExpectedCellValue(i, j);
                int actualValue = board[i][j];
                if (actualValue != expectedValue) {
                    if (actualValue == 0) continue;
                    int pos = actualValue - 1;
                    int currManDist = Math.abs(i - row[pos]) + Math.abs(j - col[pos]);
                    manDistance = manDistance + currManDist;
                }
            }
        }
        return manDistance;
    }

    private int getExpectedCellValue(int row, int col) {
        if (row == board.length-1 && col == board.length-1) return 0;
        else return row*board.length + col + 1;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != getExpectedCellValue(i, j)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        Board c = (Board) y;
        if (c.board.length != this.board.length) return false;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] != c.board[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Stack<Board> stack = new Stack<Board>();

        int r = -1, c = -1, ri, ci;
        int[][] positions = new int[][]{{-1, 0}, {0, +1}, {+1, 0}, {0,-1}};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    r = i;
                    c = j;
                    break;
                }
            }
        }
        for (int i = 0; i < positions.length; i++) {
            ri = positions[i][0];
            ci = positions[i][1];
            if (isPositionValid(r+ri, c+ci)) {
                int[][] neighbor = getArrayCopy(board);
                int tempVal = board[r+ri][c+ci];
                neighbor[r+ri][c+ci] = 0;
                neighbor[r][c] = tempVal;
                stack.push(new Board(neighbor));
            }
        }
        return stack;
    }

    private boolean isPositionValid(int row, int col) {
        return (row >= 0 && row < board.length && col >= 0 && col < board.length);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBoard = getArrayCopy(board);
        // Swap the values
        twinBoard[r1][c1] = board[r2][c2];
        twinBoard[r2][c2] = board[r1][c1];
        return new Board(twinBoard);
    }

    private int[][] getArrayCopy(int[][] orig) {
        int[][] copy = new int[orig.length][orig.length];
        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig.length; j++) {
                copy[i][j] = orig[i][j];
            }
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] b = new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        Board obj = new Board(b);

        System.out.println("Board: ");
        System.out.println(obj.toString());
        System.out.println("Board Dimension: " + obj.dimension());
        System.out.println("Board Hamming Distance: " + obj.hamming());
        System.out.println("Board Manhattan Distance: " + obj.manhattan());
        System.out.println("Is Goal Board?: " + obj.isGoal());

        int[][] b2 = new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] b3 = new int[][]{{6, 1, 3}, {5, 2, 4}, {8, 7, 0}};

        Board objSame = new Board(b2);
        Board objDiff = new Board(b3);

        System.out.println("Is Board equal to objSame ?: " + obj.equals(objSame));
        System.out.println("Is Board equal to objDiff ?: " + obj.equals(objDiff));

        Iterator<Board> itr = obj.neighbors().iterator();
        System.out.println("The neighbors are => ");
        while (itr.hasNext()) {
            System.out.println("Neighbour: " + itr.next().toString());
        }

        System.out.println("Twin: " + obj.twin().toString());
    }
}
