/***
 * @author Mateusz Puza S23121 
 * NAI project 5
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NAI_5
{
   private static final String dataSet = "iris_all.csv";
   private static final String testSet = "dane_testowe.csv";
   
   private static int k;
   
   private static ArrayList<ObjectData> allObjects = new ArrayList<>();
   private static ArrayList<ObjectData> centroids = new ArrayList<>();
   
   public static void main(String[] args) throws FileNotFoundException
   {
      System.out.println("How many groups you want to have:");
      
      k = Functions.getUserInput();
      
      allObjects = Functions.getObjectsFromFile(dataSet);
      Functions.assignToRandomGroup(allObjects, k);
      
      centroids = Functions.moveCentroids(allObjects, k);
      
      for(ObjectData centroid : centroids)
      {
         System.out.print(centroid.getObjectName() + " ");
   
         for(Double data : centroid.getData())
         {
            System.out.print(data + ", ");
         }
   
         System.out.println();
      }
   }
   
}














