package week_5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 3/9/2021
 */
public class CircularSuffixArray {

    private List<String> originalSuffixes = new ArrayList<String>();
    private List<String> sortedSuffixes = new ArrayList<String>();
    private char START = 256;
    private char END = 257;

    // circular suffix array of s
    public CircularSuffixArray(String s) {

        // s = START + s + END;
        originalSuffixes = new ArrayList<>();

        // create a table, where the rows are all possible rotations of s
        for (int i = 0; i < s.length(); i++) {
            String leftRotated = s.substring(i) + s.substring(0, i);
            originalSuffixes.add(leftRotated);
        }

        // sort rows alphabetically
        sortedSuffixes = new ArrayList<>(originalSuffixes);
        sortedSuffixes.sort(String::compareTo);
    }

    // length of s
    public int length() {return -1;}

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > originalSuffixes.size()) throw new IllegalArgumentException();
        return originalSuffixes.indexOf(sortedSuffixes.get(i));
    }

    // unit testing (required)
    public static void main(String[] args) {
        String input = "ABRACADABRA!";
        CircularSuffixArray obj = new CircularSuffixArray(input);
        for (int i = 0; i < input.length(); i++) {
            System.out.println(i + " - " + obj.index(i));
        }
    }
}
