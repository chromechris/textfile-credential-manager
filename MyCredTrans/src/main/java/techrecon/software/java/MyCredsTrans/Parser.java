package techrecon.software.java.MyCredsTrans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;

public class Parser {
	
	public static void main(String[] args) {
		String software;
		String username;
		String password;
		String email;
		String firstname;
		String lastname;
		String pin;
		int numOfCreds=0;
		
/////////////////////////////////////////////JDBC/////////////////////////////////////////////////////
Connection con = null;
try {
con =(Connection) DriverManager.getConnection("jdbc:mysql://localhost/credentials?user=chromechris&password=Ph0eni@#");

// Do something with the Connection

DatabaseMetaData md = (DatabaseMetaData) con.getMetaData();
ResultSet rs = md.getTables(null, null, "%", null);
while (rs.next()) {
	System.out.println(rs.getString(3));
}
} catch (SQLException ex) {
// handle any errors
System.out.println("SQLException: " + ex.getMessage());
System.out.println("SQLState: " + ex.getSQLState());
System.out.println("VendorError: " + ex.getErrorCode());
}
/////////////////////////////////////////////JDBC/////////////////////////////////////////////////////



//////////////////////////////////////////READ FILE//////////////////////////////////////////////////////////
		File file = new File("C:\\Users\\Chris\\Desktop\\eclipse\\MyCredTrans\\src\\main\\java\\techrecon\\software\\java\\sampledata.txt");
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		List<Data> arrayList = new ArrayList<Data>();
		
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			String line = br.readLine();
			Data data = new Data();
			int index = 0;
			
			while(line!=null) {
				if(line.trim().isEmpty()) {
					System.out.println("New Line Detected");
					arrayList.add(data);
					data = new Data();
					numOfCreds++;
				}
				
				if(index==0) {
					data.software = line;
					index++;
				} else if(index==1) {
					data.username = line;
					index++;
				} else if(index==2) {
					data.password = line;
					index++;
				} else if(index==3) {
					data.email = line;
					index++;
				} else if(index==4) {
					data.firstname = line;
					index++;
				} else if(index==5) {
					data.lastname = line;
					index++;
				} else if(index==6) {
					data.pin = line;
					index++;
				} else {
					index = 0;
				}
				
				System.out.println(line);
				line = br.readLine();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		numOfCreds--;
		System.out.println("Array Size: " + arrayList.size());
		System.out.println("Number of Credentials: " + numOfCreds);
		//System.out.println(arrayList.get(0).email + ":" + arrayList.get(1).email + ":" + arrayList.get(2).email);
//////////////////////////////////////////READ FILE//////////////////////////////////////////////////////////
		
/////////////////////////////////////////////JDBC CONTINUED///////////////////////////////////////////////////
		String sql = "INSERT INTO credentials (software, username, password, email, firstname, lastname, pin) values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement;
		try {
			statement = (PreparedStatement) con.prepareStatement(sql);
			while(numOfCreds!=0) {
				statement.setString(1, arrayList.get(numOfCreds).software);
				System.out.println(arrayList.get(numOfCreds).software);
				statement.setString(2, arrayList.get(numOfCreds).username);
				statement.setString(3, arrayList.get(numOfCreds).password);
				statement.setString(4, arrayList.get(numOfCreds).email);
				statement.setString(5, arrayList.get(numOfCreds).firstname);
				statement.setString(6, arrayList.get(numOfCreds).lastname);
				statement.setString(7, arrayList.get(numOfCreds).pin);
				System.out.println("Inserting Data Into MySQL Database, Index: " + numOfCreds);
				statement.executeUpdate();
				numOfCreds--;
			}
			
			if(numOfCreds==0) {
				statement.setString(1, arrayList.get(numOfCreds).software);
				System.out.println(arrayList.get(numOfCreds).software);
				statement.setString(2, arrayList.get(numOfCreds).username);
				statement.setString(3, arrayList.get(numOfCreds).password);
				statement.setString(4, arrayList.get(numOfCreds).email);
				statement.setString(5, arrayList.get(numOfCreds).firstname);
				statement.setString(6, arrayList.get(numOfCreds).lastname);
				statement.setString(7, arrayList.get(numOfCreds).pin);
				System.out.println("Inserting Data Into MySQL Database, Index: " + numOfCreds);
				statement.executeUpdate();
			}
			//statement.executeUpdate();
			System.out.println("Insertions Published to database");
			con.close();
			System.out.println("Database connection closed");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
/////////////////////////////////////////////JDBC CONTINUED///////////////////////////////////////////////////
	
	}
}


class Data {
	String software;
	String username;
	String password;
	String email;
	String firstname;
	String lastname;
	String pin;
}
