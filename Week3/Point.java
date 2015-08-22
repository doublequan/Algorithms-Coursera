/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week3
 *  author: Bill Quan  
 *  Last edited: 20150820
 *  Point.java, the first part of week3's programming assignment
 ****************************************************************************/
import java.util.Comparator;


public class Point implements Comparable<Point> 
{
    private int x;
    private int y;
    
    /**
     * compare points by slope to this point
     * @param that: The Point is going to be compared  
     * @return -1 if this this point is smaller; 1 bigger; 0 equal
     */
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>()
    {          
        public int compare(Point p1, Point p2) 
        {  
            if (slopeTo(p1) > slopeTo(p2))
                return 1;
            else if (slopeTo(p1) < slopeTo(p2))
                return -1;
            else 
                return 0;
            
        }
    };
    
    /**
     * compare points by slope to this point
     * @param that: The Point is going to be compared  
     * @return -1 if this this point is smaller; 1 bigger; 0 equal
     */
    public final Comparator<Point> COMPARE_ORDER = new Comparator<Point>()
    {          
        public int compare(Point p1, Point p2) 
        {
            return p1.compareTo(p2);
        }
    };
    
    

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }
    
    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Is this point lexicographically smaller than that point?
     * Compare points by their y-coordinates, breaking ties by their x-coordinates. 
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     * @param that: The Point is going to be compared  
     * @return -1 if this this point is smaller; 1 bigger; 0 equal
     */
    public int compareTo(Point that)
    {
        if (y < that.y || (y == that.y && x < that.x))
            return -1;
        else if (y == that.y && x == that.x)
            return 0;
        else 
            return 1;
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
        else if (dy == 0)
            return +0;
        else 
            return dy/dx;
    }
        
        
    public static void main(String[] args)   // unit testing
    {
        int a = 1;
        StdOut.println(a*1.0/-3);
       
    }
}