package cs544.team7.project.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Session {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate date;
	private LocalTime startTime;
	private int duration;
	private String location;
	@ManyToOne
	@JoinColumn(name = "person_id")
	@JsonIgnore
	private Person provider;
	@OneToMany(mappedBy = "session")
	@JsonIgnore
	private Collection<Appointment> appointments;
	
	public Session(LocalDate date, LocalTime startTime, int duration, String location, Person provider) {
		this.date = date;
		this.startTime = startTime;
		this.duration = duration;
		this.location = location;
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", date=" + date + ", startTime=" + startTime + ", duration=" + duration
				+ ", location=" + location + ", provider=" + provider + ", appointments=" + appointments + "]";
	}
	
}
