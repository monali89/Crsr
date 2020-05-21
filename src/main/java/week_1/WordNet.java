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

    private Map<Integer, List<String>> idMap;
    private Map<String, List<Integer>> nounMap;
    private Digraph dg;
    private Digraph rdg;
    private int root;

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

        rdg = dg.reverse();

        if (!isRootedDag()) throw new IllegalArgumentException();
    }

    private boolean isRootedDag() {
        boolean flag = false;
        for (int id: idMap.keySet()) {
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
        List<String> l = new ArrayList<String>();
        for (int id: idMap.keySet()) {
            l.addAll(idMap.get(id));
        }
        return l;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

        if (word == null) throw new IllegalArgumentException();

        if (idMap.get(root).contains(word)) return true;
        edu.princeton.cs.algs4.Stack<Integer> stack = new Stack<Integer>();
        stack.push(root);
        boolean[] isVisited = new boolean[rdg.V()];
        isVisited[root-1] = true;

        while(!stack.isEmpty()) {
            int current = stack.pop();
            isVisited[current-1] = true;
            Iterable<Integer> itr = rdg.adj(current);
            for (int id: itr) {
                if (idMap.get(id).contains(word)) return true;
                if (!isVisited[id-1]) {
                    stack.push(id);
                }
            }
        }
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        //  The methods distance() and sap() should run in time linear in the size of the WordNet digraph
        // To implement BFS
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        for (int n: nounMap.get(nounA)) vQueue.enqueue(n);
        for (int n: nounMap.get(nounB)) wQueue.enqueue(n);

        boolean[] vIsVisited = new boolean[dg.V()];
        boolean[] wIsVisited = new boolean[dg.V()];

        int[] vDistTo = new int[dg.V()];
        int[] wDistTo = new int[dg.V()];

        while (!vQueue.isEmpty() || !wQueue.isEmpty()) {

            int vCurr = -1, wCurr = -1, vDist = 0, wDist = 0;
            if (!vQueue.isEmpty()) {
                vCurr = vQueue.dequeue();
                vDist = vDistTo[vCurr];
            }
            if (!wQueue.isEmpty()) {
                wCurr = wQueue.dequeue();
                wDist = wDistTo[wCurr];
            }
            if (vSet.contains(wCurr)) {
                return vDistTo[wCurr] + wDistTo[wCurr];
            } else if (wSet.contains(vCurr)){
                return vDistTo[vCurr] + wDistTo[vCurr];
            }

            if (vCurr != -1) {
                for (int adj: dg.adj(vCurr)) {
                    if (!vIsVisited[adj]) {
                        vSet.add(adj);
                        vQueue.enqueue(adj);
                        vIsVisited[adj] = true;
                        if (vDistTo[adj] < vDist + 1) {
                            vDistTo[adj] = vDist + 1;
                        }
                    }
                }
            }

            if (wCurr != -1) {
                for (int adj: dg.adj(wCurr)) {
                    if (!wIsVisited[adj]) {
                        wSet.add(adj);
                        wQueue.enqueue(adj);
                        wIsVisited[adj] = true;
                        if (wDistTo[adj] < wDist + 1) {
                            wDistTo[adj] = wDist + 1;
                        }
                    }
                }
            }
        }
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        Set<Integer> vSet = new HashSet<Integer>();
        Set<Integer> wSet = new HashSet<Integer>();

        Queue<Integer> vQueue = new Queue<Integer>();
        Queue<Integer> wQueue = new Queue<Integer>();

        for (int n: nounMap.get(nounA)) vQueue.enqueue(n);
        for (int n: nounMap.get(nounB)) wQueue.enqueue(n);

        boolean[] vIsVisited = new boolean[dg.V()];
        boolean[] wIsVisited = new boolean[dg.V()];

        while (!vQueue.isEmpty() || !wQueue.isEmpty()) {

            int vCurr = vQueue.isEmpty() ? -1 : vQueue.dequeue();
            int wCurr = wQueue.isEmpty() ? -1 : wQueue.dequeue();

            StringBuilder rslt = new StringBuilder();
            if (vSet.contains(wCurr)) {
                for (String s: idMap.get(wCurr)) rslt.append(" ").append(s);
                return rslt.toString().trim();
            } else if (wSet.contains(vCurr)) {
                for (String s: idMap.get(vCurr)) rslt.append(" ").append(s);
                return rslt.toString().trim();
            }

            if (vCurr != -1) {
                for (int adj: dg.adj(vCurr)) {
                    if (!vIsVisited[adj]) {
                        vSet.add(adj);
                        vQueue.enqueue(adj);
                        vIsVisited[adj] = true;
                    }
                }
            }
            if (wCurr != -1) {
                for (int adj: dg.adj(wCurr)) {
                    if (!wIsVisited[adj]) {
                        wSet.add(adj);
                        wQueue.enqueue(adj);
                        wIsVisited[adj] = true;
                    }
                }
            }
        }
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\monal\\IdeaProjects\\Coursera\\src\\main\\resources\\";
        WordNet wordNet = new WordNet(folderPath + "synsets.txt", folderPath + "hypernyms.txt");
    }

}
