package week_1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

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
    private Digraph rdg;
    private Map<Integer, Synset> synsetsMap;
    private int root;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        // Your data type should use space linear in the input size (size of synsets and hypernyms files)
        // The constructor should take time linearithmic (or better) in the input size (NlogN)

        In synsetsFile = new In(synsets);
        In hypernymsFile = new In(hypernyms);
        synsetsMap = new HashMap<Integer, Synset>();

        // Create a map with id and nouns from synsets file to be used later
        // Takes O(N) time
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

        // Scan hypernyms file add edges between vertices in the graph
        while (!hypernymsFile.isEmpty()) {
            String[] lineContent = hypernymsFile.readLine().split(",");
            int v = Integer.parseInt(lineContent[0]);
            for (int i = 1; i < lineContent.length; i++) {
                int w = Integer.parseInt(lineContent[i]);
                dg.addEdge(v, w);
            }
        }

        rdg = dg.reverse();
        /*Iterable<Integer> itr = dg.adj(34);
        System.out.println("DEBUG: " + synsetsMap.get(34).getNouns());
        for (int id: itr) {
            System.out.println("DEBUG: " + synsetsMap.get(id).getNouns());
        }*/

        if (!isRootedDag()) throw new IllegalArgumentException();
    }

    private boolean isRootedDag() {
        // Takes O(N) time
        boolean flag = false;
        for (int id: synsetsMap.keySet()) {
            if (dg.outdegree(id) == 0) {
                if (flag) return false;
                flag = true;
                this.root = id;
            }
        }
        return true;
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
        // Again some graph traversal with searching - DFS
        if (word == null) throw new IllegalArgumentException();

        if (synsetsMap.get(root).isNounPresent(word)) return true;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(root);
        boolean[] isVisited = new boolean[rdg.V()];
        isVisited[root-1] = true;

        while(!stack.isEmpty()) {
            int current = stack.pop();
            isVisited[current-1] = true;
            Iterable<Integer> itr = rdg.adj(current);
            for (int id: itr) {
                if (synsetsMap.get(id).isNounPresent(word)) return true;
                if (!isVisited[id-1]) {
                    stack.push(id);
                }
            }
        }

        /*for (int id: synsetsMap.keySet()) {
            if (synsetsMap.get(id).isNounPresent(word)) {
                return true;
            }
        }*/
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        //  The methods distance() and sap() should run in time linear in the size of the WordNet digraph
        // To implement BFS
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        // Both nouns are in root
        if (synsetsMap.get(root).isNounPresent(nounA) && synsetsMap.get(root).isNounPresent(nounB)) return 0;

        boolean[] isVisited = new boolean[rdg.V()];
        Queue<Integer> queue = new Queue<Integer>();
        int[] edgeTo = new int[rdg.V()];
        int[] distTo = new int[rdg.V()];
        boolean nounAFound = false;
        boolean nounBFound = false;

        while (!queue.isEmpty()) {
            
        }

        return -1;
    }

    private int distanceHelper(int node, String nounA, String nounB) {
        if (synsetsMap.get(node).isNounPresent(nounA) && synsetsMap.get(node).isNounPresent(nounB))
            return 0;
        int rightDist = 0;
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        //  The methods distance() and sap() should run in time linear in the size of the WordNet digraph
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\";
        WordNet wordNet = new WordNet(folderPath + "synsets.txt", folderPath + "hypernyms.txt");
    }

}
