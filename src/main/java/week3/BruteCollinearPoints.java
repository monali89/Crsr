package week3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date: 10/13/2019
 * @author: Monali
 */

public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        lineSegments = new ArrayList<LineSegment>();
        Set<String> tempSet = new HashSet<String>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])){
                            List<Point> collinearPoints = new ArrayList<Point>();
                            collinearPoints.add(points[i]);
                            collinearPoints.add(points[j]);
                            collinearPoints.add(points[k]);
                            collinearPoints.add(points[l]);
                            Collections.sort(collinearPoints, new ByNaturalOrder());
                            LineSegment ls = new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size()-1));
                            if (!tempSet.contains(ls.toString())){
                                lineSegments.add(ls);
                            }
                        }
                    }
                }
            }
        }
    }

    private static class ByNaturalOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            return p1.compareTo(p2);
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

    public static void main(String[] args) {
        Point[] ip = new Point[5];
        ip[0] = new Point(1,1);
        ip[1] = new Point(2,2);
        ip[2] = new Point(3,3);
        ip[3] = new Point(4,4);
        ip[4] = new Point(4,5);

        BruteCollinearPoints p = new BruteCollinearPoints(ip);
        LineSegment[] lseg = p.segments();
        for (int i = 0; i < lseg.length; i++) {
            System.out.println(lseg[i].toString());
        }
    }
}
