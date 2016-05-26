package assignment;
import java.util.Iterator;

import javax.swing.plaf.synth.SynthStyle;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class InsertingReviewVotes {


	 String line = null;
	 ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	 JSONObject obj;
	 static Connection con = null;

	 public static void main(String[] args) throws SQLException, ParseException, IOException, java.text.ParseException {
	  // TODO Auto-generated method stub
	  DBConnection dbcontection = new DBConnection();
	  con = dbcontection.openConnection();
	  InsertingReviewVotes reviewVotes = new InsertingReviewVotes();
	  reviewVotes.insertVotes();
	 }
	 public void insertVotes() throws ParseException, IOException, SQLException, java.text.ParseException {
		 FileReader file = new FileReader("C:/Users/Divya/Downloads/YelpDataset/YelpDataset-CptS451/yelp_review.json");
		 BufferedReader bufferedReader = new BufferedReader(file);
	  con.setAutoCommit(true);
	  PreparedStatement votesSQL = null;
	    long count =0;                      
	  while ((line = bufferedReader.readLine()) != null) {
	   obj = (JSONObject) new JSONParser().parse(line);
	   json.add(obj);
	   String user_id = (String) obj.get("user_id");
	   String votes = (String) obj.get("votes").toString();
	//   System.out.println(user_name+user_id+votes);
	   JSONObject votes_obj = (JSONObject) new JSONParser().parse(votes);
	//   System.out.println(votes_obj);
	   long cool =(long) votes_obj.get("cool");
	//System.out.println(cool);
	   long useful = (long) votes_obj.get("useful");
	   long funny = (long) votes_obj.get("funny");
	   String review_id = (String) obj.get("review_id");
	   String business_id = (String) obj.get("business_id");
	//   System.out.println("user_id "+user_id+" cool "+cool+" useful "+useful+" funny "+funny);
	 if(votesSQL == null ){
		 String query = "INSERT INTO VOTES_REVIEW VALUES(?,?,?,?,?,?)";
		 votesSQL = con.prepareStatement(query);
	 }
	 
	 votesSQL.setLong(1, funny);
	 votesSQL.setLong(2,useful);
	 votesSQL.setLong(3, cool);
	 votesSQL.setString(4, user_id);
	 votesSQL.setString(5,review_id);
	 votesSQL.setString(6, business_id);
	 
	 votesSQL.executeQuery();
	 count++;
	  }
	  bufferedReader.close();
	  System.out.println("count:" + count);
	 }
	}
