package week3;

import java.util.Arrays;

/**
 * Date: 10/14/2019
 * @author: Monali
 */

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int size;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

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

        Point[] otherPoints = points.clone();

        for (int i = 0; i < points.length; i++) {

            Arrays.sort(otherPoints, points[i].slopeOrder());

            int s = 1;
            int e = s+1;
            int count = 1;
            while (e < otherPoints.length) {
                if (points[i].slopeTo(otherPoints[s]) == points[i].slopeTo(otherPoints[e])) {
                    count++;
                    e++;
                } else {
                    if (count >= 3 && points[i].compareTo(otherPoints[s]) <= 0) {
                        if (size == lineSegments.length) resizeArray(size*2);
                        lineSegments[size++] = new LineSegment(points[i], otherPoints[e]);
                    }
                    count = 1;
                    s = e;
                    e = e + 1;
                }
            }
            if (count >= 3 && points[i].compareTo(otherPoints[s]) <= 0) {
                if (size == lineSegments.length) resizeArray(size*2);
                lineSegments[size++] = new LineSegment(points[i], otherPoints[e-1]);
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
