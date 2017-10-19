import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
  
  private Item[] struc;
  private int k;
  private int size;
  
   public RandomizedQueue()                 // construct an empty randomized queue
   {
     struc = (Item[]) new Object[1];
     k = -1;
     size = 0;
   }
   public boolean isEmpty()                 // is the queue empty?
   {
     return(size==0);
   }
   private int length()
   {
     return struc.length;
   }
   public int size()                        // return the number of items on the queue
   {
     return size;
   }
   public void enqueue(Item item)           // add the item
   {
     if(item == null)
       throw new java.lang.IllegalArgumentException();
     int kt = k+1;
     if(kt>= struc.length)
     {
       int tempsize = kt*2;
       if(struc.length==0)
       {
         tempsize++;
       }
       Item[] temp = (Item[]) new Object[tempsize];
       for(int i = 0; i <struc.length; i++)
       {
         temp[i] = struc[i];
       }
       struc = (Item[]) new Object[tempsize];
       for(int i = 0; i<temp.length;i++)
       {
         struc[i] = temp[i];
       }
     }
     k++;
     struc[k] = item;
     size++;
   }
   public Item dequeue()                    // remove and return a random item
   {
     if(isEmpty())
       throw new java.util.NoSuchElementException ();
     Item item = null;
     while(item == null && !isEmpty())
     {
       int rand = StdRandom.uniform(struc.length);
       if(struc[rand]!=null)
       {
         item = struc[rand];
         struc[rand] = null;
         size--;
       }
     }
     
     if(4*size<struc.length)
     {
       int count = 0;
       int tempsize = 2*size;
       Item[] temp = (Item[]) new Object[tempsize];
       for(int i = 0; i < struc.length; i++)
       {
         if(struc[i] != null)
         {
           temp[count] = struc[i];
           count++;
         }
       }
       struc = (Item[]) new Object[tempsize];
       for(int i = 0; i<tempsize; i++)
       {
         struc[i] = temp[i];
       }
     k = size-1;
     }
     return item;
   }
   
   public Item sample()                     // return (but do not remove) a random item
   {
     if(isEmpty())
       throw new java.util.NoSuchElementException ();
     Item item = null;
     while(item == null && !isEmpty())
     {
       int rand = StdRandom.uniform(struc.length);
       if(struc[rand] != null)
         item = struc[rand];
     }
     return item;
   }
   
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
     return new ArrayIterator();
   }
   private class ArrayIterator implements Iterator<Item>
   {
     private int j =0;
     private Item[] temp;
     public ArrayIterator()
     {
       temp = (Item[]) new Object[size];
       int count = 0;
       for(int i = 0; i <struc.length;i++)
       {
         if(struc[i]!=null)
         {
           temp[count] = struc[i];
           count++;
         }
       }
       StdRandom.shuffle(temp);
     }
     public boolean hasNext()
      {
        return temp.length-j+1>1;
      }
     public void remove() 
     {
       throw new java.lang.UnsupportedOperationException();
     }
     public Item next()
     {
       if(!hasNext())
         throw new java.util.NoSuchElementException();
       return temp[j++];
     }
   }
     public static void main(String[] args) // unit testing (optional)
     {
       RandomizedQueue<Integer> test = new RandomizedQueue();
       
       test.enqueue(1);
       test.enqueue(2);
       System.out.println(test.isEmpty());
       test.enqueue(3);
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
       System.out.println(test.sample());
     }
}