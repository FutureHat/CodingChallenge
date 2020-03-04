package application;

import java.sql.*;
import javax.swing.*;
public class databaseConnection {
	Connection connection = null;
	public  Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\jacin\\listofinterviews");
			return conn;
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
