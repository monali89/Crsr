package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Assert;
import org.junit.Test;
import week3.Point;

import java.util.Iterator;


/**
 * Date: 12/4/2019
 * @author: Monali
 */

public class PointSetTest {

    @Test
    public void test01_constructor_is_created() {
        Point2D p = new Point2D(23,23);
        PointSET obj = new PointSET();
        Assert.assertTrue(true);
    }

    @Test
    public void test02_isEmpty_true() {
        PointSET obj = new PointSET();
        Assert.assertTrue(obj.isEmpty());
    }

    @Test
    public void test02_isEmpty_false() {
        Point2D p = new Point2D(23,23);
        PointSET obj = new PointSET();
        obj.insert(p);
        Assert.assertFalse(obj.isEmpty());
    }

    @Test
    public void test03_size_unique_points() {
        PointSET obj = new PointSET();
        obj.insert(new Point2D(23,23));
        obj.insert(new Point2D(12,23));
        obj.insert(new Point2D(23,16));
        obj.insert(new Point2D(10,9));
        Assert.assertEquals(4, obj.size());
    }

    @Test
    public void test03_size_duplicate_points() {
        PointSET obj = new PointSET();
        obj.insert(new Point2D(23,23));
        obj.insert(new Point2D(12,23));
        obj.insert(new Point2D(23,16));
        obj.insert(new Point2D(10,9));
        obj.insert(new Point2D(23,23));
        Assert.assertEquals(4, obj.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test04_insert_exception() {
        PointSET obj = new PointSET();
        obj.insert(null);
    }

    @Test
    public void test04_insert_successful() {
        PointSET obj = new PointSET();
        obj.insert(new Point2D(23,23));
        Assert.assertEquals(1, obj.size());
    }

    @Test
    public void test04_insert_already_present() {
        PointSET obj = new PointSET();
        obj.insert(new Point2D(23,23));
        obj.insert(new Point2D(12,23));
        obj.insert(new Point2D(23,16));
        obj.insert(new Point2D(10,9));
        Assert.assertEquals(4, obj.size());
        obj.insert(new Point2D(23,23));
        Assert.assertEquals(4, obj.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test05_contains_exception() {
        PointSET obj = new PointSET();
        obj.contains(null);
    }

    @Test
    public void test05_contains_true() {
        PointSET obj = new PointSET();
        obj.insert(new Point2D(23,23));
        obj.insert(new Point2D(12,23));
        obj.insert(new Point2D(23,16));
        obj.insert(new Point2D(10,9));
        Assert.assertEquals(true, obj.contains(new Point2D(12,23)));
    }

    @Test
    public void test05_contains_false() {
        PointSET obj = new PointSET();
        obj.insert(new Point2D(23,23));
        obj.insert(new Point2D(12,23));
        obj.insert(new Point2D(23,16));
        obj.insert(new Point2D(10,9));
        Assert.assertEquals(false, obj.contains(new Point2D(21,23)));
    }

    @Test
    public void test06_draw() { Assert.assertTrue("Not possible to test", true); }

    @Test(expected = IllegalArgumentException.class)
    public void test07_range_exception() {
        PointSET obj = new PointSET();
        obj.range(null);
    }

    @Test
    public void test07_range_points_are_inside() {
        PointSET obj = new PointSET();
        Point2D point = new Point2D(23, 23);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_left_side() {
        PointSET obj = new PointSET();
        Point2D point = new Point2D(100, 70);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_right_side() {
        PointSET obj = new PointSET();
        Point2D point = new Point2D(60, 0);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_top_side() {
        PointSET obj = new PointSET();
        Point2D point = new Point2D(40, 100);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_bottom_side() {
        PointSET obj = new PointSET();
        Point2D point = new Point2D(50, 0);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_no_points_are_inside() {
        PointSET obj = new PointSET();
        Point2D point = new Point2D(-10, -10);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test08_nearest_exception() {
        PointSET obj = new PointSET();
        obj.nearest(null);
    }

    @Test
    public void test08_nearest_not_null_set() {
        PointSET obj = new PointSET();
        Point2D[] points = new Point2D[4];
        points[0] = new Point2D(23,23);
        points[1] = new Point2D(12,23);
        points[2] = new Point2D(23,16);
        points[3] = new Point2D(10,9);
        for (int i = 0; i < 4; i++) obj.insert(points[i]);
        Assert.assertTrue("Nearest Point: " + obj.nearest(new Point2D(5,5)), obj.nearest(new Point2D(5,5)).equals(new Point2D(10, 9)));
    }

    @Test
    public void test08_nearest_null_set() {
        PointSET obj = new PointSET();
        Assert.assertEquals(null, obj.nearest(new Point2D(5,5)));
    }

}
