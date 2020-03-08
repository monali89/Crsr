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

        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        double min_total_energy = Double.POSITIVE_INFINITY;
        int[] min_array = new int[width()];

        for (int y = 0; y < height(); y++) {
            distTo[y][0] = 1000.0;
            edgeTo[y][0] = y;
            int prev_y = y;
            for (int x = 0; x < width()-1; x++) {
                double min_energy = Double.POSITIVE_INFINITY;
                for (int i = -1; i <= 1; i++) {
                    if (prev_y + i*1 <= -1 || prev_y + i*1 >= height()) continue;
                    double this_energy = energy(x+1, prev_y + i*1);
                    if (this_energy < min_energy) {
                        distTo[y][x+1] = distTo[y][x] + this_energy;
                        edgeTo[y][x+1] = prev_y + i*1;
                        min_energy = this_energy;
                    }
                }
                prev_y = edgeTo[y][x+1];
            }
            if (distTo[y][width()-1] < min_total_energy) {
                min_total_energy = distTo[y][width()-1];
                min_array = edgeTo[y];
            }
        }

        System.out.println("DEBUG: Final distance - " + min_total_energy);
        System.out.println("DEBUG: Edges - ");
        for (int i = 0; i < min_array.length; i++) {
            System.out.println("Edge[" + i + "] - " + min_array[i]);
        }

        return min_array;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];
        double min_total_energy = Double.POSITIVE_INFINITY;
        int[] min_array = new int[height()];

        for (int x = 0; x < width(); x++) {
            distTo[x][0] = 1000.0;
            edgeTo[x][0] = x;
            int prev_x = x;
            for (int y = 0; y < height()-1; y++) {
                double min_energy = Double.POSITIVE_INFINITY;
                for (int i = -1; i <= 1; i++) {
                    if (prev_x + i*1 <= -1 || prev_x + i*1 >= width()) continue;
                    double this_energy = energy(prev_x + i*1, y+1);
                    if (this_energy < min_energy) {
                        distTo[x][y+1] = distTo[x][y] + this_energy;
                        edgeTo[x][y+1] = prev_x + i*1;
                        min_energy = this_energy;
                    }
                }
                prev_x = edgeTo[x][y+1];
            }
            if (distTo[x][height()-1] < min_total_energy) {
                min_total_energy = distTo[x][height()-1];
                min_array = edgeTo[x];
            }
        }

        System.out.println("DEBUG: Final distance - " + min_total_energy);
        System.out.println("DEBUG: Edges - ");
        for (int i = 0; i < min_array.length; i++) {
            System.out.println("Edge[" + i + "] - " + min_array[i]);
        }

        return min_array;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null || seam.length > picture.width() || picture.height() <= 1)
            throw new IllegalArgumentException();

        Picture modified_picture = new Picture(width(), height());


    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        if (seam == null || seam.length > picture.height() || picture.width() <= 1)
            throw new IllegalArgumentException();

        Picture mod_pic = new Picture(width()-1, height());
        int seam_index = 0;

        for (int y = 0; y < height(); y++) {
            int mod_pic_index = 0;
            for (int x = 0; x < width(); x++) {
                if (x == seam[seam_index]) {
                    System.out.println("DEBUG: Removing " + seam[seam_index]);
                    continue;
                }
                mod_pic.set(mod_pic_index++, y, picture.get(x, y));
                System.out.println("DEBUG: For col - " + mod_pic_index + ", row - " + y);
                System.out.println("DEBUG: Energy - " + energy(mod_pic_index, y));
            }
            seam_index++;
        }
        picture = mod_pic;
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

        int[] seam = seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(seam);

        System.out.println("DEBUG: Energy matrix after removing");
        for (int i = 0; i < seamCarver.height(); i++) {
            for (int j = 0; j < seamCarver.width(); j++) {
                System.out.print(Math.round(seamCarver.energy(j, i)) + " ");
            }
            System.out.println();
        }
        System.out.println();

        //seamCarver.findHorizontalSeam();
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
