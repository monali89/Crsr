package week_5;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Monali L on 3/9/2021
 */

public class CircularSuffixArray {

    /*private List<String> originalSuffixes;
    private List<String> sortedSuffixes;*/
    private String inputString = "";
    private char[] ora;
    private Integer[] indexArray;
    private String[] sortedSuffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {

        if (s == null) throw new IllegalArgumentException();

        inputString = s;
        ora = s.toCharArray(); // original suffix array
        indexArray = new Integer[s.length()];
        sortedSuffixes = new String[length()];

        for (int i = 0; i < length(); i++) {
            sortedSuffixes[i] = inputString.substring(i) + inputString.substring(0, i);
        }

        for (int i = 0; i < s.length(); i++) {
            indexArray[i] = i;
        }

        /*for (int i = 0; i < indexArray.length; i++) {
            System.out.print(s.charAt(indexArray[i]));
        }
        System.out.println();*/

        Arrays.sort(indexArray, new IndexOrder());

        for (int i = 0; i < indexArray.length; i++) {
            System.out.print(indexArray[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < indexArray.length; i++) {
            System.out.print(s.charAt(indexArray[i]) + " ");
        }
        System.out.println();

        /*inputString = s;
        originalSuffixes = new ArrayList<>();

        // create a table, where the rows are all possible rotations of s
        for (int i = 0; i < inputString.length(); i++) {
            String leftRotated = inputString.substring(i) + inputString.substring(0, i);
            originalSuffixes.add(leftRotated);
        }

        // sort rows alphabetically
        sortedSuffixes = new ArrayList<>(originalSuffixes);
        sortedSuffixes.sort(String::compareTo);*/
    }

    private class IndexOrder implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            System.out.println("Comparing: " + o1 + "-" + inputString.charAt(o1) + " and " + o2 +"-" + inputString.charAt(o2));
            int rslt = 0;
            if (inputString.charAt(o1) != inputString.charAt(o2)) {
                rslt = inputString.charAt(o1) - inputString.charAt(o2);
            } else {
                rslt = o1 - o2;
            }
            for (int i = 0; i < indexArray.length; i++) {
                System.out.print(indexArray[i] + " ");
            }
            System.out.println();
            return rslt;
        }
    }

    // length of s
    public int length() {
        return inputString.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= inputString.length()) throw new IllegalArgumentException();
        // return originalSuffixes.indexOf(sortedSuffixes.get(i));
        return indexArray[i];
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
