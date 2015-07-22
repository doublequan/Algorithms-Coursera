/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week2
 *  author: Bill Quan
 *  RandomizedQueue.java, the second part of week2's programming assignment
 ****************************************************************************/
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private int N;                // size of the randomized queue
    private Item[] RQ;
    private int length;
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue()
    {
         N = 0;
         length = 1;
         RQ = (Item[]) new Object[length];
    }
    
    /**
     * Is this queue empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty()
    {
        return N == 0;
    }
    
    /**
     * Returns the number of items in the queue
     * @return the number of items in the deque
     */
    public int size() 
    {
        return N;
    }
    
    /**
     * Adds the item
     * @param item the item to add
     */
    public void enqueue(Item item) 
    {
        if (item == null) 
            throw new NullPointerException("null item cannot be added to the queue!");
        if (length == N) resize(2 * length);
        RQ[N] = item;
        N++;
    }
    
    //resize the array with the @param
    private void resize(int size)
    {
        Item[] tmp = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) 
            tmp[i] = RQ[i];
        RQ = tmp;
        length = size;
    }

    /**
     * remove and return a random item
     * @return a random item
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException("cannot remove from an empty queue!");
        int id = StdRandom.uniform(0, N);
        Item item = RQ[id];                 //save the item to return
        RQ[id] = RQ[N-1];                   //replace the RQ[id] with RQ[N-1](the last one)
        RQ[N-1] = null;                     //delete the last one of RQ
        N--;
        if (N > 0 && length >= N*4) resize(length/2);
        return item;                   
    }  
    
    /**
     * return a random item
     * @return a random item
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException("cannot remove from an empty queue!");
        return RQ[StdRandom.uniform(0, N)];                   
    }
    

    /**
     * Returns an iterator to this stack that iterates through the items in order from front to end
     * @return an iterator to this stack that iterates through the items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(RQ, N);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Item[] copy;
        private int N;

        public ListIterator(Item[] rq, int n) {
            N = n;
            if (N != 0) 
            {
                copy = (Item[]) new Object[N];
                for (int i = 0; i < N; i++)
                    copy[i] = RQ[i];
            }
        }
        public boolean hasNext()  { return N != 0;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int id = StdRandom.uniform(0, N);
            Item item = copy[id];                 //save the item to return
            copy[id] = copy[N-1];                   //replace the RQ[id] with RQ[N-1](the last one)
            copy[N-1] = null;                     //delete the last one of RQ
            N--;
            return item;
        }
    }
    
    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<Integer> RQ = new RandomizedQueue<Integer>();
        RQ.enqueue(1);
        RQ.enqueue(2);
        RQ.enqueue(3);
        RQ.enqueue(4);
        RQ.enqueue(5);
        RQ.enqueue(6);
        RQ.enqueue(7);
        RQ.enqueue(8);

        

        for (int i : RQ)
            StdOut.println(i);
        
        RQ.dequeue();
        RQ.dequeue();
        RQ.dequeue();
        RQ.dequeue();
        RQ.dequeue();
        RQ.dequeue();
        RQ.dequeue();
        for (int i : RQ)
            StdOut.println(i);
        RQ.dequeue();
        
        RQ.enqueue(111);
        RQ.enqueue(222);
        RQ.enqueue(333);
        RQ.enqueue(444);
        RQ.enqueue(555);
        RQ.enqueue(666);
        RQ.enqueue(777);
        RQ.enqueue(888);
        for (int i : RQ)
            StdOut.println(i);
        
    }
}