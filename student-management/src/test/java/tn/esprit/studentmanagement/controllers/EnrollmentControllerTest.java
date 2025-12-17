package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.services.IEnrollment;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EnrollmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IEnrollment enrollmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        EnrollmentController controller = new EnrollmentController(enrollmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllEnrollments() throws Exception {
        when(enrollmentService.getAllEnrollments()).thenReturn(List.of(new Enrollment()));

        mockMvc.perform(get("/Enrollment/getAllEnrollment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetEnrollmentById() throws Exception {
        Enrollment e = new Enrollment();
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(e);

        mockMvc.perform(get("/Enrollment/getEnrollment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testCreateEnrollment() throws Exception {
        Enrollment e = new Enrollment();
        when(enrollmentService.saveEnrollment(any(Enrollment.class))).thenReturn(e);

        mockMvc.perform(post("/Enrollment/createEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"departmentId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testDeleteEnrollment() throws Exception {
        doNothing().when(enrollmentService).deleteEnrollment(1L);

        mockMvc.perform(delete("/Enrollment/deleteEnrollment/1"))
                .andExpect(status().isOk());

        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }
}
