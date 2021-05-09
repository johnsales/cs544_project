package cs544.team7.project.service;

import cs544.team7.project.model.*;
import cs544.team7.project.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.Provider;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static cs544.team7.project.model.AppointmentStatus.APPROVED;
import static cs544.team7.project.model.RoleType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repo;
    @Mock
    private IEmailService emailService;
    private AppointmentService underTest = null;
    private Person person = null;
    private Session session = null;
    private Role clientRole = new Role(CLIENT);
    private Role providerRole = new Role(PROVIDER);


    @BeforeEach
    void setUp() {
        underTest = new AppointmentService(repo, emailService);
        person = new Person("John", "Smith", "jsmith@gmail.com", "jsmith", "1234", new LinkedList<>(Arrays.asList(clientRole, providerRole)));
        session = new Session(LocalDate.now().plusDays(4), LocalTime.now(), 120, "Darby Hall", person);
    }

    @Test
    void canMakeReservationTest() throws IllegalAccessException {
        // when
        Appointment appointment = underTest.makeReservation(person, session);

        // then
        verify(repo).save(appointment);
        verify(emailService).sendMessage(any(Person.class), anyString());
        assertThat(appointment.getClient()).isEqualTo(person);
        assertThat(appointment.getSession()).isEqualTo(session);
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
        new Appointment(new Person(), session).setStatus(APPROVED);
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
    @Disabled
    void cancelAppointment() {
    }

    @Test
    @Disabled
    void getAllPendingAppointmentsForSession() {
    }

    @Test
    @Disabled
    void approveAppointment() {
    }

    @Test
    @Disabled
    void deleteAppointment() {
    }

    @Test
    @Disabled
    void updateAppointment() {
    }
}