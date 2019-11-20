package week3;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

/**
 * Date: 11/8/2019
 * @author: Monali
 */

public class PointTest {

    @Test(dataProvider = "GeneratePoints")
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

    @Test(dataProvider = "GeneratePoints")
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
        int maxXY = Integer.MAX_VALUE;
        Point[] points = new Point[count];
        Random ran = new Random();
        for (int i = 0; i < count; i++) {
            int x = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            int y = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints bf = new BruteCollinearPoints(points);
        for(LineSegment ls: bf.segments()) {
            System.out.println(ls.toString());
        }
    }

    @Test
    public void test04_fastCollinearPoints(){
        int count = 50;
        int maxXY = 10; // Integer.MAX_VALUE;
        Point[] points = new Point[count];
        Random ran = new Random();
        for (int i = 0; i < count; i++) {
            int x = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            int y = ran.nextInt(maxXY) * (Math.random() > 0.5 ? 1 : -1);
            points[i] = new Point(x, y);
        }
        System.out.println(Arrays.toString(points));
        FastCollinearPoints bf = new FastCollinearPoints(points);
        Set<String> segments = new HashSet<String>();
        for(LineSegment ls: bf.segments()){
            Assert.assertFalse(segments.contains(ls.toString()), "Set: " + segments + "\nAlready Contains: " + ls.toString());
            segments.add(ls.toString());
        }
        System.out.println(segments);
    }

    @Test
    public void test05_fastCollinearPoints(){
        Point[] points = new Point[5];
        points[0] = new Point(1,1);
        points[1] = new Point(2,2);
        points[2] = new Point(3,3);
        points[3] = new Point(4,4);
        points[4] = new Point(5,5);
        FastCollinearPoints bf = new FastCollinearPoints(points);
        Set<String> segments = new HashSet<String>();
        for(LineSegment ls: bf.segments()){
            Assert.assertFalse(segments.contains(ls.toString()), "Set: " + segments + "\nAlready Contains:" + ls.toString());
            segments.add(ls.toString());
        }
        System.out.println(segments);
    }

    @Test
    public void test06_lineSegmentDuplicates(){
        List<LineSegment> lineSegments = new ArrayList<LineSegment>();
        LineSegment ls1 = new LineSegment(new Point(1,1), new Point(2,2));
        LineSegment ls2 = new LineSegment(new Point(1,1), new Point(3,3));
        LineSegment ls3 = new LineSegment(new Point(1,1), new Point(2,2));
        lineSegments.add(ls1);
        lineSegments.add(ls2);
        Assert.assertTrue(lineSegments.contains(ls3), "Linesegments to not have ls3: " + lineSegments);
    }

    @Test
    public void test07_refinedFastCollinearPoints(){

        int count = 500;
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
        System.out.println(segments);
    }

}
