/****************************************************************************
 *  the programming assignment of Algorithms, Part I 
 *  Week2
 *  author: Bill Quan
 *  Deque.java, the first part of week2's programming assignment
 ****************************************************************************/
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{
    private int N;                // size of the deque
    private Node<Item> first;     // first of deque
    private Node<Item> last;     // last of deque
    
    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> front;
    }
    
    /**
     * construct an empty deque
     */
    public Deque()
    {
         N = 0;
         first = null;
         last = null;
    }
    
    /**
     * Is this deque empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty()
    {
        return N == 0 && first == null && last == null;
    }

    /**
     * Returns the number of items in the deque
     * @return the number of items in the deque
     */
    public int size() 
    {
        return N;
    }
  
    /**
     * Adds the item to the front
     * @param item the item to add
     */
    public void addFirst(Item item) 
    {
        if (item == null) 
            throw new NullPointerException("null item cannot be added to the deque!");
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;                                
        if (N == 0) last = first;
        else        oldfirst.front = first;   
        N++;
    }

    /**
     * add the item to the end
     * @param item the item to add
     */
    public void addLast(Item item) 
    {
        if (item == null) 
            throw new NullPointerException("null item cannot be added to the deque!");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.front = oldlast;                                   
        if (N == 0) first = last;
        else        oldlast.next = last;
        N++;
    }
    
    /**
     * remove and return the item from the front
     * @return the item most from the front
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item removeFirst()
    {
        if (isEmpty()) throw new NoSuchElementException("cannot remove from an empty deque!");
        Item item = first.item;        // save item to return
        if (N == 1) last = null;
        else        first.next.front = null;     //set the next node's front to null
        first = first.next;            // delete first node
        N--;
        return item;                   // return the saved item
    }    
    
    /**
     * remove and return the item in the end
     * @return the last item 
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException("cannot remove from an empty deque!");
        Item item = last.item;        // save item to return
        if (N == 1) 
        {
            first = null;
            last = null;
        }
        else        
        {
            last.front.next = null;
            last = last.front;
        }
        N--;
        return item;                   // return the saved item
    }
    
    /**
     * Returns an iterator to this stack that iterates through the items in order from front to end
     * @return an iterator to this stack that iterates through the items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    public static void main(String[] args)   // unit testing
    {
        Deque<Integer> D = new Deque<Integer>();
        D.addLast(1);
        D.addLast(2);
        D.removeLast();
        D.addLast(3);
        D.addFirst(0);
        D.addFirst(-1);
        D.removeFirst();
        for (int i : D)
            StdOut.println(i);
        
    }
}
