import java.util.Arrays;

public class FastCollinearPoints {
  
  private Point[] endpts;
  
  public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
  {
    Arrays.sort(points);
    Point holder = points[0];
    int endptscounter = 0;
    
    for(int i = 0; i<points.length; i++)
    {
      if(points[i] == null)
        throw new java.lang.IllegalArgumentException();
      if(holder.compareTo(points[i])==0 && i != 0)
      {
        throw new java.lang.IllegalArgumentException();
      }
      holder = points[i];
    }
    
    endpts = new Point[points.length*2];
    for(int i = 0; i<points.length-1; i++) // removed -1 to compare every point with every point - true n^2
    {
      int count = 0;
      IndexedSlopes[] tempslp = new IndexedSlopes[points.length];
      for(int j = i+1; j<points.length;j++) // collecting slopes
      //for(int j = 0; j<points.length; j++)
      {
        double temp = points[i].slopeTo(points[j]);// scanning through the points 
        IndexedSlopes tempobj = new IndexedSlopes(j,temp);
        tempslp[count++] = tempobj;
      }
      
      int value = 0;
      for (int r = 0; r<tempslp.length; r++) // removing null values
      {
        if(tempslp[r]!= null)
          value++;
      }
      IndexedSlopes[] temptempslp = new IndexedSlopes[value]; // removing null values
      for (int p = 0; p<temptempslp.length; p++)
      {
        temptempslp[p] = tempslp[p];
      }
      tempslp = new IndexedSlopes[temptempslp.length];
      for(int p = 0; p < tempslp.length; p++)
      {
        tempslp[p] = temptempslp[p];
      }
      Arrays.sort(tempslp);     // sort the array by slope
      
      int repeat = 0;
      double pointer = tempslp[0].slope;
      int tempcounter = 0;
      for(int k = 1; k < tempslp.length; k++)
      {
        if(tempslp[k].slope == pointer)
        {
          if(repeat == 0)
            repeat = 2;
          else
            repeat++;
        }
        else if(repeat > 2) // if there are 3 or more instnaces of the same slope
        {// will store repeat+1 points  all the j values of the slopes and i
          // to not repeat a segment with 5 poitns with a line with 4 points - only if i is the least value
          int countvar = 0;
          double sameslp = tempslp[k-1].slope;
          Point[] temparray = new Point[repeat+1];
          for(int q = 0; q<tempslp.length ; q++) // store all values that have the same slope
          {
            if(tempslp[q].slope == sameslp)
              temparray[countvar++] = points[tempslp[q].index];     // tempslp, tempslp-1, tempslp -2 (0,1,2)                   
          }// points index is null
          temparray[repeat] = points[i];
          Arrays.sort(temparray);
          // if i is the smallest value
          if(temparray[0].compareTo(points[i])==0)
          {
            endpts[endptscounter++] = temparray[0]; // insert first and last points into the endpoints array
            endpts[endptscounter++] = temparray[temparray.length-1];
          }
          
          pointer = tempslp[k].slope; // reset pointers
          repeat = 0;
        }
        else // not enough poitns in one line segment
        {
          pointer = tempslp[k].slope; // reset pointers
          repeat = 0;
        }
      }
    }
    
    int value = 0;
    for (int p = 0; p<endpts.length; p++)
    {
      if(endpts[p]!= null)
        value++;
    }
    Point[] temppts = new Point[value];
    for (int p = 0; p<temppts.length; p++)
    {
      temppts[p] = endpts[p];
    }
    endpts = new Point[temppts.length];
    for(int p = 0; p < endpts.length; p++)
    {
      endpts[p] = temppts[p];
    }
  }
  
  private class IndexedSlopes implements Comparable<IndexedSlopes>
  {
    public int index;
    public double slope;
    public IndexedSlopes(int ind, double slp)
    {
      index = ind;
      slope = slp;
    }
    public int compareTo(IndexedSlopes that)
    {
      int compslp = Double.compare(slope,that.slope);
      if(compslp == 0)
        return index-that.index;
      else
        return compslp;
    }
    public String toString()
    {
      return "index: " + index + " slope: " +slope;
    }
  }
  public int numberOfSegments()        // the number of line segments
  {
    return endpts.length/2;
  }
  public LineSegment[] segments()                // the line segments
  {
    int pointer = 0;
    LineSegment[] seg = new LineSegment [numberOfSegments()];
    for(int i = 0; i< seg.length; i++)
    {
      LineSegment temp = new LineSegment(endpts[pointer],endpts[pointer+1]);
      seg[i] = temp;
      pointer+=2;
    }
    return seg;
  }
  public static void main(String args[])
  {
    Point test1 = new Point(10000,0);
    Point test2 = new Point(0,10000);
    Point test3 = new Point(3000,7000);
    Point test4 = new Point(7000,3000);
    Point test5 = new Point(20000,21000);
    Point test6 = new Point(3000,4000);
    Point test7 = new Point(14000,15000);
    Point test8 = new Point(6000,7000);
    
    Point[] testarr = new Point[8];
    
    testarr[0] = test1;
    testarr[1] = test2;
    testarr[2] = test3;
    testarr[3] = test4;
    testarr[4] = test5;
    testarr[5] = test6;
    testarr[6] = test7;
    testarr[7] = test8;
    
    FastCollinearPoints  test = new FastCollinearPoints(testarr);
    
    System.out.println(test.numberOfSegments() + "num");
    
    LineSegment[] templine;
    templine = test.segments();
    
    for(int i = 0; i<templine.length; i++)
    {
      System.out.println(templine[i].toString());
    }
    
  }
}