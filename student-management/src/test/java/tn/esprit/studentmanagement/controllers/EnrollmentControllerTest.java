package tn.esprit.studentmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.studentmanagement.entities.*;
import tn.esprit.studentmanagement.services.IEnrollment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IEnrollment enrollmentService;

    private EnrollmentController enrollmentController;
    private ObjectMapper objectMapper;
    private Enrollment enrollment1;
    private Enrollment enrollment2;

    @BeforeEach
    void setUp() {
        enrollmentController = new EnrollmentController();
        enrollmentController.setEnrollmentService(enrollmentService);

        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Create student
        Student student = new Student();
        student.setIdStudent(1L);
        student.setFirstName("John");
        student.setLastName("Doe");

        // Create course
        Course course = new Course();
        course.setIdCourse(1L);
        course.setName("Mathematics");
        course.setCode("MATH101");

        // Create enrollments
        enrollment1 = new Enrollment();
        enrollment1.setIdEnrollment(1L);
        enrollment1.setEnrollmentDate(LocalDate.of(2024, 1, 15));
        enrollment1.setGrade(85.5);
        enrollment1.setStatus(Status.ACTIVE);
        enrollment1.setStudent(student);
        enrollment1.setCourse(course);

        enrollment2 = new Enrollment();
        enrollment2.setIdEnrollment(2L);
        enrollment2.setEnrollmentDate(LocalDate.of(2024, 1, 20));
        enrollment2.setGrade(90.0);
        enrollment2.setStatus(Status.COMPLETED);
        enrollment2.setStudent(student);
        enrollment2.setCourse(course);
    }

    @Test
    void getAllEnrollments_ShouldReturnAllEnrollments() throws Exception {
        // Arrange
        List<Enrollment> enrollments = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentService.getAllEnrollments()).thenReturn(enrollments);

        // Act & Assert
        mockMvc.perform(get("/Enrollment/getAllEnrollment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].idEnrollment").value(1))
                .andExpect(jsonPath("$[0].grade").value(85.5))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].idEnrollment").value(2))
                .andExpect(jsonPath("$[1].grade").value(90.0))
                .andExpect(jsonPath("$[1].status").value("COMPLETED"));

        verify(enrollmentService, times(1)).getAllEnrollments();
    }

    @Test
    void getEnrollmentById_WithValidId_ShouldReturnEnrollment() throws Exception {
        // Arrange
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment1);

        // Act & Assert
        mockMvc.perform(get("/Enrollment/getEnrollment/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnrollment").value(1))
                .andExpect(jsonPath("$.grade").value(85.5))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(enrollmentService, times(1)).getEnrollmentById(1L);
    }

    @Test
    void getEnrollmentById_WithNonExistentId_ShouldReturnEmpty() throws Exception {
        // Arrange
        when(enrollmentService.getEnrollmentById(999L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/Enrollment/getEnrollment/{id}", 999))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(enrollmentService, times(1)).getEnrollmentById(999L);
    }

    @Test
    void createEnrollment_WithValidData_ShouldCreateAndReturnEnrollment() throws Exception {
        // Arrange
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setEnrollmentDate(LocalDate.of(2024, 2, 1));
        newEnrollment.setGrade(75.0);
        newEnrollment.setStatus(Status.ACTIVE);

        Enrollment savedEnrollment = new Enrollment();
        savedEnrollment.setIdEnrollment(3L);
        savedEnrollment.setEnrollmentDate(LocalDate.of(2024, 2, 1));
        savedEnrollment.setGrade(75.0);
        savedEnrollment.setStatus(Status.ACTIVE);

        when(enrollmentService.saveEnrollment(any(Enrollment.class))).thenReturn(savedEnrollment);

        // Act & Assert
        mockMvc.perform(post("/Enrollment/createEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEnrollment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnrollment").value(3))
                .andExpect(jsonPath("$.grade").value(75.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(enrollmentService, times(1)).saveEnrollment(any(Enrollment.class));
    }

    @Test
    void updateEnrollment_WithValidData_ShouldUpdateAndReturnEnrollment() throws Exception {
        // Arrange
        enrollment1.setGrade(95.0);
        enrollment1.setStatus(Status.COMPLETED);

        when(enrollmentService.saveEnrollment(any(Enrollment.class))).thenReturn(enrollment1);

        // Act & Assert
        mockMvc.perform(put("/Enrollment/updateEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollment1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnrollment").value(1))
                .andExpect(jsonPath("$.grade").value(95.0))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(enrollmentService, times(1)).saveEnrollment(any(Enrollment.class));
    }

    @Test
    void deleteEnrollment_WithValidId_ShouldDeleteSuccessfully() throws Exception {
        // Arrange
        doNothing().when(enrollmentService).deleteEnrollment(1L);

        // Act & Assert
        mockMvc.perform(delete("/Enrollment/deleteEnrollment/{id}", 1))
                .andExpect(status().isOk());

        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }
}