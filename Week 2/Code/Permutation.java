import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
   public static void main(String[] args)
   {
     int num = Integer.parseInt(args[0]);
     
     RandomizedQueue data = new RandomizedQueue();
     while(!StdIn.isEmpty())
     {
       data.enqueue(StdIn.readString());
     }
     
     for(int i = 0; i<num;i++)
     {
       StdOut.println(data.dequeue());
     }
   }
}