package cs544.team7.project.service;

import java.util.Collection;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import cs544.team7.project.model.Appointment;
import cs544.team7.project.model.AppointmentStatus;
import cs544.team7.project.model.Person;
import cs544.team7.project.model.Role;
import cs544.team7.project.model.RoleType;
import cs544.team7.project.model.Session;
import cs544.team7.project.repository.AppointmentRepository;

public class AppointementService implements IAppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public Appointment makeReservation(Person p, Session s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cancelAppointment(Appointment a) {
		if(a == null || a.getClient() == null || a.getSession()==null) 
			return false;
		
		// Checking if session start time is not in the past
		if(LocalDate.now().isAfter(a.getSession().getDate()) ||
                (LocalDate.now().isEqual(a.getSession().getDate()) && 
                		LocalTime.now().isAfter(a.getSession().getStartTime()))) {
			return false;
		}

		Collection<Role> roles = a.getClient().getRoles();
		
		if(roles.contains(RoleType.ADMIN)) {
			//RoleType is ADMIN & session start time is not past
			a.setStatus(AppointmentStatus.CANCELED);
			updateAppointment(a);
		}
		else if(roles.contains(RoleType.CLIENT) || roles.contains(RoleType.PROVIDER)) {
			// RoleType is not ADMIN --> Checking if session start time is after more than 48 hrs 
			if(LocalTime.now().plusHours(48).isAfter(a.getSession().getStartTime())){
				return false;
			}
			a.setStatus(AppointmentStatus.CANCELED);
			updateAppointment(a);
		}
		else {
			return false;
		}
		
		return true;
	}

	@Override
	public List<Appointment> getAllPendingAppointmentsForSession(Session s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean approveAppointment(Appointment a) {
		if(a == null || a.getClient() == null || a.getSession()==null) 
			return false;
		
		Session s = a.getSession();
		Collection<Appointment> appointments = s.getAppointments();
		
		// Check session if it already has an APPROVED appointment
		for(Appointment apt : appointments) {
			if(apt.getStatus().equals(AppointmentStatus.APPROVED))
				return false;
		}
		
		Collection<Role> roles = a.getClient().getRoles();
		// Only PROVIDER can approve an appointment
		if(roles.contains(RoleType.PROVIDER)) {
			a.setStatus(AppointmentStatus.APPROVED);
			updateAppointment(a);
			return true;
		}
		
		return false;
	}

	@Override
	public void deleteAppointment(Appointment a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAppointment(Appointment a) {
		appointmentRepository.save(a);
			
	}

}
