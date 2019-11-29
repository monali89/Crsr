package week4;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Date: 10/23/2019
 * @author: Monali
 */

public class Board {

    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        board = new int[tiles.length][tiles.length];
        board = tiles;
    }

    // string representation of this board
    public String toString(){
        String boardString = "" + board.length + '\n';
        for (int i = 0; i < board.length; i++) {
            String thisLine = "";
            for (int j = 0; j < board.length; j++) {
                thisLine = thisLine + board[i][j] + " ";
            }
            boardString = boardString + thisLine + '\n';
        }
        return boardString;
    }

    // board dimension n
    public int dimension(){return board.length;}

    // number of tiles out of place
    public int hamming(){
        int hammingDistance = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != (i+1)) hammingDistance++;
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manDistance = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != (i+1)) manDistance = manDistance + i + 1;
            }
        }
        return manDistance;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != (i+1)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){

        if (y == this) return true;
        if (!(y instanceof Board)) return false;

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
    public Iterable<Board> neighbors(){
        return new boardIterable();
    }

    private class boardIterable implements Iterable<Board>{
        public Iterator<Board> iterator() {
            return new boardIterator();
        }
    }

    private class boardIterator implements Iterator<Board>{

        private int r, c;
        private int[][] itrArr;
        private int ctr;
        private int ri, ci;

        boardIterator(){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if(board[i][j] == 0){
                        r = i;
                        c = j;
                        break;
                    }
                }
            }
            ctr = 0;
            itrArr = new int[][]{{-1,0},{0,+1},{+1,0},{0,-1}}; // top, right, bottom, left - clockwise
            System.out.println("DEBUG: Empty cell is - " + r + "," + c);
        }

        public boolean hasNext() {

            if(ctr >= 4) return false;

            do {
                ri = itrArr[ctr][0];
                ci = itrArr[ctr][1];
                ctr++;
            } while (coordinatesNotValid(r+ri, c+ci) && ctr < 4);

            if (ctr >= 4) return false;
            else return true;

        }

        public Board next() {

            int[][] itrBoard = new int[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    itrBoard[i][j] = board[i][j];
                }
            }

            System.out.println("DEBUG: Neighbor coordinates - " + (r+ri) + "," + (c+ci));
            System.out.println("DEBUG: coordinatesNotValid - " + coordinatesNotValid(r+ri, c+ci));
            int tempVal = board[r+ri][c+ci];
            itrBoard[r+ri][c+ci] = 0;
            itrBoard[r][c] = tempVal;
            return new Board(itrBoard);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private boolean coordinatesNotValid(int row, int col) {
            if (row < 0 || row > board.length || col < 0 || col > board.length) return true;
            else return false;
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[][] twin = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                twin[i][j] = board[i][j];
            }
        }
        return new Board(twin);
    }

    // unit testing (not graded)
    public static void main(String[] args){

        /*int[][] b = new int[][]{{1,0,3},{4,2,5},{7,8,6}};
        Board obj = new Board(b);

        System.out.println("Board: ");
        System.out.println(obj.toString());
        System.out.println("Board Dimension: " + obj.dimension());
        System.out.println("Board Hamming Distance: " + obj.hamming());
        System.out.println("Board Manhattan Distance: " + obj.manhattan());
        System.out.println("Is Goal Board?: " + obj.isGoal());

        int[][] b2 = new int[][]{{1,0,3},{4,2,5},{7,8,6}};
        int[][] b3 = new int[][]{{6,1,3},{5,2,4},{8,7,0}};

        Board objSame = new Board(b2);
        Board objDiff = new Board(b3);

        System.out.println("Is Board equal to objSame ?: " + obj.equals(objSame));
        System.out.println("Is Board equal to objDiff ?: " + obj.equals(objDiff)); */

        int[][] b = new int[][]{{0,1,3},{4,2,5},{7,8,6}};
        Board obj = new Board(b);
        System.out.println("Board: " + obj.toString());

        Iterator<Board> itr = obj.neighbors().iterator();
        System.out.println("The neighbors are => ");
        while(itr.hasNext()){
            System.out.println("Neighbour: " + itr.next().toString());
        }

        //System.out.println("Twin: " + obj.twin().toString());


    }
}
