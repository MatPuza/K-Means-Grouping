/***
 * @author Mateusz Puza S23121 
 * NAI project 5
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NAI_5
{
   private static final String trainSet = "iris_all.csv";
   private static final String testSet = "dane_testowe.csv";
   
   private static int k;
   
   private static ArrayList<ObjectData> allObjects = new ArrayList<>();
   
   public static void main(String[] args) throws FileNotFoundException
   {
      System.out.println("How many groups you want to have:");
      
      k = Functions.getUserInput();
      
      allObjects = Functions.getObjectsFromFile(trainSet);
      
      
   }
   
}














