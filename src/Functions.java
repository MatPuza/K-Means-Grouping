/***
 * @author Mateusz Puza S23121 
 * NAI project 5
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
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
   
   public static double measureDistance(ObjectData objectA, ObjectData objectB)
   {
      double distance = 0.0;
      
      ArrayList<Double> cordsA = objectA.getData();
      ArrayList<Double> cordsB = objectB.getData();
      
      for(int i = 0 ; i < cordsA.size() ; i++)
      {
         double xA = cordsA.get(i);
         double xB = cordsB.get(i);
         
         distance += Math.pow(xA - xB, 2);
      }
      
      distance = Math.sqrt(distance);
      
      return distance;
   }
   
   public static ArrayList<ObjectData> moveCentroids(ArrayList<ObjectData> objectsArray, int k)
   {
      ArrayList<ObjectData> resultCentroids = new ArrayList<>();
      
      //Create k centroids
      for(int i = 1 ; i <= k ; i++)
      {
         int counter = 0;
         
         for(ObjectData object : objectsArray)
         {
            if(object.getGroupID() == i) counter++;
         }
         
         ArrayList<Double> coordinates = new ArrayList<>();
         
         int amountOfCoordinates = objectsArray.get(0).getData().size();
         
         for(int j = 0 ; j < amountOfCoordinates ; j++)
         {
            double coord = 0;
   
            for(ObjectData object : objectsArray)
            {
               //If object belongs to K group
               if(object.getGroupID() == i)
               {
                  coord += object.getData().get(j);
               }
            }
            
            coord /= counter;
            
            coordinates.add(coord);
         }
         
         String tempName = "Centroid" + i;
         resultCentroids.add(new ObjectData(i, coordinates, tempName));
      }
      
      return resultCentroids;
   }
   
   //Returns map of list of distances for each object to the Kth centroid
   public static Map<Integer, ArrayList<Double>> getDistances(ArrayList<ObjectData> objectsArray, int k)
   {
//      Map<Integer, ArrayList<Double>> resultMap = new Map
      return null;
   }
   
   public static boolean changeObjectsClassification(ArrayList<ObjectData> centroids, ArrayList<ObjectData> objectsArray, int k)
   {
      boolean didClassificationChange = false;
      
      ArrayList<Double> sumOfDistances = new ArrayList<>();
      
      //Initialize distances with empty array
      for(int i = 0 ; i < k ; i++)
      {
         sumOfDistances.add(0.0);
      }
      
      for(ObjectData object : objectsArray)
      {
         boolean initialLoop = true;
         double shortestDistance = 0;
         ObjectData currentCentroid = new ObjectData();
         
         for(ObjectData centroid : centroids)
         {
            if(initialLoop)
            {
               shortestDistance = measureDistance(object, centroid);
               currentCentroid = centroid; 
               initialLoop = false;
            }
            else
            {
               double newDist = measureDistance(object, centroid);
               if(newDist < shortestDistance)
               {
                  shortestDistance = newDist;
                  currentCentroid = centroid;
               }
            }
         }
         
         double currentVal = sumOfDistances.get(currentCentroid.getGroupID());
         sumOfDistances.set(currentCentroid.getGroupID(), currentVal + shortestDistance);
         
         if(object.getGroupID() != currentCentroid.getGroupID())
         {
            object.setGroupID(currentCentroid.getGroupID());
            
            didClassificationChange = true;
         }
      }
      
      int counter = 1;
      
      for(Double dist : sumOfDistances)
      {
         System.out.println("Centroid " + counter + " = " + dist);
         counter++;
      }
      
      return didClassificationChange;
   }
   
}



















