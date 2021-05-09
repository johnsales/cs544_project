package cs544.team7.project.service;

import cs544.team7.project.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    private AppointmentService underTest = null;

    @BeforeEach
    void setUp() {
        underTest = new AppointmentService(appointmentRepository);
    }

    @Test
    @Disabled
    void makeReservation() {

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