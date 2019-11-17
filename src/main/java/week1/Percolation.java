package week1;/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int[][] grid;
    private boolean[] status;
    private int size;
    private int openSites;

    /*
     * Instructor: The constructor should take time proportional to n2
     * The Weighted QuickUnionUF constructor is going to create an array
     * with all the points and store it's connection information
     * So to store our grid information we will need n^2 sized UF array
     * */

    private int getIndex(int row, int col){
        //int index =(row-1)*size + (col-1) + 1;
        int index =row*size + col + 1;
        return index;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){

        if(n < 0) throw new IllegalArgumentException();

        uf = new WeightedQuickUnionUF(n*n + 2);
        grid = new int[n][n];
        status = new boolean[n*n + 2];
        status[0] = status[status.length - 1] = true;
        size = n;
        openSites = 0;

        /*for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = getIndex(i, j);
            }
        }*/
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){

        if(row < 0 || row >= size || col < 0 || col >= size) throw new IllegalArgumentException();

        int currIndex = getIndex(row, col);

        if(row == 0) uf.union(currIndex, 0);
        if(row == size-1) uf.union(currIndex, size*size+1);

        if(!status[currIndex]){

            status[currIndex] = true;
            openSites++;

            int left = getIndex(row, col-1);
            int right = getIndex(row, col+1);
            int top = getIndex(row+1, col);
            int bottom = getIndex(row-1, col);

            if(col - 1 > -1 && status[left] && !uf.connected(currIndex, left))
                uf.union(currIndex, left);
            if(col + 1 < size && status[right] && !uf.connected(currIndex, right))
                uf.union(currIndex, right);
            if(row + 1 < size && status[top] && !uf.connected(currIndex, top))
                uf.union(currIndex, top);
            if(row - 1 > -1 && status[bottom] && !uf.connected(currIndex, bottom))
                uf.union(currIndex, bottom);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row < 0 || row >= size || col < 0 || col >= size) throw new IllegalArgumentException();
        return status[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row < 0 || row >= size || col < 0 || col >= size) throw new IllegalArgumentException();
        if(row == 0) return status[getIndex(row, col)];
        return uf.connected(getIndex(row, col), 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.connected(0, size*size + 1);
    }

    // test client (optional)
    public static void main(String[] args){
        int n = 4;
        Percolation p = new Percolation(n);

        /*p.open(0,0);
        p.open(1,0);
        p.open(1,1);
        p.open(2,2);*/

        while(!p.percolates()){
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);
            if(!p.isOpen(row, col)) p.open(row, col);
        }

        System.out.println("Number of open sites: " + p.numberOfOpenSites());
        System.out.println(p.percolates() ? "Grid percolates" : "Grid does not percolate");
    }

}
