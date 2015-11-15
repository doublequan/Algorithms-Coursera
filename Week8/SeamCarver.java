/*
 *  the programming assignment of Algorithms, Part II 
 *  Week1 Part II
 *  author: Bill Quan  
 *  Last edited: 20151115
 *  SeamCarver.java
 */
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;

public class SeamCarver 
{
    private Picture p;
    private double[][] energyArray;
    public double[][] distTo;
    public int[][] edgeTo;
    
    private static final double INFINITY = Double.MAX_VALUE;
    
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        if (picture != null)
            p = new Picture(picture);
        else
            throw new NullPointerException("input is null");
        
        energyArray = new double[p.width()][p.height()];
        calEnergyArray();
        

    }
    
    // current picture
    public Picture picture()   
    {
        return p;
    }
    
    // width of current picture
    public int width()  
    {
        return p.width();
    }
    
    // height of current picture
    public int height()
    {
        return p.height();
    }
    
    // energy of pixel at column x and row y
    public  double energy(int x, int y)    
    {
        if (x < 0 || x >= width() 
                || y < 0 || y >= height())
            throw new IndexOutOfBoundsException("input IndexOutOfBounds");
        
        //define the energy of a pixel at the border of the image to be 1000
        if (x == 0 || x == (width() - 1) 
                || y == 0 || y == (height() - 1))
            return 1000;
        
        Color c = p.get(x, y);
        double detaX2 = Math.pow((p.get(x-1, y).getRed() - p.get(x+1, y).getRed()), 2)
            + Math.pow((p.get(x-1, y).getGreen() - p.get(x+1, y).getGreen()), 2)
            + Math.pow((p.get(x-1, y).getBlue() - p.get(x+1, y).getBlue()), 2);
        double detaY2 = Math.pow((p.get(x, y-1).getRed() - p.get(x, y+1).getRed()), 2)
            + Math.pow((p.get(x, y-1).getGreen() - p.get(x, y+1).getGreen()), 2)
            + Math.pow((p.get(x, y-1).getBlue() - p.get(x, y+1).getBlue()), 2);
        return Math.sqrt(detaX2 + detaY2);
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()        
    {
        //reverse this.energyArray
        double[][] energyR = reverse(energyArray);
        
        //initial distTo and edgeTo
        distTo = new double[p.height()][p.width()];
        for (double i[] : distTo)
            for (double j : i)
                j = INFINITY;
        edgeTo = new int[p.height()][p.width()];
        
        int[] rst = findSeam(energyR, energyR.length, energyR[0].length);
        
        //reverse rst
        for (int i = 0; i < rst.length; i++)
        {
            rst[i] = p.height() - 1 - rst[i];
        }
        return rst;
        
    }
    
    // sequence of indices for vertical seam        
    public   int[] findVerticalSeam()
    {
        distTo = new double[p.width()][p.height()];
        for (double i[] : distTo)
            for (double j : i)
                j = INFINITY;
        
        edgeTo = new int[p.width()][p.height()];
        
        return findSeam(this.energyArray, this.width(), this.height());
    }
    /**
     public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
     public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
     **/
    
    private double[][] reverse(double[][] matrix)
    {
        if (matrix == null) throw new NullPointerException("input is null");
        
        double[][] rst = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix.length; j++)
            {
                rst[i][j] = matrix[j][matrix[0].length - 1 - i];
            }
        return rst;
        
    }
    
    private int[] findSeam(double[][] energy, int W, int H)
    {
        for (int r = 0; r < H; r++)
            for (int c = 0; c < W; c++)
        {
            relax(energy, c, r, W, H);
        }
        
        int minID = 0;
        for (int c = 0; c < W; c++)
        {
            if (distTo[c][H-1] < distTo[minID][H-1])
                minID = c;
        } 
        
        int[] rst = new int[H];
        rst[H-1] = minID;
        for (int r = H-2; r >= 0; r--)
        {
            rst[r] = edgeTo[rst[r + 1]][r + 1];
        }
        return rst;
    }
    
    //calulate the EnergyArray
    private void calEnergyArray()
    {
        if (energyArray == null)
            energyArray = new double[width()][height()];
        for (int c = 0; c < width(); c++)
            for (int r = 0; r < height(); r++)
        {
            energyArray[c][r] = energy(c, r);
        }
    }
    
    //update the distTo and edgeTo according to the input column c and row r 
    private void relax(double[][] energy, int c, int r, int W, int H)
    {
        if (c < 0 || c >= W 
                || r < 0 || r >= H)
            throw new IndexOutOfBoundsException("input IndexOutOfBounds");
        
        if (r == 0)
        {
            distTo[c][r] = 1000;
            edgeTo[c][r] = -1;
            return;
        }
        if (W == 1)
        {
            distTo[c][r] = distTo[c][r - 1] + energy[c][r];
            edgeTo[c][r] = c;
            return;
        }
        if (c == 0)
        {
            if (distTo[c][r - 1] <= distTo[c + 1][r - 1])
            {
                distTo[c][r] = distTo[c][r - 1] + energy[c][r];
                edgeTo[c][r] = c;
            }
            else
            {
                distTo[c][r] = distTo[c + 1][r - 1] + energy[c][r];
                edgeTo[c][r] = c + 1;
            }
            return;
        }
        if (c == W-1)
        {
            if (distTo[c - 1][r - 1] <= distTo[c][r - 1])
            {
                distTo[c][r] = distTo[c - 1][r - 1] + energy[c][r];
                edgeTo[c][r] = c - 1;
            }
            else
            {
                distTo[c][r] = distTo[c][r - 1] + energy[c][r];
                edgeTo[c][r] = c;
            }
            return;
        }
        
        if (distTo[c - 1][r - 1] <= distTo[c][r - 1] && distTo[c - 1][r - 1] <= distTo[c + 1][r - 1])
        {
            distTo[c][r] = distTo[c - 1][r - 1] + energy[c][r];
            edgeTo[c][r] = c - 1;
        }
        else if (distTo[c][r - 1] <= distTo[c + 1][r - 1])
        {
            distTo[c][r] = distTo[c][r - 1] + energy[c][r];
            edgeTo[c][r] = c;
        }
        else
        {
            distTo[c][r] = distTo[c + 1][r - 1] + energy[c][r];
            edgeTo[c][r] = c + 1;
        }
    }
    
    
    
    public static void main(String[] args) {
        
//        StdOut.print(args[0]);
//        Picture picture = new Picture(args[0]);
//        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());
//        
//        SeamCarver sc = new SeamCarver(picture);
//        
//        StdOut.printf("Printing energy calculated for each pixel.\n");        
//        
//        for (int j = 0; j < sc.height(); j++) 
//        {
//            for (int i = 0; i < sc.width(); i++)
//                StdOut.printf("%9.0f ", sc.energy(i, j));
//            StdOut.println();
//        }
    }
}
