import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.*;

public class KdTree {
  
  private Node root;
  
  //instance variables used in nearest neighbor
  private Node Champion,highver, highhor, prevver, prevhor;
  private double distance = 0;
  private boolean verpass, horpass;
  
  public KdTree()                               // construct an empty set of points 
  {
    root = null;
  }
  public boolean isEmpty()                      // is the set empty? 
  {
    return root == null;
  }
  public int size()                         // number of points in the set 
  {
    return root.rank;
  }
  public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
  {
    Node temp = new Node(p);
    put(temp);
  }
  public boolean contains(Point2D p)            // does the set contain point p? 
  {
    Node contains = new Node(p);
    return search(root,contains)!=null;
  }
  public void draw()                         // draw all points to standard draw 
  {
    LinkedList<Node> points = new LinkedList<Node>();
    inorder(root,points);
    
    for(Node p : points)
    {
      if(p.height%2 == 0)//horizontal
      {
        StdDraw.setPenColor(StdDraw.BLUE);
        for(int i = 0; i<1000; i++)//draw line
        {
          StdDraw.point((double)(i/1000),p.point.x());
        }
      }
      if(p.height%2 == 0)//horizontal
      {
        
        StdDraw.setPenColor(StdDraw.RED);
        for(int i = 0; i<100; i++)//draw line
        {
          StdDraw.point(p.point.y(),(double)(i/100));
        }
      }
      StdDraw.setPenColor(StdDraw.BLACK);
      p.point.draw();
    }
  }
  public Iterable<Point2D> range(RectHV rect)      // all points that are inside the rectangle (or on the boundary) 
  {
    LinkedList<Point2D> inrange = new LinkedList<Point2D>();
    Node scan = root;
    rangesearch(inrange,rect,scan);
    return inrange;
  }
  public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
  {
    nearsearch(p,root);
    return Champion.point;
  }
  //--------------SUPPORTING FUNCTIONS-----------------
  
  private class Node implements Comparable<Node>
  {
    public Node left;
    public Node right;
    public Node parent;
    public int height;
    public int rank;
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
  }
  
  private void put(Node val)// insert new values here
  {
    root = put(root,null,val);
  }

  private Node put(Node tree, Node parent, Node val)
  {
    if(tree == null)
      return new Node(val.point, parent, parent.height);
    int comp = val.compareTo(tree);
    if(comp<0) //val is less than the current tree value
      put(tree.left, tree, val);
    else if(comp>0)
      put(tree.right, tree, val);
    else // equal to values (comp = 0)
      return val;
    tree.rank = 1 + tree.left.rank + tree.right.rank;
    return tree;
  }
  
  private Node search(Node tree, Node searchval)
  {
    if(tree == null)
      return null;
    int comp = tree.compareTo(searchval);
    if(comp<0) // searchval is less than the node in the tree
      search(tree.left,searchval);
    else if(comp>0) // search val is greater than the node in the tree
      search(tree.right,searchval);
    else // comp == 0 - search hit
      return tree;
    return null;
  }
  
  private void inorder(Node x, LinkedList<Node> points)
  {
    if(x == null)
      return;
    inorder(x.left,points);
    boolean added = points.add(x);
    inorder(x.right,points);
  }

  private boolean inter (Node tree, RectHV rect) //if the rectangle intersects the paritioning line
  {
    if(tree.height%2==0)// horizontal
    {
      RectHV partition = new RectHV(0,tree.point.y(),1,tree.point.y());
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
        rangesearch(list,range,p.left);
      else if(p.height%2==1 && p.point.x() > range.xmax()) // vertical and right of range
        rangesearch(list,range,p.right);
      else if(p.height%2==0  && p.point.y() < range.ymin()) // horizontal and below range
        rangesearch(list,range,p.left);
      else // horizontal and above range
        rangesearch(list,range,p.right);
    }
  }
  
  private void nearsearch(Point2D goal, Node search)
  {
    double dcurr = goal.distanceSquaredTo(search.point);
    
    if(dcurr<distance)
    {
      Champion = search;
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
  
  public static void main(String[] args)                  // unit testing of the methods (optional) 
  {
  }
}