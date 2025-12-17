package tn.esprit.studentmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.services.IDepartmentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IDepartmentService departmentService;

    private DepartmentController departmentController;
    private ObjectMapper objectMapper;
    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        // Initialiser le contrôleur avec le service mock
        departmentController = new DepartmentController();

        // Utiliser le setter pour injecter le service mock
        departmentController.setDepartmentService(departmentService);

        // Configurer MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();

        // Initialiser ObjectMapper
        objectMapper = new ObjectMapper();

        // Créer des objets Department de test
        department1 = new Department();
        department1.setIdDepartment(1L);
        department1.setName("Computer Science");
        department1.setLocation("Building A");
        department1.setPhone("1234567890");
        department1.setHead("Dr. Smith");

        department2 = new Department();
        department2.setIdDepartment(2L);
        department2.setName("Mathematics");
        department2.setLocation("Building B");
        department2.setPhone("0987654321");
        department2.setHead("Dr. Johnson");
    }

    @Test
    void getAllDepartments_ShouldReturnList() throws Exception {
        // Arrange
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/Department/getAllDepartment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDepartment").value(1))
                .andExpect(jsonPath("$[0].name").value("Computer Science"))
                .andExpect(jsonPath("$[1].idDepartment").value(2))
                .andExpect(jsonPath("$[1].name").value("Mathematics"));

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void getDepartmentById_WithValidId_ShouldReturnDepartment() throws Exception {
        // Arrange
        when(departmentService.getDepartmentById(1L)).thenReturn(department1);

        // Act & Assert
        mockMvc.perform(get("/Department/getDepartment/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepartment").value(1))
                .andExpect(jsonPath("$.name").value("Computer Science"));

        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    void getDepartmentById_WithNonExistentId_ShouldReturnEmpty() throws Exception {
        // Arrange
        when(departmentService.getDepartmentById(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/Department/getDepartment/{id}", 99))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(departmentService, times(1)).getDepartmentById(99L);
    }

    @Test
    void createDepartment_WithValidData_ShouldCreateAndReturnDepartment() throws Exception {
        // Arrange
        Department newDepartment = new Department();
        newDepartment.setName("Physics");
        newDepartment.setLocation("Building C");

        Department savedDepartment = new Department();
        savedDepartment.setIdDepartment(3L);
        savedDepartment.setName("Physics");
        savedDepartment.setLocation("Building C");

        when(departmentService.saveDepartment(any(Department.class))).thenReturn(savedDepartment);

        // Act & Assert
        mockMvc.perform(post("/Department/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDepartment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepartment").value(3))
                .andExpect(jsonPath("$.name").value("Physics"));

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void updateDepartment_WithValidData_ShouldUpdateAndReturnDepartment() throws Exception {
        // Arrange
        department1.setHead("Dr. Anderson");

        when(departmentService.saveDepartment(any(Department.class))).thenReturn(department1);

        // Act & Assert
        mockMvc.perform(put("/Department/updateDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDepartment").value(1))
                .andExpect(jsonPath("$.head").value("Dr. Anderson"));

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void deleteDepartment_WithValidId_ShouldDeleteSuccessfully() throws Exception {
        // Arrange
        doNothing().when(departmentService).deleteDepartment(1L);

        // Act & Assert
        mockMvc.perform(delete("/Department/deleteDepartment/{id}", 1))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}