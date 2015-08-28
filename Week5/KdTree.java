/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week5
 *  author: Bill Quan  
 *  Last edited: 20150828
 *  KdTree.java, the second part of week5's programming assignment
 ****************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;


import edu.princeton.cs.algs4.StdOut;


public class KdTree 
{

    private Node root;
        
    private static class Node 
    {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        
        public Node(Point2D point, RectHV rect) 
        {
            this.p = point;
            this.rect = rect;
        }
    }

    /**
     * construct an empty set of points 
     */
    public KdTree()
    {
        //do nothing
    }
    
    /**
     * is the set empty? 
     */
    public boolean isEmpty()
    {
        return this.size() == 0;
    }
    
    /**
     * number of points in the set 
     */
    public int size()
    {
        return size(root);
    }
    
    // return number of Nodes rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return size(x.left) + size(x.right) + 1;
    }
    
    /**
     * add the point to the tree (if it is not already in the tree)
     */
    public void insert(Point2D p)
    {
        if (p == null) throw new NullPointerException("Point2D is null");
        root = insert(root, new RectHV(0, 0, 1, 1), p, true);
    }
    
    //insert the point2D p into the subtree of Node n according to the x-coordinate or y-coordinate decided by boolean b
    //x-coordinate if true, y-coordinate if false
    //@rect is the axis-aligned rectangle of node n
    //@return n if n is not null; return new node constructed by p if n is null
    private Node insert(Node n, RectHV rect, Point2D p, boolean b)
    {
        if (n == null) 
            return new Node(p, rect);
        
        if (b) //according to x-coordinate
        {
            if (p.x() < n.p.x())
                n.left = insert(n.left, new RectHV(n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax()), p, !b);
            else
            {
                if (p.x() == n.p.x() && p.y() == n.p.y()) return n;
                n.right = insert(n.right, new RectHV(n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax()), p, !b);
            }
        }
        else  //according to y-coordinate
        {
            if (p.y() < n.p.y())
                n.left = insert(n.left, new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y()), p, !b);
            else
            {
                if (p.y() == n.p.y() && p.x() == n.p.x()) return n;
                n.right = insert(n.right, new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax()), p, !b);
            }
        }
        return n;
    }
    
    /**
     * does the set contain point p? 
     */
    public boolean contains(Point2D p)
    {
        return contains(root, p, true);
    }
    
    //check if p is contained by the subtree of Node n according to the x-coordinate or y-coordinate decided by boolean b
    //x-coordinate if true, y-coordinate if false
    //@return true if contains, otherwise false;
    private boolean contains(Node n, Point2D p, boolean b)
    {
        if (n == null) return false;
        if (n.p.equals(p)) return true;
        if (b)
        {
            if (p.x() < n.p.x())
                return contains(n.left, p, !b);
            else
                return contains(n.right, p, !b);
        }
        else
        {
            if (p.y() < n.p.y())
                return contains(n.left, p, !b);
            else
                return contains(n.right, p, !b);
        }
        
    }
    
    /**
     * draw all points to standard draw 
     */
    public void draw()             
    {
        draw(root, true);
    }
            
    private void draw(Node n, boolean b)
    {
        if (n == null) return;
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        n.p.draw();
    
        if (b)
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            new Point2D(n.p.x(), n.rect.ymin()).drawTo(new Point2D(n.p.x(), n.rect.ymax()));
        }
        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            new Point2D(n.rect.xmin(), n.p.y()).drawTo(new Point2D(n.rect.xmax(), n.p.y()));
        }
        draw(n.left, !b);
        draw(n.right, !b);

    }
            
    /**
     * all points that are inside the rectangle 
     */
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> s = new Stack<Point2D>();
        return range(root, rect, s, true);
    }
    
    private Stack<Point2D> range(Node n, RectHV rect, Stack<Point2D> s, boolean b)
    {   
        if (n == null) return s;
        
        if (b)
        {
            if (n.p.x() > rect.xmax()) //split line does not intersect the query rectangle
            {
                return range(n.left, rect, s, !b);
            }
            else if (n.p.x() <= rect.xmin()) //split line does not intersect the query rectangle
            {
                return range(n.right, rect, s, !b);
            }
            else                        //split line intersects the query rectangle
            {
                if (rect.contains(n.p))
                    s.push(n.p);
                return range(n.left, rect, range(n.right, rect, s, !b), !b);
            }
        }
        else
        {
            if (n.p.y() > rect.ymax()) //split line does not intersect the query rectangle
            {
                return range(n.left, rect, s, !b);
            }
            else if (n.p.y() <= rect.ymin()) //split line does not intersect the query rectangle
            {
                return range(n.right, rect, s, !b);
            }
            else                        //split line intersects the query rectangle
            {
                if (rect.contains(n.p))
                    s.push(n.p);
                return range(n.left, rect, range(n.right, rect, s, !b), !b);
            }
        }
        
    }
    
    /**
     *  a nearest neighbor in the set to point p; null if the set is empty 
     */
    public Point2D nearest(Point2D p)
    {
        return nearest(root, p, null, true);
    }
    
    private Point2D nearest(Node n, Point2D p, Point2D np, boolean b)
    {
        if (n == null) return np;
        
        if (np == null) np = n.p;
        if (p.equals(np)) return np;
        if (p.distanceSquaredTo(np) > p.distanceSquaredTo(n.p)) np = n.p;
        if (p.distanceSquaredTo(np) <= n.rect.distanceSquaredTo(p)) return np;
//        StdOut.println("searching nearest in : " + n.p);
        
        if (b)
        {
//            if (n.left != null && p.distanceSquaredTo(np) <= n.left.rect.distanceSquaredTo(p))
//                return nearest(n.right, p, np, !b);
//            else if (n.right != null && p.distanceSquaredTo(np) <= n.right.rect.distanceSquaredTo(p))
//                return nearest(n.left, p, np, !b);
//            else 
//            {
                if (p.x() < n.p.x())
                    return nearest(n.right, p, nearest(n.left, p, np, !b), !b);
                else
                    return nearest(n.left, p, nearest(n.right, p, np, !b), !b);
//            }
        }
        else
        {
//            if (n.left != null && p.distanceSquaredTo(np) <= n.left.rect.distanceSquaredTo(p))
//                return nearest(n.right, p, np, !b);
//            else if (n.right != null && p.distanceSquaredTo(np) <= n.right.rect.distanceSquaredTo(p))
//                return nearest(n.left, p, np, !b);
//            else 
//            {
                if (p.y() < n.p.y())
                    return nearest(n.right, p, nearest(n.left, p, np, !b), !b);
                else
                    return nearest(n.left, p, nearest(n.right, p, np, !b), !b);
//            }
        }
            

        
        
    }

//    private double distanceSquaredTo(Point2D p, Point2D min, Point2D max) 
//    {
//        double dx = 0.0, dy = 0.0;
//        if      (p.x() < min.x()) dx = p.x() - min.x();
//        else if (p.x() > max.x()) dx = p.x() - max.x();
//        if      (p.y() < min.y()) dy = p.y() - min.y();
//        else if (p.y() > max.y()) dy = p.y() - max.y();
//        return dx*dx + dy*dy;
//    }
//    public void printAll()
//    {
//        printTree(root);
//    }
//    // for debug
//    public void printTree(Node n)
//    {
//        if (n == null) 
//        {
//            StdOut.print(" null ");
//            return;
//        }
//        StdOut.print("{ Point2D = " + n.p + " | RectHV = " + n.rect + "\n");
//        StdOut.print(" | my LeftNode is ");
//        printTree(n.left);
//        StdOut.print("\n");
//        StdOut.print(" | my RightNode is ");
//        printTree(n.right);
//        StdOut.print("\n");
//        StdOut.print(" } \n");
//    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
//        Point2D p1 = new Point2D(0.5, 0.5);
//        Point2D p2 = new Point2D(0.25, 0.25);
//        Point2D p3 = new Point2D(0.1, 0.3);
//        RectHV r = new RectHV(0.0, 0.0, 0.99, 0.99);
//        
//        KdTree kd = new KdTree();
//        kd.insert(p1);
//        kd.insert(new Point2D(0.5, 0.6));
//        kd.insert(p2);
//        kd.insert(p3);
//        kd.insert(new Point2D(0.05, 0.05));
//        kd.insert(new Point2D(0.2, 0.2));
//        kd.insert(new Point2D(0.9, 0.9));
////        kd.printAll();
//        kd.draw();
//        StdOut.println(kd.nearest(new Point2D(0.99, 0.99)));
//        Stack<Point2D> s = (Stack<Point2D>) kd.range(r);
//        for (Point2D pp : s)
//        {
//            StdOut.println(pp);
//        }
//        StdOut.print(kd.contains(new Point2D(0.1, 0.3)));
//        PointSET pSet = new PointSET();
//        pSet.insert(p1);
//        pSet.insert(p2);
//        pSet.draw();
//        StdOut.println(pSet.nearest(p1));
//        r.draw();
        
    }
       
    
}
