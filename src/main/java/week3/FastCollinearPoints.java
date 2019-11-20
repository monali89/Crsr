package week3;

import java.util.*;

/**
 * Date: 10/14/2019
 * @author: Monali
 */

public class FastCollinearPoints {

    //private final List<LineSegment> lineSegments;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            if (i>0 && points[i-1] == points[i]) throw new IllegalArgumentException();
        }


        lineSegments = new LineSegment[10];
        int lsIdx = 0;

        for (int i = 0; i < points.length; i++) {

            Point[] otherPoints = new Point[points.length - 1];
            int idx = 0;
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                otherPoints[idx++] = points[j];
            }

            Arrays.sort(otherPoints, points[i].slopeOrder());
            int s = 0;
            int e = s+1;
            int count = 1;
            while (e < otherPoints.length) {
                if (points[i].slopeTo(otherPoints[s]) == points[i].slopeTo(otherPoints[e])) {
                    count++;
                    e++;
                } else {
                    if (count >= 3 && points[i].compareTo(otherPoints[s]) < 0) {
                        if(lsIdx == lineSegments.length) resizeArray(lsIdx*2);
                        lineSegments[lsIdx++] = new LineSegment(points[i], otherPoints[e]);
                    }
                    count = 1;
                    s = e;
                    e = e + 1;
                }
            }
            if (count >= 3 && points[i].compareTo(otherPoints[s]) < 0) {
                if(lsIdx == lineSegments.length) resizeArray(lsIdx*2);
                lineSegments[lsIdx++] = new LineSegment(points[i], otherPoints[e-1]);
            }
        }

        int index = 0;
        while (index < lineSegments.length && lineSegments[index] != null) {
            index++;
        }
        if (index > 0) resizeArray(index);
    }

    private void resizeArray(int newSize) {
        LineSegment[] newLineSegments = Arrays.copyOfRange(lineSegments, 0, newSize);
        lineSegments = newLineSegments;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = new Point(3,3);
        points[3] = new Point(4,4);
        points[4] = new Point(5,5);
        FastCollinearPoints fc = new FastCollinearPoints(points);
        LineSegment[] ls = fc.lineSegments;
        for (int i = 0; i < fc.numberOfSegments(); i++) {
            System.out.println(ls[i].toString());
        }
    }

}
