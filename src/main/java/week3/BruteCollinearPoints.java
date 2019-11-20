package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Date: 10/13/2019
 * @author: Monali
 */

public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        Collections.sort(Arrays.asList(points), new ByNaturalOrder());

        lineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int m = k+1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[k]) == points[i].slopeTo(points[m])) {
                            List<Point> collinearPoints = new ArrayList<Point>();
                            collinearPoints.add(points[i]);
                            collinearPoints.add(points[j]);
                            collinearPoints.add(points[k]);
                            collinearPoints.add(points[m]);
                            Collections.sort(collinearPoints, new ByNaturalOrder());
                            if (points[i].compareTo(collinearPoints.get(0)) < 0) {
                                LineSegment ls = new LineSegment(points[i], collinearPoints.get(collinearPoints.size()-1));
                                lineSegments.add(ls);
                            }
                        }
                    }
                }
            }
        }
    }

    private static class ByNaturalOrder implements Comparator<Point> {
        public int compare(Point o1, Point o2) {
            if (o1 == o2) throw new java.lang.IllegalArgumentException();
            return o1.compareTo(o2);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}
