package cs544.team7.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cs544.team7.project.model.Appointment;
import cs544.team7.project.model.Person;
import cs544.team7.project.model.Session;
import cs544.team7.project.repository.AppointmentRepository;

public class AppointmentService implements IAppointmentService{

	
	
	public Appointment makeReservation(Person p, Session s) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean cancelAppointment(Appointment a) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Appointment> getAllPendingAppointmentsForSession(Session s) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean approveAppointment(Appointment a) {
		// TODO Auto-generated method stub
		return false;
	}

	public void deleteAppointment(Appointment a) {
		// TODO Auto-generated method stub
		
	}

	public void updateAppointment(Appointment a) {
		// TODO Auto-generated method stub
		
	}

}
