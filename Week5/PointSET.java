/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week5
 *  author: Bill Quan  
 *  Last edited: 20150826
 *  PointSET.java, the first part of week5's programming assignment
 ****************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;

import edu.princeton.cs.algs4.StdOut;


public class PointSET {
    
    private TreeSet<Point2D> set;
    
    /**
     * construct an empty set of points
     */
    public         PointSET()
    {
        set = new TreeSet<Point2D>();
    }
    
    /**
     * is the set empty? 
     */
    public boolean isEmpty()
    {
        return set.isEmpty();
    }
    
    /**
     * number of points in the set
     */
    public int size()
    {
        return set.size();
    }  
    
    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p)
    {
        set.add(p);
    }
    
    /**
     * does the set contain point p
     */
    public boolean contains(Point2D p)
    {
        return set.contains(p);
    }
    
    
    /**
     * draw all points to standard draw 
     * Use StdDraw.setPenColor(StdDraw.BLACK) and StdDraw.setPenRadius(.01) before before drawing the points; 
     * use StdDraw.setPenColor(StdDraw.RED) or StdDraw.setPenColor(StdDraw.BLUE) and StdDraw.setPenRadius() before drawing the splitting lines.
     */
    public void draw()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D p : set)
        {
            p.draw();
        }
    }
    
    /**
     * all points that are inside the rectangle 
     */
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D p : set)
        {
            if (rect.contains(p)) 
                stack.push(p);
        }
        return stack;
    }
    
    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p)
    {
        if (set.isEmpty()) return null;
        Point2D nearest = set.last();
        for (Point2D i : set)
        {
            if (i.distanceSquaredTo(p) < p.distanceSquaredTo(nearest)) 
                nearest = i;
        }
        return nearest;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        Point2D p1 = new Point2D(0.0, 1);
        Point2D p2 = new Point2D(0.8, 0.8);
//        RectHV r = new RectHV(0.5, 0.5, 0.8, 0.8);
        
        PointSET pSet = new PointSET();
        //pSet.insert(p1);
        //pSet.insert(p2);
        for (int i = 0; i < 1000; i++)
            pSet.insert(new Point2D(i*0.001, i*0.001));
        pSet.draw();
        StdOut.println(pSet.nearest(p1));
//        r.draw();
        
    }
       
    
}
