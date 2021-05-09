package cs544.team7.project.service;

import cs544.team7.project.model.*;
import cs544.team7.project.repository.AppointmentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cs544.team7.project.model.AppointmentStatus.*;
import static cs544.team7.project.model.RoleType.CLIENT;

@Service
@Log4j2
public class AppointmentService implements IAppointmentService {
    private AppointmentRepository  appointmentRepository;
    @Autowired
    private IEmailService emailService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              IEmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.emailService = emailService;
    }

    @Override
    public Appointment makeReservation(Person p, Session s) throws IllegalAccessException {
        if(p == null || s == null) throw new IllegalArgumentException("Argument is null");
        boolean sessionApproved =  s.getAppointments().stream().filter(
                appointment -> appointment.getStatus() == APPROVED
        ).count() >= 1L;
        boolean isClient =  p.getRoles()
                             .stream()
                             .filter(role -> role.getType() == CLIENT)
                             .count() >= 1L;
        if(sessionApproved) throw new IllegalStateException("Sesssion has already been assigned!");
        if(LocalDate.now().isAfter(s.getDate()) ||
                (LocalDate.now().isEqual(s.getDate()) && LocalTime.now().isAfter(s.getStartTime())))
            throw new IllegalStateException("Session has been finished!");
        if(!isClient) throw new IllegalAccessException("Person is not client");
        emailService.sendMessage(p, "You have successfully created Appointment!");
        Appointment appointment = new Appointment(p, s);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Override
    public boolean cancelAppointment(Appointment a) {
        if(a == null) throw new IllegalArgumentException("Argument is null");
        Person p = a.getClient();
        Session s = a.getSession();
        if(LocalDate.now().isAfter(s.getDate()) ||
                (LocalDate.now().isEqual(s.getDate()) && LocalTime.now().isAfter(s.getStartTime())))
            throw new IllegalStateException("Session has been finished!");
        switch (a.getStatus()) {
            case PENDING:   a.setStatus(CANCELED);
                            appointmentRepository.save(a);
                            break;
            case CANCELED: return true;
            default:  a.setStatus(CANCELED);
                      updateAppointment(a);
                      getAllPendingAppointmentsForSession(s).stream()
                              .findFirst().ifPresent(a2 -> approveAppointment(a2));
                break;
        }
        emailService.sendMessage(p, "Your Appointment has been canceled!");
        return true;
    }

    @Override
    public List<Appointment> getAllPendingAppointmentsForSession(Session s) {
        return s.getAppointments().stream().filter(
                appointment -> appointment.getStatus() == PENDING
        ).sorted(
                Comparator.comparing(app -> app.getRequestTime())
        ).collect(Collectors.toList());
    }

    @Override
    public boolean approveAppointment(Appointment a) {
        if(a == null) throw new IllegalArgumentException("Argument is null");
        a.setStatus(APPROVED);
        updateAppointment(a);
        emailService.sendMessage(a.getClient(),
                "Your appointment has been approved! Session details: " + a.getSession().toString());
        return true;
    }

    @Override
    public void deleteAppointment(Appointment a) {
        appointmentRepository.delete(a);
        log.info("An appointment has been deleted!");
    }

    @Override
    public void updateAppointment(Appointment a) {
        appointmentRepository.save(a);
        log.info("An appointment has been updated!");
    }
}
