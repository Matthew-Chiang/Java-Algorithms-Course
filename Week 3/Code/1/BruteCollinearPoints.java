import java.util.Arrays;

public class BruteCollinearPoints {
  private Point[] endpts;
  
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {//a line segment is defined as the begining and end points
     for(int i = 0; i<points.length; i++)
     {
     if(points[i] == null)
         throw new java.lang.IllegalArgumentException();
     }
     Arrays.sort(points);
     Point holder = points[0];
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
     int count = 0;
       
     for(int i = 0; i<points.length; i++)
     {
       for(int j = 1+i; j<points.length; j++)
       {
         for (int k = 1+j ; k<points.length; k++)
         {
           for (int m = 1+k; m<points.length; m++)
           {
             double slp12 = points[i].slopeTo(points[j]);
             double slp23 = points[j].slopeTo(points[k]);
             double slp34 = points[k].slopeTo(points[m]);
             
             if(slp12==slp23 && slp23==slp34)
             {
               Point[] temp = new Point[4];
               temp[0] = points[i];
               temp[1] = points[j];
               temp[2] = points[k];
               temp[3] = points[m];
               
               Arrays.sort(temp);
               for(int p = 0; p< temp.length ; p+=3) // placing the temp points into the endpoints array
               {
                 endpts[count++] = temp [p];
               }
             }                     
           }
         }
       }
     }
     int value = 0;
      for (int p = 0; p<endpts.length; p++)
      {
        if(endpts[p]!= null)
        {
          value++;
        }
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
   
   public static void main(String[] args) 
   {
     Point test1 = new Point(1,2);
     Point test2 = new Point(5,2);
     Point test3 = new Point(18,2);
     Point test4 = new Point(67,2);
     Point test5 = new Point(4,5);
     
     Point[] testarr = new Point[5];
     
     testarr[0] = test1;
     testarr[1] = test2;
     testarr[2] = test3;
     testarr[3] = test4;
     testarr[4] = test5;
     
     BruteCollinearPoints test = new BruteCollinearPoints(testarr);
     System.out.println(test.numberOfSegments());
     
     LineSegment[] templine;
     templine = test.segments();
     
     for(int i = 0; i<templine.length; i++)
     {
       System.out.println(templine[i].toString());
     }
   }
}