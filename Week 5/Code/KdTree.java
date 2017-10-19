import java.util.*;
import edu.princeton.cs.algs4.Point2D;

public class KdTree {
  
  private TreeSet<Division> square;
  
  public KdTree()                               // construct an empty set of points 
  {
    square = new TreeSet<Division>();
  }
  public boolean isEmpty()                      // is the set empty? 
  {
    return square.isEmpty();
  }
  public int size()                         // number of points in the set 
  {
    return square.size();
  }
  public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
  {
    Division temp = new Division(p);
    boolean in = square.add(p);
    //find heightt - update temp
  }
  public boolean contains(Point2D p)            // does the set contain point p? 
  {
    return square.contains(p);
  }
  public void draw()                         // draw all points to standard draw 
  {
    //StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    for(Point2D p: square.descendingSet())
    {
      if(p.height%2 == 0)//horizontal
      {
        
        StdDraw.setPenColor(StdDraw.BLUE);
        for(int i = 0; i<1000; i++)//draw line
        {
          StdDraw.point((double)(i/1000),p.current.x());
        }
      }
      StdDraw.setPenColor(StdDraw.BLACK);
      p.current.draw();
      if(p.height%2 == 0)//horizontal
      {
        
        StdDraw.setPenColor(StdDraw.RED);
        for(int i = 0; i<1000; i++)//draw line
        {
          StdDraw.point(p.current.y(),(double)(i/1000));
        }
      }
      StdDraw.setPenColor(StdDraw.BLACK);
      p.current.draw();
    }
  }
  public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
  {
    
  }
  public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
  {
  }
  
    //---------SUPPORTING FUNCTIONS------------------
  private class Division implements Comparable<Division>
  {
    public Point2D current;
    public int height;
    public Division(Point2D p)
    {
      current = p;
    }
    public Division(Point2D p, int h)
    {
      this(p);
      height = h;
    }
    public int compareTo(Division that)
    {
      if(this.height%2 == 1)
      {
        if(current.x() < that.current.x())
          return -1;
        else if(current.x() == that.current.x())
          return 0;
        else
          return 1;
      }
      else // height %2 == 0;
      {
        if(current.y() < that.current.y())
          return -1;
        else if (current.y() == that.current.y())
          return 0; 
        else
          return 1;
      }
    }
  }
  
  private int getHeight(Divison node)
  {
    Division climb = node;
    int height = 0;
    while(climb!= null)// will count one more
    {
      height++;
      climb = node.
    }
  }
  
  //------------TESTING---------------
  public static void main(String[] args)                  // unit testing of the methods (optional) 
  {
    KdTree test = new KdTree();
    
    Point2D one = new Point2D(0.5,0.5);
    Point2D a = new Point2D(0.4,0.5);
    
    test.insert(one);
    test.insert(a);
    test.draw();
    System.out.println("hi");
  }
}