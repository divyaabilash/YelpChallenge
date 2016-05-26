package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class InsertMainCategory {
//	MAIN_CATEGORY
	String line = null;
	ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	PreparedStatement CategorySQL = null;
	JSONObject obj;
	static Connection con = null;
	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		DBConnection dbcontection = new DBConnection();
		con = dbcontection.openConnection();
		InsertMainCategory cat = new InsertMainCategory();
		cat.insertMainCategory();
		
	}
	private void insertMainCategory() throws SQLException, IOException {
		// TODO Auto-generated method stub
		FileReader file = new FileReader("C:/Users/Divya/Desktop/maincategory.txt");
		BufferedReader bufferedReader = new BufferedReader(file);
		con.setAutoCommit(true);
		PreparedStatement CategorySQL = null;
		while ((line = bufferedReader.readLine()) != null) {
//		System.out.println(line.trim());
			String category= line.trim();
		if (CategorySQL == null) {
			String sql = "INSERT INTO MAIN_CATEGORY VALUES(?)";
			CategorySQL = con.prepareStatement(sql);
		}
		CategorySQL.setString(1,category);
		System.out.println(category);
		CategorySQL.executeQuery();
		}
		}
	}


