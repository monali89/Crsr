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

    private final Trie[] trieArray;
    private List<String> validWords;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {

        trieArray = new Trie[26*26];
        for (int i = 0; i < dictionary.length; i++) {
            String w = dictionary[i];
            if (w.length() < 3) continue;
            int idx = (w.charAt(0)-'A')*26 + (w.charAt(1)-'A');
            trieArray[idx] = put(trieArray[idx], w, i+1, 2);
        }
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

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        long startTime = System.currentTimeMillis();

        validWords = new ArrayList<String>();
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
            if (t != null && t.val > 0 && !validWords.contains(str.toString())) {
                validWords.add(str.toString());
            }
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
        args[1] = folderPath + "board-points4540.txt";

        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
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
