import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  //instance variables
  private Node first=null;
  private Node last=null;
  private int size = 0;
  
  private class Node
  {
    Item item;
    Node next;
    Node prev;
  }
  public Deque()                           // construct an empty deque
  {  }
  public boolean isEmpty()                 // is the deque empty?
  {
    return size==0;
  }
  public int size()                        // return the number of items on the deque
  {
    return size;
  }
  public void addFirst(Item item)          // add the item to the front
  {
    if(item == null)
       throw new java.lang.IllegalArgumentException();
    if(size==0)
    {
      first=new Node();
      first.item = item;
      first.next = null;
      first.prev = null;
      last = first;
    }
    else
    {
      Node oldfirst = first;
      first = new Node();
      first.item = item;
      first.next = oldfirst;
      first.prev = null;
      if(oldfirst!=null)
        oldfirst.prev = first;
    }
    size++;
  }
 
  public void addLast(Item item)           // add the item to the end
  {
    if(size==0)
    {
      first=new Node();
      first.item = item;
      first.next = null;
      first.prev = null;
      last = first;
    }
    else
    {
      if(item == null)
        throw new java.lang.IllegalArgumentException();
      Node oldlast = last; 
      last = new Node();
      last.item = item;
      last.prev = oldlast;
      last.next = null;
      if(oldlast!=null)
        oldlast.next = last;
    }
    size++;
  }
  public Item removeFirst()                // remove and return the item from the front
  {
    if(isEmpty())
       throw new java.util.NoSuchElementException ();
    Item item;
    if(size==1)
    {
      item = first.item;
      first = null;
      last = null;
    }
    else
    {
      item = first.item;
      first = first.next;
      if(first.prev != null)
        first.prev = null;
    }
    size--;
    return item; 
  }
  public Item removeLast()                 // remove and return the item from the end
  {
    if(isEmpty())
       throw new java.util.NoSuchElementException ();
    Item item;
    if(size==1)
    {
      item = first.item;
      first = null;
      last = null;
    }
    else
    {
      item = last.item;
      last = last.prev;
      if(last.next != null)
        last.next = null;
    }
    size--;
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