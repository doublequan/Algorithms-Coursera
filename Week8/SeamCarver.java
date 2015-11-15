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
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        if (picture != null)
            p = new Picture(picture);
        else
            throw new NullPointerException("input is null");
        
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
            throw new NullPointerException("input is null");
        
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
           /**
   public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
   public   int[] findVerticalSeam()                 // sequence of indices for vertical seam
   public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
   public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
   **/
    
    public static void main(String[] args) {
        
        StdOut.print(args[0]);
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());
        
        SeamCarver sc = new SeamCarver(picture);
        
        StdOut.printf("Printing energy calculated for each pixel.\n");        

        for (int j = 0; j < sc.height(); j++) 
        {
            for (int i = 0; i < sc.width(); i++)
                StdOut.printf("%9.0f ", sc.energy(i, j));
            StdOut.println();
        }
    }
}
