package week3;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Random;

/**
 * Date: 11/8/2019
 * @author: Monali
 */

public class PointTest {

    @Test(dataProvider = "GeneratePoints")
    public void test_compareTo(int x0, int y0, int x1, int y1){

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
    public void test_slopeTo(int x0, int y0, int x1, int y1) {
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


    public void test_temp(){

        Point p1, p2, p3;
        Comparator<Point> itr;

        System.out.println("TEST 1: All points on same line with p1 as the smallest");
        p1 = new Point(1,1);
        p2 = new Point(2,2);
        p3 = new Point(3,3);
        System.out.println("Comparing "+ p2.toString() +" to "+ p1.toString() +": " + p1.compareTo(p2));
        System.out.println("Comparing "+ p3.toString() +" to "+ p1.toString() +": " + p1.compareTo(p3));
        System.out.println("Slope of "+ p2.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p2));
        System.out.println("Slope of "+ p3.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p3));
        itr = p1.slopeOrder();
        System.out.print("Comparing slopes to "+ p1.toString() +": ");
        if(itr.compare(p2,p3) == 0) System.out.println(p2.toString() +"'s slope equal to "+ p3.toString());
        if(itr.compare(p2,p3) > 0) System.out.println(p2.toString() +"'s slope greater than "+ p3.toString());
        if(itr.compare(p2,p3) < 0) System.out.println(p2.toString() +"'s slope less than "+ p3.toString());
        System.out.println();

        System.out.println("TEST 2: All points on same line with p1 as the middle");
        p1 = new Point(2,2);
        p2 = new Point(1,1);
        p3 = new Point(3,3);
        System.out.println("Comparing "+ p2.toString() +" to "+ p1.toString() +": " + p1.compareTo(p2));
        System.out.println("Comparing "+ p3.toString() +" to "+ p1.toString() +": " + p1.compareTo(p3));
        System.out.println("Slope of "+ p2.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p2));
        System.out.println("Slope of "+ p3.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p3));
        itr = p1.slopeOrder();
        System.out.print("Comparing slopes to "+ p1.toString() +": ");
        if(itr.compare(p2,p3) == 0) System.out.println(p2.toString() +"'s slope equal to "+ p3.toString());
        if(itr.compare(p2,p3) > 0) System.out.println(p2.toString() +"'s slope greater than "+ p3.toString());
        if(itr.compare(p2,p3) < 0) System.out.println(p2.toString() +"'s slope less than "+ p3.toString());
        System.out.println();

        System.out.println("TEST 3: All points on different lines");
        p1 = new Point(4,3);
        p2 = new Point(2,7);
        p3 = new Point(5,6);
        System.out.println("Comparing "+ p2.toString() +" to "+ p1.toString() +": " + p1.compareTo(p2));
        System.out.println("Comparing "+ p3.toString() +" to "+ p1.toString() +": " + p1.compareTo(p3));
        System.out.println("Slope of "+ p2.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p2));
        System.out.println("Slope of "+ p3.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p3));
        itr = p1.slopeOrder();
        System.out.print("Comparing slopes to "+ p1.toString() +": ");
        if(itr.compare(p2,p3) == 0) System.out.println(p2.toString() +"'s slope equal to "+ p3.toString());
        if(itr.compare(p2,p3) > 0) System.out.println(p2.toString() +"'s slope greater than "+ p3.toString());
        if(itr.compare(p2,p3) < 0) System.out.println(p2.toString() +"'s slope less than "+ p3.toString());
        System.out.println();

        System.out.println("TEST 4: Points in different quadrants");
        p1 = new Point(-4,8);
        p2 = new Point(6,-3);
        p3 = new Point(-5,-2);
        System.out.println("Comparing "+ p2.toString() +" to "+ p1.toString() +": " + p1.compareTo(p2));
        System.out.println("Comparing "+ p3.toString() +" to "+ p1.toString() +": " + p1.compareTo(p3));
        System.out.println("Slope of "+ p2.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p2));
        System.out.println("Slope of "+ p3.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p3));
        itr = p1.slopeOrder();
        System.out.print("Comparing slopes to "+ p1.toString() +": ");
        if(itr.compare(p2,p3) == 0) System.out.println(p2.toString() +"'s slope equal to "+ p3.toString());
        if(itr.compare(p2,p3) > 0) System.out.println(p2.toString() +"'s slope greater than "+ p3.toString());
        if(itr.compare(p2,p3) < 0) System.out.println(p2.toString() +"'s slope less than "+ p3.toString());
        System.out.println();

        System.out.println("TEST 5: Points parallel to X axis");
        p1 = new Point(-4,3);
        p2 = new Point(6,3);
        p3 = new Point(-5,3);
        System.out.println("Comparing "+ p2.toString() +" to "+ p1.toString() +": " + p1.compareTo(p2));
        System.out.println("Comparing "+ p3.toString() +" to "+ p1.toString() +": " + p1.compareTo(p3));
        System.out.println("Slope of "+ p2.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p2));
        System.out.println("Slope of "+ p3.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p3));
        itr = p1.slopeOrder();
        System.out.print("Comparing slopes to "+ p1.toString() +": ");
        if(itr.compare(p2,p3) == 0) System.out.println(p2.toString() +"'s slope equal to "+ p3.toString());
        if(itr.compare(p2,p3) > 0) System.out.println(p2.toString() +"'s slope greater than "+ p3.toString());
        if(itr.compare(p2,p3) < 0) System.out.println(p2.toString() +"'s slope less than "+ p3.toString());
        System.out.println();

        System.out.println("TEST 6: Points parallel to Y axis");
        p1 = new Point(3,-3);
        p2 = new Point(3,8);
        p3 = new Point(3,-2);
        System.out.println("Comparing "+ p2.toString() +" to "+ p1.toString() +": " + p1.compareTo(p2));
        System.out.println("Comparing "+ p3.toString() +" to "+ p1.toString() +": " + p1.compareTo(p3));
        System.out.println("Slope of "+ p2.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p2));
        System.out.println("Slope of "+ p3.toString() +" to "+ p1.toString() +": " + p1.slopeTo(p3));
        itr = p1.slopeOrder();
        System.out.print("Comparing slopes to "+ p1.toString() +": ");
        if(itr.compare(p2,p3) == 0) System.out.println(p2.toString() +"'s slope equal to "+ p3.toString());
        if(itr.compare(p2,p3) > 0) System.out.println(p2.toString() +"'s slope greater than "+ p3.toString());
        if(itr.compare(p2,p3) < 0) System.out.println(p2.toString() +"'s slope less than "+ p3.toString());
        System.out.println();
    }

}
