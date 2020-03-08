package week_2;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.Random;

/**
 * @author Monali L on 2/29/2020
 */

public class SeamCarver {

    /*class Pixel {
        int x;
        int y;
        double energy;
    }*/

    private Picture picture;
    //private Digraph dg;
    private int size;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.picture = picture;

        /*this.picture = picture;
        this.size = picture.height() * picture.width();
        this.dg = new Digraph(size);
        System.out.println("DEBUG: Created graph of size - " + size);

        for (int x = 0; x < picture.height()-1; x++) { // rows
            for (int y = 0; y < picture.width(); y++) { // columns

                int curr = getIndex(x, y);
                System.out.println("DEBUG: For pixel (" + x + "," + y + ")");

                if (y-1 > -1) {
                    int bottom_left = getIndex(x+1, y-1);
                    dg.addEdge(curr, bottom_left);
                    System.out.println("DEBUG: Adding bottom left edge");
                }

                int bottom_center = getIndex(x+1, y);
                dg.addEdge(curr, bottom_center);
                System.out.println("DEBUG: Adding bottom center edge");

                if (y+1 < picture.width()) {
                    int bottom_right = getIndex(x+1, y+1);
                    dg.addEdge(curr, bottom_right);
                    System.out.println("DEBUG: Adding bottom right edge");
                }
            }
        }

        *//*Topological topological = new Topological(dg);
        System.out.println("DEBUG: T hasOrder - " + topological.hasOrder());
        System.out.println("DEBUG: T Order - " + topological.order());*//*
        System.out.println("DEBUG: dg - " + dg.toString());*/
    }

    private int getIndex(int x, int y) { // Column x, row y
        int index = y * picture.width() + x;
        System.out.println("DEBUG: Getting Index (Col, Row) -> (" + x + "," + y + ") - " + index);
        return index;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture - columns
    public int width() {
        return picture.width();
    }

    // height of current picture - rows
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x <0 || y < 0 || x >= picture.width() || y >= picture.height())
            throw new IllegalArgumentException();

        // Border coordinate
        if(x == 0 || x == picture.width()-1 || y == 0 || y == picture.height()-1)
            return 1000.0;

        // X gradient
        int x_red = picture.get(x+1, y).getRed() - picture.get(x-1, y).getRed();
        int x_blue = picture.get(x+1, y).getBlue() - picture.get(x-1, y).getBlue();
        int x_green = picture.get(x+1, y).getGreen() - picture.get(x-1, y).getGreen();
        int x_sqr = x_red*x_red + x_blue*x_blue + x_green*x_green;

        // Y gradient
        int y_red = picture.get(x, y+1).getRed() - picture.get(x, y-1).getRed();
        int y_blue = picture.get(x, y+1).getBlue() - picture.get(x, y-1).getBlue();
        int y_green = picture.get(x, y+1).getGreen() - picture.get(x, y-1).getGreen();
        int y_sqr = y_red*y_red + y_blue*y_blue + y_green*y_green;

        double energy = Math.sqrt(x_sqr + y_sqr);
        //System.out.println("DEBUG: Energy for pixel (Col, Row) -> (" + x + "," + y + ") - " + energy);

        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        return null;
    }

    public void verticalSeamHelper(int start) {

        double[] distTo = new double[height()];
        int[] edgeTo = new int[height()];
        distTo[0] = 1000.0;
        edgeTo[0] = start;

        int x = start;

        for (int y = 0; y < height()-1; y++) {
            double current_dist = distTo[y];
            double min_energy = Double.POSITIVE_INFINITY;
            for (int i = -1; i <= 1; i++) {
                if (x + i*1 < -1 || x + i*1 >= width()) continue;
                double this_energy = energy(x + i*1, y+1);
                if (this_energy < min_energy) {
                    distTo[y+1] = distTo[y] + this_energy;
                    edgeTo[y+1] = x + i*1;
                    min_energy = this_energy;
                }
            }
            x = edgeTo[y+1];
        }

        System.out.println("DEBUG: Final distance - " + distTo[height()-1]);
        System.out.println("DEBUG: Edges - ");
        for (int i = 0; i < edgeTo.length; i++) {
            System.out.println("Edge[" + i + "] - " + edgeTo[i]);
        }
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length > picture.width() || picture.height() <= 1)
            throw new IllegalArgumentException();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length > picture.height() || picture.width() <= 1)
            throw new IllegalArgumentException();
    }

    //  unit testing (optional)
    public static void main(String[] args) {

        Picture picture = new Picture("C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\week_2\\6x5.png");
        SeamCarver seamCarver = new SeamCarver(picture);

        System.out.println("DEBUG: Energy matrix");
        for (int i = 0; i < seamCarver.height(); i++) {
            for (int j = 0; j < seamCarver.width(); j++) {
                System.out.print(Math.round(seamCarver.energy(j, i)) + " ");
            }
            System.out.println();
        }
        System.out.println();

        seamCarver.verticalSeamHelper(3);
    }

    private static Picture getPictureWxH(int width, int height) {

        Picture picture = new Picture(width, height);
        Color color;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int r = getRandomBetween(0, 255);
                int g = getRandomBetween(0, 255);
                int b = getRandomBetween(0, 255);
                color = new Color(r, g, b);
                picture.setRGB(i, j, color.getRGB());
            }
        }
        return picture;
    }

    private static int getRandomBetween(int min, int max) {
        Random r = new Random();
        int random = r.nextInt(max+1-min) + min;
        return random;
    }


}
