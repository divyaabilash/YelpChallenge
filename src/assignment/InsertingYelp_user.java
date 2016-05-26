package assignment;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InsertingYelp_user  {
	String line = null;
	ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	PreparedStatement businessSQL = null;
	JSONObject obj;
	static Connection con = null;

	public static void main(String[] args) throws SQLException, ParseException, IOException, java.text.ParseException {
		// TODO Auto-generated method stub
		DBConnection dbcontection = new DBConnection();
		con = dbcontection.openConnection();
		InsertingYelp_user user = new InsertingYelp_user();
		user.insertUser();
	}
	public void insertUser() throws ParseException, IOException, SQLException, java.text.ParseException {
		FileReader file = new FileReader("C:/Users/Divya/Downloads/YelpDataset/YelpDataset-CptS451/yelp_user.json");
		BufferedReader bufferedReader = new BufferedReader(file);
		con.setAutoCommit(true);
		PreparedStatement userSQL = null;

		while ((line = bufferedReader.readLine()) != null) {
			obj = (JSONObject) new JSONParser().parse(line);
			json.add(obj);
			String date = (String) obj.get("yelping_since");
			long review_count = (long)obj.get("review_count");
			String user_name = (String) obj.get("name");
			String user_id = (String) obj.get("user_id");
			long fans = (long) obj.get("fans");
			double average_stars = (double) obj.get("average_stars");
			String user_type = (String) obj.get("type");
			System.out.println(date + " "+review_count+" "+user_name+" "+
					user_id+" "+fans+" "+average_stars+" "+user_type);
			if (userSQL == null) {
				String sql = "INSERT INTO yelp_user VALUES(?,?,?,?,?,?,?)";
				userSQL = con.prepareStatement(sql);
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			java.util.Date parsed = format.parse(date.toString());
			userSQL.setDate(1,new Date(parsed.getTime()));
			userSQL.setLong(2,review_count);
			userSQL.setString(3, user_name);
			userSQL.setString(4,user_id);
			userSQL.setLong(5, fans);
			userSQL.setDouble(6, average_stars);
			userSQL.setString(7,user_type);
			userSQL.executeUpdate();

		}
		userSQL.close();
		bufferedReader.close();
	}
}
