/****************************************************************************
 *  the programming assignment of Algorithms, Part II 
 *  Week1 Part II
 *  author: Bill Quan  
 *  Last edited: 20151109
 *  myBFS.java
 ****************************************************************************/

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class myBFS 
{
    private static final int INFINITY = Integer.MAX_VALUE;
    
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path
    
    private boolean[] marked2;  // use to find the shortest ancestral path
    private int[] edgeTo2;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo2;      // distTo[v] = length of shortest s->v path
    
    private int ancestor;
    
    
    public myBFS(Digraph G, int s) 
    {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        bfs(G, s);
    }
    
    public myBFS(Digraph G, int s, int w) 
    {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        
        
        marked2 = new boolean[G.V()];
        distTo2 = new int[G.V()];
        edgeTo2 = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo2[v] = INFINITY;
        ancestor = -1;
        bfs(G, s, w);
   
    }

    public myBFS(Digraph G, Iterable<Integer> s, Iterable<Integer> w) 
    {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        
        marked2 = new boolean[G.V()];
        distTo2 = new int[G.V()];
        edgeTo2 = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo2[v] = INFINITY;
        ancestor = -1;
        bfs(G, s, w);
    }
          
    // BFS for SAP from single sources
    private void bfs(Digraph G, int s, int w) 
    {
        //prepare for s
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        
        //prepare for w
        Queue<Integer> q2 = new Queue<Integer>();
        marked2[w] = true;
        distTo2[w] = 0;
        q2.enqueue(w);
        
        if (w == s) 
        {
            ancestor = s;
            return;
        }
     
        while (!q.isEmpty() || !q2.isEmpty()) 
        {
            if (!(!q.isEmpty() && (length() > distTo[q.peek()])) && !(!q2.isEmpty() && (length() > distTo2[q2.peek()])))
                break;
            //search s first
            if (!q.isEmpty() && (length() > distTo[q.peek()]))
            {
                StdOut.print("q = " + q);
                
                int i = q.dequeue();
                
                
//                for (int j : G.adj(i))
//                    StdOut.print("["+j+"}");
                for (int j : G.adj(i)) 
                {
//                    StdOut.print("Checking " + j + " from " + i + "  ");
                    
                    if (!marked[j]) 
                    {
                        edgeTo[j] = i;
                        distTo[j] = distTo[i] + 1;
                        StdOut.print("Goal ---- distTo" + j + " = " + distTo[j] + " from  " + i + "\n");
                        marked[j] = true;
                        
                        // find the ancestral which is marked both by s and w
                        if (marked2[j] && (length() > (distTo[j]+distTo2[j])))
                        {
                            ancestor = j;
                        }
                        
                        q.enqueue(j);
                    }
                  
                } 
            }
            
            //search w then
            if (!q2.isEmpty() && (length() > distTo2[q2.peek()]))
            {
                StdOut.print("q2 = " + q2);
                int i = q2.dequeue();
                
//                for (int j : G.adj(i))
//                    StdOut.print("["+j+"}");
                for (int j : G.adj(i)) 
                {
//                    StdOut.print("Checking " + j + " from " + i + "  ");
                    
                    if (!marked2[j]) 
                    {
                        edgeTo2[j] = i;
                        distTo2[j] = distTo2[i] + 1;
                        StdOut.print("Goal ---- distTo" + j + " = " + distTo2[j] + " from  " + i + "\n");
                        marked2[j] = true;
                        
                        // find the ancestral which is marked both by s and w
                        if (marked[j] && (length() > (distTo[j]+distTo2[j])))
                        {
                            ancestor = j;
                        }
                        
                        q2.enqueue(j);
                    }
                                        
                }
            }
        }
    }

    // BFS for SAP from multiple sources
    private void bfs(Digraph G, Iterable<Integer> s, Iterable<Integer> w) 
    {
        //prepare for s
        Queue<Integer> q = new Queue<Integer>();
        for (int m : s)
        {
            marked[m] = true;
            distTo[m] = 0;
            q.enqueue(m);
        }
        //prepare for w
        Queue<Integer> q2 = new Queue<Integer>();
        for (int m : w)
        {
            marked2[m] = true;
            distTo2[m] = 0;
            q2.enqueue(m);
        }
        
        //check if there is any same vexcle in s and w
        for (int m : s)
        for (int n : w)
            if (m == n)
            {
                ancestor = m;
                return;
            }


        //main loop
        while (!q.isEmpty() || !q2.isEmpty()) 
        {
            if (!(!q.isEmpty() && (length() > distTo[q.peek()])) && !(!q2.isEmpty() && (length() > distTo2[q2.peek()])))
                break;
            //search s first
            if (!q.isEmpty() && (length() > distTo[q.peek()]))
            {
                int i = q.dequeue();
                for (int j : G.adj(i)) 
                {
//                    StdOut.print("Checking " + j + " from " + i + "  ");
                    
                    if (!marked[j]) 
                    {
                        edgeTo[j] = i;
                        distTo[j] = distTo[i] + 1;
                        StdOut.print("---- distTo" + j + " = " + distTo[j] + " from  " + i + "\n");
                        marked[j] = true;
                        // find the ancestral which is marked both by s and w
                        if (marked2[j] && (length() > (distTo[j]+distTo2[j])))
                        {
                            ancestor = j;
                        }
                        q.enqueue(j);
                    }                  
                }  
            }
            
            //search w then
            if (!q2.isEmpty() && (length() > distTo2[q2.peek()]))
            {
                int i = q2.dequeue();   
                for (int j : G.adj(i)) 
                {
//                    StdOut.print("Checking " + j + " from " + i + "  ");
                    if (!marked2[j]) 
                    {
                        edgeTo2[j] = i;
                        distTo2[j] = distTo2[i] + 1;
                        StdOut.print("|||| distTo" + j + " = " + distTo2[j] + " from  " + i + "\n");
                        marked2[j] = true;
                        
                        // find the ancestral which is marked both by s and w
                        if (marked[j] && (length() > (distTo[j]+distTo2[j])))
                        {
                            ancestor = j;
                        }
                        
                        q2.enqueue(j);
                    }
                                        
                }
            }
        }
    }
    
    // BFS from single source
    private void bfs(Digraph G, int s) 
    {
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) 
        {
            int v = q.dequeue();
            for (int w : G.adj(v)) 
            {
                if (!marked[w]) 
                {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    
    
    
    
    public int ancestor()
    {
        return ancestor;
    }
    
    private int length()
    {
        return ancestor == -1 ? INFINITY : (distTo[ancestor] + distTo2[ancestor]);
    }
    
    public int sap()
    {
        return ancestor == -1 ? -1 : (distTo[ancestor] + distTo2[ancestor]);
    }
    
}
