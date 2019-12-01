package week4;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * Date: 11/28/2019
 * @author: Monali
 */

public class BoardTest {

    @Test
    public void test01_toString() {
        int[][] b = new int[][]{{1,0,3},{4,2,5},{7,8,6}};
        Board obj = new Board(b);
        String expected = "3\n1 0 3 \n4 2 5 \n7 8 6 \n";
        Assert.assertEquals(expected, obj.toString());
    }

    @Test
    public void test02_dimension() {
        int[][] b = new int[][]{{1,0,3},{4,2,5},{7,8,6}};
        Board obj = new Board(b);
        Assert.assertEquals(3, obj.dimension());
    }

    @Test
    public void test03_hamming() {
        int[][] b = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
        Board obj = new Board(b);
        Assert.assertEquals(5, obj.hamming());
    }

    @Test
    public void test03_manhattan() {
        int[][] b = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
        Board obj = new Board(b);
        Assert.assertEquals(10, obj.manhattan());
    }

    @Test
    public void test04_isGoal_true() {
        int[][] b = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj = new Board(b);
        Assert.assertEquals(true, obj.isGoal());
    }

    @Test
    public void test04_isGoal_false() {
        int[][] b = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
        Board obj = new Board(b);
        Assert.assertEquals(false, obj.isGoal());
    }

    @Test
    public void test05_equals_true() {
        int[][] b1 = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj1 = new Board(b1);
        int[][] b2 = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj2 = new Board(b2);
        Assert.assertEquals(true, obj1.equals(obj2));
    }

    @Test
    public void test05_equals_false() {
        int[][] b1 = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj1 = new Board(b1);
        int[][] b2 = new int[][]{{8,1,3},{4,0,2},{7,6,5}};
        Board obj2 = new Board(b2);
        Assert.assertEquals(false, obj1.equals(obj2));
    }

    // Neighbors is implemented in sequence top, right, bottom, left (clockwise)
    @Test
    public void test06_neighbors_cell_one_blank() {
        int[][] b = new int[][]{{0,1,2},{3,4,5},{6,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        //Board top = null;
        Board right = new Board(new int[][]{{1,0,2},{3,4,5},{6,7,8}});
        Board bottom = new Board(new int[][]{{3,1,2},{0,4,5},{6,7,8}});
        //Board left = null;
        for (Board neighbor: obj.neighbors()) {
            System.out.println(neighbor.toString());
        }
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test07_neighbors_cell_two_blank() {
        int[][] b = new int[][]{{1,0,2},{3,4,5},{6,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        //Board top = new Board(new int[][]{{},{},{}});
        Board right = new Board(new int[][]{{1,2,0},{3,4,5},{6,7,8}});
        Board bottom = new Board(new int[][]{{1,4,2},{3,0,5},{6,7,8}});
        Board left = new Board(new int[][]{{0,1,2},{3,4,5},{6,7,8}});
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));*/
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test08_neighbors_cell_three_blank() {
        int[][] b = new int[][]{{1,2,0},{3,4,5},{6,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        //Board top = new Board(new int[][]{{},{},{}});
        //Board right = new Board(new int[][]{{},{},{}});
        Board bottom = new Board(new int[][]{{1,2,5},{3,4,0},{6,7,8}});
        Board left = new Board(new int[][]{{1,0,2},{3,4,5},{6,7,8}});
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));*/
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test09_neighbors_cell_four_blank() {
        int[][] b = new int[][]{{1,2,3},{0,4,5},{6,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        Board top = new Board(new int[][]{{0,2,3},{1,4,5},{6,7,8}});
        Board right = new Board(new int[][]{{1,2,3},{4,0,5},{6,7,8}});
        Board bottom = new Board(new int[][]{{1,2,3},{6,4,5},{0,7,8}});
        //Board left = null; //new Board(new int[][]{{},{},{}});
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));*/
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test10_neighbors_cell_five_blank() {
        int[][] b = new int[][]{{1,2,3},{4,0,5},{6,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        Board top = new Board(new int[][]{{1,0,3},{4,2,5},{6,7,8}});
        Board right = new Board(new int[][]{{1,2,3},{4,5,0},{6,7,8}});
        Board bottom = new Board(new int[][]{{1,2,3},{4,7,5},{6,0,8}});
        Board left = new Board(new int[][]{{1,2,3},{0,4,5},{6,7,8}});
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test11_neighbors_cell_six_blank() {
        int[][] b = new int[][]{{1,2,3},{4,5,0},{6,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        Board top = new Board(new int[][]{{1,2,0},{4,5,3},{6,7,8}});
        //Board right = new Board(new int[][]{{},{},{}});
        Board bottom = new Board(new int[][]{{1,2,3},{4,5,8},{6,7,0}});
        Board left = new Board(new int[][]{{1,2,3},{4,0,5},{6,7,8}});
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));*/
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test12_neighbors_cell_seven_blank() {
        int[][] b = new int[][]{{1,2,3},{4,5,6},{0,7,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        Board top = new Board(new int[][]{{1,2,3},{0,5,6},{4,7,8}});
        Board right = new Board(new int[][]{{1,2,3},{4,5,6},{7,0,8}});
        //Board bottom = new Board(new int[][]{{},{},{}});
        //Board left = new Board(new int[][]{{},{},{}});
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));*/
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test13_neighbors_cell_eight_blank() {
        int[][] b = new int[][]{{1,2,3},{4,5,6},{7,0,8}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        Board top = new Board(new int[][]{{1,2,3},{4,0,6},{7,5,8}});
        Board right = new Board(new int[][]{{1,2,3},{4,5,6},{7,8,0}});
        //Board bottom = new Board(new int[][]{{},{},{}});
        Board left = new Board(new int[][]{{1,2,3},{4,5,6},{0,7,8}});
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));*/
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test14_neighbors_cell_nine_blank() {
        int[][] b = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj = new Board(b);
        Iterator<Board> itr = obj.neighbors().iterator();
        Board top = new Board(new int[][]{{1,2,3},{4,5,0},{7,8,6}});
        //Board right = new Board(new int[][]{{},{},{}});
        //Board bottom = new Board(new int[][]{{},{},{}});
        Board left = new Board(new int[][]{{1,2,3},{4,5,6},{7,0,8}});
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(top));
        /*Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(right));
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(bottom));*/
        Assert.assertEquals(true, itr.hasNext());
        Assert.assertEquals(true, itr.next().equals(left));
        Assert.assertEquals(false, itr.hasNext());
    }

    @Test
    public void test15_twin() {
        int[][] b = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        Board obj = new Board(b);
        Board twin1 = obj.twin();
        Board twin2 = obj.twin();
        Assert.assertEquals(true, twin1.equals(twin2));
    }

    @Test
    public void test16_Misc() {

        Board b = new Board(new int[][]{
                {1,2,3},
                {4,5,0},
                {7,8,6}
        });

        for (Board neighbor: b.neighbors()) {
            System.out.println(neighbor.manhattan());
            System.out.println(neighbor);
        }
    }


}










