package week_1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

/**
 * @author Monali L on 2/8/2020
 */

public class WordNet {

    private final Map<Integer, List<String>> idMap;
    private final Map<String, List<Integer>> nounMap;
    private final Digraph dg;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In synsetsFile = new In(synsets);
        In hypernymsFile = new In(hypernyms);
        idMap = new HashMap<Integer, List<String>>();
        nounMap = new HashMap<String, List<Integer>>();

        // Create a map with id and nouns from synsets file to be used later
        // Takes O(N) time
        while (!synsetsFile.isEmpty()) {
            String[] lineContent = synsetsFile.readLine().split(",");
            int id = Integer.parseInt(lineContent[0]);
            String[] nouns = lineContent[1].split(" ");
            idMap.put(id, Arrays.asList(nouns));
            for (String noun : nouns) {
                List<Integer> tempList;
                tempList = nounMap.containsKey(noun) ? nounMap.get(noun) : new ArrayList<Integer>();
                tempList.add(id);
                nounMap.put(noun, tempList);
            }
        }

        dg = new Digraph(idMap.size());

        // Scan hypernyms file add edges between vertices in the graph
        while (!hypernymsFile.isEmpty()) {
            String[] lineContent = hypernymsFile.readLine().split(",");
            int v = Integer.parseInt(lineContent[0]);
            for (int i = 1; i < lineContent.length; i++) {
                int w = Integer.parseInt(lineContent[i]);
                dg.addEdge(v, w);
            }
        }

        DirectedCycle directedCycle = new DirectedCycle(dg);
        if (directedCycle.hasCycle()) throw new IllegalArgumentException();
        if (!isRootedDag()) throw new IllegalArgumentException();
    }

    private boolean isRootedDag() {
        boolean flag = false;
        for (int id: idMap.keySet()) {
            if (dg.outdegree(id) == 0) {
                if (flag) return false;
                flag = true;
            }
        }
        return true;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nounMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        if (nounA == null || nounB == null || !nounMap.containsKey(nounA) || !nounMap.containsKey(nounB)) throw new IllegalArgumentException();

        SAP sap = new SAP(dg);
        List<Integer> vList = new ArrayList<Integer>(nounMap.get(nounA));
        List<Integer> wList = new ArrayList<Integer>(nounMap.get(nounB));

        return sap.length(vList, wList);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        if (nounA == null || nounB == null || !nounMap.containsKey(nounA) || !nounMap.containsKey(nounB)) throw new IllegalArgumentException();

        SAP sap = new SAP(dg);
        List<Integer> vList = new ArrayList<Integer>(nounMap.get(nounA));
        List<Integer> wList = new ArrayList<Integer>(nounMap.get(nounB));

        StringBuilder rslt = new StringBuilder();
        int rsltId = sap.ancestor(vList, wList);
        for (String s: idMap.get(rsltId)) rslt.append(" ").append(s);

        return rslt.toString().trim();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\";
        WordNet wordNet = new WordNet(folderPath + "synsets.txt", folderPath + "hypernyms.txt");
        wordNet.distance("demotion", "variation");
    }

}
