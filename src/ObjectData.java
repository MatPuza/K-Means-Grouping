/***
 * @author Mateusz Puza S23121 
 * NAI project 5
 */

import java.util.ArrayList;

public class ObjectData
{
   private int ID;
   private ArrayList<Double> data;
   private String objectName;
   private int groupID;
   
   public ObjectData(int ID, ArrayList<Double> list, String objectName)
   {
      this.ID = ID;
      this.data = list;
      this.objectName = objectName;
   }
   
   public int getGroupID()
   {
      return groupID;
   }
   
   public ArrayList<Double> getData()
   {
      return data;
   }
   
   public String getObjectName()
   {
      return objectName;
   }
   
}
