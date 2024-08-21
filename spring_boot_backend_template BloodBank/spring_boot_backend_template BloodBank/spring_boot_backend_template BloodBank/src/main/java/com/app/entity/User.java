package com.app.entity; 
 
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity 
public class User  
{ 
 //@Id 
 //@GeneratedValue(strategy = GenerationType.AUTO) 
 //private int id; 
	 @Id 
	 private String email; 
	 private String username; 
	 private String mobile; 
	 private String bloodgroup; 
	 private String gender; 
	 private int age; 
	 private String password;
	 
	 public User() {
		super();
	}


	public User(String email, String username, String mobile, String bloodgroup, String gender, int age,
			String password) {
		super();
		this.email = email;
		this.username = username;
		this.mobile = mobile;
		this.bloodgroup = bloodgroup;
		this.gender = gender;
		this.age = age;
		this.password = password;
	}
	
	
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
	 
	 
 
 
}