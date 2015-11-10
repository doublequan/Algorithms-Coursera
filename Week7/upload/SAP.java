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
import edu.princeton.cs.algs4.Stack;

public class SAP {
    private Digraph mG;
    
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        if (G == null) throw new NullPointerException("input Digraph is null");
        mG = new Digraph(G);
//        StdOut.print(mG + "\n");
    }
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        isValid(v);
        isValid(w);
        
        myBFS bfs = new myBFS(mG, v, w);
        
        return bfs.sap();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        isValid(v);
        isValid(w);
        myBFS bfs = new myBFS(mG, v, w);
        return bfs.ancestor();
    }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
       isValid(v);
       isValid(w);
       myBFS bfs = new myBFS(mG, v, w);
       return bfs.sap();
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
       isValid(v);
       isValid(w);
       myBFS bfs = new myBFS(mG, v, w);
       return bfs.ancestor();
   }

    
    private void isValid(int v)
    {
       if ((v < 0) && (v >= mG.V()))
           throw new IndexOutOfBoundsException("input invalid");
    }
    
    private void isValid(Iterable<Integer> v)
    {
        for (int i : v)
            if ((i < 0) && (i >= mG.V()))
            throw new IndexOutOfBoundsException("input invalid");
    }
    
    
    // do unit testing of this class
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        
//        Stack<Integer> s1 = new Stack<Integer>();
//        Stack<Integer> s2 = new Stack<Integer>();
//        s1.push(1);
//        s1.push(8);
//        s2.push(7);
//        s2.push(0);
//        
//        StdOut.print("ancestor:  " + sap.ancestor(s1, s2) + "\n");
//        StdOut.print("length:  " + sap.length(s1, s2) + "\n");
//        StdOut.print("ancestor:  " + sap.ancestor(1, 7) + "\n");
//        StdOut.print("length:  " + sap.length(1, 7) + "\n");
        while (!StdIn.isEmpty()) 
        {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}