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

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private double  percThres[];

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){

        if(n <= 0 || trials <= 0) throw new IllegalArgumentException();

        Percolation p = new Percolation(n);
        percThres = new double[trials];

        for (int i = 0; i < trials; i++) {
            while(!p.percolates()){
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if(!p.isOpen(row, col)) p.open(row, col);
            }
            percThres[i] = (p.numberOfOpenSites()*1.0)/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percThres);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percThres);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return StdStats.mean(percThres) - ((1.96 * StdStats.stddev(percThres)) / Math.sqrt(percThres.length-1));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return StdStats.mean(percThres) + ((1.96 * StdStats.stddev(percThres)) / Math.sqrt(percThres.length-1));
    }

    // test client (see below)
    public static void main(String[] args) {
        int ip[] = StdIn.readAllInts();
        PercolationStats ps = new PercolationStats(ip[0], ip[1]);
        StdOut.println("Mean = " + ps.mean());
        StdOut.println("StdDev = " + ps.stddev());
        StdOut.println("Confidence Interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}
