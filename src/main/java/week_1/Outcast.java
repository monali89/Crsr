package week_1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Monali L on 2/8/2020
 */

public class Outcast {

    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        int maxDist = Integer.MIN_VALUE;
        int maxI = 0;
        for (int i = 0; i < nouns.length; i++) {
            int thisDist = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) continue;
                thisDist = thisDist + wordNet.distance(nouns[i], nouns[j]);
            }
            if (thisDist > maxDist) {
                maxI = i;
                maxDist = thisDist;
            }
        }
        return nouns[maxI];
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
