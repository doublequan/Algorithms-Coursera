/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week4
 *  author: Bill Quan  
 *  Last edited: 20150823
 *  Board.java, the first part of week4's programming assignment
 ****************************************************************************/
import java.lang.Math;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Board{
    
    private int[][] grid;                //used to store the blocks
    private int dimension;
//    private int moves;
//    private Board previousBoard;
    
    /**
      * construct a board from an N-by-N array of blocks
      * (where blocks[i][j] = block in row i, column j)
      * @throws java.lang.NullPointerException if blocks is null
      * @throws java.lang.IllegalArgumentException if blocks is not a N*N grid
      * @param blocks the blocks data of board
      */
    public Board(int[][] blocks)
    {
        if (blocks == null) throw new NullPointerException("blocks is null");
        for (int i = 0; i < blocks.length; i++)
            if (blocks.length != blocks[i].length) throw new IllegalArgumentException("blocks is not a N*N grid");
        this.dimension = blocks.length;
        this.grid = new int[this.dimension][this.dimension];
        for (int j = 0; j < this.dimension; j++)
        {
            for (int k = 0; k < this.dimension; k++)
            {
//                StdOut.println("i = " + i + " | j = " + j);
                this.grid[j][k] = blocks[j][k];
            }
        }
    }
    
    
    /**
     * board dimension N
     * @return the dimension N 
     */
    public int dimension()
    {
        return this.dimension;
    }
    
    /**
     * number of blocks out of place
     */
    public int hamming()
    {
        int hamming = 0;
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension; j++)
            {
                if (this.grid[i][j] != (i * this.dimension + j + 1) && this.grid[i][j] != 0)
                        hamming++;
            }
        }
        return hamming;
        
    }
    
    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan()
    {
        int manhattan = 0;
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension; j++)
            {
                if (this.grid[i][j] != (i * this.dimension + j + 1) && this.grid[i][j] != 0)
                {
                        manhattan += Math.abs((this.grid[i][j] - 1)/this.dimension - i); //moves need to get to the right row
                        manhattan += Math.abs((this.grid[i][j] - 1)%this.dimension - j); //moves need to get to the right column
                }
            }
        }
        return manhattan;
    }
    
    
    /**
     * is this board the goal board?
     */
    public boolean isGoal()
    {
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension; j++)
            {
                if (this.grid[i][j] != (i * this.dimension + j + 1))
                {
                    if (i != this.dimension - 1 || j != this.dimension - 1) 
                        return false;
                }
            }
        }
        if (this.grid[this.dimension-1][this.dimension-1] != 0) return false;
        return true;
    }
    
    /**
     * @return a board that is obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin()
    {
        if (this.dimension == 1) return null;   //one-dimensional grid does not have a twin
        if (this.grid == null) return null;
        
        boolean exchange = false;
        int[][] tmp = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension; j++)
            {
                if (j != this.dimension - 1 && this.grid[i][j] != 0 && this.grid[i][j+1] != 0 && !exchange)
                {
                    //exchange this.grid[i][j] and this.grid[i][j+1]
                    tmp[i][j] = this.grid[i][j+1];
                    tmp[i][j+1] = this.grid[i][j];
                    j++;  //switch the next j because we have done the assignment
                    exchange = true;
                }
                else 
                {
                    tmp[i][j] = this.grid[i][j];
                }
            }
        }
        
        return new Board(tmp);
        
    }
    
    /**
     * does this board equal y?
     */
    public boolean equals(Object other)        
    {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (that.dimension() != this.dimension) return false;
        if (that.toString().equals(this.toString())) return true;
        else                                         return false;
    }
    
    /**
     * @return all the neighboring boards
     */
    public Iterable<Board> neighbors()
    {
        if (this.dimension == 1) return null;   //one-dimensional grid does not have any neighbor
        if (this.grid == null) return null;
        
        int[][] tmp = new int[this.dimension][this.dimension];
        int oi = -1;
        int oj = -1;
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension; j++)
            {
                if (this.grid[i][j] == 0)   //save the id of elemention 0
                {
                    oi = i;
                    oj = j;
                }
                tmp[i][j] = this.grid[i][j];
            }
        }
        
        Stack<Board> stack = new Stack<Board>();
        if(oi != -1 && oj != -1)
        {
            if(oi != 0)
            {
                exchange(tmp, oi, oj, oi-1, oj);
                stack.push(new Board(tmp));
                exchange(tmp, oi, oj, oi-1, oj);
            }
            if(oi != this.dimension - 1)
            {
                exchange(tmp, oi, oj, oi+1, oj);
                stack.push(new Board(tmp));
                exchange(tmp, oi, oj, oi+1, oj);
            }
            if(oj != 0)
            {
                exchange(tmp, oi, oj, oi, oj-1);
                stack.push(new Board(tmp));
                exchange(tmp, oi, oj, oi, oj-1);
            }
            if(oj != this.dimension - 1)
            {
                exchange(tmp, oi, oj, oi, oj+1);
                stack.push(new Board(tmp));
                exchange(tmp, oi, oj, oi, oj+1);
            }
        }
        return stack;
    }
    
    //exchange blocks[i][j] and blocks[m][n]
    private void exchange(int[][] blocks, int i, int j, int m, int n)
    {
        int tmp;
        tmp = blocks[i][j];
        blocks[i][j] = blocks[m][n];
        blocks[m][n] = tmp;
    }
    
    /**
     * string representation of this board (in the output format)
     * @return String : the string to be print
     */
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(this.dimension + "\n");
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                s.append(String.format("%2d ", this.grid[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
//    public int priority()
//    {
//        return this.manhattan() + this.moves;
//    }
//    
//    public Board previousBoard()
//    {
//        return this.previousBoard;
//    }
//    
//    public void setPreviousBoard(Board b)
//    {
//        this.previousBoard = b;
//    }
//    
//    public int getMoves()
//    {
//        return this.moves;
//    }
//    
//    public void setMoved(int m)
//    {
//        this.moves = m;
//    }
//    public int compareTo(Board other)  
//    {  
//        if (this.priority() < other.priority()) 
//            return - 1 ;  
//        if (this.priority() > other.priority())  
//            return 1 ;  
//        return 0 ;  
//    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] blocks = {{1, 2, 3},
                          {4, 5, 6},  
                          {7, 8, 0}};
        Board board = new Board(blocks);
//        Stack<Board> stack = (Stack<Board>)board.neighbors();
//        while (!stack.isEmpty())
//        {
        StdOut.println(board.manhattan());
//        }
    }
}
