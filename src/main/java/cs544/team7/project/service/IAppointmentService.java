package cs544.team7.project.service;

import java.util.List;

import cs544.team7.project.model.Appointment;
import cs544.team7.project.model.Person;
import cs544.team7.project.model.Session;

public interface IAppointmentService {
	public Appointment makeReservation(Person p, Session s) throws IllegalAccessException;
	public boolean cancelAppointment(Person p, Appointment a);
	public List<Appointment> getAllPendingAppointmentsForSession(Person p, Session s);
	public boolean approveAppointment(Person p, Appointment a);
	public void deleteAppointment(Person p, Appointment a);
	public void updateAppointment(Person p, Appointment a);
}
