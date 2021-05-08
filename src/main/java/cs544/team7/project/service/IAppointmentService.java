package cs544.team7.project.service;

import java.util.List;

import cs544.team7.project.model.Appointment;
import cs544.team7.project.model.Person;
import cs544.team7.project.model.Session;

public interface IAppointmentService {
	public Appointment makeReservation(Person p, Session s);
	public boolean cancelAppointment(Appointment a);
	public List<Appointment> getAllPendingAppointmentsForSession(Session s);
	public boolean approveAppointment(Appointment a);
	public void deleteAppointment(Appointment a);
	public void updateAppointment(Appointment a);
}
