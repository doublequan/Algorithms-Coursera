/****************************************************************************
 *  the programming assignment of Algorithms, Part II 
 *  Week1 Part II
 *  author: Bill Quan  
 *  Last edited: 20151109
 *  SAP.java
 ****************************************************************************/


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SET;

public class SAP {
    private Digraph mG;
    
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        if (G == null) throw new NullPointerException("input Digraph is null");
        mG = new Digraph(G);
        StdOut.print(mG + "\n");
    }
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        isValid(v);
        isValid(w);
        
        myBFS bfs = new myBFS(mG, v, w);
        
        return bfs.length();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        isValid(v);
        isValid(w);
        myBFS bfs = new myBFS(mG, v, w);
        return bfs.ancestor();
    }
/**
   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   **/
    
    private void isValid(int v)
    {
       if ((v < 0) && (v >= mG.V()))
           throw new IndexOutOfBoundsException("input invalid");
    }
    
    
    // do unit testing of this class
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        
        StdOut.print("ancestor:  " + sap.ancestor(5, 7) + "\n");
        StdOut.print("length:  " + sap.length(7, 5) + "\n");
//        while (!StdIn.isEmpty()) 
//        {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
    }
}