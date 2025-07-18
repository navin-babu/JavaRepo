package hospital_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import hospital_management.dto.Patient;

public class PatientDAO {
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
	
	public Patient getPatientDetails(String username, String password) {
		Patient patient = null;
		try (Connection con = getConnection()) {
			String getUserSql = "select * from Patients where username = ? and pass = ?";
			PreparedStatement psmt = con.prepareStatement(getUserSql);
			psmt.setString(1, username);
			psmt.setString(2, password);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				patient = new Patient();
				patient.setName(rs.getString("name"));
				patient.setAge(rs.getInt("age"));
				patient.setPhone(rs.getLong("phone"));
				patient.setUsername(rs.getString("username"));
				patient.setPassword(rs.getString("pass"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patient;
	}
	
	public int registerPatient(Patient patient) {
		int rowsUpdated = 0;
		
		try (Connection con = getConnection()) {
			String insertSql = "insert into Patients(name, age, phone, username, pass) values(?, ?, ?, ?, ?)";
			PreparedStatement psmt = con.prepareStatement(insertSql);
			psmt.setString(1, patient.getName());
			psmt.setInt(2, patient.getAge());
			psmt.setLong(3, patient.getPhone());
			psmt.setString(4, patient.getUsername());
			psmt.setString(5, patient.getPassword());
			
			rowsUpdated = psmt.executeUpdate();
			psmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsUpdated;
	}
}
	
	