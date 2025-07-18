package hospital_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import hospital_management.dto.Doctor;

public class DoctorDAO {
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
	
	public Doctor validateLogin(String username, String password) {
		Doctor loginDr = null;
		try (Connection con = getConnection()){
			String selectSql = "select * from Doctors where username = ? and pass = ?";
			PreparedStatement psmt = con.prepareStatement(selectSql);
			psmt.setString(1, username);
			psmt.setString(2, password);
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				loginDr = new Doctor();
				loginDr.setName(rs.getString("name"));
				loginDr.setAge(rs.getInt("age"));
				loginDr.setSpecialization(rs.getString("specialization"));
				loginDr.setUsername(rs.getString("username"));
				loginDr.setPassword(rs.getString("pass"));
				loginDr.setStatus(rs.getBoolean("isAvailable"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginDr;
	}
	
	public int insertDoctor(Doctor doctor) {
		int rowsUpdated = 0;
		
		try (Connection con = getConnection()) {
			String insertSql = "insert into Doctors(name, age, specialization, username, pass) values(?,?,?,?,?)"; 
			PreparedStatement psmt = con.prepareStatement(insertSql);
			psmt.setString(1, doctor.getName());
			psmt.setInt(2, doctor.getAge());
			psmt.setString(3, doctor.getSpecialization());
			psmt.setString(4, doctor.getUsername());
			psmt.setString(5, doctor.getPassword());
			
			rowsUpdated = psmt.executeUpdate();
			psmt.close();
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		return rowsUpdated;
	}
	
	public int deleteDoctorByUsername(String username) {
		int rowsUpdated = 0;
	
		try (Connection con = getConnection()){
			String deleteSql = "delete from Doctors where username = ?";
			PreparedStatement psmt = con.prepareStatement(deleteSql);
			psmt.setString(1, username);
			
			rowsUpdated = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsUpdated;
	}
	
	public static void displayDoctors() {
		boolean isDocorAvailable = false;
		
		try (Connection con = getConnection()){
			String displaySql = "select * from Doctors";
			PreparedStatement psmt = con.prepareStatement(displaySql);
			ResultSet rs = psmt.executeQuery();
			System.out.println("ID🔹    DETAILS");
			while(rs.next()) {
				isDocorAvailable = true;
				System.out.println(rs.getInt(1)+" 🔹 "+rs.getString(2)+", "+rs.getString(4));
			}
			
			if (!isDocorAvailable) {
				System.out.println("❌ No Data Available...");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayDoctorsbyAdmin() {
		boolean isDocorAvailable = false;
		
		try (Connection con = getConnection()){
			String displaySql = "select * from Doctors";
			PreparedStatement psmt = con.prepareStatement(displaySql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				isDocorAvailable = true;
				System.out.println("\nID: "+rs.getInt(1));
				System.out.println("Name: "+rs.getString(2));
				System.out.println("Age: "+rs.getInt(3));
				System.out.println("Specialization: "+rs.getString(4));
				System.out.println("Username: "+rs.getString(5));
				System.out.println("----------------------------");
			}
			
			if (!isDocorAvailable) {
				System.out.println("❌ No Data Available...");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
