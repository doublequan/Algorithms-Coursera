/****************************************************************************
  *  the programming assignment of Algorithms, Part II 
  *  Week1 Part II
  *  author: Bill Quan  
  *  Last edited: 20151109
  *  WordNet.java
  ****************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.*;

public class WordNet {
    
    private HashMap<String , ArrayList<Integer>> map;
    private HashMap<Integer , String> map2;
    private Digraph mG;
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        readSynsets(synsets);
        readHypernyms(hypernyms);
        isRooted();
        isDAG();
        sap = new SAP(mG);
//        StdOut.print(mG);
//        In in2 = new In(hypernyms);
//        //int i = in.readInt();
//        String line = in.readLine();
//        String[] s = line.split(",");
//        StdOut.print(line);
//        StdOut.print(s[0]);
//        String i = in2.readLine();
//        StdOut.print(i);
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return map.keySet();
//        Stack<String> s = new Stack<String>();
//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext())
//        {
//            Map.Entry entry = (Map.Entry) iter.next();
//            s.push((String)entry.getKey());
//        }
//        return s;
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        return map.containsKey(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
//        int idA = map.get(nounA);
//        int idB = map.get(nounB);
        return sap.length(map.get(nounA), map.get(nounB));
        
    }

     // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     // in a shortest ancestral path (defined below)
     public String sap(String nounA, String nounB)
     {
         return map2.get(sap.ancestor(map.get(nounA), map.get(nounB)));
     }

    // do unit testing of this class
    
    private void readSynsets(String name)
    {
        if (name == null) throw new NullPointerException("input name is null");
        In in = new In(name);
        map = new HashMap<String, ArrayList<Integer>>();
        map2 = new HashMap<Integer, String>();
        
        while(!in.isEmpty())
        {
            String line = in.readLine();
            String[] all = line.split(",");
            String[] words = all[1].split(" ");
            for (String w : words)
            {
//                StdOut.print(w);
                if (!map.containsKey(w))
                    map.put(w, new ArrayList<Integer>());
                
//                StdOut.print("Save Key: " + w + " Value --ADD--: " + Integer.parseInt(all[0]) + "\n");
                map.get(w).add(Integer.parseInt(all[0]));
                
            }
            
            
            map2.put(Integer.parseInt(all[0]), words[0]);
            
        }
    }
    
    private void readHypernyms(String name)
    {
        if (name == null) throw new NullPointerException("input name is null");
        In in = new In(name);
        mG = new Digraph(map.size());

        
        while(!in.isEmpty())
        {
            String line = in.readLine();
            String[] nums = line.split(",");
//            if (nums.length == 1)
//                root++;
            for (int i = 1; i < nums.length; i++)
            {
                mG.addEdge(Integer.parseInt(nums[0]), Integer.parseInt(nums[i])); 
            }
        }
        

        
    }
    
    private int isRooted()
    {
        int root = 0;
        int rootNum = 0;
        for (int i = 0; i < mG.V(); i++)
        {
            if(mG.outdegree(i) == 0)
            {
                root = i;
                rootNum++;
            }
        }
        
        if (rootNum != 1)
            throw new IllegalArgumentException("root numbers is wrong!");
        
        return root;
    }
    
    private void isDAG()
    {
        DirectedCycle dc = new DirectedCycle(mG);
        if(dc.hasCycle())
            throw new IllegalArgumentException("Has Cycle!");
    }
    
    public static void main(String[] args)
    {
        WordNet wn = new WordNet(args[0], args[1]);
//        StdOut.print("distance :  " + wn.distance("a", "c") + "\n");
//        StdOut.print("SAP : " + wn.sap("a", "c") + "\n");
    }
}
