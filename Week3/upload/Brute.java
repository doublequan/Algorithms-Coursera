/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week3
 *  author: Bill Quan  
 *  Last edited: 20150820
 *  Brute.java, the second part of week3's programming assignment
 ****************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Brute {
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        
        Point[] pointsSet = new Point[N];   //create a set of points to be searched
        
        for (int i = 0; i < N; i++) 
        {
            int x = in.readInt();
            int y = in.readInt();
            pointsSet[i] = new Point(x, y);
            pointsSet[i].draw();
        }
        
        for (int i = 0; i < N; i++)
        {
            for (int j = i + 1; j < N; j++)
            {
                for (int k = j + 1; k < N; k++)
                {
                    if (pointsSet[i].slopeTo(pointsSet[j]) != pointsSet[i].slopeTo(pointsSet[k]))
                        continue;
                    for (int l = k + 1; l < N; l++)
                    {
                        if (pointsSet[i].slopeTo(pointsSet[j]) == pointsSet[i].slopeTo(pointsSet[l]))
                        {
                        
                            if(pointsSet[i].slopeTo(pointsSet[j]) == Double.NEGATIVE_INFINITY)    
                                continue;
                            
                            StdOut.println(pointsSet[i].toString() + " -> "
                                         + pointsSet[j].toString() + " -> "
                                         + pointsSet[k].toString() + " -> "
                                         + pointsSet[l].toString()
                                          );
                            
                            //do a simple insert sort to find the head and tail of the line
                            Point[] tmp = new Point[]{ pointsSet[i], pointsSet[j], pointsSet[k], pointsSet[l] }; 
                            for (int m = 1; m < 4; m++)
                            {
                                int n = m;
                                Point target = tmp[m];
                                
                                while (n > 0 && target.compareTo(tmp[n-1]) == 1)
                                {
                                    tmp[n] = tmp[n - 1];
                                    n--;
                                }
                                
                                tmp[n] = target;
                            }
                            tmp[0].drawTo(tmp[3]);
                        }
                    }
                }
            }
        
        }
  

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}