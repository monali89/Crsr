package week_5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 3/9/2021
 */
public class BurrowsWheeler {

    private static int first;
    private static String inputString;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
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

        System.out.println(first);
        System.out.println(sb);
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
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
            // try {
                //BufferedReader bf = new BufferedReader(new FileReader(args[1]));
                inputString = args[1]; // bf.readLine();
                transform();
            /*} catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } else if (args[0].equals("+")) {
            // Scanner in = new Scanner(System.in);
            first = Integer.valueOf(args[1]); // in.nextInt();
            inputString = args[2]; // in.next();
            inverseTransform();
        }
    }
}
