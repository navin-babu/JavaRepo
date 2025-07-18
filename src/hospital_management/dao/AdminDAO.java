package hospital_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import hospital_management.dto.Admin;

public class AdminDAO {
	
	static String url = "jdbc:mysql://localhost:3306/hospitalDB";
	static String user = "root";
	static String password = "root";
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public boolean validateUser(Admin admin) {
		boolean isLogin = false;
		
		try (Connection con = getConnection()) {
			PreparedStatement psmt = con.prepareStatement("select * from Admin where username = ? and pass = ?");
			psmt.setString(1, admin.getUsername());
			psmt.setString(2, admin.getPassword());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				isLogin = true;

			}
			rs.close();
			psmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isLogin;
	}
}
