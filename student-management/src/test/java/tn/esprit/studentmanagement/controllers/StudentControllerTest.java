package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IStudentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IStudentService studentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        StudentController controller = new StudentController(studentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(new Student()));

        mockMvc.perform(get("/students/getAllStudents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetStudentById() throws Exception {
        Student s = new Student();
        when(studentService.getStudentById(1L)).thenReturn(s);

        mockMvc.perform(get("/students/getStudent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testCreateStudent() throws Exception {
        Student s = new Student();
        when(studentService.saveStudent(any(Student.class))).thenReturn(s);

        mockMvc.perform(post("/students/createStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Ayoub\",\"lastName\":\"Test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/deleteStudent/1"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(1L);
    }
}
