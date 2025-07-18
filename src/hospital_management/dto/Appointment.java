package hospital_management.dto;

import java.security.Timestamp;

public class Appointment {
	private int id;
	private int doctorId;
	private Doctor doctor;
	private Patient patient;
	private String status;
	private Timestamp appointmentTime;
	
	public Appointment() {}
	
	public int getId() { return id; }
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDoctorId() { return doctorId; }
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	
	public Doctor getDoctor() { return doctor; }
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public Patient getPatient() { return patient; }
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public String getStatus() { return status; }
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Timestamp getAppointmentTime() { return appointmentTime; }
	public void setAppointmentTime(Timestamp appoimtmentTime) {
		this.appointmentTime = appoimtmentTime;
	}
}
