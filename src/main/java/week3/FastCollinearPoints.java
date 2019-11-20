package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Date: 10/14/2019
 * @author: Monali
 */

public class FastCollinearPoints {

    private final List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if(points == null) throw new IllegalArgumentException();

        Collections.sort(Arrays.asList(points), new ByNaturalOrder());

        lineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {

            if (points[i] == null) throw new java.lang.IllegalArgumentException();

            List<Point> otherPoints = new ArrayList<Point>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                otherPoints.add(points[j]);
            }

            Collections.sort(otherPoints, points[i].slopeOrder());

            int s = 0;
            int e = s+1;
            int count = 1;
            List<Point> collinearPoints = new ArrayList<Point>();
            collinearPoints.add(otherPoints.get(s));
            while (e < otherPoints.size()) {
                if (points[i].slopeTo(otherPoints.get(s)) == points[i].slopeTo(otherPoints.get(e))) {
                    count++;
                    collinearPoints.add(otherPoints.get(e));
                    e++;
                } else {
                    if (count >= 3) {
                        Collections.sort(collinearPoints, new ByNaturalOrder());
                        if (points[i].compareTo(collinearPoints.get(0)) < 0) {
                            LineSegment ls = new LineSegment(points[i], collinearPoints.get(collinearPoints.size()-1));
                            lineSegments.add(ls);
                        }
                    }
                    collinearPoints.clear();
                    count = 1;
                    s = e;
                    e = s+1;
                }
            }
            if(count >= 3 && e >= otherPoints.size()) {
                Collections.sort(collinearPoints, new ByNaturalOrder());
                if (points[i].compareTo(collinearPoints.get(0)) < 0) {
                    LineSegment ls = new LineSegment(points[i], collinearPoints.get(collinearPoints.size()-1));
                    lineSegments.add(ls);
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
