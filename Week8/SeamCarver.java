/*
 *  the programming assignment of Algorithms, Part II 
 *  Week1 Part II
 *  author: Bill Quan  
 *  Last edited: 20151115
 *  SeamCarver.java
 */
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver 
{
    private Picture p;
    private int W;
    private int H;
//    private double[][] energyArray;
//    private double[][] distTo;
//    private short[][] edgeTo;
    private final int[][] color;
    
    private static final double INFINITY = Double.MAX_VALUE;
    
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        if (picture != null)
            p = new Picture(picture);
        else
            throw new NullPointerException("input is null");

        W = p.width();
        H = p.height();
        //initial color[][]
        color = new int[W][H];
        for (int c = 0; c < W; c++)
            for (int r = 0; r < H; r++)
        {
            color[c][r] = p.get(c, r).getRGB();
        }
        
//        energyArray = new double[W][H];
//        calEnergyArray();
        

    }
    
    // current picture
    public Picture picture()   
    {
        Picture newP = new Picture(W, H);
        for (int c = 0; c < W; c++)
            for (int r = 0; r < H; r++)
        {
            newP.set(c, r, new Color(color[c][r]));
        }
        return newP;
    }
    
    // width of current picture
    public int width()  
    {
        return W;
    }
    
    // height of current picture
    public int height()
    {
        return H;
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
        
//        Color c = p.get(x, y);
//        double detaX2 = Math.pow((p.get(x-1, y).getRed() - p.get(x+1, y).getRed()), 2)
//            + Math.pow((p.get(x-1, y).getGreen() - p.get(x+1, y).getGreen()), 2)
//            + Math.pow((p.get(x-1, y).getBlue() - p.get(x+1, y).getBlue()), 2);
//        double detaY2 = Math.pow((p.get(x, y-1).getRed() - p.get(x, y+1).getRed()), 2)
//            + Math.pow((p.get(x, y-1).getGreen() - p.get(x, y+1).getGreen()), 2)
//            + Math.pow((p.get(x, y-1).getBlue() - p.get(x, y+1).getBlue()), 2);
//        Color c = p.get(x, y);
        

//        double detaX2 = Math.pow((color[x-1][y].getRed() - color[x+1][y].getRed()), 2)
//            + Math.pow((color[x-1][y].getGreen() - color[x+1][y].getGreen()), 2)
//            + Math.pow((color[x-1][y].getBlue() - color[x+1][y].getBlue()), 2);
//        double detaY2 = Math.pow((color[x][y-1].getRed() - color[x][y+1].getRed()), 2)
//            + Math.pow((color[x][y-1].getGreen() - color[x][y+1].getGreen()), 2)
//            + Math.pow((color[x][y-1].getBlue() - color[x][y+1].getBlue()), 2);

        double detaX2 = Math.pow((((color[x-1][y] >> 16) & 0xFF) - ((color[x+1][y] >> 16) & 0xFF)), 2)
            + Math.pow((((color[x-1][y] >> 8) & 0xFF) - ((color[x+1][y] >> 8) & 0xFF)), 2)
            + Math.pow(((color[x-1][y] & 0xFF) - (color[x+1][y] & 0xFF)), 2);
        double detaY2 = Math.pow((((color[x][y-1] >> 16) & 0xFF) - ((color[x][y+1] >> 16) & 0xFF)), 2)
            + Math.pow((((color[x][y-1] >> 8) & 0xFF) - ((color[x][y+1] >> 8) & 0xFF)), 2)
            + Math.pow(((color[x][y-1] & 0xFF) - (color[x][y+1] & 0xFF)), 2);
        
        return Math.sqrt(detaX2 + detaY2);
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()        
    {
        double[][] energyArray = calEnergyArray();
        //reverse this.energyArray
        double[][] energyR = reverse(energyArray);
        
        //initial distTo and edgeTo
        double[][] distTo = new double[H][W];
        for (double i[] : distTo)
            for (double j : i)
                j = INFINITY;
        short[][] edgeTo = new short[H][W];
        
        int[] rst = findSeam(energyR, distTo, edgeTo, energyR.length, energyR[0].length);
        
        //reverse rst
        for (int i = 0; i < rst.length; i++)
        {
            rst[i] = H - 1 - rst[i];
        }
        return rst;
        
    }
    
    // sequence of indices for vertical seam        
    public   int[] findVerticalSeam()
    {
        double[][] energyArray = calEnergyArray();
        double[][] distTo = new double[W][H];
        for (double i[] : distTo)
            for (double j : i)
                j = INFINITY;
        
        short[][] edgeTo = new short[W][H];
        
        return findSeam(energyArray, distTo, edgeTo, this.width(), this.height());
    }
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) 
    {
        if (H < 2)
            throw new IllegalArgumentException("Picture height less than 2");
        isValid(seam, false);
        
        for (int c = 0; c < W; c++)
        {
            for (int r = seam[c]; r < H - 1; r++)
            {
                color[c][r] = color[c][r + 1];
            }
        }
        H--;
        
//        calEnergyArray();
    }
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)     
    {
        if (W < 2)
            throw new IllegalArgumentException("Picture width less than 2");
        isValid(seam, true);


        for (int r = 0; r < H; r++)
        {
            for (int c = seam[r]; c < W - 1; c++)
            {
                color[c][r] = color[c + 1][r];
            }
        }
        W--;
        
//        calEnergyArray();
    }
     
    //check if the input seam[] is valid, 
    //boolean VH = true means seam[] is a VerticalSeam, otherwise seam[] is a HorizontalSeam
    private void isValid(int[] seam, boolean VH)
    {
        if (seam == null)
            throw new NullPointerException("input seam is null");
        if (VH && seam.length != H)
            throw new IllegalArgumentException("input seam is illegal");
        if (!VH && seam.length != W)
            throw new IllegalArgumentException("input seam is illegal");
        for (int i = 0; i < seam.length; i++)
        {
            if (seam[i] < 0)
                throw new IllegalArgumentException("input seam is illegal");
            if (VH && seam[i] >= W)
                throw new IllegalArgumentException("input seam is illegal");
            if (!VH && seam[i] >= H)
                throw new IllegalArgumentException("input seam is illegal");
            if ((i != seam.length -1) && (seam[i] - seam[i + 1] > 1 || seam[i] - seam[i + 1] < -1))
                throw new IllegalArgumentException("input seam is illegal");
        }
    }
    
    
    private double[][] reverse(double[][] matrix)
    {
        if (matrix == null) throw new NullPointerException("input is null");
        
//        double[][] rst = new double[matrix[0].length][matrix.length];
//        for (int i = 0; i < matrix[0].length; i++)
//            for (int j = 0; j < matrix.length; j++)
//            {
//                rst[i][j] = matrix[j][matrix[0].length - 1 - i];
//            }
        double[][] rst = new double[H][W];
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
            {
                rst[i][j] = matrix[j][H - 1 - i];
            }
        return rst;
        
    }
    
    private int[] findSeam(double[][] energy, double[][] distTo, short[][] edgeTo, int W, int H)
    {

        for (int r = 0; r < H; r++)
            for (int c = 0; c < W; c++)
        {
            relax(energy, distTo, edgeTo, c, r, W, H);
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
    private double[][] calEnergyArray()
    {
//        if (energyArray == null)
//            energyArray = new double[width()][height()];
        double[][] energyArray = new double[width()][height()];
        for (int c = 0; c < width(); c++)
            for (int r = 0; r < height(); r++)
        {
            energyArray[c][r] = energy(c, r);
        }
        return energyArray;
    }
    
    //update the distTo and edgeTo according to the input column c and row r 
    private void relax(double[][] energy, double[][] distTo, short[][] edgeTo, int c, int r, int W, int H)
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
            edgeTo[c][r] = (short)c;
            return;
        }
        if (c == 0)
        {
            if (distTo[c][r - 1] <= distTo[c + 1][r - 1])
            {
                distTo[c][r] = distTo[c][r - 1] + energy[c][r];
                edgeTo[c][r] = (short)c;
            }
            else
            {
                distTo[c][r] = distTo[c + 1][r - 1] + energy[c][r];
                edgeTo[c][r] = (short)(c + 1);
            }
            return;
        }
        if (c == W-1)
        {
            if (distTo[c - 1][r - 1] <= distTo[c][r - 1])
            {
                distTo[c][r] = distTo[c - 1][r - 1] + energy[c][r];
                edgeTo[c][r] = (short)(c - 1);
            }
            else
            {
                distTo[c][r] = distTo[c][r - 1] + energy[c][r];
                edgeTo[c][r] = (short)c;
            }
            return;
        }
        
        if (distTo[c - 1][r - 1] <= distTo[c][r - 1] && distTo[c - 1][r - 1] <= distTo[c + 1][r - 1])
        {
            distTo[c][r] = distTo[c - 1][r - 1] + energy[c][r];
            edgeTo[c][r] = (short)(c - 1);
        }
        else if (distTo[c][r - 1] <= distTo[c + 1][r - 1])
        {
            distTo[c][r] = distTo[c][r - 1] + energy[c][r];
            edgeTo[c][r] = (short)c;
        }
        else
        {
            distTo[c][r] = distTo[c + 1][r - 1] + energy[c][r];
            edgeTo[c][r] = (short)(c + 1);
        }
    }
    

    
}
