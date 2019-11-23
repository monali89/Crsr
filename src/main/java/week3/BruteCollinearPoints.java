package week3;

import java.util.Arrays;

/**
 * Date: 10/13/2019
 * @author: Monali
 */

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int size;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (i > 0 && points[i-1].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
        }

        size = 0;
        lineSegments = new LineSegment[points.length/2];

        for (int a = 0; a < points.length; a++) {
            for (int b = a + 1; b < points.length; b++) {
                for (int c = b + 1; c < points.length; c++) {
                    for (int d = c + 1; d < points.length; d++) {
                        Point[] ap = new Point[4];
                        ap[0] = points[a];
                        ap[1] = points[b];
                        ap[2] = points[c];
                        ap[3] = points[d];
                        Arrays.sort(ap);
                        double slope1 = points[a].slopeTo(points[b]);
                        double slope2 = points[a].slopeTo(points[c]);
                        double slope3 = points[a].slopeTo(points[d]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            if (size >= lineSegments.length) resizeArray(size*2);
                            lineSegments[size++] = new LineSegment(ap[0], ap[3]);
                        }
                    }
                }
            }
        }
    }

    private void resizeArray(int newSize) {
        LineSegment[] copy = new LineSegment[newSize];
        for (int i = 0; i < size; i++) {
            copy[i] = lineSegments[i];
        }
        lineSegments = copy;
    }

    // the number of line segments
    public int numberOfSegments() {
        return size;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] shrinked = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            shrinked[i] = lineSegments[i];
        }
        return shrinked;
    }
}
