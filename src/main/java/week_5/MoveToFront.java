package week_5;

import edu.princeton.cs.algs4.BinaryStdIn;

/**
 * @author Monali L on 3/9/2021
 */

public class MoveToFront {

    private static char[] mtf;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        System.out.println("In encode");
        // mtf = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
        mtf = new char[26];
        for (int i = 0; i < 26; i++) {
            mtf[i] = (char) ('A' + i);
        }

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();

            // find char in mtf
            int idx = 0;
            while (mtf[idx] != c) {
                idx++;
            }

            System.out.print(idx + " ");

            // push char to front and left rotate array
            for (int i = idx; i > 0; i--) {
                mtf[i] = mtf[i-1];
            }
            mtf[0] = c;

            // BinaryStdOut.write(idx);

        }
        // BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        System.out.println("In decode");
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        }

        /*while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();*/

        /*boolean bit = false;
        while (!BinaryStdIn.isEmpty())
        {
            int run = BinaryStdIn.readInt(8);
            for (int i = 0; i < run; i++)
                BinaryStdOut.write(bit);
            bit = !bit;
        }
        BinaryStdOut.close();*/

    }

}
