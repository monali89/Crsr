package week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Date: 11/30/2019
 * @author: Monali
 */

public class SolverTest {

    @Test
    public void test01_isSolvable_false_01() {
        int[][] b = new int[][]{
                {1,2,3},
                {4,5,6},
                {7,8,0}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(false, s.isSolvable());
    }

    @Test
    public void test01_isSolvable_false_02() {
        int[][] b = new int[][]{
                {1,0},
                {2,3}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(false, s.isSolvable());
    }

    @Test
    public void test01_isSolvable_false_03() {
        int[][] b = new int[][]{
                { 1, 2, 3, 4},
                { 5, 6, 7, 8},
                { 9,10,11,12},
                {13,14,15,0}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(false, s.isSolvable());
    }

    @Test
    public void test02_isSolvable_true_01() {
        int[][] b = new int[][]{
                {0,1,3},
                {4,2,5},
                {7,8,6}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(true, s.isSolvable());
    }

    @Test
    public void test02_isSolvable_true_02() {
        int[][] b = new int[][]{
                {1,2},
                {0,3}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(true, s.isSolvable());
    }

    @Test
    public void test02_isSolvable_true_03() {
        int[][] b = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9,10,11,12},
                {13,14,0,15}};
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(true, s.isSolvable());
    }

    @Test
    public void test02_isSolvable_true_04() {
        int[][] b = new int[][]{
                {1,3,5},
                {4,2,0},
                {7,8,6}
        };
        Board obj = new Board(b);
        Solver s = new Solver(obj);
        if(s.isSolvable()){
            System.out.println("No. of Moves: " + s.moves());
            for (Board board: s.solution()) {
                System.out.println(board.toString());
            }
        }
        Assert.assertEquals(true, s.isSolvable());
    }
}
