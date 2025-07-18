package hospital_management.main;

import java.util.Scanner;

import hospital_management.dao.AdminDAO;
import hospital_management.dao.AppointmentDAO;
import hospital_management.dao.DoctorDAO;
import hospital_management.dao.PatientDAO;
import hospital_management.dto.Admin;
import hospital_management.dto.Appointment;
import hospital_management.dto.Doctor;
import hospital_management.dto.Patient;

public class HospitalApp {
	static {
		System.out.println("---HOSPITAL MANAGEMENT SYSTEM---");
	}

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int op;
		do {
			System.out.println("\n--MAIN MENU--\n1. LOG IN");
			System.out.println("2. PATIENT REGISTER");
			System.out.println("3. EXIT");
			System.out.print("Enter Choice: ");
			op = sc.nextInt();
			switch (op) {
			case 1:
				login();
				break;
			case 2:
				registerPatient();
				break;
			case 3:
				System.out.println("Exiting...");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid Input!");
				break;
			}

		} while (op != 3);
	}

	public static void login() {
		System.out.println("\n--LOG IN MENU--");
		System.out.print("1. Doctor Login\n2. Patient Login\n3. Admin Login\n4. Main Menu\nEnter Choice: ");
		int ch = sc.nextInt();
		switch (ch) {
		case 1:
			drLogin();
			break;
		case 2:
			patientLogin();
			break;
		case 3:
			adminLogin();
			break;
		case 4:
			break;
		default:
			System.out.println("Invalid Input!");
			break;
		}
	}

	public static void drLogin() {
		Doctor loggedInDoctor = null;
		System.out.println("\n--DOCTOR LOGIN--");
		System.out.print("Username: ");
		String drUser = sc.next();
		System.out.print("Password: ");
		String drPass = sc.next();

		DoctorDAO newLoginDoa = new DoctorDAO();
		loggedInDoctor = newLoginDoa.validateLogin(drUser, drPass);

		if (loggedInDoctor != null) {
			System.out.println("\n✅ Log in Successful...");
			drMenu(loggedInDoctor);
		} else {
			System.out.println("\n❌ Login Failed...");
			login();
		}
	}

	public static void drMenu(Doctor loggedInDoctor) {
		System.out.println("\n--DOCTOR DASHBOARD--");
		System.out.println("1. View Pending Appointments");
		System.out.println("2. Update Appointment Status");
		System.out.println("3. Log out");
		System.out.print("Enter Choice: ");
		int drCh = sc.nextInt();

		switch (drCh) {
		case 1:
			displayPendingAppointments(loggedInDoctor);
			break;
		case 2:
			displayAppointments(loggedInDoctor);
			break;
		case 3:
			System.out.println("Logging out...");
			login();
			break;

		default:
			System.out.println("Invalid Input!");
			break;
		}
	}

	public static void displayAppointments(Doctor loggedInDoctor) {
		AppointmentDAO.displayPendingAppointments(loggedInDoctor);
		System.out.println("1. ACCEPT APPOINTMENT");
		System.out.println("2. REJECT APPOINTMENT");
		System.out.print("Enter choice: ");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.println("\n--ACCEPT APPOINTMENT--");
			System.out.print("Enter Patient ID: ");
			int patientIdAccept = sc.nextInt();
			int rowsUpdatedAccept = AppointmentDAO.acceptAppointment(patientIdAccept, loggedInDoctor);
			if (rowsUpdatedAccept > 0) {
				System.out.println("\n✅ Appointment Status Updated...");
			} else {
				System.out.println("❌ Couldn't Update Status...");
			}
			break;
			
		case 2:
			System.out.println("\n--REJECT APPOINTMENT--");
			System.out.print("Enter Patient ID: ");
			int patientIdReject = sc.nextInt();
			int rowsUpdatedReject = AppointmentDAO.rejectAppointment(patientIdReject, loggedInDoctor);
			if (rowsUpdatedReject > 0) {
				System.out.println("\n✅ Appointment Status Updated...");
			} else {
				System.out.println("❌ Couldn't Update Status...");
			}
			break;

		default:
			break;
		}
	}

	public static void displayPendingAppointments(Doctor loggedInDoctor) {
		AppointmentDAO.displayPendingAppointments(loggedInDoctor);
		drMenu(loggedInDoctor);
	}

	public static void patientLogin() {
		Patient loggedinPatient = null;
		System.out.println("\n--PATIENT LOGIN--");
		System.out.print("Username: ");
		String ptUser = sc.next();
		System.out.print("Password: ");
		String ptPass = sc.next();

		PatientDAO patientDao = new PatientDAO();
		loggedinPatient = patientDao.getPatientDetails(ptUser, ptPass);

		if (loggedinPatient != null) {
			System.out.println("\n✅ Login Successful...");
			patientMenu(loggedinPatient);
		} else {
			System.out.println("\n❌ Login Failed...");
			login();
		}
	}

	public static void patientMenu(Patient patientloggedIn) {
		System.out.println("\n--PATIENT DASHBOARD--");
		System.out.println("1. View Doctors Available");
		System.out.println("2. Book Appointment");
		System.out.println("3. Appointment Status");
		System.out.println("4. Logout");
		System.out.print("Enter Choice: ");
		int ptCh = sc.nextInt();

		switch (ptCh) {
		case 1:
			viewDoctors(patientloggedIn);
			break;
		case 2:
			bookAppointments(patientloggedIn);
			break;
		case 3:
			appointmentStatus(patientloggedIn);
			break;
		case 4:
			System.out.println("Logging out...");
			login();
			break;

		default:
			System.out.println("Invalid Input!");
			break;
		}
	}

	public static void viewDoctors(Patient loggedInPatient) {
		System.out.println("\n--DOCTORS--");
		DoctorDAO.displayDoctors();
		patientMenu(loggedInPatient);
	}

	public static void bookAppointments(Patient loggedInPatientDetails) {
		int rowsUpdated = 0;
		System.out.println("\n--BOOK APPOINTMENT--");
		DoctorDAO.displayDoctors();
		System.out.print("\nEnter Doctor ID to book an appointment: ");
		int appointmentDrId = sc.nextInt();

		Appointment newAppointment = new Appointment();
		newAppointment.setDoctorId(appointmentDrId);
		newAppointment.setPatient(loggedInPatientDetails);

		AppointmentDAO newBooking = new AppointmentDAO();
		rowsUpdated = newBooking.bookPatientAppointment(newAppointment);

		if (rowsUpdated > 0) {
			System.out.println("\n✅ Appointment Added...");
			patientMenu(loggedInPatientDetails);
		} else {
			System.out.println("\n❌ Booking Failed...");
			patientMenu(loggedInPatientDetails);
		}
	}

	public static void appointmentStatus(Patient loggedInPatient) {
		AppointmentDAO status = new AppointmentDAO();
		if (loggedInPatient != null) {
			System.out.println("\n--APPOINTMENT STATUS--");
			status.showAppointmentStatus(loggedInPatient);
			patientMenu(loggedInPatient);
		} else {
			System.out.println("\n❌ No Data Available...");
			patientMenu(loggedInPatient);
		}

	}

	public static void adminLogin() {
		System.out.println("\n--ADMIN LOGIN--");
		System.out.print("Username: ");
		String adUser = sc.next();
		System.out.print("Password: ");
		String adPass = sc.next();

		Admin admin = new Admin(adUser, adPass);
		AdminDAO adminDoa = new AdminDAO();
		boolean isLogin = adminDoa.validateUser(admin);
		if (isLogin) {
			System.out.println("\n✅ Log in Successful...");
			adminMenu();
		} else {
			System.out.println("\n❌ Incorrect Username or Password...");
		}
	}

	public static void adminMenu() {

		System.out.println("\n--ADMIN DASHBOARD--");
		System.out.println("1. Add Doctors");
		System.out.println("2. Remove Doctors");
		System.out.println("3. View Doctors");
		System.out.println("4. Log out");
		System.out.print("Enter Choice: ");
		int adCh = sc.nextInt();

		switch (adCh) {
		case 1:
			addDoctor();

			break;
		case 2:
			removeDoctor();
			break;
		case 3:
			viewDoctorsbyAdmin();
			break;
		case 4:
			System.out.println("Logging out...");
			login();
			break;

		default:
			System.out.println("Invalid Input!");
			break;
		}
	}

	public static void addDoctor() {
		int rowCount = 0;
		sc.nextLine();
		System.out.println("\n--ADD DOCTOR--");
		System.out.print("Enter Doctor Name: ");
		String newDrName = sc.nextLine();
		System.out.print("Enter Specialization: ");
		String newDrSpec = sc.nextLine();
		System.out.print("Enter Age: ");
		int newDrAge = sc.nextInt();
		System.out.print("Set username: ");
		String newDrUsername = sc.next();
		System.out.print("Set Password: ");
		String newDrPass = sc.next();

		Doctor newDoctor = new Doctor(newDrName, newDrSpec, newDrAge, newDrUsername, newDrPass, true);
		DoctorDAO doctorDao = new DoctorDAO();
		rowCount = doctorDao.insertDoctor(newDoctor);

		if (rowCount > 0) {
			System.out.println("\n✅ Doctor Added Successfully...");
			adminMenu();
		} else {
			System.out.println("\n❌ Couldn't Add Doctor, Try again");
			adminMenu();
		}
	}

	public static void removeDoctor() {
		System.out.println("--REMOVE DOCTOR--");
		System.out.print("Enter Username: ");
		String delUser = sc.next();
//		Doctor deleteDoctor = new Doctor(delUser);
		DoctorDAO deleteDoa = new DoctorDAO();
		int rowsUpdated = deleteDoa.deleteDoctorByUsername(delUser);

		if (rowsUpdated > 0) {
			System.out.println("\n✅ Doctor Deleted Successfully...");
			adminMenu();
		} else {
			System.out.println("\n❌ Couldn't Delete Doctor, Try Again");
			adminMenu();
		}
	}

	public static void viewDoctorsbyAdmin() {
		System.out.println("\n--DOCTOR DETAILS--");
		DoctorDAO displayDocAdmin = new DoctorDAO();
		displayDocAdmin.displayDoctorsbyAdmin();
		adminMenu();
	}

	public static void registerPatient() {
		int rowsUpdated = 0;
		sc.nextLine();
		System.out.println("\n--NEW REGISTRATION--");
		System.out.print("Name: ");
		String ptName = sc.nextLine();
		System.out.print("Age: ");
		int ptAge = sc.nextInt();
		System.out.print("Phone: ");
		long ptPhone = sc.nextLong();
		System.out.print("Username: ");
		String ptUsername = sc.next();
		System.out.print("Password: ");
		String ptPass = sc.next();

		Patient patient = new Patient(ptName, ptAge, ptPhone, ptUsername, ptPass);
		PatientDAO patientDoa = new PatientDAO();
		rowsUpdated = patientDoa.registerPatient(patient);

		if (rowsUpdated > 0) {
			System.out.println("\n✅ Registration Successful...");
			System.out.print("Do you want to login (Y/N): ");
			String is_login = sc.next();
			if (is_login.equalsIgnoreCase("y")) {
				patientMenu(patient);
			}
		} else {
			System.out.println("❌ Registration Failed...");
		}

	}
}
