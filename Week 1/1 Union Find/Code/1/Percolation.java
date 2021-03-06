import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// weighted union variable = ds
public class Percolation {
  
  
  
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   {
     n2 = (n*n)+2; // n is the top site n+1 is the botttom site
     WeightedQuickUnionUF ds = new WeightedQuickUnionUF(n2);
     
     int[][] grid =  new int[n][n];   
   }
   // input from 1-n
   
   public    void open(int row, int col)    // open site (row, col) if it is not open already
   {
     if( row ==1 && col ==1)//corners
     {
       connect(grid.length,grid.length+1,row,col);//connect square to top
       if(isOpen(row,col+1))
         connect(row,col+1,row,col);
       if(isOpen(row+1,col))
            connect(row+1,col,row,col);
     }
     else if( row ==1 && col ==grid.length)
     {
       connect(grid.length,grid.length+1,row,col);//connect square to top
       if(isOpen(row,col-1))
            connect(row,col-1,row,col);
       if(isOpen(row+1,col))
            connect(row+1,col,row,col);
     }
     else if( row ==grid.length && col ==1)
     {
       connect(grid.length,grid.length+2,row,col);//connect square to bottom
       if(isOpen(row-1,col))
         connect(row-1,col,row,col);
       if(isOpen(row,col+1))
         connect(row,col+1,row,col);
     }
     else if( row ==grid.length && col ==grid.length)
     {
       connect(grid.length,grid.length+2,row,col);//connect square to bottom
       if(isOpen(row-1,col))
         connect(row-1,col,row,col);
       if(isOpen(row,col-1))
         connect(row,col-1,row,col);
     }
     else if (row ==1 || row == grid.length || col ==1|| col==grid.length)//edges
     {
       if( row==1)//top row
       {
         connect(grid.length,grid.length+1,row,col);//connect square to top
         if(isOpen(row,col-1))
           connect(row,col-1,row,col);
         if(isOpen(row,col+1))
           connect(row,col+1,row,col);
         if(isOpen(row+1,col))
           connect(row+1,col,row,col);
       }
       if(row == grid.length)//bottom row
       {
         connect(grid.length,grid.length+2,row,col);//connect square to bottom
         if(isOpen(row,col-1))
           connect(row,col-1,row,col);
         if(isOpen(row,col+1))
           connect(row,col+1,row,col);
         if(isOpen(row-1,col))
           connect(row-1,col,row,col);
       }
       if(col ==1) //left side
       {
         if(isOpen(row-1,col))
           connect(row-1,col,row,col);
         if(isOpen(row+1,col))
           connect(row+1,col,row,col);
         if(isOpen(row,col+1))
           connect(row,col+1,row,col);
       }
       if(col == grid.length)//right side
       {
         if(isOpen(row-1,col))
           connect(row-1,col,row,col);
         if(isOpen(row+1,col))
           connect(row+1,col,row,col);
         if(isOpen(row,col-1))
           connect(row,col+1,row,col);
       }
     }
     else//everythign middle squares
     {
       if(isOpen(row,col-1))
         connect(row,col-1,row,col);
       if(isOpen(row,col+1))
         connect(row,col+1,row,col);
       if(isOpen(row-1,col))
         connect(row-1,col,row,col);
       if(isOpen(row+1,col))
         connect(row+1,col,row,col);
     }
   private int 2dto1d (int row, int col)
   {
     int q = (row-1)*grid.length + col-1;
     return q;
   }
   
   private void connect(int row1,int col1,int row2,int col2) //row 2 and col 2 are the original spot
     {
       int a = 2dto1d(row1,col1);
       int a2 = 2dto1d (row2,col2);
       ds.union(a,a2);
       grid[row2-1][col2-1] =1 ;
     }
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
     if(grid[row-1][col-1] == 1)
       return true;
     else
       return false;
   }
   
   
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
     if(ds.connected(2dto1d(grid.length,grid.length+1),2dto1d(row,col)))
       //             first node                        spot given by user
       return true;
     else
       return false;
   }
     
   public int numberOfOpenSites()       // number of open sites
   {
     int count = 0;
     for(int i =0; i< grid.length ; i++)
     {
       for(int j = 0;j<grid[0].length;j++)
       {
         if (grid[i][j] = 1)
           count++;
       }
     }
   }
   
   
   public boolean percolates()            // does the system percolate?
   {
      if(ds.connected(2dto1d(grid.length,grid.length+1),2dto1d(grid.length,grid.length+2)))
        // first node                              last node
        return true;
      else
        return false; 
   }

   
   public static void main(String[] args)   // test client (optional)
}

