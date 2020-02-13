package week_1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * @author Monali L on 2/8/2020
 */

public class WordNet {

    class Synset {
        int id;
        Set<String> nouns;
        Synset(int id) {
            this.id = id;
            this.nouns = new HashSet<String>();
        }
        public void addNoun(String noun) {
            nouns.add(noun);
        }
        public Set<String> getNouns() {
            return nouns;
        }
        public boolean isNounPresent(String noun) {
            return nouns.contains(noun);
        }
    }

    private Digraph dg;
    private Map<Integer, Synset> synsetsMap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        // Your data type should use space linear in the input size (size of synsets and hypernyms files)
        // The constructor should take time linearithmic (or better) in the input size (NlogN)

        In synsetsFile = new In(synsets);
        In hypernymsFile = new In(hypernyms);
        synsetsMap = new HashMap<Integer, Synset>();

        while (!synsetsFile.isEmpty()) {
            String[] lineContent = synsetsFile.readLine().split(",");
            int id = Integer.parseInt(lineContent[0]);
            String[] nouns = lineContent[1].split(" ");
            Synset synset = new Synset(id);
            for (int i = 0; i < nouns.length; i++) {
                synset.addNoun(nouns[i]);
            }
            synsetsMap.put(id, synset);
        }

        dg = new Digraph(synsetsMap.size());

        while (!hypernymsFile.isEmpty()) {
            // Get hypernym
            String[] lineContent = hypernymsFile.readLine().split(",");
            int v = Integer.parseInt(lineContent[0]);
            for (int i = 1; i < lineContent.length; i++) {
                int w = Integer.parseInt(lineContent[i]);
                dg.addEdge(v, w);
            }
        }
        System.out.println(dg.E());
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        // some graph traversal
        List<String> l = new ArrayList<String>();
        for (int id: synsetsMap.keySet()) {
            l.addAll(synsetsMap.get(id).getNouns());
        }
        return l;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        // The method isNoun() should run in time logarithmic (or better) in the number of nouns
        // so NlogN or N
        // Again some graph traversal with searching

        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        //  The methods distance() and sap() should run in time linear in the size of the WordNet digraph
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        //  The methods distance() and sap() should run in time linear in the size of the WordNet digraph
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\";
        WordNet wordNet = new WordNet(folderPath + "synsets.txt", folderPath + "hypernyms.txt");
    }

}
