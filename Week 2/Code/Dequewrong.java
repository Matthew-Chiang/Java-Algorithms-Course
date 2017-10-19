import java.util.Iterator;
//generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure. 
public class Deque<Item> implements Iterable<Item> {
  
  private Item[] structure;
  private int j,k; // j is the begining and k is the end
  
   public Deque()                           // construct an empty deque
   {
     structure = (Item[]) new Object[1];
     j = 0;
     k = 0;
   }
   
   public boolean isEmpty()                 // is the deque empty?
   {return(structure.length==1 && structure[0] == null);}
   
   public int size()                        // return the number of items on the deque
   {return(structure.length);}
   public void addFirst(Item item)          // add the item to the front
   {
     if(item == null)
       throw new java.lang.IllegalArgumentException();
     int jt = j-1;// use in next line to check if j is there is any more room
     if(jt<0)
     {
       int doublesize = 2*(k-j)+1;// calculate how much space to add
       int tempsize = structure.length + doublesize; // size of new array
       
       Item[] temp = (Item[])new Object[tempsize]; // initialize temp array
       
       for(int i = doublesize; i<temp.length; i++) // copying structure into temp array (starts at a non zero value)
       {
         temp[i] = structure[i-doublesize];
       }
       j = doublesize; // the whole array is shifted by doublesize - j and k shift accordingly
       k = k+doublesize;
       
       structure = (Item[]) new Object[temp.length];// resize structure
       for(int i = 0; i<temp.length; i++) //place temp's values into structure array
       {
         structure[i] = temp[i];
       }
     }
     j--;// decrement
     structure[j] = item; // store
   }
   
   public void addLast(Item item)           // add the item to the end
   {
     if(item == null)
       throw new java.lang.IllegalArgumentException();
     int kt = k+1;
     if(kt>structure.length)
     {
       int doublesize = 2*(k-j)+1;// calculate how much space to add
       int tempsize = structure.length + doublesize; // size of new array
       
       Item[] temp = (Item[]) new Object[tempsize]; // initialize temp array
       
       for(int i = 0 ; i<structure.length; i++) // copying structure into temp array (starts at a zero value)
       {
         temp[i] = structure[i];
       }
        //j and k are unchanged
       structure = (Item[]) new Object[temp.length];// resize structure
       for(int i = 0; i<temp.length; i++)// recopy values
       {
         structure[i] = temp[i];
       }
     }
     k++; //increment
     structure[k] = item;// store
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
     if(isEmpty())
       throw new java.util.NoSuchElementException ();
     Item item; 
     item = structure[j];// saving
     structure[j] = null;// removing
     j++;// incrementing
     int elements = k-j+1;
     if(j>=(4*elements))// if there is four times the amount of space before the first element than are elements in the stack
     {
       Item[] temp = (Item[]) new Object[structure.length-2*elements];// initialize new smaller temp array
       for(int i = 2*elements; i<temp.length; i++)// copy into temp array
       {
         temp[i-2*elements] = structure[i];
       }
       structure = (Item[]) new Object[temp.length];
       for(int i = 0; i<temp.length; i++)
       {
         structure[i] = temp[i]; // recopy structure
       }
       j = j- 2*elements;// shift j and k back
       k = k- 2*elements;
     }
     return item;
   }
   
   public Item removeLast()                 // remove and return the item from the end
   {
     if(isEmpty())
       throw new java.util.NoSuchElementException ();
     Item item; 
     item = structure[k];// saving
     structure[k] = null; //removing
     k--;
     int elements = k-j+1;
     if((structure.length-k)>= 4*elements)
     {
       Item[] temp = (Item[]) new Object[structure.length-2*elements];// initialize new smaller temp array
       for(int i = 0; i<temp.length; i++)
       {
         temp[i] = structure[i];
       }
       structure = (Item[]) new Object[temp.length];
       for(int i = 0; i<temp.length; i++)
       {
         structure[i] = temp[i]; // recopy structure
       }
       //j,k remain unchanged.
     }
     return item;
   }
     
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
     return new ArrayIterator();
   }
   private class ArrayIterator implements Iterator<Item>
   {
     private int i = 0;
     public boolean hasNext()
     {
       return i>0;
     }
     public void remove() 
     {
       throw new java.lang.UnsupportedOperationException();
     }
     public Item next()
     {
       if(!hasNext())
         throw new java.util.NoSuchElementException();
       return structure[++i];
     }
   }
   public static void main(String[] args){}   // unit testing (optional)
}