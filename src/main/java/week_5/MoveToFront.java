package week_5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * @author Monali L on 3/9/2021
 */

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] mtf = new char[256];
        for (int i = 0; i < mtf.length; i++) {
            mtf[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);

            // find char in mtf
            int idx = 0;
            while (idx < mtf.length && mtf[idx] != c) idx++;
            // System.out.print(idx + " ");

            // push char to front and left rotate array
            for (int i = idx; i > 0; i--) mtf[i] = mtf[i-1];
            mtf[0] = c;

            BinaryStdOut.write(idx, 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        System.out.println("Decode");
        char[] mtf = new char[256];
        for (int i = 0; i < mtf.length; i++) {
            mtf[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            int idx = BinaryStdIn.readChar(8);
            char c = mtf[idx];
            BinaryStdOut.write(mtf[idx], 8);
            // System.out.println(idx + " "  + c);

            // move this char to the front
            for (int i = c; i > 0; i--) mtf[i] = mtf[i-1];
            mtf[0] = c;
        }
        BinaryStdOut.close();

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
