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
        Picture newPicture = this.picture;
        return newPicture;
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
        int xRed = picture.get(x+1, y).getRed() - picture.get(x-1, y).getRed();
        int xBlue = picture.get(x+1, y).getBlue() - picture.get(x-1, y).getBlue();
        int xGreen = picture.get(x+1, y).getGreen() - picture.get(x-1, y).getGreen();
        int xSqr = xRed*xRed + xBlue*xBlue + xGreen*xGreen;

        // Y gradient
        int yRed = picture.get(x, y+1).getRed() - picture.get(x, y-1).getRed();
        int yBlue = picture.get(x, y+1).getBlue() - picture.get(x, y-1).getBlue();
        int yGreen = picture.get(x, y+1).getGreen() - picture.get(x, y-1).getGreen();
        int ySqr = yRed*yRed + yBlue*yBlue + yGreen*yGreen;

        double energy = Math.sqrt(xSqr + ySqr);

        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        double minTotalEnergy = Double.POSITIVE_INFINITY;
        int[] minArray = new int[width()];

        for (int y = 0; y < height(); y++) {
            distTo[y][0] = BORDER_ENERGY;
            edgeTo[y][0] = y;
            int prevY = y;
            for (int x = 0; x < width() - 1; x++) {
                double minEnergy = Double.POSITIVE_INFINITY;
                for (int i = -1; i <= 1; i++) {
                    if (prevY + i * 1 <= -1 || prevY + i * 1 >= height()) continue;
                    double this_energy = energy(x + 1, prevY + i * 1);
                    if (this_energy < minEnergy) {
                        distTo[y][x + 1] = distTo[y][x] + this_energy;
                        edgeTo[y][x + 1] = prevY + i * 1;
                        minEnergy = this_energy;
                    }
                }
                prevY = edgeTo[y][x + 1];
            }
            if (distTo[y][width() - 1] < minTotalEnergy) {
                minTotalEnergy = distTo[y][width() - 1];
                minArray = edgeTo[y];
            }
        }
        return minArray;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];
        double minTotalEnergy = Double.POSITIVE_INFINITY;
        int[] minArray = new int[height()];

        for (int x = 0; x < width(); x++) {
            distTo[x][0] = BORDER_ENERGY;
            edgeTo[x][0] = x;
            int prevX = x;
            for (int y = 0; y < height()-1; y++) {
                double minEnergy = Double.POSITIVE_INFINITY;
                for (int i = -1; i <= 1; i++) {
                    if (prevX + i*1 <= -1 || prevX + i*1 >= width()) continue;
                    double this_energy = energy(prevX + i*1, y+1);
                    if (this_energy < minEnergy) {
                        distTo[x][y+1] = distTo[x][y] + this_energy;
                        edgeTo[x][y+1] = prevX + i*1;
                        minEnergy = this_energy;
                    }
                }
                prevX = edgeTo[x][y+1];
            }
            if (distTo[x][height()-1] < minTotalEnergy) {
                minTotalEnergy = distTo[x][height()-1];
                minArray = edgeTo[x];
            }
        }
        return minArray;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null || seam.length > picture.width() || picture.height() <= 1 || !isSeamValid(seam))
            throw new IllegalArgumentException();

        Picture mod_pic = new Picture(width(), height()-1);
        int seamIndex = 0;

        for (int x = 0; x < width(); x++) {
            int modPicIndex = 0;
            for (int y = 0; y < height(); y++) {
                if (y == seam[seamIndex]) continue;
                mod_pic.setRGB(x, modPicIndex++, picture.getRGB(x, y));
            }
            seamIndex++;
        }
        picture = mod_pic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        if (seam == null || seam.length > picture.height() || picture.width() <= 1 || !isSeamValid(seam))
            throw new IllegalArgumentException();

        Picture mod_pic = new Picture(width()-1, height());
        int seamIndex = 0;

        for (int y = 0; y < height(); y++) {
            int modPicIndex = 0;
            for (int x = 0; x < width(); x++) {
                if (x == seam[seamIndex]) continue;
                mod_pic.setRGB(modPicIndex++, y, picture.getRGB(x, y));
            }
            seamIndex++;
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

        Picture picture = new Picture(5, 5);
        SeamCarver seamCarver = new SeamCarver(picture);

        System.out.println("Picture");
        System.out.println(seamCarver.picture().toString());

        int[] seamVertical = seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(seamVertical);
        System.out.println("Removing vertical seam");
        System.out.println(seamCarver.picture().toString());

        int[] seamHorizontal = seamCarver.findHorizontalSeam();
        seamCarver.removeHorizontalSeam(seamHorizontal);
        System.out.println("Removing horizontal seam");
        System.out.println(seamCarver.picture().toString());

        // TEMP
        System.out.println("DEBUG: picture rgb - " + picture.getRGB(0, 0));
    }

}
