/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week4
 *  author: Bill Quan  
 *  Last edited: 20150823
 *  Solver.java, the second part of week4's programming assignment
 ****************************************************************************/


public class Solver 
{
    private boolean isSolvable;
    private int moves;
    private SearchNode goal;
    private class SearchNode implements Comparable<SearchNode>
    {
        public Board board;
        public int moves;
        public SearchNode previousSearchNode;
        
        public int priority()
        {
            return this.board.manhattan() + this.moves;
        }
        
        public SearchNode(Board b, int m, SearchNode pSN)
        {
            this.board = b;
            this.moves = m;
            this.previousSearchNode = pSN;
        }
        public int compareTo(SearchNode other)  
        {  
            if (this.priority() < other.priority()) 
                return - 1 ;  
            if (this.priority() > other.priority())  
                return 1 ;  
            return 0 ;  
        }
    }
    
    
    
    
    
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial the Board need to be solved
     */
    public Solver(Board initial)
    {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, 0, null));
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        SearchNode b;
        SearchNode bTwin;
        while (!pq.min().board.isGoal() && !pqTwin.min().board.isGoal())
        {
            b = pq.delMin();
            bTwin = pqTwin.delMin();
            for (Board i : b.board.neighbors())
            {
                pq.insert(new SearchNode(i, b.moves + 1, b));
            }
            for (Board j : bTwin.board.neighbors())
            {
                pqTwin.insert(new SearchNode(j, bTwin.moves + 1, bTwin));
            }
        }
        if(pq.min().board.isGoal())
        {
            this.isSolvable = true;
            b = pq.min();
            this.moves = b.moves;
            goal = b;
        }
        else if(pqTwin.min().board.isGoal())
        {
            this.isSolvable = false;
            this.moves = -1;
            goal = null;
            
        }
            

        
        
    }
    

    
    
    /**
     * is the initial board solvable?
     */
    public boolean isSolvable()
    {
        return this.isSolvable;
    }
    
    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves()
    {
        return this.moves;
    }
    
    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution()
    {
        if(goal == null) return null;
        Stack<Board> stack = new Stack<Board>();
        stack.push(goal.board);
        SearchNode b = goal.previousSearchNode;
        while(b != null)
        {
            stack.push(b.board);
            b = b.previousSearchNode;
        }
        return stack;
    }
    
    
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
