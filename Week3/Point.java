/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week3
 *  author: Bill Quan  
 *  Last edited: 20150812
 *  Point.java, the first part of week2's programming assignment
 ****************************************************************************/

public class Point implements Comparable<Point> {
    private int x;
    private int y;
    
//    public final Comparator<Point> SLOPE_ORDER;        // compare points by slope to this point

//    public Point(int x, int y)                         // construct the point (x, y)

//    public   void draw()                               // draw this point
//    public   void drawTo(Point that)                   // draw the line segment from this point to that point
//    public String toString()                           // string representation

    /**
     * Is this point lexicographically smaller than that point?
     * Compare points by their y-coordinates, breaking ties by their x-coordinates. 
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     * @param that: The Point is going to be compared  
     * @return true if this this point is smaller; false otherwise
     */
    public int compareTo(Point that)
    {
        if (y < that.y)
            return 1;
        if (y == that.y && x < that.x)
            return 1;
        return 0;
    }
    
    /**
     * the slope between this point and that point
     * Treat the slope of a horizontal line segment as positive zero; treat the slope of a vertical line segment as positive infinity; 
     * treat the slope of a degenerate line segment (between a point and itself) as negative infinity
     * @param that: The Point is going to be compared  
     * @return the slope between this point and that point
     */
    public double slopeTo(Point that) 
    {
        double dy = that.y - y;
        double dx = that.x - x;
        if (dx == 0)
        {
            if (dy == 0)
                return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (dy == 0)
            return 0;
        return dy/dx;
    }
        
        
    public static void main(String[] args)   // unit testing
    {
        double a = 1.0;
        StdOut.println(a/-0.0);
       
    }
}