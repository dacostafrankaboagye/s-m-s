package service;

import model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.DepartmentRepository;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        departmentService = new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    void createDepartment_ValidDepartment_CallsRepository() {
        Department department = Department.builder()
                .id("CS")
                .name("Computer Science")
                .build();

        departmentService.createDepartment(department);

        verify(departmentRepository).createDepartment(department);
    }

    @Test
    void createDepartment_NullDepartment_ThrowsException() {
        assertThatThrownBy(() -> departmentService.createDepartment(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Department and its ID/Name cannot be null");
    }

    @Test
    void createDepartment_NullId_ThrowsException() {
        Department department = Department.builder()
                .name("Computer Science")
                .build();

        assertThatThrownBy(() -> departmentService.createDepartment(department))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Department and its ID/Name cannot be null");
    }

    @Test
    void createDepartment_NullName_ThrowsException() {
        Department department = Department.builder()
                .id("CS")
                .build();

        assertThatThrownBy(() -> departmentService.createDepartment(department))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Department and its ID/Name cannot be null");
    }

    @Test
    void getDepartmentById_ValidId_ReturnsDepartment() {
        String departmentId = "CS";
        Department expectedDepartment = Department.builder()
                .id(departmentId)
                .name("Computer Science")
                .build();

        when(departmentRepository.getById(departmentId)).thenReturn(expectedDepartment);

        Department result = departmentService.getDepartmentById(departmentId);

        assertThat(result).isEqualTo(expectedDepartment);
        verify(departmentRepository).getById(departmentId);
    }

    @Test
    void listAllDepartments_ReturnsAllDepartments() {
        List<Department> repositoryDepartments = List.of(
                Department.builder().id("CS").name("Computer Science").build(),
                Department.builder().id("MATH").name("Mathematics").build(),
                Department.builder().id("PHYS").name("Physics").build()
        );

        when(departmentRepository.listAll()).thenReturn(repositoryDepartments);

        List<Department> result = departmentService.listAllDepartments();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrderElementsOf(repositoryDepartments);
        verify(departmentRepository).listAll();
    }
}