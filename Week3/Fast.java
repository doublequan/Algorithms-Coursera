/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week3
 *  author: Bill Quan  
 *  Last edited: 20150820
 *  Fast.java, the third part of week3's programming assignment
 ****************************************************************************/
import java.util.Arrays;

public class Fast {
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
        
        Arrays.sort(pointsSet, 1, N, pointsSet[0].SLOPE_ORDER);   //sort pointsSet from pointsSet[1] to pointsSet[N-1] according to pointsSet[0].SLOPE_ORDER

        
        
        
        int head = 1;
        int tail = 1;
        for (int i = 2; i < N; i++) 
        {
            if (pointsSet[0].slopeTo(pointsSet[i]) == pointsSet[0].slopeTo(pointsSet[head]))    
            {
                tail = i;
            }
            else
            {
                if (tail - head >= 2)
                {
                    //do print and draw with the currect head and tail
                    StdOut.print(pointsSet[0].toString() + " -> ");
                    for (int j = head; j < tail; j++)
                    {
                        StdOut.print(pointsSet[j].toString() + " -> ");
                    }
                    StdOut.print(pointsSet[tail].toString() + "\n");
                                                        
                    Arrays.sort(pointsSet, head, tail+1, pointsSet[0].COMPARE_ORDER);
                    
                    if (pointsSet[0].compareTo(pointsSet[head]) == -1)
                        pointsSet[0].drawTo(pointsSet[tail]);
                    else if (pointsSet[0].compareTo(pointsSet[tail]) == 1)
                        pointsSet[0].drawTo(pointsSet[head]);
                    else
                        pointsSet[head].drawTo(pointsSet[tail]);
                    
                }
                
                head = i;
            }
            
            
            
            
            
            
            
            
            
            StdOut.println(pointsSet[0].slopeTo(pointsSet[i]));
        }
        
        
        
        /**
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
        **/

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}