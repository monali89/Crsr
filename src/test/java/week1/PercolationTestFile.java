package week1;

import org.junit.Assert;
import org.junit.Test;
import week1.Percolation;

import java.io.*;

/**
 * Date: 10/5/2019
 * @author: Monali
 */

public class PercolationTestFile {

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
    public void testInput01(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input1.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput02(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input2.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput03(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input3.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput04(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input4.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput05(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input5.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput06(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input6.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput07(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input7.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput08(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input8.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput08_2(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input8-no.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(false, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput10(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input10.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput10_2(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input10-no.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.split(" ");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(false, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput20(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input20.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.trim().split("\\s+");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInput50(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "input50.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.trim().split("\\s+");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputJava(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "java60.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.trim().split("\\s+");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputJerry(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "jerry47.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.trim().split("\\s+");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputSedgewick(){

        try {
            String folderPath = "D:\\Monali\\KN\\OOPs, DS, Algo\\Coursera\\Percolation\\";
            String fileName = "sedgewick60.txt";

            File file = new File(folderPath + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int n = Integer.parseInt(bufferedReader.readLine());
            Percolation p = new Percolation(n);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] tempArr = line.trim().split("\\s+");
                int row = Integer.parseInt(tempArr[0]);
                int col = Integer.parseInt(tempArr[1]);
                try{
                    p.open(row, col);
                    Assert.assertEquals(true, p.isOpen(row, col));
                }catch(IllegalArgumentException e){
                    Assert.assertTrue("("+row+","+col+")", row <= 0 || col <= 0 || row > n || col > n);
                }
            }
            bufferedReader.close();
            //printGrid(p, n);
            Assert.assertEquals(true, p.percolates());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
