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
        RectHV leftBottom;
        RectHV rightTop;
        Node(Point2D p, Color color, RectHV leftBottom, RectHV rightTop) {
            this.point = p;
            this.left = null;
            this.right = null;
            this.color = color;
            this.leftBottom = leftBottom;
            this.rightTop = rightTop;
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
        // System.out.println("DEBUG: Point -" + p.toString());
        if (root == null) {
            root = new Node(p, Color.RED,
                    new RectHV(0, 0, p.x(), 1),
                    new RectHV(p.x(), 0, 1, 1));
            size++;
            return;
        }
        Node curr = root;
        while (curr != null) {
            Point2D currPoint = curr.point;
            // System.out.println("DEBUG: curr - " + currPoint + "|" + curr.color);
            if (currPoint.equals(p)) return;
            if (curr.color == Color.RED) {
                if (p.x() < currPoint.x()) {
                    if (curr.left == null) {
                        // System.out.println("DEBUG: Inserting in left of " + currPoint);
                        RectHV parent = curr.leftBottom;
                        curr.left = new Node(p, Color.BLACK,
                                new RectHV(parent.xmin(), parent.ymin(), parent.xmax(), p.y()), // Bottom
                                new RectHV(parent.xmin(), p.y(), parent.xmax(), parent.ymax())); // Top
                        size++;
                        return;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        // System.out.println("DEBUG: Inserting in right of " + currPoint);
                        RectHV parent = curr.rightTop;
                        curr.right = new Node(p, Color.BLACK,
                                new RectHV(parent.xmin(), parent.ymin(), parent.xmax(), p.y()), // Bottom
                                new RectHV(parent.xmin(), p.y(), parent.xmax(), parent.ymax())); // Top
                        size++;
                        return;
                    }
                    curr = curr.right;
                }
            } else if (curr.color == Color.BLACK) {
                if (p.y() < currPoint.y()) {
                    if (curr.left == null) {
                        // System.out.println("DEBUG: Inserting in bottom of " + currPoint);
                        RectHV parent = curr.leftBottom;
                        curr.left = new Node(p, Color.RED,
                                new RectHV(parent.xmin(), parent.ymin(), p.x(), parent.ymax()),  // Left
                                new RectHV(p.x(), parent.ymin(), parent.xmax(), parent.ymax())); // Right
                        size++;
                        return;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        // System.out.println("DEBUG: Inserting in top of " + currPoint);
                        RectHV parent = curr.rightTop;
                        curr.right = new Node(p, Color.RED,
                                new RectHV(parent.xmin(), parent.ymin(), p.x(), parent.ymax()),  // Left
                                new RectHV(p.x(), parent.ymin(), parent.xmax(), parent.ymax())); // Right
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
        Point2D currPoint = node.point;
        if (rect.contains(currPoint)) point2DS.add(currPoint);
        if (rect.intersects(node.leftBottom)) {
            rangeHelper(node.left, rect, point2DS);
        }
        if (rect.intersects(node.rightTop)) {
            rangeHelper(node.right, rect, point2DS);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearestHelper(root, p, root.point.distanceSquaredTo(p));
    }

    private Point2D nearestHelper(Node node, Point2D p, double minDist) {
        if (node == null) return null;
        double leftBottomDist = node.leftBottom.distanceTo(p);
        double rightTopDistance = node.rightTop.distanceTo(p);
        //System.out.println("DEBUG: Node - " + node.point + "|" + leftBottomDist + "|" + rightTopDistance);
        if (leftBottomDist < rightTopDistance) {
            if (minDist > leftBottomDist && node.left != null)
                return nearestHelper(node.left, p, node.left.point.distanceSquaredTo(p));
        } else {
            if (minDist > rightTopDistance && node.right != null)
                return nearestHelper(node.right, p, node.right.point.distanceSquaredTo(p));
        }
        return node.point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree obj = new KdTree();
        Point2D[] points = new Point2D[5];
        points[0] = new Point2D(0.7, 0.2);
        points[1] = new Point2D(0.5, 0.4);
        points[2] = new Point2D(0.2, 0.3);
        points[3] = new Point2D(0.4, 0.7);
        points[4] = new Point2D(0.9, 0.6);
        for (int i = 0; i < points.length; i++) obj.insert(points[i]);
        Point2D queryPoint = new Point2D(0.517, 0.956);
        Point2D expected = new Point2D(.4, .7);
        Point2D actual = obj.nearest(new Point2D(0.517, 0.956));
        System.out.println("DEBUG: Expected - " + expected + ", Actual - " + actual);
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].toString() + " - " + queryPoint.distanceSquaredTo(points[i]));
        }
    }
}
