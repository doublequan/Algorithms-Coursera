/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week4
 *  author: Bill Quan  
 *  Last edited: 20150822
 *  Board.java, the first part of week4's programming assignment
 ****************************************************************************/
public class Board {
    
    private int[][] grid;                //used to store the blocks
    private int dimension;
    
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
    
    
//    public int hamming()                   // number of blocks out of place
//    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    
    
    /**
     * is this board the goal board?
     */
    public boolean isGoal()
    {
        for (int i = 0; i < this.dimension; i++)
        {
            for (int j = 0; j < this.dimension - 1; j++)
            {
                if (this.grid[i][j] != (i * this.dimension + j + 1))
                    return false;
            }
        }
        if (this.grid[this.dimension-1][this.dimension-1] != 0) return false;
        return true;
    }
    
    
//    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    
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
    
//    public Iterable<Board> neighbors()     // all neighboring boards
    
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
    

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] blocks = {{1, 2, 3},
                          {4, 5, 6},  
                          {7, 8, 0}};
        Board board = new Board(blocks);
        StdOut.println(board.isGoal());
    }
}