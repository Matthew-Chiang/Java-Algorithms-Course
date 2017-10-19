import java.lang.Math;
import java.util.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private int[][] board; 
  public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
  {
    board = new int[blocks.length][blocks[0].length];
    for(int i = 0; i<board.length; i++)  // down
    {
      for(int j = 0; j< board[0].length; j++) //  across
      {
        //System.out.println("i: " + i + "j: " + j);
        board[i][j] = blocks[i][j];
        //System.out.println(board[i][j]);
        
      }
    }
  }
  public int dimension()                 // board dimension n
  {
    return board.length;
  }
  public int hamming()                   // number of blocks out of place
  {
    int count = 1;
    int count2 = 0;
    int square = (board.length-1)* (board.length-1);
    for(int i = 0; i<board.length; i++)
    {
      for(int j = 0; j<board[0].length ; j++)
      {
        if(board[i][j] != count && (i*j!=square))
          count2++;
        count++;
      }
    }
    return count2;
  }
  public int manhattan()                 // sum of Manhattan distances between blocks and goal
  {
    int count = 0;
    for(int i = 0; i<board.length; i++)
    {
      for(int j = 0; j<board[0].length; j++)
      {
        if(board[i][j] != 0)
        {
          int value = board[i][j] -1;
          int x = value%board.length; // across - corresponds with j
          int y = value/board.length; // down - corresponds with i
          count = count + Math.abs(x-j) + Math.abs(y-i); // finds distance
        }
      }
    }
    return count;
  }
  public boolean isGoal()                // is this board the goal board?
  {
    int square = (board.length*board.length)-1;
    int goal = 0;
    int count = 1;
    for(int i = 0; i<board.length; i++)
    {
      for(int j = 0 ; j<board[0].length; j++)
      {
        if(board[i][j] == count)//((i*3)/3 + j))
          goal++;
        count++;
      }
    }
    return goal==square; 
  }
  public Board twin()                    // a board that is obtained by exchanging any pair of blocks
  {
    int[][] twin = new int[board.length][board[0].length];
    // finding 0
    int locx = 0;
    int locy = 0;
    
    for(int i = 0; i<board.length; i++)
    {
      for(int j = 0; j<board[0].length; j++)
      {
        twin[i][j] = board[i][j];
        if(board[i][j] == 0)
        {
          locx = j;
          locy = i;
        }
      }
    }
    
    int temp = 0;
    int length = board.length;
    if(locy == 0)
    {
      /*temp = twin[board.length-1][board.length-2];
       twin[board.length-1][board.length-2] = twin[board.length-1][board.length-1];
       twin[board.length-1][board.length-1] = temp;*/
      twin = swap(twin,0,1,1,1);
    }
    else
    {
      /*temp = twin[0][board.length-2];
       twin[0][board.length-2] = twin[0][board.length-1];
       twin[0][board.length-1] = temp;*/
      twin = swap(twin,0,0,1,0);
    }
    
    Board Twin = new Board(twin);
    return Twin;
  }
  public boolean equals(Object y)        // does this board equal y?
  {
    Board temp = (Board) y;
    int goal = 0;
    for(int i = 0; i<board.length; i++)
    {
      for(int j = 0 ; j<board[0].length; j++)
      {
        if(board[i][j] == temp.board[i][j])//y.board[i][j])// FIX
          goal++;
      }
    }
    return goal==9; 
  }
  public Iterable<Board> neighbors()     // all neighboring boards   //NEED TO USE ARRAY LIST OR LINKED LIST BECAUSE IT IMPLEMENTS ITERABLE
  {
    LinkedList<Board> Neighbors = new LinkedList<Board>();
    
    // finding 0
    int locx = 0;
    int locy = 0;
    
    for(int i = 0; i<board.length; i++)
    {
      for(int j = 0; j<board[0].length; j++)
      {
        if(board[i][j] == 0)
        {
          locx = j;
          locy = i;
        }
      }
    }
    
    if(locy-1 >= 0) //if space above is in bounds
    {
      int[][] temparr = swap(board,locx,locy,locx,locy-1);
      Board tempboard = new Board(temparr);
      Neighbors.add(tempboard);
    }
    if(locy+1 < board.length) // if space below is in bounds
    {
      int[][] temparr = swap(board,locx,locy,locx,locy+1);
      Board tempboard = new Board(temparr);
      Neighbors.add(tempboard);
    }
    if(locx-1 >= 0) // if space to the left is in bounds
    {
      int[][] temparr = swap(board,locx,locy,locx-1,locy);
      Board tempboard = new Board(temparr);
      Neighbors.add(tempboard);
    }
    if(locx+1 < board.length) // if space to the right is in bounds
    {
      int[][] temparr = swap(board,locx,locy,locx+1,locy);
      Board tempboard = new Board(temparr);
      Neighbors.add(tempboard);
    }
    return Neighbors;
  }
  public String toString()               // string representation of this board (in the output format specified below)
  {
    /* return (board.length + "\n"
     + board[0][0] + " " + board[0][1] + " " + board[0][2] + "\n" 
     + board[1][0] + " " + board[1][1] + " " + board[1][2] + "\n" 
     + board[2][0] + " " + board[2][1] + " " + board[2][2]);*/
    String returnthis = board.length + "\n";
    
    for(int i = 0; i< board.length; i++)
    {
      for (int j = 0; j<board[0].length; j++)
      {
        if(j!= board[0].length -1)
          returnthis = returnthis + board[i][j] + " ";
        else
          returnthis = returnthis + board[i][j] + "\n";
      }
    }
    return returnthis;
  }
//---------------------SUPPORTING METHODS--------------------
  
  private int[][] swap(int[][] input, int locx1, int locy1, int locx2, int locy2)
  {
    int[][] that = new int[input.length][input[0].length];
    
    for(int i = 0; i< input.length; i++)
    {
      for(int j = 0; j< input.length; j++)
      {
        that[i][j] = input[i][j];
      }
    }
    
    int temp = that[locy2][locx2];
    that[locy2][locx2] = that[locy1][locx1];
    that[locy1][locx1] = temp;
    
    return that;
  }
//--------------------------MAIN--------------------------------------    
  /*public static void main(String[] args) // unit tests (not graded)
   {// test 1
   int[][] testarr = new int[3][3];
   
   testarr[0][0] = 4;
   testarr[0][1] = 8;
   testarr[0][2] = 3;
   
   testarr[1][0] = 7;
   testarr[1][1] = 1;
   testarr[1][2] = 2;
   
   testarr[2][0] = 0;
   testarr[2][1] = 6;
   testarr[2][2] = 5;
   
   //Board test = new Board(testarr);
   
   // test 2
   int[][] testarr2 = new int[3][3];
   
   testarr2[0][0] = 1;
   testarr2[0][1] = 2;
   testarr2[0][2] = 3;
   
   testarr2[1][0] = 4;
   testarr2[1][1] = 5;
   testarr2[1][2] = 6;
   
   testarr2[2][0] = 7;
   testarr2[2][1] = 8;
   testarr2[2][2] = 0;
   
   Board test2 = new Board(testarr2);
   
   LinkedList<Board> tempneighbors = new LinkedList<Board>();
   /*
   for(Board n : test.neighbors())
   {
   tempneighbors.add(n);
   System.out.println(tempneighbors.pop());
   }
   
   System.out.println(test2.isGoal());
   
   
   
   //System.out.println("equals: " + test.equals(test2));
   /*System.out.println("hamming: " + test.hamming());
   System.out.println("manhattan: " + test.manhattan());
   System.out.println("goal: " + test.isGoal());
   System.out.println("tiwn: " + test.twin());
   
   }*/
  public static void main(String[] args) {
    
    // create initial board from file
    //In in = new In(args[0]);
    int n = StdIn.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
      blocks[i][j] = StdIn.readInt();
    Board initial = new Board(blocks);
    
    System.out.println(initial.toString());
  }
}