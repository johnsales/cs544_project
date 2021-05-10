package cs544.team7.project.service;

import cs544.team7.project.model.*;
import cs544.team7.project.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static cs544.team7.project.model.AppointmentStatus.*;
import static cs544.team7.project.model.RoleType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repo;
    @Mock
    private IEmailService emailService;
    private AppointmentService underTest = null;
    private Person person = null;
    private Session session = null;
    private Appointment appointment = null;
    private Role clientRole = new Role(CLIENT);
    private Role providerRole = new Role(PROVIDER);


    @BeforeEach
    void setUp() {
        underTest = new AppointmentService(repo, emailService);
        person = new Person("John", "Smith", "jsmith@gmail.com", "jsmith", "1234", new LinkedList<>(Arrays.asList(clientRole, providerRole)));
        session = new Session(LocalDate.now().plusDays(4), LocalTime.now(), 120, "Darby Hall", person);
        appointment = new Appointment(person, session);
    }

    @Test
    void canMakeReservationTest() throws IllegalAccessException {
        // when
        Appointment a = underTest.makeReservation(person, session);

        // then
        verify(repo).save(a);
        verify(emailService).sendMessage(any(Person.class), anyString());
        assertThat(a.getClient()).isEqualTo(person);
        assertThat(a.getSession()).isEqualTo(session);
    }

    @Test
    void NotClientMakeReservationTest() {
        // Given
        person.removeRole(clientRole);
        // then
        assertThatThrownBy(() ->underTest.makeReservation(person, session));
    }

    @Test
    void NullSessionMakeReservationTest() {
        // then
        assertThatThrownBy(() ->underTest.makeReservation(person, null));
    }

    @Test
    void SessionAlreadyAssignedMakeReservationTest() {
        appointment.setStatus(APPROVED);
        // then
        assertThatThrownBy(() ->underTest.makeReservation(person, session));
    }

    @Test
    void SessionExpiredMakeReservationTest() {
        session.setDate(LocalDate.now().minusDays(1));
        // then
        assertThatThrownBy(() ->underTest.makeReservation(person, session));
    }

    @Test
    void canCancelAppointmentTest() {
        // when
        underTest.cancelAppointment(appointment);

        // then
        assertThat(appointment.getStatus()).isEqualTo(CANCELED);
        assertFalse(session.getAppointments().contains(appointment));
    }

    @Test
    void canCancelAppointmentThatWasApprovedTest() {
        // given
        appointment.setStatus(APPROVED);

        // when
        underTest.cancelAppointment(appointment);

        // then
        assertThat(appointment.getStatus()).isEqualTo(CANCELED);
        assertFalse(session.getAppointments().contains(appointment));
        assertTrue(session.getAppointments().isEmpty() ||
                session.getAppointments().stream()
                       .filter(a -> a.getStatus() == APPROVED)
                       .count() == 1L
                );
    }

    @Test
    void tryCancelAppointmentWhenItAlreadyCanceledTest() {
        // given
        appointment.setStatus(CANCELED);

        // when
        underTest.cancelAppointment(appointment);

        // then
        verify(repo, never()).save(appointment);
        verify(repo, never()).saveAndFlush(appointment);
        assertFalse(session.getAppointments().contains(appointment));
    }

    @Test
    void SessionExpiredCancelAppointmentTest() {
        session.setDate(LocalDate.now().minusDays(1));
        // then
        assertThatThrownBy(() ->underTest.cancelAppointment(new Appointment(person, session)));
    }

    @Test
    void getAllPendingAppointmentsForSessionTest() {
        // when
        List<Appointment> appointments = underTest.getAllPendingAppointmentsForSession(session);

        // then
        appointments.forEach(a -> {
            assertTrue(a.getStatus() == PENDING);
            assertTrue(a.getSession().equals(session));
        });
    }

    @Test
    void approveAppointmentTest() {
        // when
        underTest.approveAppointment(appointment);

        // then
        assertTrue(appointment.getStatus() == APPROVED);
        verify(repo).save(appointment);
        verify(emailService).sendMessage(any(), anyString());
    }

    @Test
    void deleteAppointment() {
        Appointment app = new Appointment(person, session);
        // when
        underTest.deleteAppointment(app);
        // then
        verify(repo).delete(app);
        assertFalse(session.getAppointments().contains(app));
        assertFalse(person.getAppointments().contains(app));
    }

    @Test
    void updateAppointment() {
        Appointment app = new Appointment(person, session);
        // when
        underTest.updateAppointment(app);
        // then
        verify(repo).save(app);
    }
}