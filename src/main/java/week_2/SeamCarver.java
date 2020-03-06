package week_2;

import edu.princeton.cs.algs4.Digraph;
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
        System.out.println("DEBUG: Energy for pixel + (" + x + "," + y + ") - " + energy);

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
        //System.out.println(seamCarver);

    }

}
