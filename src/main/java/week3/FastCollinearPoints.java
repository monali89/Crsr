package week3;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

/**
 * Date: 10/14/2019
 * @author: Monali
 */

public class FastCollinearPoints {

    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){

        lineSegments = new ArrayList<LineSegment>();
        Set<String> tempSet = new HashSet<String>();

        for (int i = 0; i < points.length; i++) {

            List<Point> otherPoints = new ArrayList<Point>();
            for (int j = 0; j < points.length; j++) {
                if(i == j) continue;
                otherPoints.add(points[j]);
            }

            Collections.sort(otherPoints, points[i].slopeOrder());

            int s = 0;
            int e = s+1;
            int count = 1;
            List<Point> collinearPoints = new ArrayList<Point>();
            collinearPoints.add(otherPoints.get(s));
            while(e < otherPoints.size()){
                if(points[i].slopeTo(otherPoints.get(s)) == points[i].slopeTo(otherPoints.get(e))){
                    count++;
                    collinearPoints.add(otherPoints.get(e));
                    e++;
                }else{
                    if(count >= 3){
                        collinearPoints.add(points[i]);
                        Collections.sort(collinearPoints, new ByNaturalOrder());
                        LineSegment ls = new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size()-1));
                        if(!tempSet.contains(ls.toString())){
                            tempSet.add(ls.toString());
                            lineSegments.add(ls);
                        }
                    }
                    collinearPoints.clear();
                    count = 1;
                    s = e;
                    e = s+1;
                }
            }

            /*Map<Double, List<Point>> map = new HashMap<Double, List<Point>>();
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
                    Point start = tempList.get(0);
                    Point end = tempList.get(tempList.size() - 1);
                    if (points[i].compareTo(start) <= 0) {
                        start = points[i];
                    } else if (points[i].compareTo(end) >= 1) {
                        end = points[i];
                    }
                    LineSegment curr = new LineSegment(start, end);
                    boolean flag = false;
                    for (LineSegment seg : lineSegments) {
                        if (seg.toString().equals(curr.toString())) flag = true;
                    }
                    if (!flag) lineSegments.add(curr);
                }
            }*/
        }
    }

    private static class ByNaturalOrder implements Comparator<Point>{
        public int compare(Point p1, Point p2){
            return p1.compareTo(p2);
        }
    }

    // the number of line segments
    public int numberOfSegments(){
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

        FastCollinearPoints p = new FastCollinearPoints(ip);
        LineSegment[] lseg = p.segments();
        for (int i = 0; i < lseg.length; i++) {
            System.out.println(lseg[i].toString());
        }
    }
}
