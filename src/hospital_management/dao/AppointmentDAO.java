package hospital_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import hospital_management.dto.Appointment;
import hospital_management.dto.Doctor;
import hospital_management.dto.Patient;

public class AppointmentDAO {
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

	public int bookPatientAppointment(Appointment appointment) {
		int rowsUpdated = 0;
		try (Connection con = getConnection()) {

			String patientIdSql = "select id from Patients where username = ?";
			PreparedStatement getIdpsmt = con.prepareStatement(patientIdSql);
			getIdpsmt.setString(1, appointment.getPatient().getUsername());
			int patientId = 0;
			ResultSet rs1 = getIdpsmt.executeQuery();
			if (rs1.next()) {
				patientId = rs1.getInt(1);
			}

			String appointmentSql = "insert into Appointments (patient_id, doctor_id) values (?, ?);";
			PreparedStatement psmt = con.prepareStatement(appointmentSql);
			psmt.setInt(1, patientId);
			psmt.setInt(2, appointment.getDoctorId());
			rowsUpdated = psmt.executeUpdate();

			getIdpsmt.close();
			psmt.close();

		} catch (Exception e) {
			System.out.println("\n❌ An Error Occured, Check Doctor ID entered...");
		}
		return rowsUpdated;
	}

	public void showAppointmentStatus(Patient patient) {
		try (Connection con = getConnection()) {
			String patientIdSql = "select id from Patients where username = ?";
			PreparedStatement getIdpsmt = con.prepareStatement(patientIdSql);
			getIdpsmt.setString(1, patient.getUsername());
			int patientId = 0;
			ResultSet rs1 = getIdpsmt.executeQuery();
			if (rs1.next()) {
				patientId = rs1.getInt(1);
			}

			String statusSql = "select a.id, a.status, a.appointment_time, d.name as doctor_name from Appointments a join Doctors d ON a.doctor_id = d.id where a.patient_id = ?";

			PreparedStatement psmt = con.prepareStatement(statusSql);
			psmt.setInt(1, patientId);
			ResultSet rs = psmt.executeQuery();
			boolean hasAppointment = false;
			
			while (rs.next()) {
				hasAppointment = true;
				System.out.println("\nAppointment ID: " + rs.getInt(1));
				System.out.println("Date & Time: " + rs.getTimestamp(3));
				System.out.println("Doctor Name: " + rs.getString(4));
				System.out.println("Status: " + rs.getString(2));
			}
			
			if (!hasAppointment) {
				System.out.println("\n❌ No Appointments...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void displayPendingAppointments(Doctor loggedInDoctor) {
		boolean hasAppointment = false;
		System.out.println("\n--PENDING APPOINTMENTS--");
		try (Connection con = getConnection()) {
			PreparedStatement psmt1 = con.prepareStatement("select * from Doctors where username = ?");
			psmt1.setString(1, loggedInDoctor.getUsername());
			ResultSet rs1 = psmt1.executeQuery();
			int drId = 0;
			if (rs1.next()) {
				drId = rs1.getInt("id");
			} else {
				System.out.println("❌ Doctor not found!");
			}
			
			String displaySql = "select p.id, p.name, p.age from Appointments a join Patients p on p.id = a.patient_id where doctor_id = ? and status = 'pending'";
			PreparedStatement psmt = con.prepareStatement(displaySql);
			psmt.setInt(1, drId);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				hasAppointment = true;
				System.out.println("Patient ID: "+rs.getInt(1));
				System.out.println("Name: "+rs.getString(2));
				System.out.println("Age: "+rs.getInt(3));
				System.out.println("----------------------------");
			}
			
			if(!hasAppointment) {
				System.out.println("\n❌ No Appointments...");
			}
			
			rs.close();
			rs1.close();
			psmt.close();
			psmt1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int acceptAppointment(int patientId, Doctor loggedInDoctor) {
		int rowsUpdated = 0;
		try (Connection con = getConnection()){
			PreparedStatement psmt1 = con.prepareStatement("select * from Doctors where username = ?");
			psmt1.setString(1, loggedInDoctor.getUsername());
			ResultSet rs1 = psmt1.executeQuery();
			int doctorId = 0;
			if (rs1.next()) {
				doctorId = rs1.getInt("id");
			} else {
				System.out.println("❌ Invalid Data");
			}
			
			String updateSql = "update Appointments set status = 'approved' where patient_id = ? and doctor_id = ?";
			PreparedStatement psmt = con.prepareStatement(updateSql);
			psmt.setInt(1, patientId);
			psmt.setInt(2, doctorId);
			rowsUpdated = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsUpdated;
	}
	
	public static int rejectAppointment(int patientId, Doctor loggedInDoctor) {
		int rowsUpdated = 0;
		try (Connection con = getConnection()){
			PreparedStatement psmt1 = con.prepareStatement("select * from Doctors where username = ?");
			psmt1.setString(1, loggedInDoctor.getUsername());
			ResultSet rs1 = psmt1.executeQuery();
			int doctorId = 0;
			if (rs1.next()) {
				doctorId = rs1.getInt("id");
			} else {
				System.out.println("❌ Invalid Data");
			}
			
			String updateSql = "update Appointments set status = 'rejected' where patient_id = ? and doctor_id = ?";
			PreparedStatement psmt = con.prepareStatement(updateSql);
			psmt.setInt(1, patientId);
			psmt.setInt(2, doctorId);
			rowsUpdated = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsUpdated;
	}
}
