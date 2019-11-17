package week1;

import org.junit.Assert;
import org.junit.Test;
import week1.Percolation;

/**
 * Date: 10/5/2019
 * @author: Monali
 */


public class PercolationTest {

    public void printGrid(Percolation p, int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(p.isOpen(i, j) ? " T |" : " F |");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Test
    public void test01(){
        Percolation p = new Percolation(3);
        p.open(0,0);
        Assert.assertEquals(true, p.isOpen(0,0));
        Assert.assertEquals(false, p.isOpen(0,1));
        Assert.assertEquals(false, p.isOpen(2,0));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(1, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(0,0));
    }

    @Test
    public void test02(){
        Percolation p = new Percolation(3);
        p.open(1,1);
        Assert.assertEquals(true, p.isOpen(1,1));
        Assert.assertEquals(false, p.isOpen(0,1));
        Assert.assertEquals(false, p.isOpen(2,0));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(1, p.numberOfOpenSites());
        Assert.assertEquals(false, p.isFull(1,1));
    }

    @Test
    public void test03(){
        Percolation p = new Percolation(3);
        p.open(2,2);
        Assert.assertEquals(true, p.isOpen(2,2));
        Assert.assertEquals(false, p.isOpen(0,2));
        Assert.assertEquals(false, p.isOpen(2,0));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(1, p.numberOfOpenSites());
        Assert.assertEquals(false, p.isFull(2,2));
    }

    @Test
    public void test04() {

        Percolation p = new Percolation(3);

        p.open(0, 0);
        Assert.assertEquals(true, p.isOpen(0,0));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(1, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(0,0));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(i == 0 && j == 0) continue;
                Assert.assertEquals(false, p.isOpen(i,j));
                Assert.assertEquals(false, p.isFull(i,j));
            }
        }
    }

    @Test
    public void test05() {

        Percolation p = new Percolation(3);

        p.open(0, 0);
        printGrid(p, 3);
        Assert.assertEquals(true, p.isOpen(0,0));
        Assert.assertEquals(false, p.isOpen(0,2));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(1, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(0,0));
        Assert.assertEquals(false, p.isFull(0,1));

        p.open(1, 0);
        Assert.assertEquals(true, p.isOpen(1,0));
        Assert.assertEquals(false, p.isOpen(1,1));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(2, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(1,0));
        Assert.assertEquals(false, p.isFull(1,1));

        p.open(1, 1);
        Assert.assertEquals(true, p.isOpen(1,1));
        Assert.assertEquals(false, p.isOpen(0,1));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(3, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(1,1));

        p.open(1, 2);
        Assert.assertEquals(true, p.isOpen(1,2));
        Assert.assertEquals(false, p.isOpen(0,2));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(4, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(1,2));

        p.open(2, 2);
        Assert.assertEquals(true, p.isOpen(2,2));
        Assert.assertEquals(false, p.isOpen(2,0));
        Assert.assertEquals(true, p.percolates());
        Assert.assertEquals(5, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(2,2));
        Assert.assertEquals(false, p.isFull(2,0));
    }

    @Test
    public void test06() {

        Percolation p = new Percolation(3);

        p.open(0,0);
        Assert.assertEquals(true, p.isOpen(0,0));
        Assert.assertEquals(false, p.isOpen(0,2));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(1, p.numberOfOpenSites());
        Assert.assertEquals(true, p.isFull(0,0));

        p.open(1, 1);
        Assert.assertEquals(true, p.isOpen(1,1));
        Assert.assertEquals(false, p.isOpen(1,0));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(2, p.numberOfOpenSites());
        Assert.assertEquals(false, p.isFull(1,0));

        p.open(2, 2);
        Assert.assertEquals(true, p.isOpen(2,2));
        Assert.assertEquals(false, p.isOpen(2,1));
        Assert.assertEquals(false, p.percolates());
        Assert.assertEquals(3, p.numberOfOpenSites());
        Assert.assertEquals(false, p.isFull(2,2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test07(){
        Percolation p = new Percolation(-23);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test08(){
        Percolation p = new Percolation(Integer.MAX_VALUE + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test09(){
        Percolation p = new Percolation(3);
        p.open(23,43);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test10(){
        Percolation p = new Percolation(3);
        p.isOpen(23,43);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test11(){
        Percolation p = new Percolation(3);
        p.isFull(23,43);
    }




}
