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
      
      boolean isChangingValues = true;
      
      while(isChangingValues)
      {
         isChangingValues = Functions.changeObjectsClassification(centroids, allObjects, k);
         centroids = Functions.moveCentroids(allObjects, k);
      }
      
      Functions.giveResults(allObjects, k);
   }
   
}














