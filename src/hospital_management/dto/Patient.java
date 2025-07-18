package hospital_management.dto;

public class Patient {
	private String name;
	private int age;
	private long phone;
	private String password;
	private String username;
	public Patient(String name, int age, long phone, String username,String password) {
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.password = password;
		this.username = username;
	}
	
	public Patient(String username, String password) {
		this.password = password;
		this.username = username;
	}
	
	public Patient() {}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	public long getPhone() {
		return phone;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
