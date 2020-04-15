package week_2;

import edu.princeton.cs.algs4.Picture;

/**
 * @author Monali L on 2/29/2020
 */

public class SeamCarver {

    private static final double BORDER_ENERGY = 1000.0;
    private Picture picture;
    private int[][] yx;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.picture = new Picture(picture);
        this.yx = new int[height()*width()][2];
    }

    // current picture
    public Picture picture() {
        Picture newPicture = new Picture(this.picture);
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
        boolean[][] isVisited = new boolean[height()][width()];
        int[] minArray = new int[width()];

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                distTo[i][j] = j == 0 ? BORDER_ENERGY : Double.POSITIVE_INFINITY;
            }
        }

        for (int j = 0; j < height()*width(); j++) {

            int current = getMinDistIndex(distTo, isVisited);
            int y = yx[current][0]; // col
            int x = yx[current][1]; // row
            if (x >= width()-1) break;

            isVisited[y][x] = true;

            // because everything here is width x height (cols x rows)
            for (int i = -1; i <= 1; i++) {
                if (y + i <= -1 || y + i >= height()) continue;
                double dist = energy(x + 1, y + i) + distTo[y][x];
                if (!isVisited[y+i][x+1] && distTo[y+i][x+1] > dist) {
                    distTo[y+i][x+1] = dist;
                    edgeTo[y+i][x+1] = current;
                }
            }
        }

        // Get min array
        double minEnergy = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int i = 0; i < height(); i++) {
            if (distTo[i][width()-1] < minEnergy) {
                minEnergy = distTo[i][width()-1];
                minIndex = getIndex(width()-1, i);
            }
        }

        for (int i = width()-1; i > -1; i--) {
            int y = yx[minIndex][0]; // col
            minArray[i] = y;
            minIndex = edgeTo[y][i];
        }

        return minArray;
    }

    // sequence of indices for vertical seam
    // Performance requirements: O(N) = width x length

    public int[] findVerticalSeam() {

        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        boolean[][] isVisited = new boolean[height()][width()];
        int[] minArray = new int[height()];

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                distTo[i][j] = i == 0 ? BORDER_ENERGY : Double.POSITIVE_INFINITY;
            }
        }

        for (int j = 0; j < height()*width(); j++) {

            int current = getMinDistIndex(distTo, isVisited);
            int y = yx[current][0]; // col
            int x = yx[current][1]; // row
            if (y >= height()-1) continue;

            isVisited[y][x] = true;

            // because everything here is width x height (cols x rows)
            for (int i = -1; i <= 1; i++) {
                if (x + i <= -1 || x + i >= width()) continue;
                double dist = energy(x + i, y + 1) + distTo[y][x];
                if (!isVisited[y+1][x+i] && distTo[y+1][x+i] > dist) {
                    distTo[y+1][x+i] = dist;
                    edgeTo[y+1][x+i] = current;
                }
            }
        }

        // Get min array
        double minEnergy = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int i = 0; i < width(); i++) {
            if (distTo[height()-1][i] < minEnergy) {
                minEnergy = distTo[height()-1][i];
                minIndex = getIndex(i, height()-1);
            }
        }

        for (int i = height()-1; i > -1; i--) {
            int x = yx[minIndex][1]; // row
            minArray[i] = x;
            minIndex = edgeTo[i][x];
        }

        return minArray;
    }

    private int getMinDistIndex(double[][] distTo, boolean[][] isVisited) {

        double minDist = Double.POSITIVE_INFINITY;
        int minIndex = -1;

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (!isVisited[i][j] && distTo[i][j] < minDist) {
                    minDist = distTo[i][j];
                    minIndex = getIndex(j, i);
                }
            }
        }
        return minIndex;
    }

    private int getIndex(int width, int height) {
        int ind = height*width() + width;
        yx[ind][0] = height;
        yx[ind][1] = width;
        return ind;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (seam == null || seam.length >= picture.width() || picture.height() <= 1 || !isSeamValid(seam))
            throw new IllegalArgumentException();

        Picture modPic = new Picture(width(), height()-1);
        int seamIndex = 0;

        for (int x = 0; x < width(); x++) {
            int modPicIndex = 0;
            for (int y = 0; y < height(); y++) {
                if (y == seam[seamIndex]) continue;
                modPic.setRGB(x, modPicIndex++, picture.getRGB(x, y));
            }
            seamIndex++;
        }
        picture = modPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        if (seam == null || seam.length >= picture.height() || picture.width() <= 1 || !isSeamValid(seam))
            throw new IllegalArgumentException();

        Picture modPic = new Picture(width()-1, height());
        int seamIndex = 0;

        for (int y = 0; y < height(); y++) {
            int modPicIndex = 0;
            for (int x = 0; x < width(); x++) {
                if (x == seam[seamIndex]) continue;
                modPic.setRGB(modPicIndex++, y, picture.getRGB(x, y));
            }
            seamIndex++;
        }
        picture = modPic;
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
