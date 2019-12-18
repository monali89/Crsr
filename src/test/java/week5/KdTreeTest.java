package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Monali L on 12/9/2019
 */

public class KdTreeTest {
    @Test
    public void test01_constructor_is_created() {
        Point2D p = new Point2D(23,23);
        KdTree obj = new KdTree();
        Assert.assertTrue(true);
    }

    @Test
    public void test02_isEmpty_true() {
        KdTree obj = new KdTree();
        Assert.assertTrue(obj.isEmpty());
    }

    @Test
    public void test02_isEmpty_false() {
        Point2D p = new Point2D(.23,.23);
        KdTree obj = new KdTree();
        obj.insert(p);
        Assert.assertFalse(obj.isEmpty());
    }

    @Test
    public void test03_size_unique_points() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(.23,.23));
        obj.insert(new Point2D(.12,.23));
        obj.insert(new Point2D(.23,.16));
        obj.insert(new Point2D(.10,.9));
        Assert.assertEquals(4, obj.size());
    }

    @Test
    public void test03_size_duplicate_points() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(.23,.23));
        obj.insert(new Point2D(.12,.23));
        obj.insert(new Point2D(.23,.16));
        obj.insert(new Point2D(.10,.9));
        obj.insert(new Point2D(.23,.23));
        Assert.assertEquals(4, obj.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test04_insert_exception() {
        KdTree obj = new KdTree();
        obj.insert(null);
    }

    @Test
    public void test04_insert_successful() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(23,23));
        Assert.assertEquals(1, obj.size());
    }

    @Test
    public void test04_insert_already_present() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(.23,.23));
        obj.insert(new Point2D(.12,.23));
        obj.insert(new Point2D(.23,.16));
        obj.insert(new Point2D(.10,.9));
        Assert.assertEquals(4, obj.size());
        obj.insert(new Point2D(.23,.23));
        Assert.assertEquals(4, obj.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test05_contains_exception() {
        KdTree obj = new KdTree();
        obj.contains(null);
    }

    @Test
    public void test05_contains_true() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(.23,.23));
        obj.insert(new Point2D(.12,.23));
        obj.insert(new Point2D(.23,.16));
        obj.insert(new Point2D(.10,.9));
        Assert.assertEquals(true, obj.contains(new Point2D(12,23)));
    }

    @Test
    public void test05_contains_false() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(.23,.23));
        obj.insert(new Point2D(.12,.23));
        obj.insert(new Point2D(.23,.16));
        obj.insert(new Point2D(.10,.9));
        Assert.assertEquals(false, obj.contains(new Point2D(21,23)));
    }

    @Test
    public void test06_draw() { Assert.assertTrue("Not possible to test", true); }

    @Test(expected = IllegalArgumentException.class)
    public void test07_range_exception() {
        KdTree obj = new KdTree();
        obj.range(null);
    }

    @Test
    public void test07_range_points_are_inside() {
        KdTree obj = new KdTree();
        Point2D point = new Point2D(.23, .23);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,1,1);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_left_side() {
        KdTree obj = new KdTree();
        Point2D point = new Point2D(100, 70);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_right_side() {
        KdTree obj = new KdTree();
        Point2D point = new Point2D(60, 0);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_top_side() {
        KdTree obj = new KdTree();
        Point2D point = new Point2D(40, 100);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,100,100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_point_on_bottom_side() {
        KdTree obj = new KdTree();
        Point2D point = new Point2D(.50, 0);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,.100,.100);
        Iterable<Point2D> itr = obj.range(rect);
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test
    public void test07_range_no_points_are_inside() {
        KdTree obj = new KdTree();
        Point2D point = new Point2D(.10, .10);
        obj.insert(point);
        RectHV rect = new RectHV(0,0,.100,.100);
        Iterable<Point2D> itr = obj.range(rect);
    }

    @Test
    public void test07_range_from_course1() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(0.625, 0.875));
        obj.insert(new Point2D(0.0, 0.0    ));
        obj.insert(new Point2D(0.625, 0.25 ));
        obj.insert(new Point2D(0.625, 0.5  ));
        obj.insert(new Point2D(0.125, 0.0  ));
        obj.insert(new Point2D(0.25, 0.125 ));
        obj.insert(new Point2D(0.125, 0.25 ));
        obj.insert(new Point2D(0.875, 0.375));
        obj.insert(new Point2D(0.5, 0.25   ));
        obj.insert(new Point2D(0.0, 1.0    ));
        obj.insert(new Point2D(0.375, 0.875));
        obj.insert(new Point2D(0.375, 0.375));
        obj.insert(new Point2D(0.875, 0.875));
        obj.insert(new Point2D(0.625, 0.75 ));
        obj.insert(new Point2D(0.375, 0.75 ));
        obj.insert(new Point2D(0.375, 0.25 ));
        obj.insert(new Point2D(0.875, 0.75 ));
        obj.insert(new Point2D(0.875, 0.625));
        obj.insert(new Point2D(0.75, 0.125 ));
        obj.insert(new Point2D(0.75, 0.875 ));
        RectHV rect = new RectHV(0.0, 0.125,0.125, 0.875);
        Point2D point = new Point2D(0.125, 0.25);
        Iterable<Point2D> itr = obj.range(rect);
        System.out.println("Inside rect - " + itr);
        //obj.tempPrint();
        for (Point2D p: itr)
            Assert.assertTrue(p.equals(point));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test08_nearest_exception() {
        KdTree obj = new KdTree();
        obj.nearest(null);
    }

    @Test
    public void test08_nearest_not_null_set() {
        KdTree obj = new KdTree();
        Point2D[] points = new Point2D[4];
        points[0] = new Point2D(.23,.23);
        points[1] = new Point2D(.12,.23);
        points[2] = new Point2D(.23,.16);
        points[3] = new Point2D(.10,.9);
        for (int i = 0; i < 4; i++) obj.insert(points[i]);
        Assert.assertTrue("Nearest Point: " + obj.nearest(new Point2D(.5,.5)), obj.nearest(new Point2D(.5,.5)).equals(new Point2D(.10, .9)));
    }

    @Test
    public void test08_nearest_null_set() {
        KdTree obj = new KdTree();
        Assert.assertEquals(null, obj.nearest(new Point2D(5,5)));
    }

    @Test
    public void test08_nearest_from_course() {
        KdTree obj = new KdTree();
        Point2D[] points = new Point2D[5];
        points[0] = new Point2D(.7,.2);
        points[1] = new Point2D(.5,.4);
        points[2] = new Point2D(.2,.3);
        points[3] = new Point2D(.4,.7);
        points[4] = new Point2D(.9,.6);
        for (int i = 0; i < points.length; i++) obj.insert(points[i]);
        //obj.tempPrint();
        Point2D queryPoint = new Point2D(0.517, 0.956);
        Point2D expected = new Point2D(.4, .7);
        Point2D actual = obj.nearest(queryPoint);
        Assert.assertEquals(expected.toString(), obj.nearest(queryPoint).toString());
    }

    @Test
    public void test08_nearest_from_course_2() {
        KdTree obj = new KdTree();
        obj.insert(new Point2D(0.372, 0.497));
        obj.insert(new Point2D(0.564, 0.413));
        obj.insert(new Point2D(0.226, 0.577));
        obj.insert(new Point2D(0.144, 0.179));
        obj.insert(new Point2D(0.083, 0.51));
        obj.insert(new Point2D(0.32, 0.708));
        obj.insert(new Point2D(0.417, 0.362));
        obj.insert(new Point2D(0.862, 0.825));
        obj.insert(new Point2D(0.785, 0.725));
        obj.insert(new Point2D(0.499, 0.208));
        Point2D queryPoint = new Point2D(0.767, 0.194);
        Point2D expected = new Point2D(0.499, 0.208);
        Point2D actual = obj.nearest(queryPoint);
        Assert.assertEquals(expected.toString(), obj.nearest(queryPoint).toString());
    }
}
