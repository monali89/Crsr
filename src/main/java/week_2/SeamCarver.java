package week_2;

import edu.princeton.cs.algs4.Picture;

/**
 * @author Monali L on 2/29/2020
 */

public class SeamCarver {

    private Picture picture;
    private static final double BORDER_ENERGY = 1000.0;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.picture = picture;
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
        if (x < 0 || y < 0 || x >= picture.width() || y >= picture.height())
            throw new IllegalArgumentException();

        // Border coordinate
        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1)
            return BORDER_ENERGY;

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

        return energy;
    }

    public int[] findHorizontalSeam() {

        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 1; j < width(); j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
                edgeTo[i][j] = -1;
            }
            distTo[i][0] = BORDER_ENERGY;
            edgeTo[i][0] = 0;
        }

        for (int x = 0; x < width()-1; x++) {
            for (int y = 0; y < height(); y++) {
                for (int i = -1; i <= 1; i++) {
                    if ((y + i) < 0 || (y + i) >= height()) continue;
                    if (distTo[y+i][x+1] > (distTo[y][x] + energy(x+1, y+i))) {
                        distTo[y+i][x+1] = distTo[y][x] + energy(x+1, y+i);
                        edgeTo[y+i][x+1] = y;
                    }
                }
            }
        }

        // Find min array
        // get min y from last row
        double minDist = distTo[0][width()-1];
        int minY = 0;
        for (int i = 1; i < height(); i++) {
            if (minDist > distTo[i][width()-1]) {
                minDist = distTo[i][width()-1];
                minY = i;
            }
        }

        // Get y for all rows
        int[] minArray = new int[width()];
        int thisMinY = minY;
        minArray[width()-1] = minY;
        for (int i = width()-2; i > -1; i--) {
            minArray[i] = edgeTo[thisMinY][i+1];
            thisMinY = minArray[i];
        }
        return minArray;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                distTo[i][j] = i == 0 ? BORDER_ENERGY : Double.POSITIVE_INFINITY;
                edgeTo[i][j] = i == 0 ? i : -1;
            }
        }

        for (int y = 0; y < height()-1; y++) {
            for (int x = 0; x < width(); x++) {
                for (int i = -1; i <= 1; i++) {
                    if ((x + i) < 0 || (x + i) >= width()) continue;
                    if (distTo[y+1][x+i] > (distTo[y][x] + energy(x+i, y+1))) {
                        distTo[y+1][x+i] = (distTo[y][x] + energy(x+i, y+1));
                        edgeTo[y+1][x+i] = x;
                    }
                }
            }
        }

        // Find min array
        // get min x from last row
        double minDist = distTo[height()-1][0];
        int minX = 0;
        for (int i = 1; i < width(); i++) {
            if (minDist > distTo[height()-1][i]) {
                minDist = distTo[height()-1][i];
                minX = i;
            }
        }

        // Get x for all rows
        int[] minArray = new int[height()];
        int thisMinX = minX;
        minArray[height()-1] = minX;
        for (int i = height()-2; i > -1; i--) {
            minArray[i] = edgeTo[i+1][thisMinX];
            thisMinX = minArray[i];
        }
        return minArray;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null || seam.length > picture.width() || seam.length < picture.width() || picture.height() <= 1 || !isSeamValid(seam))
            throw new IllegalArgumentException();

        Picture mod_pic = new Picture(width(), height()-1);
        int seam_index = 0;

        for (int x = 0; x < width(); x++) {
            int mod_pic_index = 0;
            for (int y = 0; y < height(); y++) {
                if (y == seam[seam_index]) continue;
                mod_pic.set(x, mod_pic_index++, picture.get(x, y));
            }
            seam_index++;
        }
        picture = mod_pic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        if (seam == null || seam.length > picture.height() || seam.length < picture.height() || picture.width() <= 1 || !isSeamValid(seam))
            throw new IllegalArgumentException();

        Picture mod_pic = new Picture(width()-1, height());
        int seam_index = 0;

        for (int y = 0; y < height(); y++) {
            int mod_pic_index = 0;
            for (int x = 0; x < width(); x++) {
                if (x == seam[seam_index]) continue;
                mod_pic.set(mod_pic_index++, y, picture.get(x, y));
            }
            seam_index++;
        }
        picture = mod_pic;
    }

    private boolean isSeamValid(int[] seam) {
        for (int i = 0; i < seam.length-1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1) return false;
        }
        return true;
    }

    //  unit testing (optional)
    public static void main(String[] args) {

        Picture picture = new Picture("../../resources/week_2/6x5.png");
        SeamCarver seamCarver = new SeamCarver(picture);

        System.out.println(seamCarver.picture().toString());

        int[] seam_vertical = seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(seam_vertical);
        System.out.println(seamCarver.picture().toString());

        int[] seam_horizontal = seamCarver.findHorizontalSeam();
        seamCarver.removeHorizontalSeam(seam_horizontal);
        System.out.println(seamCarver.picture().toString());
    }

}
