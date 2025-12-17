package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void getAllEnrollments() {
        when(enrollmentRepository.findAll()).thenReturn(List.of(new Enrollment()));

        assertEquals(1, enrollmentService.getAllEnrollments().size());
    }

    @Test
    void getEnrollmentById() {
        Enrollment e = new Enrollment();
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(e));

        assertNotNull(enrollmentService.getEnrollmentById(1L));
    }

    @Test
    void saveEnrollment() {
        Enrollment e = new Enrollment();
        when(enrollmentRepository.save(e)).thenReturn(e);

        assertNotNull(enrollmentService.saveEnrollment(e));
    }

    @Test
    void deleteEnrollment() {
        enrollmentService.deleteEnrollment(1L);
        verify(enrollmentRepository).deleteById(1L);
    }
}
