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
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IStudentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IStudentService studentService;

    private StudentController studentController;
    private ObjectMapper objectMapper;
    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        studentController = new StudentController();
        studentController.setStudentService(studentService);

        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Create department
        Department department = new Department();
        department.setIdDepartment(1L);
        department.setName("Computer Science");

        // Create students
        student1 = new Student();
        student1.setIdStudent(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setPhone("1234567890");
        student1.setDateOfBirth(LocalDate.of(2000, 1, 15));
        student1.setAddress("123 Main St");
        student1.setDepartment(department);

        student2 = new Student();
        student2.setIdStudent(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane.smith@example.com");
        student2.setPhone("0987654321");
        student2.setDateOfBirth(LocalDate.of(2001, 5, 20));
        student2.setAddress("456 Oak St");
        student2.setDepartment(department);
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() throws Exception {
        // Arrange
        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);

        // Act & Assert
        mockMvc.perform(get("/students/getAllStudents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStudent").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].idStudent").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentById_WithValidId_ShouldReturnStudent() throws Exception {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(student1);

        // Act & Assert
        mockMvc.perform(get("/students/getStudent/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStudent").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void getStudentById_WithNonExistentId_ShouldReturnEmpty() throws Exception {
        // Arrange
        when(studentService.getStudentById(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/students/getStudent/{id}", 99))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(studentService, times(1)).getStudentById(99L);
    }

    @Test
    void createStudent_WithValidData_ShouldCreateAndReturnStudent() throws Exception {
        // Arrange
        Student newStudent = new Student();
        newStudent.setFirstName("Alice");
        newStudent.setLastName("Johnson");
        newStudent.setEmail("alice.johnson@example.com");

        Student savedStudent = new Student();
        savedStudent.setIdStudent(3L);
        savedStudent.setFirstName("Alice");
        savedStudent.setLastName("Johnson");
        savedStudent.setEmail("alice.johnson@example.com");

        when(studentService.saveStudent(any(Student.class))).thenReturn(savedStudent);

        // Act & Assert
        mockMvc.perform(post("/students/createStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStudent").value(3))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Johnson"));

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    void updateStudent_WithValidData_ShouldUpdateAndReturnStudent() throws Exception {
        // Arrange
        student1.setEmail("john.updated@example.com");
        student1.setPhone("9999999999");

        when(studentService.saveStudent(any(Student.class))).thenReturn(student1);

        // Act & Assert
        mockMvc.perform(put("/students/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStudent").value(1))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"))
                .andExpect(jsonPath("$.phone").value("9999999999"));

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    void deleteStudent_WithValidId_ShouldDeleteSuccessfully() throws Exception {
        // Arrange
        doNothing().when(studentService).deleteStudent(1L);

        // Act & Assert
        mockMvc.perform(delete("/students/deleteStudent/{id}", 1))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(1L);
    }
}