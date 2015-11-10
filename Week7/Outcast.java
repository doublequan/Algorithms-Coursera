/****************************************************************************
  *  the programming assignment of Algorithms, Part II 
  *  Week1 Part II
  *  author: Bill Quan  
  *  Last edited: 20151109
  *  Outcast.java
  ****************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
    private WordNet wn;
    
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        wn = wordnet;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int[] dist = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++)
            for (int j = 0; j < nouns.length; j++)
        {
            if (i != j)
            {
                dist[i] += wn.distance(nouns[i], nouns[j]);
            }
        }
        int maxid = 0;
        for (int i = 0; i < nouns.length; i++)
        {
            if (dist[maxid] < dist[i])
                maxid = i;
        }
        return nouns[maxid];
    }
    
    
    
    public static void main(String[] args) 
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) 
        {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}