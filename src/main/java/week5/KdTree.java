package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 12/2/2019
 * @author: Monali
 */

public class KdTree {

    private Node root;
    private int size;

    private enum Color { RED, BLACK }

    private class Node {
        Point2D point;
        Node left;
        Node right;
        Color color;
        Node(Point2D p, Color color) {
            this.point = p;
            this.left = null;
            this.right = null;
            this.color = color;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        insertHelper(p);
    }

    private void insertHelper(Point2D p) {
        if (root == null) {
            root = new Node(p, Color.RED);
            size++;
            return;
        }
        Node curr = root;
        while (curr != null) {
            Point2D currPoint = curr.point;
            if (currPoint.equals(p)) return;
            if (curr.color == Color.RED) {
                if (p.x() < currPoint.x()) {
                    if (curr.left == null) {
                        curr.left = new Node(p, Color.BLACK);
                        size++;
                        return;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        curr.right = new Node(p, Color.BLACK);
                        size++;
                        return;
                    }
                    curr = curr.right;
                }
            } else {
                if (p.y() < currPoint.y()) {
                    if (curr.left == null) {
                        curr.left = new Node(p, Color.BLACK);
                        size++;
                        return;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        curr.right = new Node(p, Color.BLACK);
                        size++;
                        return;
                    }
                    curr = curr.right;
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node curr = root;
        while (curr != null) {
            if (curr.point.equals(p)) return true;
            if (curr.color == Color.RED) {
                if (p.x() < curr.point.x()) curr = curr.left;
                else curr = curr.right;
            } else {
                if (p.y() < curr.point.y()) curr = curr.left;
                else curr = curr.right;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        drawHelper(root);
    }

    private void drawHelper(Node node) {
        if (node == null) return;
        node.point.draw();
        if (node.left != null) drawHelper(node.left);
        if (node.right != null) drawHelper(node.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> points = new ArrayList<Point2D>();
        rangeHelper(root, rect, points);
        return points;
    }
    private void rangeHelper(Node node, RectHV rect, List<Point2D> point2DS) {
        if (node == null) return;
        if (rect.contains(node.point)) point2DS.add(node.point);
        //if (node.left != null && rect.contains(node.left.point))
            rangeHelper(node.left, rect, point2DS);
        //if (node.right != null && rect.contains(node.right.point))
            rangeHelper(node.right, rect, point2DS);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double minCurr = root != null ? root.point.distanceSquaredTo(p) : Double.POSITIVE_INFINITY;
        return nearestHelper(root, p, minCurr);
    }

    private Point2D nearestHelper(Node node, Point2D p, double minDist) {
        if (node == null) return null;
        double minLeft = node.left != null ? node.left.point.distanceSquaredTo(p) : Double.POSITIVE_INFINITY;
        double minRight = node.right != null ? node.right.point.distanceSquaredTo(p) : Double.POSITIVE_INFINITY;
        if (minLeft <= minRight) {
            if (minLeft < minDist) return nearestHelper(node.left, p, minLeft);
        } else {
            if (minRight < minDist) return nearestHelper(node.right, p, minRight);
        }
        return node.point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree obj = new KdTree();

        // Test1
        /*obj.insert(new Point2D(23,23));
        obj.insert(new Point2D(12,23));
        obj.insert(new Point2D(23,16));
        obj.insert(new Point2D(10,9));*/

        // Test 2
        obj.insert(new Point2D(0.7, 0.2));
        obj.insert(new Point2D(0.5, 0.4));
        obj.insert(new Point2D(0.2, 0.3));
        obj.insert(new Point2D(0.4, 0.7));
        obj.insert(new Point2D(0.9, 0.6));

        // obj.tempPrint();
        System.out.println(obj.contains(new Point2D(0.4, 0.7)));
    }
}
