package week3;

import java.util.*;

/**
 * Date: 10/13/2019
 * @author: Monali
 */

public class BruteCollinearPoints {

    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){

        lineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {

            List<Point> otherPoints = new ArrayList<Point>();
            for (int j = 0; j < points.length; j++) {
                if(i == j) continue;
                otherPoints.add(points[j]);
            }

            Map<Double, List<Point>> map = new HashMap<Double, List<Point>>();
            for(Point p: otherPoints){
                double currSlope = points[i].slopeTo(p);
                List<Point> tempList = new ArrayList<Point>();
                if(map.containsKey(currSlope)){
                    tempList = map.get(currSlope);
                }
                tempList.add(p);
                map.put(currSlope, tempList);
            }

            for(double s: map.keySet()) {
                if (map.get(s).size() >= 3) {
                    List<Point> tempList = map.get(s);
                    Point start = points[i];
                    Point end = points[i];
                    for(Point currP: tempList){
                        start = start.compareTo(currP) <= 0 ? start : currP;
                        end = end.compareTo(currP) >= 1 ? end : currP;
                    }
                    LineSegment curr = new LineSegment(start, end);
                    boolean flag = false;
                    for (LineSegment seg : lineSegments) {
                        if (seg.toString().equals(curr.toString())) flag = true;
                    }
                    if (!flag) lineSegments.add(curr);
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments(){
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
