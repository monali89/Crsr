package week_5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 3/9/2021
 */
public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        int first = -1;
        String inputString = BinaryStdIn.readString();
        List<String> originalSuffixes = new ArrayList<String>();

        // create a table, where the rows are all possible rotations of s
        for (int i = 0; i < inputString.length(); i++) {
            String leftRotated = inputString.substring(i) + inputString.substring(0, i);
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
            if (st.equals(inputString)) first = i;
        }

        BinaryStdOut.write(first, 8*4);
        for (int i = 0; i < sb.length(); i++) {
            BinaryStdOut.write(sb.charAt(i), 8);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String inputString = BinaryStdIn.readString();

        List<String> sortedSuffixes = new ArrayList<>();

        for (int i = 0; i < inputString.length(); i++) {
            sortedSuffixes.add(i, "");
        }

        for (int i = 0; i < inputString.length(); i++) {
            for (int j = 0; j < inputString.length(); j++) {
                sortedSuffixes.set(j, inputString.charAt(j) + sortedSuffixes.get(j));
            }
            sortedSuffixes.sort(String::compareTo);
        }

        System.out.println(sortedSuffixes.get(first));
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {

        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}
