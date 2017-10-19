public class Deque<Item> implements Iterable<Item> {
  //instance variables
  private Node first;
  private Node last;
  private int size = 0;
  
  private class Node
  {
    Item item;
    Node next;
    Node prev;
  }
  public Deque()                           // construct an empty deque
  {
     first = null;
     last = null;
  }
  public boolean isEmpty()                 // is the deque empty?
  {
    return first = null;
  }
  public int size()                        // return the number of items on the deque
  {
    return size;
  }
  public void addFirst(Item item)          // add the item to the front
  {
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    first.prev = null;
    oldfirst.prev = first;
  }
  public void addLast(Item item)           // add the item to the end
  {
    Node oldlast = last; 
    last = new Node();
    last.item = item;
    last.prev = oldlast;
    last.next = null;
    oldlast.next = last; 
  }
  public Item removeFirst()                // remove and return the item from the front
  {
    Item item = first.item;
    first = first.next;
    first.prev = null;
    return item; 
  }
  public Item removeLast()                 // remove and return the item from the end
  {
    item item = last.item;
    last = last.prev;
    last.next = null;
    return item;
  }
    
  public Iterator<Item> iterator()         // return an iterator over items in order from front to end
  {
     return new ArrayIterator();
   }
   private class ArrayIterator implements Iterator<Item>
   {
     private Node current = last; 
     public boolean hasNext()
     {
       return current!= null;
     }
     public void remove() 
     {
       throw new java.lang.UnsupportedOperationException();
     }
     public Item next()
     {
       if(!hasNext())
         throw new java.util.NoSuchElementException();
       Item item = current.item;
       current = current.next;
       return item;
     }
   }
   public static void main(String[] args) {}
}