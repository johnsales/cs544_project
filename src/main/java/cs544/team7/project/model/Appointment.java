package cs544.team7.project.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime requestTime = LocalDateTime.now();
	private AppointmentStatus status = AppointmentStatus.PENDING;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "person_id")
	private Person client;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "session_id")
	private Session session;
	
	public Appointment(Person client, Session session) {
		this.client = client;
		this.session = session;
		client.addAppointment(this);
		session.addAppointment(this);
	}
}
