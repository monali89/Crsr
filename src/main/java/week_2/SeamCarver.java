package week_2;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

/**
 * @author Monali L on 2/29/2020
 */

public class SeamCarver {

    class Pixel {
        int x;
        int y;
        double energy;
    }

    private Picture picture;
    private Digraph dg;
    private int size;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();

        this.picture = picture;
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

        /*Topological topological = new Topological(dg);
        System.out.println("DEBUG: T hasOrder - " + topological.hasOrder());
        System.out.println("DEBUG: T Order - " + topological.order());*/
        System.out.println("DEBUG: dg - " + dg.toString());
    }

    private int getIndex(int x, int y) {
        int index = x * picture.width() + y;
        System.out.println("DEBUG: Getting Index (" + x + "," + y + ") - " + index);
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
        if (x <0 || y < 0 || x > picture.width() || y > picture.height())
            throw new IllegalArgumentException();

        // Border coordinate
        if(x == 0 || x == picture.height()-1 || y == 0 || y == picture.width()-1)
            return 1000.0;

        // X gradient
        int x_red = picture.get(y, x+1).getRed() - picture.get(y, x-1).getRed();
        int x_blue = picture.get(y, x+1).getBlue() - picture.get(y, x-1).getBlue();
        int x_green = picture.get(y, x+1).getGreen() - picture.get(y, x-1).getGreen();
        int x_sqr = x_red*x_red + x_blue*x_blue + x_green*x_green;

        // Y gradient
        int y_red = picture.get(y+1, x).getRed() - picture.get(y-1, x).getRed();
        int y_blue = picture.get(y+1, x).getBlue() - picture.get(y-1, x).getBlue();
        int y_green = picture.get(y+1, x).getGreen() - picture.get(y-1, x).getGreen();
        int y_sqr = y_red*y_red + y_blue*y_blue + y_green*y_green;

        double energy = Math.sqrt(x_sqr + y_sqr);
        System.out.println("DEBUG: Energy for pixel + (" + x + "," + y + ") - " + energy);

        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] arr = new int[height()];
        double[] distTo = new double[height()];
        int[] edgeTo = new int[height()];

        int y = 2;
        distTo[0] = 0;
        edgeTo[0] = -1;

        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(height());
        pq.insert(0, energy(0, 2));

        for (int x = 0; x < height()-1; x++) {
            // left
            double left_e = energy(x+1, y-1);
            // center
            double center_e = energy(x+1, y);
            // right
            double right_e = energy(x+1, y+1);

        }

        return null;
    }

    public void verticalSeamHelper(int start) {
        double[] distTo = new double[height()];
        int[] prev = new int[height()];
        int y = start;
        prev[0] = start;
        distTo[0] = 1000.0;
        for (int x = 0; x < height()-1; x++) {
            double l_e = 0.0, c_e = 0.0, r_e = 0.0;
            if (y-1 > 0) l_e = energy(x+1, y-1);
            c_e = energy(x+1, y);
            if (y+1 < width()) r_e = energy(x+1, y+1);
            if (l_e <= c_e) {
                if (l_e <= r_e) { // left is min
                    distTo[x+1] = distTo[x] + l_e;
                    prev[x+1] = y-1;
                } else { // right is min
                    distTo[x+1] = distTo[x] + r_e;
                    prev[x+1] = y+1;
                }
            } else {
                if (c_e <= r_e) { // center is min
                    distTo[x+1] = distTo[x] + c_e;
                    prev[x+1] = y;
                } else { // right is min
                    distTo[x+1] = distTo[x] + r_e;
                    prev[x+1] = y+1;
                }
            }
            y = prev[x+1];
        }
        System.out.println("DEBUG: Final distance - " + distTo[height()-1]);
        System.out.println("DEBUG: Edges - ");
        for (int i = 0; i < prev.length; i++) {
            System.out.println("Prev[" + i + "] - " + prev[i]);
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

        Picture picture = new Picture(4,3);
        SeamCarver seamCarver = new SeamCarver(picture);
        seamCarver.verticalSeamHelper(3);
        //Topological topological = new Topological();

    }

}
