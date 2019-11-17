package week3;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * Date: 11/9/2019
 * @author: Monali
 */

public class BruteTest {

    @DataProvider(name = "GeneratePoints")
    public Object[][] dataProvider() {
        int max = 5;
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

    @Test
    public void test_lineSegments(){

    }

}
