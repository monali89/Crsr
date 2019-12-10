package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Date: 12/2/2019
 * @author: Monali
 */

public class PointSET {

    private final SortedSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!pointSet.contains(p)) pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        Iterator<Point2D> itr = pointSet.iterator();
        while (itr.hasNext()) itr.next().draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) throw new IllegalArgumentException();

        SortedSet<Point2D> insidePoints = new TreeSet<Point2D>();
        Iterator<Point2D> itr = pointSet.iterator();

        while (itr.hasNext()) {
            Point2D p = itr.next();
            if (rect.contains(p)) insidePoints.add(p);
        }
        return insidePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Iterator<Point2D> itr = pointSet.iterator();
        double minDist = Double.MAX_VALUE;
        Point2D minPoint = null;
        while (itr.hasNext()) {
            Point2D tempP = itr.next();
            double thisDist = tempP.distanceSquaredTo(p);
            if (thisDist <= minDist) {
                minPoint = tempP;
                minDist = thisDist;
            }
        }
        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET obj = new PointSET();
        Point2D[] points = new Point2D[4];
        points[0] = new Point2D(100, 200);
        points[1] = new Point2D(-50, 100);
        points[2] = new Point2D(23, 16);
        points[3] = new Point2D(10, 9);
        for (int i = 0; i < points.length; i++) obj.insert(points[i]);
        obj.draw();
    }
}
