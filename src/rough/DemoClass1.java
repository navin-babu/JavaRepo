package rough;

import java.sql.Connection;
import java.sql.DriverManager;

class DemoClass1 {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/demoDB";
		String user = "root";
		String password = "root";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver	 loaded...");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found!");
		}
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
			System.out.println("Connection Established Successfully...");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
