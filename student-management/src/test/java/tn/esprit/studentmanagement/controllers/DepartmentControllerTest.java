package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.services.IDepartmentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IDepartmentService departmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        DepartmentController controller = new DepartmentController(departmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllDepartment() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(List.of(new Department()));

        mockMvc.perform(get("/Depatment/getAllDepartment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetDepartmentById() throws Exception {
        Department d = new Department();
        when(departmentService.getDepartmentById(1L)).thenReturn(d);

        mockMvc.perform(get("/Depatment/getDepartment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testCreateDepartment() throws Exception {
        Department d = new Department();
        when(departmentService.saveDepartment(any(Department.class))).thenReturn(d);

        mockMvc.perform(post("/Depatment/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"IT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testDeleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/Depatment/deleteDepartment/1"))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}
