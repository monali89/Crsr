package week_4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Monali L on 7/22/2020
 */

public class BoggleSolver {

    private TrieSET dict;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {

        dict = new TrieSET();

        for (int i = 0; i < dictionary.length; i++) {
            dict.add(dictionary[i]);
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int totalScore = 0;
        List<String> validWords =  new ArrayList<String>();
        List<String> allWords = new ArrayList<String>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                allWords.addAll(getAllWords(board, i, j));
            }
        }

        System.out.println("DEBUG: All words - " + allWords);

        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return -1;
    }

    // SUPPORTING FUNCTIONS

    List<String> words;
    private List<String> getAllWords(BoggleBoard b, int r, int c) {
        words = new ArrayList<String>();
        boolean[][] isVisited = new boolean[b.rows()][b.cols()];
        StringBuilder str = new StringBuilder();
        dfs(b, r, c, str, isVisited);
        return words;
    }

    private void dfs(BoggleBoard b, int rt, int ct, StringBuilder str, boolean[][] isVisited) {
        isVisited[rt][ct] = true;
        str.append(b.getLetter(rt, ct));
        if (str.length() == b.rows()*b.cols()) {
            words.add(str.toString());
            return;
        }
        int[] rows = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] cols = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        for (int i = 0; i < 9; i++) {
            if (rt+rows[i] > -1 && rt+rows[i] < b.rows() && ct+cols[i] > -1 && ct+cols[i] < b.cols() && !isVisited[rt+rows[i]][ct+cols[i]]) {
                dfs(b, rt+rows[i], ct+cols[i], str, isVisited);
            }
        }
        str.deleteCharAt(str.length()-1);
        isVisited[rt][ct] = false;
    }

    public static void main(String[] args) {

        String folderPath = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\week_4\\";
        args = new String[2];
        args[0] = folderPath + "dictionary-2letters.txt";
        args[1] = folderPath + "board4x4.txt";

        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        System.out.println("Dictionary - ");
        for (int i = 0; i < dictionary.length; i++) System.out.print(dictionary[i] + " ");
        System.out.println();
        System.out.println(board.toString());
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
