/****************************************************************************
 *  the programming assignment of Algorithms, Part II 
 *  Week1 Part II
 *  author: Bill Quan  
 *  Last edited: 20151108
 *  WordNet.java
 ****************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    
    /**
     * Contructor
     */
    public WordNet(String synsets, String hypernyms)
    {
        In in = new In(synsets);
        In in2 = new In(hypernyms);
        //int i = in.readInt();
        String line = in.readLine();
        String[] s = line.split(",");
        StdOut.print(line);
        StdOut.print(s[0]);
        String i = in2.readLine();
        StdOut.print(i);
    }
    
    
    
    
/**
     // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)

   // returns all WordNet nouns
   public Iterable<String> nouns()

   // is the word a WordNet noun?
   public boolean isNoun(String word)

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
*/
   // do unit testing of this class
   public static void main(String[] args)
   {
       WordNet wn = new WordNet(args[0], args[1]);
       return;
   }
}
