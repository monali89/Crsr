package week_5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 3/9/2021
 */

public class CircularSuffixArray {

    private static List<String> originalSuffixes = new ArrayList<String>();
    private static List<String> sortedSuffixes = new ArrayList<String>();
    private static String inputString = "";

    // circular suffix array of s
    public CircularSuffixArray(String s) {

        inputString = s;
        originalSuffixes = new ArrayList<>();

        // create a table, where the rows are all possible rotations of s
        for (int i = 0; i < inputString.length(); i++) {
            String leftRotated = inputString.substring(i) + inputString.substring(0, i);
            originalSuffixes.add(leftRotated);
        }

        // sort rows alphabetically
        sortedSuffixes = new ArrayList<>(originalSuffixes);
        sortedSuffixes.sort(String::compareTo);
    }

    // length of s
    public int length() {
        return inputString.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= inputString.length()) throw new IllegalArgumentException();
        return originalSuffixes.indexOf(sortedSuffixes.get(i));
    }

    // unit testing (required)
    public static void main(String[] args) {
        String input = "ABRACADABRA!";
        CircularSuffixArray obj = new CircularSuffixArray(input);
        System.out.println("Length - " + obj.length());
        for (int i = 0; i < input.length(); i++) {
            System.out.println(i + " - " + obj.index(i));
        }
    }
}
