package tn.esprit.studentmanagement.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void getAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(new Department()));

        assertEquals(1, departmentService.getAllDepartments().size());
    }

    @Test
    void getDepartmentById() {
        Department d = new Department();
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(d));

        assertNotNull(departmentService.getDepartmentById(1L));
    }

    @Test
    void saveDepartment() {
        Department d = new Department();
        when(departmentRepository.save(d)).thenReturn(d);

        assertNotNull(departmentService.saveDepartment(d));
    }

    @Test
    void deleteDepartment() {
        departmentService.deleteDepartment(1L);
        verify(departmentRepository).deleteById(1L);
    }
}
