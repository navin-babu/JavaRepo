package rough;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class DemoClass2 {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/demoDB";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			
//			String sql = "insert into Teachers(name, age) values(?,?)";
//			PreparedStatement psmt = con.prepareStatement(sql);
//			psmt.setString(1, "Nami");
//			psmt.setInt(2, 20);
//			psmt.executeUpdate();
//			
//			psmt.setString(1, "Robin");
//			psmt.setInt(2, 29);
//			psmt.executeUpdate();
			
			String sql = "select * from Teachers";
			PreparedStatement psmt = con.prepareStatement(sql);
			ResultSet res = psmt.executeQuery();
			while(res.next()) {
				System.out.println(res.getString(2)+" -- "+res.getInt(3));
			}
			
			
			con.close();
			psmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
