/***
 * @author Mateusz Puza S23121 
 * NAI project 5
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Functions
{
   public static int getUserInput()
   {
      Scanner scanner = new Scanner(System.in);
      
      return scanner.nextInt();
   }
   
   public static ArrayList<ObjectData> getObjectsFromFile(String fileName) throws FileNotFoundException
   {
      ArrayList<ObjectData> resultArray = new ArrayList<>();
      ArrayList<String> collumnName = new ArrayList<>();                   //If needed can be shared as global variable
      
      File file = new File(fileName);
      Scanner scanner = new Scanner(file);
      
      boolean isInitialLine = true;
      
      while(scanner.hasNextLine())
      {
         String newLine = scanner.nextLine();
         
         String[] splitted = newLine.split(",");
         
         if(isInitialLine)
         {
            for(String s : splitted)
            {
               collumnName.add(s.replaceAll("\"", ""));
            }
            
            isInitialLine = false;
         }
         else
         {
            ArrayList<Double> dataForObject = new ArrayList<>();
            
            for(int i = 1 ; i <= collumnName.size() - 2 ; i++)
            {
               double tempDouble = Double.parseDouble(splitted[i]);
               
               dataForObject.add(tempDouble);
            }
            
            String tempID = splitted[0].replaceAll("\"", "");
            String tempName = splitted[collumnName.size() - 1].replaceAll("\"", "");
            
            resultArray.add(new ObjectData(Integer.parseInt(tempID), dataForObject, tempName));
         }
      }
      
      return resultArray;
   }
   
   public static void assignToRandomGroup(ArrayList<ObjectData> objectsArray, int k)
   {
      Random random = new Random();
      
      for(ObjectData object : objectsArray)
      {
         object.setGroupID(random.ints(1, k + 1).findFirst().getAsInt());
      }
   }
   
   
   
}



















