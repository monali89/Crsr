package week_5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 3/9/2021
 */
public class BurrowsWheeler {

    private static int first;
    private static String t;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform(String s) {
        List<String> originalSuffixes = new ArrayList<String>();

        // create a table, where the rows are all possible rotations of s
        for (int i = 0; i < s.length(); i++) {
            String leftRotated = s.substring(i) + s.substring(0, i);
            originalSuffixes.add(leftRotated);
        }

        // sort rows alphabetically
        List<String> sortedSuffixes = new ArrayList<>(originalSuffixes);
        sortedSuffixes.sort(String::compareTo);

        // return (last column of the table)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortedSuffixes.size(); i++) {
            String st = sortedSuffixes.get(i);
            sb.append(st.charAt(st.length() - 1));
            if (st.equals(s)) first = i;
        }

        t = sb.toString();
        System.out.println("first - " + first + ", transform - " + sb.toString());

    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {}

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        String path = "";
        transform("BANANA");
        transform("ABRACADABRA!");
        /*try {
            FileInputStream fileRead = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
