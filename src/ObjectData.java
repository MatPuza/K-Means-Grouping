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
   
   public ObjectData() {}
   
   public ObjectData(int ID, ArrayList<Double> values, String objectName)
   {
      this.ID = ID;
      this.data = values;
      this.objectName = objectName;
   }
   
   public void setGroupID(int groupID)
   {
      this.groupID = groupID;
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
