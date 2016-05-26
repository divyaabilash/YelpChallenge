package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InsertingReviews {
	String line = null;
	ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	JSONObject obj;
	static Connection con = null;
	
	public static void main(String[] args) throws SQLException, IOException, ParseException, java.text.ParseException {
		DBConnection dbcontection = new DBConnection();
		con = dbcontection.openConnection();
		InsertingReviews reviews = new InsertingReviews();
		reviews.insertingReview();
	}
	
	public void insertingReview() throws SQLException, IOException, ParseException, java.text.ParseException{
		FileReader file = new FileReader("C:/Users/Divya/Downloads/YelpDataset/YelpDataset-CptS451/yelp_review.json");
		BufferedReader bufferedReader = new BufferedReader(file);
		con.setAutoCommit(true);
		PreparedStatement reviewSQL = null;
		while ((line = bufferedReader.readLine()) != null) {
			obj = (JSONObject) new JSONParser().parse(line);
			json.add(obj);
			String user_id = (String) obj.get("user_id");
			String review_id =(String)obj.get("review_id");
			long stars = (long) obj.get("stars");
			String date = (String) obj.get("date");
			String text = (String) obj.get("text");
			String type = (String) obj.get("type");
			String business_id = (String) obj.get("business_id");
			System.out.println(user_id+" "+review_id+" "+stars+" "+
			text+" "+type+" "+date+" "+type+" "+business_id);
		if(reviewSQL == null){
			String sql="INSERT INTO REVIEWS VALUES(?,?,?,?,?,?,?)";
			reviewSQL = con.prepareStatement(sql);
		}
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		java.util.Date parsed = format.parse(date.toString());
		reviewSQL.setString(1,user_id);
		reviewSQL.setString(2,review_id);
		reviewSQL.setLong(3, stars);
		reviewSQL.setDate(4,new Date(parsed.getTime()));
		reviewSQL.setString(5,text);
		reviewSQL.setString(6,type);
		reviewSQL.setString(7,business_id);
		reviewSQL.executeUpdate();
		}
		bufferedReader.close();
		reviewSQL.close();
	}

}
