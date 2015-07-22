/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week2
 *  author: Bill Quan
 *  Subset.java, the third part of week2's programming assignment
 ****************************************************************************/

public class Subset {
   public static void main(String[] args)
   {
       int k = Integer.parseInt(args[0]);
       RandomizedQueue<String> RQ = new RandomizedQueue<String>();
       while (!StdIn.isEmpty())
       {
              RQ.enqueue(StdIn.readString());
       }
       for (int i = 0; i < k; i++)
            StdOut.println(RQ.dequeue());
   }
}


