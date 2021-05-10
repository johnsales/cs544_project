package cs544.team7.project.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Person {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fname;
	private String lname;
	private String email;
	private String username;
	private String password;
	@ManyToMany
	@JoinTable(name = "Person_Role", 
			   joinColumns = @JoinColumn(name = "person_id"), 
			   inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles = new ArrayList<>();
	@OneToMany(mappedBy = "client")
	private Collection<Appointment> appointments = new ArrayList<>();
	@OneToMany(mappedBy = "provider")
	private Collection<Session> sessions = new ArrayList<>();
	


	public Person(String fname, String lname, String email, String username, String password, Collection<Role> roles) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", username="
				+ username + ", password=" + password + ", roles=" + roles + ", appointments=" + appointments
				+ ", sessions=" + sessions + "]";
	}
	
}
