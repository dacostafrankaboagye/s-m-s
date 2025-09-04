package repository;

import model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryDepartmentRepositoryTest {

    private DepartmentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryDepartmentRepository();
    }

    @Test
    void testCreateDepartment_Success() {
        // Given
        Department department = new Department();
        department.setId("CS");
        department.setName("Computer Science");

        // When
        repository.createDepartment(department);

        // Then
        Department retrieved = repository.getById("CS");
        assertThat(retrieved).isEqualTo(department);
    }

    @Test
    void testCreateDepartment_NullDepartment() {
        // Then
        assertThatThrownBy(() -> repository.createDepartment(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Department or ID cannot be null");
    }

    @Test
    void testCreateDepartment_NullId() {
        // Given
        Department department = new Department();
        department.setName("Computer Science");
        // ID is null

        // Then
        assertThatThrownBy(() -> repository.createDepartment(department))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Department or ID cannot be null");
    }

    @Test
    void testCreateDepartment_DuplicateId() {
        // Given
        Department department1 = new Department();
        department1.setId("CS");
        department1.setName("Computer Science");

        Department department2 = new Department();
        department2.setId("CS"); // Same ID
        department2.setName("Cybersecurity");

        // When
        repository.createDepartment(department1);

        // Then
        assertThatThrownBy(() -> repository.createDepartment(department2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Department with ID CS already exists");
    }

    @Test
    void testGetById_ExistingDepartment() {
        // Given
        Department department = new Department();
        department.setId("CS");
        department.setName("Computer Science");
        repository.createDepartment(department);

        // When
        Department retrieved = repository.getById("CS");

        // Then
        assertThat(retrieved).isEqualTo(department);
    }

    @Test
    void testGetById_NonExistentDepartment() {
        // When
        Department retrieved = repository.getById("nonexistent");

        // Then
        assertThat(retrieved).isNull();
    }

    @Test
    void testListAll_MultipleDepartments() {
        // Given
        Department cs = new Department();
        cs.setId("CS");
        cs.setName("Computer Science");
        repository.createDepartment(cs);

        Department math = new Department();
        math.setId("MATH");
        math.setName("Mathematics");
        repository.createDepartment(math);

        Department eng = new Department();
        eng.setId("ENG");
        eng.setName("English");
        repository.createDepartment(eng);

        // When
        List<Department> departments = repository.listAll();

        // Then
        assertThat(departments).hasSize(3);
        assertThat(departments).contains(cs, math, eng);
        
        // Verify alphabetical ordering by ID
        assertThat(departments.get(0).getId()).isEqualTo("CS");
        assertThat(departments.get(1).getId()).isEqualTo("ENG");
        assertThat(departments.get(2).getId()).isEqualTo("MATH");
    }

    @Test
    void testListAll_EmptyRepository() {
        // When
        List<Department> departments = repository.listAll();

        // Then
        assertThat(departments).isEmpty();
    }

    @Test
    void testListAll_WithCourses() {
        // Given
        Department cs = new Department();
        cs.setId("CS");
        cs.setName("Computer Science");
        Set<String> csCourses = new TreeSet<>();
        csCourses.add("CS101");
        csCourses.add("CS201");
        cs.setCourses(csCourses);
        repository.createDepartment(cs);

        // When
        List<Department> departments = repository.listAll();

        // Then
        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getCourses()).hasSize(2);
        assertThat(departments.get(0).getCourses()).contains("CS101", "CS201");
    }
}