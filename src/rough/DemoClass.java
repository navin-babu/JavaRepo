package rough;
import java.sql.*;

class DemoClass {

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demoDB","root", "root");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			String query = "insert into student(name, mark) values(?,?)";
//			PreparedStatement psmt = conn.prepareStatement(query);
//			psmt.setString(1, "Zoro");
//			psmt.setFloat(2, 70); 	
//			System.out.println("No. of rows updated: "+psmt.executeUpdate());
			PreparedStatement psmt = conn.prepareStatement("select * from student");
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("name")+" -> "+ rs.getInt(3));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
