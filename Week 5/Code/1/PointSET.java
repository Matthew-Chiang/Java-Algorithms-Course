import java.util.*;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
  private TreeSet<Point2D> square;
  public PointSET()                               // construct an empty set of points 
  {
    square = new TreeSet<Point2D>();
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
    boolean in = square.add(p);
  }
  public boolean contains(Point2D p)            // does the set contain point p? 
  {
    return square.contains(p);
  }
  public void draw()                         // draw all points to standard draw 
  {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    for(Point2D p: square.descendingSet())
    {
      p.draw();
    }
  }
  public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
  {
    LinkedList<Point2D> points = new LinkedList<Point2D>();
    for(Point2D p : square.descendingSet())
    {
      if(rect.contains(p))
      {
        points.add(p);
      }
    }
    return points;
  }
  public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
  {
    double distance = -1;
    Point2D closest = null;
    for(Point2D scan : square.descendingSet())
    {
      double temp = scan.distanceTo(p);
      if(temp>distance || distance == -1)
      {
        distance = temp;
        closest = scan;
      }
    }
    return closest;
  }
  public static void main(String[] args)                  // unit testing of the methods (optional) 
  {
    PointSET test = new PointSET();
    
    Point2D one = new Point2D(0.5,0.5);
    Point2D a = new Point2D(0.4,0.5);
    
    test.insert(one);
    test.insert(a);
    test.draw();
    System.out.println("hi");
  }
}