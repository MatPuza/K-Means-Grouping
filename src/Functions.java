/***
 * @author Mateusz Puza S23121 
 * NAI project 5
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

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
      ArrayList<String> columnName = new ArrayList<>();                   //If needed can be shared as global variable
      
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
               columnName.add(s.replaceAll("\"", ""));
            }
            
            isInitialLine = false;
         }
         else
         {
            ArrayList<Double> dataForObject = new ArrayList<>();
            
            for(int i = 1 ; i <= columnName.size() - 2 ; i++)
            {
               double tempDouble = Double.parseDouble(splitted[i]);
               
               dataForObject.add(tempDouble);
            }
            
            String tempID = splitted[0].replaceAll("\"", "");
            String tempName = splitted[columnName.size() - 1].replaceAll("\"", "");
            
            resultArray.add(new ObjectData(Integer.parseInt(tempID), dataForObject, tempName));
         }
      }
      
      return resultArray;
   }
   
   public static void assignToRandomGroup(ArrayList<ObjectData> objectsArray, int k)
   {
      Random random = new Random();
      
      boolean noEmptyGroups = true;
      
      while(noEmptyGroups)
      {
         int[] repeats = new int[k];
         
         for(int i = 0 ; i < k ; i++)
         {
            repeats[i] = 0;
         }
         
         for(ObjectData object : objectsArray)
         {
            object.setGroupID(random.ints(1, k + 1).findFirst().getAsInt());
            
            repeats[object.getGroupID() - 1]++;
         }
         
         noEmptyGroups = IntStream.of(repeats).anyMatch(n -> n == 0);
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
         
         ObjectData newObj = new ObjectData(i, coordinates, tempName);
         newObj.setGroupID(i);
         
         resultCentroids.add(newObj);
      }
      
      return resultCentroids;
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
         
         double currentVal = sumOfDistances.get(currentCentroid.getGroupID() - 1);
         sumOfDistances.set((currentCentroid.getGroupID() - 1), currentVal + shortestDistance);
         
         if(object.getGroupID() != currentCentroid.getGroupID())
         {
            object.setGroupID(currentCentroid.getGroupID());
            
            didClassificationChange = true;
         }
      }
      
      int counter = 1;
      double totalSum = 0;
      
      for(Double dist : sumOfDistances)
      {
         System.out.println("Centroid " + counter + " = " + dist);
         totalSum += dist;
         counter++;
      }
      
      System.out.println("Total sum = " + totalSum);
      System.out.println();
      
      return didClassificationChange;
   }
   
   public static void giveResults(ArrayList<ObjectData> objectsArray, int k)
   {
      for(int i = 1 ; i <= k ; i++)
      {
         HashMap<String, Integer> repeats = new HashMap<>();
         int counter = 0;
         
         for(ObjectData object : objectsArray)
         {
            if(object.getGroupID() == i)
            {
               counter++;
               
               if(repeats.containsKey(object.getObjectName()))
               {
                  int prevVal = repeats.get(object.getObjectName());
                  repeats.replace(object.getObjectName(), prevVal, ++prevVal);
               }
               else
               {
                  repeats.put(object.getObjectName(), 1);
               }
            }
         }
         
         String res;
         int amount;
         double perc;
         
         if(repeats.isEmpty())
         {
            res = "Brak grupy";
            amount = 0;
            perc = 0;
         }
         else
         {
            res = repeats
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey();
            
            amount = repeats.get(res);
            perc = (double) amount / counter * 100;
         }
         
         
         System.out.println(res + " has " + amount + " correct guesses - " + perc + "%");
      }
   }
   
}



















