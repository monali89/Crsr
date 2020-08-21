package week_4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 7/22/2020
 */

public class BoggleSolver {

    private class Trie {
        int val;
        char c;
        Trie left, mid, right;
    }

    private final Trie root;
    private final Trie[] trieArray;
    private List<String> validWords;
    private List<String> validWords2;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {

        trieArray = new Trie[26*26];
        for (int i = 0; i < dictionary.length; i++) {
            String w = dictionary[i];
            if (w.length() < 3) continue;
            //int idx = (w.charAt(0)-'A')*(w.charAt(1)-'A') + 26;
            int idx = (w.charAt(0)-'A')*26 + (w.charAt(1)-'A');
            trieArray[idx] = put(trieArray[idx], w, i+1, 2);
        }

        /*for (int i = 0; i < trieArray.length; i++) {
            System.out.print((trieArray[i] != null ? trieArray[i].c : "NULL") + " ");
        }
        System.out.println();

        for (String s : dictionary) {
            if (s.length() < 3) continue;
            //int idx = (s.charAt(0) - 'A') * (s.charAt(1) - 'A') + 26;
            int idx = (s.charAt(0)-'A')*26 + (s.charAt(1)-'A');
            Trie t = get(trieArray[idx], s, 2);
            if ((s.charAt(0) + "" + s.charAt(1)).equals("TW"))
            // if (s.equals("ATEYE"))
                System.out.print(s + " - " + (t != null ? t.val : "NULL") + " | ");
        }
        System.out.println();*/

        Trie root1;
        root1 = new Trie();
        for (int i = 0; i < dictionary.length; i++) {
            root1 = put(root1, dictionary[i], i+1, 0);
        }
        root = root1;

        /*for (String s : dictionary) {
            Trie t = get(root1, s, 0);
            System.out.print(s + " - " + (t != null ? t.val : "NULL") + " | ");
        }*/

    }

    private Trie put(Trie x, String key, int val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Trie();
            x.c = c;
            x.val = 0;
        }
        if (c < x.c) x.left = put(x.left, key, val, d);
        else if (c > x.c) x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1) x.mid = put(x.mid, key, val, d+1);
        else x.val = val;
        return x;
    }

    private Trie get(Trie x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid, key, d+1);
        else return x;
    }

    /*private Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, prefix, queue);
        return queue;
    }

    private void collect(Node x, String prefix, Queue<String> q)
    {
        if (x == null) return;
        if (x.val > 0) q.enqueue(prefix);
        collect(x.left, prefix + x.c, q);
        collect(x.mid, prefix + x.c, q);
        collect(x.right, prefix + x.c, q);
    }*/

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        long startTime = System.currentTimeMillis();

        validWords = new ArrayList<String>();
        validWords2 = new ArrayList<String>();
        boolean[][] isVisited = new boolean[board.rows()][board.cols()];
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, str, isVisited);
            }
        }

        System.out.println("DEBUG: Total valid words - " + validWords.size());
        long endTime = System.currentTimeMillis();
        System.out.println("DEBUG: Total run time - " + (endTime - startTime));

        return validWords;
    }

    private void dfs(BoggleBoard b, int rt, int ct, StringBuilder str, boolean[][] isVisited) {

        isVisited[rt][ct] = true;
        str.append(b.getLetter(rt, ct));
        if (str.length() >= 3) {
            int idx = (str.charAt(0)-'A')*26 + (str.charAt(1)-'A');
            Trie t = get(trieArray[idx], str.toString(), 2);
            //Trie t2 = get(root, str.toString(), 0);
            if (t != null && t.val > 0 && !validWords.contains(str.toString())) {
                //System.out.println("DEBUG: " + str + " | " + t.c + " | " + t.val);
                validWords.add(str.toString());
            }
            /* else if (t2 != null && t2.val > 0 && !validWords2.contains(str.toString())) {
                validWords2.add(str.toString());
                System.out.println("Found in other version - " + str);
            }*/
         }
        int[] rows = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] cols = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        for (int i = 0; i < 9; i++) {
            if (rt+rows[i] > -1 && rt+rows[i] < b.rows() && ct+cols[i] > -1 && ct+cols[i] < b.cols() && !isVisited[rt+rows[i]][ct+cols[i]]) {
                dfs(b, rt + rows[i], ct + cols[i], str, isVisited);
            }
        }
        if (str.length() > 0) str.deleteCharAt(str.length()-1);
        isVisited[rt][ct] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {

        int len = word.length();
        if (len < 3) return 0;
        if (len <= 4) return 1;
        if (len == 5) return 2;
        if (len == 6) return 3;
        if (len == 7) return 5;
        else return 11;
    }

    public static void main(String[] args) {

        String folderPath = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\week_4\\";
        args = new String[2];
        args[0] = folderPath + "dictionary-yawl.txt";
        args[1] = folderPath + "board-points300.txt";

        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        //String[] dictionary = {"PAGURIAN", "PAID", "PAIDEUTIC"};
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;

        System.out.println();
        System.out.println(board.toString());

        for (String word : solver.getAllValidWords(board)) {
            StdOut.print(word + ", ");
            score += solver.scoreOf(word);
        }
        StdOut.println();
        StdOut.println("Score = " + score);

        // System.out.println("DEBUG: Keys with prefix TAI - " + solver.keysWithPrefix("TAI"));
    }

}
