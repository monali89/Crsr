package week3;

import java.util.Arrays;

/**
 * Date: 10/14/2019
 * @author: Monali
 */

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int lsIndex;

    private Point[] collinearPoints;
    private int cpIndex;

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

        lsIndex = 0;
        lineSegments = new LineSegment[points.length/2];

        for (int i = 0; i < points.length; i++) {

            Point[] otherPoints = points.clone();
            Arrays.sort(otherPoints, points[i].slopeOrder());

            collinearPoints = new Point[1];
            cpIndex = 0;

            for (int j = 1; j < otherPoints.length; j++) {
                if (cpIndex == 0) {
                    addToCollinearArray(otherPoints[j]);
                } else {
                    double slope1 = points[i].slopeTo(collinearPoints[cpIndex - 1]);
                    double slope2 = points[i].slopeTo(otherPoints[j]);
                    if (slope1 == slope2) {
                        addToCollinearArray(otherPoints[j]);
                    } else {
                        if (cpIndex >= 3) {
                            Point[] tempArr = new Point[cpIndex];
                            for (int k = 0; k < cpIndex; k++) {
                                tempArr[k] = collinearPoints[k];
                            }
                            Arrays.sort(tempArr);
                            if (points[i].compareTo(tempArr[0]) <= 0) {
                                addToLineSegmentArray(new LineSegment(points[i], collinearPoints[cpIndex - 1]));
                            }
                        }
                        collinearPoints = new Point[1];
                        cpIndex = 0;
                        addToCollinearArray(otherPoints[j]);
                    }
                }
            }
            if (cpIndex >= 3) {
                Point[] tempArr = new Point[cpIndex];
                for (int k = 0; k < cpIndex; k++) {
                    tempArr[k] = collinearPoints[k];
                }
                Arrays.sort(tempArr);
                if (points[i].compareTo(tempArr[0]) <= 0) {
                    addToLineSegmentArray(new LineSegment(points[i], collinearPoints[cpIndex - 1]));
                }
                collinearPoints = new Point[1];
                cpIndex = 0;
            }
        }
    }

    private void addToCollinearArray(Point p) {
        if (cpIndex == collinearPoints.length) {
            Point[] copy = new Point[cpIndex * 2];
            for (int i = 0; i < cpIndex; i++) {
                copy[i] = collinearPoints[i];
            }
            collinearPoints = copy;
        }
        collinearPoints[cpIndex++] = p;
    }

    private void addToLineSegmentArray(LineSegment ls) {
        if (lsIndex == lineSegments.length) {
            LineSegment[] copy = new LineSegment[lsIndex*2];
            for (int i = 0; i < lsIndex; i++) {
                copy[i] = lineSegments[i];
            }
            lineSegments = copy;
        }
        lineSegments[lsIndex++] = ls;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lsIndex;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] shrinked = new LineSegment[lsIndex];
        for (int i = 0; i < lsIndex; i++) {
            shrinked[i] = lineSegments[i];
        }
        return shrinked;
    }
}
