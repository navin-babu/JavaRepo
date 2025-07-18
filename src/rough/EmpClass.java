package rough;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class EmpClass {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/emp_DB";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection(url, user, password);){
			
			// Insert
			String insert_sql = "insert into Employee (name, department, salary) values (?, ?, ?)";
			PreparedStatement psmt = con.prepareStatement(insert_sql);
			String[] names = {"Luffy","Zoro", "Sanji"};
			String[] dept = {"HR", "Sales", "Marketing"};
			Float[] salary = {5000f, 4000f, 3000f};
			
			for (int i=0;i<names.length;i++) {
				psmt.setString(1, names[i]);
				psmt.setString(2, dept[i]);
				psmt.setFloat(3, salary[i]);
				psmt.addBatch();;
			}
			
			psmt.executeBatch();
			
			// Update
			String select_sql = "update Employee set salary = salary+500 where name = ?";
			PreparedStatement psmt1 = con.prepareStatement(select_sql);
			String name = "Luffy";
			psmt1.setString(1, name);
			System.out.println("Rows Updated: "+psmt1.executeUpdate());
			
			// Delete 
			String delete_sql = "delete from Employee where id = ?";
			PreparedStatement psmt2 = con.prepareStatement(delete_sql);
			int id = 3;
			psmt2.setInt(1, id);
			psmt2.executeUpdate();
			
			// Print 
			String print_sql = "select * from Employee";
			PreparedStatement psmt3 = con.prepareStatement(print_sql);
			ResultSet rs = psmt3.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getFloat(4));
			}
			
			// Highest Paid Employee
			String pay_sql = "select * from Employee order by salary desc limit 1";
			PreparedStatement psmt4 = con.prepareStatement(pay_sql);
			ResultSet rs1 = psmt4.executeQuery();
			while (rs1.next()) {
				System.out.println(rs1.getInt(1)+" | "+rs1.getString(2)+" | "+rs1.getString(3)+" | "+rs1.getFloat(4));
			}
			
			con.close();
			rs1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
