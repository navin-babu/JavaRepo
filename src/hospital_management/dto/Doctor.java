package hospital_management.dto;

public class Doctor {
	private String name;
	private String specialization;
	private int age;
	private String username;
	private String password;
	private boolean isAvailable;
	
	public Doctor(String name, String specialization, int age, String username, String password, boolean isAvailable) {
		this.name = name;
		this.specialization = specialization;
		this.age = age;
		this.username = username;
		this. password = password;
		this.isAvailable = isAvailable;
	}
	
	public Doctor(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Doctor(String username) {
		this.username = username;
	}
	
	public Doctor() {}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getStatus() {
		return isAvailable;
	}
	
	public void setStatus(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}

