package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InsertingCategory {
	String line = null;
	ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	JSONObject obj;
	
	static Connection con = null;
	public void populateBAC()throws ParseException, IOException, SQLException, java.text.ParseException{
		DBConnection dbconnection = new DBConnection();
		con = dbconnection.openConnection();
		String[] CatList = {"Active Life\n" ,"Arts & Entertainment\n", "Automotive", "Car Rental", "Cafes", "Transportation",
		                                "Beauty & Spas", "Convenience Stores", "Dentists", "Doctors", "Drugstores", "Department Stores", "Education", 
		                                "Event Planning & Services", "Flowers & Gifts", "Food", "Health & Medical", "Home Services", "Home & Garden", 
		                                "Hospitals", "Hotels & Travel", "Hardware Stores", "Grocery", "Medical Centers", "Nurseries & Gardening", "Shopping",
		                                "Nightlife", "Medical Centers", "Nurseries & Gardening", "Nurseries & Gardening", "Nightlife", "Restaurants"};
		 
		                            BufferedReader br = new BufferedReader(new FileReader("C:/Users/Ketha/Desktop/YelpDataset-CptS451/yelp_business.json"));
		                            String line = br.readLine();
		                            
		                            int ibus = 0;
		                            int ibuscat = 0;
		                            int ibusAttr = 0;
		                            int iNeighbour = 0;
		                            int iOphrs = 0;
		                           
		                    
		                      
		                            PreparedStatement psBusnCat = con.prepareStatement("INSERT INTO BUSINESSCATEGORY (BID, BUSNCATGNAME, BUSNSUBCAT) VALUES (?,?,?)");
		                            PreparedStatement psBusAttr = con.prepareStatement("INSERT INTO BUSNATTR (BID, ATTRJSON) VALUES (?, ?)");
		                           
		                            while (line != null) {
		                                //JSONObject obj = (JSONObject) JSONParser.parse(line);
		                                 obj = (JSONObject) new JSONParser().parse(line);
		                                String sBusnID = (String)obj.get("business_id");
		                          
		                                JSONArray alCategories = (JSONArray)obj.get("categories");
		                         
		                                JSONObject sAttributes = (JSONObject)obj.get("attributes");
		                               
		                                
		                              
		                                
		                              
		                                
		                                if(sAttributes !=null){
		                                    psBusAttr.setString(1, sBusnID);
		                                    psBusAttr.setString(2, sAttributes.toJSONString());
		                                    psBusAttr.addBatch();
		                                    ibusAttr++;
		                                    if(ibusAttr>500){
		                                       
		                                        ibus = 0;
		                                        psBusAttr.executeBatch();
		                                        ibusAttr = 0;
		                                    }
		                                }
		                               
		                                
		                                if(alCategories!=null){
		                                    ArrayList<String> sBusCat = new ArrayList();
		                                    ArrayList<String> sBusSubCat = new ArrayList();
		                                    for(int i = 0; i< alCategories.size(); i++){
		                                        String str = (String)alCategories.get(i);
		                                        boolean isCat = false;
		                                        for(int j = 0; j< CatList.length; j++){
		                                            if(str.equals(CatList[j])){
		                                                isCat = true;
		                                                break;
		                                            }
		                                        }
		                                        if(isCat)
		                                            sBusCat.add(str);
		                                        else
		                                            sBusSubCat.add(str);
		                                    }
		                                    for(String sCat : sBusCat){
		                                        for(String sSubCat : sBusSubCat){
		                                            psBusnCat.setString(1, sBusnID);
		                                            psBusnCat.setString(2, sCat);
		                                            psBusnCat.setString(3, sSubCat);
		                                            psBusnCat.addBatch();
		                                            ibuscat++;
		                                            if(ibuscat>500){
		                                               
		                                                ibus = 0;
		                                                psBusnCat.executeBatch();
		                                                ibuscat = 0;
		                                            }
		                                        }
		                                    }
		                                }
		                              
		                              
		                                psBusAttr.executeBatch();
		                               
		                                psBusnCat.executeBatch();
		                                
		                                line = br.readLine();
		                            }
		                           
		                        }
}
