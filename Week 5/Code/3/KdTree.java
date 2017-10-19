import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class KdTree {
  
  private Node root;
  
  // size()
  private int countsize;
  
  //debugging
  private int count = 0;
  
  //instance variables used in inorder
  private LinkedList<Node> inorderpoints;
  
  //instance variables used in nearest neighbor
  private Node champion,highver, highhor, prevver, prevhor;
  private double distance;
  private boolean verpass, horpass;
  
  public KdTree()                               // construct an empty set of points 
  {
    root = null;
    countsize = 0;
  }
  public boolean isEmpty()                      // is the set empty? 
  {
    return root == null;
  }
  public int size()                         // number of points in the set 
  {
    /*if(root!= null)
      return rank(root);
    else
      return 0;*/
    return countsize;
  }
  public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
  {
    if(!contains(p))
      countsize++;
    put(p);
  }
  public boolean contains(Point2D p)            // does the set contain point p? 
  {
    Node contain = new Node(p);
    return search(contain);
  }
  public void draw()                         // draw all points to standard draw 
  {
    inorderpoints = new LinkedList<Node>();
    inorder(root);
    
    for(Node p : inorderpoints)
    {
      if(p.height%2 == 0)//horizontal
      {
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        double stop = 1;
        double start = 0;
        if(p.parent!=null)
        {
          if(p.point.x() < p.parent.point.x())
            stop = p.parent.point.x();
          else
            start = p.parent.point.x();
        }
        for(double i = start; i<stop; i+=0.001)//draw line
        {
          StdDraw.point((double)(i),p.point.y());
        }
      }
      if(p.height%2 == 1)//vertical
      {
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.RED);
        double stop = 1;
        double start = 0;
        if(p.parent!=null)
        {
          if(p.point.y() < p.parent.point.y())
            stop = p.parent.point.y();
          else
            start = p.parent.point.y();
        }
        for(double i = start; i<stop; i+=0.001)//draw line
        {
          StdDraw.point(p.point.x(),(double)(i));
        }
      }
      
      StdDraw.setPenRadius(0.02);
      StdDraw.setPenColor(StdDraw.BLACK);
      p.point.draw();
    }
  }
  public Iterable<Point2D> range(RectHV rect)      // all points that are inside the rectangle (or on the boundary) 
  {
    LinkedList<Point2D> inrange = new LinkedList<Point2D>();
    Node scan = root;
    RectHV rectangle = rect;
    rangesearch(inrange,rectangle,scan);
    return inrange;
  }
  public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
  {
    champion = null;
    highver = null;
    highhor = null;
    prevver = null;
    prevhor = null;
    verpass = false;
    horpass = false;
    distance = -1;
    
    nearsearch(p,root);
    return champion.point;
  }
  //--------------SUPPORTING FUNCTIONS-----------------
  
  private class Node implements Comparable<Node>
  {
    public Node left;
    public Node right;
    public Node parent;
    public int height;
    public Point2D point;
    
    private Node(Point2D p)
    {
      point = p;
    }
    
    private Node (Point2D p, Node parent, int height)
    {
      this(p);
      this.parent = parent;
      this.height = height;
    }
    
    public int compareTo(Node that)
    {
      if(this.height%2 == 1)
      {
        if(point.x() < that.point.x())
          return -1;
        else if(point.x() == that.point.x())
          return 0;
        else
          return 1;
      }
      else // height %2 == 0;
      {
        if(point.y() < that.point.y())
          return -1;
        else if (point.y() == that.point.y())
          return 0; 
        else
          return 1;
      }
    }
    public String toString()
    {
      String a;
      a = ("point: " + point);
      a = a + ("  parent: " + parent);
      a = a + ("  height: " + height);
      return a;
    }
  }
  
  private void put(Point2D val)// insert new values here
  {
    Node temp = new Node(val);
    root = put(root,null,temp);
  }

  private Node put(Node tree, Node parent, Node val)
  {
    Node temp;
    if(tree == null && parent == null)
    {
      temp = new Node(val.point,parent,1);
      //temp.rank = 1;
      return temp;
    }
    if(tree == null)
    {
      temp = new Node(val.point, parent, parent.height+1);
      //temp.rank = 1;
      return temp;
    }
    int comp = -1*tree.compareTo(val);
    if(comp<0) //val is less than the current tree value
      tree.left = put(tree.left, tree, val);
    else if(comp>=0)
      tree.right = put(tree.right, tree, val);
    return tree;
  }
  /*
  private int rank(Node ref)
  {
    if(ref != null)
      return 1 + rank(ref.left) + rank(ref.right);
    else
      return 0;
  }*/
  
  private boolean search(Node searchval)
  {
    Node x = root;
    while(x != null)
    {
      if(x.point.equals(searchval.point))
        return true;
      int comp = x.compareTo(searchval);
      if (comp > 0)
        x = x.left;
      else// (comp < 0)
        x = x.right;
    }
    return false;
  }
  
  private void inorder(Node x)
  {
    if(x == null)
      return;
    inorder(x.left);
    inorderpoints.add(x);
    inorder(x.right);
  }

  private boolean inter (Node tree, RectHV rect) //if the rectangle intersects the paritioning line
  {
    if(tree.height%2==0)// horizontal
    {
      RectHV partition = new RectHV(0,tree.point.y(),1,tree.point.y());
      count++;
      return partition.intersects(rect);
    }
    else
    {
      RectHV partition = new RectHV(tree.point.x(),0,tree.point.x(),1);
      return partition.intersects(rect);
    }
  }
  
  private void rangesearch (LinkedList<Point2D> list, RectHV range, Node p)
  {
    if(p == null)
      return;
    if(range.contains(p.point))
      list.add(p.point);
    if(inter(p,range)) // if the partitioning line intersects the range
    {//search both sides of the tree
      rangesearch(list,range,p.left);
      rangesearch(list,range,p.right);
    }
    else // if the partitioning line does not intersect the range
    {
      if(p.height%2==1 && p.point.x() < range.xmin()) // vertical and left of range
      {
        rangesearch(list,range,p.left);
        rangesearch(list,range,p.right);
      }
      else if(p.height%2==1 && p.point.x() > range.xmax()) // vertical and right of range
      {
        rangesearch(list,range,p.right);
        rangesearch(list,range,p.left);
      }
      else if(p.height%2==0  && p.point.y() < range.ymin()) // horizontal and below range
      {
        rangesearch(list,range,p.left);
        rangesearch(list,range,p.right);
      }
      else // horizontal and above range
      {
        rangesearch(list,range,p.right);
        rangesearch(list,range,p.left);
      }
    }
  }
  
  private void nearsearch(Point2D goal, Node search)
  {
    double dcurr;
    if(search!= null)
      dcurr = goal.distanceSquaredTo(search.point);
    else
      return;
    
    if(dcurr<distance || distance == -1)
    {
      champion = search;
      distance = dcurr;
    }
    if(search.height%2 == 1 && prevver != null) //vertical and not the first vertical node
    {
      if(goal.x() > search.point.x() && goal.x() > prevver.point.x()) // point is right of both lines
        highver = prevver;
      if(goal.x() < search.point.x() && goal.x() < prevver.point.x()) // point is left of both lines
        highver = prevver;
      
      prevver = search;
    }
    else if(search.height%2 == 0 && prevhor != null) // horizontal and not the first horizional node
    {
      if(goal.y() > search.point.y() && goal.y() > prevhor.point.y()) // point is above both lines
        highhor = prevhor;
      if(goal.y() < search.point.y() && goal.y() < prevhor.point.y()) // point is below both lines
        highhor = prevhor;
      prevhor = search;
    }
    if(search.height%2 == 1) // line is vertical
    {
      if(goal.x() < search.point.x()) // goal point is less than the search point ( left of the line)
      {
        nearsearch(goal,search.left); // search the left tree
        if(verpass)   // determine if the mehtod should continue searching
          return;
        if(search == highver)
        {
          verpass = true;
          return;
        }
        nearsearch(goal,search.right);
      }
      else //right or on the line
      {
        nearsearch(goal,search.right); // search the right tree
        if(verpass)
          return;
        if(search == highver)
        {
          verpass = true;
          return;
        }
        nearsearch(goal,search.left);
      }
    }
    else // line is horizontal
    {
      if(goal.y() < search.point.y()) // goal point is less than search point (below line)
      {
        nearsearch(goal,search.left);
        if(horpass)
          return;
        if(search == highhor)
        {
          horpass =  true;
          return;
        }
        nearsearch(goal,search.right);
      }
      else //above or on the line
      {
        nearsearch(goal,search.right);
        if(horpass)
          return;
        if(search == highhor)
        {
          horpass = true;
          return;
        }
        nearsearch(goal,search.left);
      }
    }
  }
  
  public static void main(String[] args) 
  {
    /*KdTree test = new KdTree();
    
    Point2D p1 = new Point2D(0.1,0.5);
    Point2D p2 = new Point2D(0.4,0.8);
    Point2D p3 = new Point2D(0.5,0.7);
    Point2D p4 = new Point2D(0.17,0.3);
    Point2D p5 = new Point2D(0.9,0.1);
    
    test.insert(p1);
    test.insert(p2);
    test.insert(p3);
    test.insert(p4);
    test.insert(p5);
    
    test.draw();
    
    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    
    Point2D query = new Point2D (0.2,0.25);
    query.draw();
    
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(StdDraw.YELLOW);
    test.nearest(query).draw();
    
    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    RectHV rect = new RectHV (0.08,0.1,0.4,0.9);
    rect.draw();
    
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(StdDraw.YELLOW);
    for(Point2D n: test.range(rect))
    {
      n.draw();
    }*/
    
    
    
    KdTree test = new KdTree();
    
    Point2D p1 = new Point2D(0.206107,0.095492);
    Point2D p2 = new Point2D(0.975528,0.654508);
    Point2D p3 = new Point2D(0.024472,0.345492);
    Point2D p4 = new Point2D(0.793893,0.095492);
    Point2D p5 = new Point2D(0.793893,0.904508);
    Point2D p6 = new Point2D(0.975528,0.345492);
    Point2D p7 = new Point2D(0.206107,0.904508);
    Point2D p8 = new Point2D(0.500000,0.000000);
    Point2D p9 = new Point2D(0.024472,0.654508);
    Point2D p10 = new Point2D(0.500000,1.000000);
    
    test.insert(p1);
    test.insert(p2);
    test.insert(p3);
    test.insert(p4);
    test.insert(p5);
    test.insert(p6);
    test.insert(p7);
    test.insert(p8);
    test.insert(p9);
    test.insert(p10);
    
    System.out.println(test.size());
    System.out.println(test.contains(new Point2D(0.8,0.345492)));
    
    test.draw();
    
    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    
    Point2D query = new Point2D (0.2,0.25);
    //query.draw();
    
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(StdDraw.YELLOW);
    //test.nearest(query).draw();
    
    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    RectHV rect = new RectHV (0.0,0.5,0.5,1);
    rect.draw();
    
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(StdDraw.YELLOW);
    for(Point2D n: test.range(rect))
    {
      n.draw();
    }
  }
}