package week3;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.*;

/**
 * Date: 11/8/2019
 * @author: Monali
 */

public class PointTest {

    //@Test(dataProvider = "GeneratePoints")
    public void test01_compareTo(int x0, int y0, int x1, int y1) {

        Point p1 = new Point(x0, y0);
        Point p2 = new Point(x1, y1);
        int r = p1.compareTo(p2);
        if(y0 < y1)
            Assert.assertEquals(-1, r);
        else if(y0 > y1)
            Assert.assertEquals(1, r);
        else{
            if(x0 == x1) Assert.assertEquals(0, r);
            else if(x0 > x1) Assert.assertEquals(1, r);
            else Assert.assertEquals(-1 , r);
        }
    }

    //@Test(dataProvider = "GeneratePoints")
    public void test02_slopeTo(int x0, int y0, int x1, int y1) {
        Point p1 = new Point(x0, y0);
        Point p2 = new Point(x1, y1);
        Double s = Double.valueOf((y1 - y0)*1.0 / (x1 - x0)*1.0);
        Assert.assertEquals(s, p1.slopeTo(p2));
    }

    @DataProvider(name = "GeneratePoints")
    public Object[][] dataProvider(){
        int max = 100;
        Random ran = new Random();
        Object[][] obj = new Object[max][4];
        for (int i = 0; i < max; i++) {
            int x1 = ran.nextInt(32767) * (Math.random() > 0.5 ? 1 : -1);
            int y1 = ran.nextInt(32767) * (Math.random() > 0.5 ? 1 : -1);
            int x2 = ran.nextInt(32767) * (Math.random() > 0.5 ? 1 : -1);
            int y2 = ran.nextInt(32767) * (Math.random() > 0.5 ? 1 : -1);
            obj[i][0] = x1;
            obj[i][1] = y1;
            obj[i][2] = x2;
            obj[i][3] = y2;
        }
        return obj;
    }

    // Generating line segments test

    @Test
    public void test03_bruteCollinearPoints(){
        int count = 10;
        int maxXY = 10; //Integer.MAX_VALUE;
        Point[] points = new Point[count];
        Random ran = new Random();
        for (int i = 0; i < count; i++) {
            int x = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            int y = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        LineSegment[] ls = bf.segments();
        System.out.println(bf.numberOfSegments());
        for (int i = 0; i < bf.numberOfSegments(); i++) {
            System.out.println(ls[i]);
        }
    }

    @Test
    public void test04_fastCollinearPoints(){
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = new Point(3,3);
        points[3] = new Point(4,4);
        points[4] = new Point(5,5);
        FastCollinearPoints fc = new FastCollinearPoints(points);
        LineSegment[] ls = fc.segments();
        Assert.assertEquals(1, fc.numberOfSegments());
        Assert.assertEquals("(1, 1) -> (5, 5)", ls[0].toString());
    }

    @Test
    public void test05_refinedFastCollinearPoints(){

        int count = 50;
        int maxXY = 10;

        Random ran = new Random();
        List<Point> pointList = new ArrayList<Point>();
        Set<String> present = new HashSet<String>();
        Point[] points = new Point[count];

        int ctr = 0;
        while(!(pointList.size() > count) && ctr < count){
            int x = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            int y = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            Point p = new Point(x,y);
            if(present.contains(p.toString())) continue;
            pointList.add(p);
            present.add(p.toString());
            points[ctr++] = p;
        }

        System.out.println(pointList.toString());
        FastCollinearPoints fc = new FastCollinearPoints(points);
        LineSegment[] ls = fc.segments();
        Set<String> segments = new HashSet<String>();
        for (int i = 0; i < fc.numberOfSegments(); i++) {
            Assert.assertFalse(segments.contains(ls[i].toString()), "Set: " + segments + "\nAlready Contains: " + ls[i].toString());
            segments.add(ls[i].toString());
        }
        Assert.assertEquals(fc.numberOfSegments(), segments.size());
        System.out.println(segments);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_bruteFroce_constructor_is_null(){
        BruteCollinearPoints bf = new BruteCollinearPoints(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_bruteFroce_point_Is_Null(){
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = null;
        points[3] = new Point(4,4);
        points[4] = new Point(5,5);
        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        System.out.println(bf.numberOfSegments());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_bruteFroce_point_is_repeated(){
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = new Point(3,3);
        points[3] = new Point(4,4);
        points[4] = new Point(1,1);
        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        System.out.println(bf.numberOfSegments());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_fastCollinear_constructor_is_null(){
        BruteCollinearPoints bf = new BruteCollinearPoints(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_fastCollinear_point_Is_Null(){
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = null;
        points[3] = new Point(4,4);
        points[4] = new Point(5,5);
        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        System.out.println(bf.numberOfSegments());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_fastCollinears_point_is_repeated(){
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = new Point(3,3);
        points[3] = new Point(4,4);
        points[4] = new Point(1,1);
        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        System.out.println(bf.numberOfSegments());
    }

    @Test
    public void test_fastCollinear_linesegments(){

        Point s = new Point(17,12);
        Point e = new Point(25,56);
        Point[] points = new Point[10];
        points[0] = s;
        points[1] = e;
        double slope = s.slopeTo(e);

        for (int i = 2; i < points.length; i++) {
            points[i] = getPointBetween(s, e, slope);
        }

        for (int i = 0; i < points.length; i++) System.out.print(points[i] + " ");
        System.out.println();

        FastCollinearPoints fc = new FastCollinearPoints(points);
        LineSegment[] ls = fc.segments();
        System.out.println(fc.numberOfSegments());
        for (int i = 0; i < fc.numberOfSegments(); i++) {
            System.out.println(i + " " + ls[i].toString());
            if(ls[i].toString().equals("(17, 12) -> (25, 56)")){
                Assert.assertTrue(true);
            }
        }
        Assert.assertTrue(false);
    }

    private Point getPointBetween(Point min, Point max, double slope){

        int x = getRandomBetween(17,25);
        int y = (int) slope * (x - 17) + 12;
        Point p = new Point(x, y);

        while(min.compareTo(p) > -1 && max.compareTo(p) < 1){
            x = getRandomBetween(17,25);
            y = (int) slope * (x - 17) + 12;
            p = new Point(x, y);
        }

        return p;
    }

    private int getRandomBetween(int min, int max){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
