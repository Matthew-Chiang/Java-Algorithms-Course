import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
     int[] results = new int[trials];
   StdRandom ran = new StdRandom();
   StdStats stat = new StdStats();
   ran.setSeed(20);
     for(int i = 0 ; i< trials ; i++)
     {
       if(n < 1)
         throw new IllegalArgumentException(Integer.toString(n));
       if(trials<1)
         throw new IllegalArgumentException(Integer.toString(n));
       Perlocation per = new Perlocation(n);
       
       while(results[i]!=0) // while no result is given
       {
       
       int set =1;
       while(set = 1) // randomly open a blocked square
       {
         int a = ran.uniform(n) +1;
         int a2 = ran.uniform(n) +1;
         if(per.isOpen(a,a2)
         {
            per.open(a,a2);
            set = 0; 
         }
       }
       if(per.perlocates()) // check if perlocates
         results[i] = per.numberofoopensites();
       }
     }
   
   public double mean()                          // sample mean of percolation threshold
   {
     return stat.mean(results);
   }
   public double stddev()                        // sample standard deviation of percolation threshold
     return stat.stddev(results);
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
     return mean()-(1.96*stddev()/sqrt(trials));
   public double confidenceHi()                  // high endpoint of 95% confidence interval
     return mean()+x`(1.96*stddev()/sqrt(trials));
   
   public static void main(String[] args)        // test client (described below)
   {
     StdOut out = new StdOut();
     
     int n = Integer.parseInt(args[0]);
     int T = Integer.parseInt(args[1]);
     
     PerlocationStats perst = new PerlocationStats(n,T);
     
     out.print("mean = ");
     out.println(perst.mean());
     
     out.print("stddev = ");
     out.println(perst.stddev()):
       
     out.print("95% confidence interval = [");
     out.print(perst.confidenceLo());
     out.print(", ");
     out.print(perst.confidenceHi());
     out.println("]");
     
   }
}