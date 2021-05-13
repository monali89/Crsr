package week_5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

/**
 * @author Monali L on 3/9/2021
 */
public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
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
                BinaryStdOut.write(i, 32);
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
        char[] t = BinaryStdIn.readString().toCharArray();
        char[] firstCol = Arrays.copyOf(t, t.length);
        Arrays.sort(firstCol);

        int[] next = new int[t.length];
        Arrays.fill(next, -1);
        next[0] = first;

        for (int i = 0; i < t.length; i++) {
            for (int j = 1; j < firstCol.length; j++) {
                if (t[i] == firstCol[j] && next[j] == -1) {
                    next[j] = i;
                    break;
                }
            }
        }

        int i = first;
        int ctr = 0;
        while (i < next.length && ctr < next.length) {
            BinaryStdOut.write(firstCol[i], 8);
            i = next[i];
            ctr++;
        }
        BinaryStdOut.close();
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
