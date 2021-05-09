package cs544.team7.project.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
	@OneToMany(mappedBy = "client", orphanRemoval = true)
	private Collection<Appointment> appointments = new ArrayList<>();
	@OneToMany(mappedBy = "provider", orphanRemoval = true)
	private Collection<Session> sessions = new ArrayList<>();
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", username="
				+ username + ", password=" + password + ", roles=" + roles + ", appointments=" + appointments
				+ ", sessions=" + sessions + "]";
	}

	public Person(String fname, String lname, String email, String username, String password, Collection<Role> roles) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	// Convenience methods

	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
	}
	public void removeAppointment(Appointment appointment) {
		if(appointment.getClient() == this)
			appointments.remove(appointment);
		else
			throw new IllegalArgumentException("This appointment belongs to other user");
	}
	private void setAppointments(Collection<Appointment> a) {
		appointments = a;
	}
	public Collection<Appointment> getAppointments() {
		return Collections.unmodifiableCollection(appointments);
	}

	public void addSession(Session session) {
		sessions.add(session);
	}
	public void removeSession(Session session) {
		if(session.getProvider() == this)
			sessions.remove(session);
		else
			throw new IllegalArgumentException("This appointment belongs to other user");
	}
	private void setSessions(Collection<Session> s) {
		sessions = s;
	}
	public Collection<Session> getSessions() {
		return Collections.unmodifiableCollection(sessions);
	}

	public void addRole(Role role) {
		if(!roles.contains(role)) roles.add(role);
	}
	public void removeRole(Role role) {
		if(roles.contains(role)) roles.remove(role);
	}
	public Collection<Role> getRoles() {
		return Collections.unmodifiableCollection(roles);
	}
}
