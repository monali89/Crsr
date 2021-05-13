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
        String inputString = "";
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);
            inputString = inputString.concat(String.valueOf(c));
        }

        String[] originalSuffixes = new String[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            String leftRotated = inputString.substring(i) + inputString.substring(0, i);
            originalSuffixes[i] = leftRotated;
        }

        CircularSuffixArray csa = new CircularSuffixArray(inputString);

        String outputString = "";
        for (int i = 0; i < inputString.length(); i++) {
            int idx = csa.index(i);
            if (idx == 0) {
                BinaryStdOut.write(i, 8*4);
            }
            char last = originalSuffixes[idx].charAt(inputString.length()-1);
            outputString = outputString.concat(String.valueOf(last));
        }
        BinaryStdOut.write(outputString);
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
