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

import java.time.LocalDate;
import java.time.Month;

import static cs544.team7.project.model.RoleType.CLIENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repo;
    @Mock
    private IEmailService emailService;
    private AppointmentService underTest = null;

    @BeforeEach
    void setUp() {
        underTest = new AppointmentService(repo, emailService);
    }

    @Test
    void canMakeReservation() throws IllegalAccessException {
        // Given
        Person person = new Person();
        person.addRole(new Role(CLIENT));
        person.setEmail("some@email.com");
        Session session = new Session();
        session.setDate(LocalDate.of(2021, Month.MAY, 25));

        // when
        Appointment appointment = underTest.makeReservation(person, session);

        // then
        verify(repo).save(appointment);
        verify(emailService).sendMessage(any(Person.class), anyString());
        assertThat(appointment.getClient()).isEqualTo(person);
        assertThat(appointment.getSession()).isEqualTo(session);
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