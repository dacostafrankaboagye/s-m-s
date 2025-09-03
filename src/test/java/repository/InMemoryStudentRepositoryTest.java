package repository;

import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryStudentRepositoryTest {

    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryStudentRepository();
    }

    @Test
    void testCreateStudent_Success() {
        // Given
        Student student = new Student();
        student.setId("1");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");

        // When
        repository.createStudent(student);

        // Then
        Student retrieved = repository.getById("1");
        assertThat(retrieved).isEqualTo(student);
    }

    @Test
    void testCreateStudent_NullStudent() {
        // Then
        assertThatThrownBy(() -> repository.createStudent(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student cannot be null");
    }

    @Test
    void testCreateStudent_NullIdOrEmail() {
        // Given
        Student studentWithNullId = new Student();
        studentWithNullId.setFullName("John Doe");
        studentWithNullId.setEmail("john.doe@example.com");

        Student studentWithNullEmail = new Student();
        studentWithNullEmail.setId("1");
        studentWithNullEmail.setFullName("John Doe");

        // Then
        assertThatThrownBy(() -> repository.createStudent(studentWithNullId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student, id, email cannot be null");

        assertThatThrownBy(() -> repository.createStudent(studentWithNullEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student, id, email cannot be null");
    }

    @Test
    void testCreateStudent_DuplicateId() {
        // Given
        Student student1 = new Student();
        student1.setId("1");
        student1.setFullName("John Doe");
        student1.setEmail("john.doe@example.com");

        Student student2 = new Student();
        student2.setId("1"); // Same ID
        student2.setFullName("Jane Smith");
        student2.setEmail("jane.smith@example.com");

        // When
        repository.createStudent(student1);

        // Then
        assertThatThrownBy(() -> repository.createStudent(student2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student with this id or email already exists");
    }

    @Test
    void testCreateStudent_DuplicateEmail() {
        // Given
        Student student1 = new Student();
        student1.setId("1");
        student1.setFullName("John Doe");
        student1.setEmail("same.email@example.com");

        Student student2 = new Student();
        student2.setId("2");
        student2.setFullName("Jane Smith");
        student2.setEmail("same.email@example.com"); // Same email

        // When
        repository.createStudent(student1);

        // Then
        assertThatThrownBy(() -> repository.createStudent(student2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student with this id or email already exists");
    }

    @Test
    void testGetById_ExistingStudent() {
        // Given
        Student student = new Student();
        student.setId("1");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
        repository.createStudent(student);

        // When
        Student retrieved = repository.getById("1");

        // Then
        assertThat(retrieved).isEqualTo(student);
    }

    @Test
    void testGetById_NonExistentStudent() {
        // When
        Student retrieved = repository.getById("nonexistent");

        // Then
        assertThat(retrieved).isNull();
    }

    @Test
    void testSearchByNameToken_SingleResult() {
        // Given
        Student student = new Student();
        student.setId("1");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
        repository.createStudent(student);

        // When
        List<Student> results = repository.searchByNameToken("John");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(student);
    }

    @Test
    void testSearchByNameToken_MultipleResults() {
        // Given
        Student student1 = new Student();
        student1.setId("1");
        student1.setFullName("John Doe");
        student1.setEmail("john.doe@example.com");
        repository.createStudent(student1);

        Student student2 = new Student();
        student2.setId("2");
        student2.setFullName("John Smith");
        student2.setEmail("john.smith@example.com");
        repository.createStudent(student2);

        // When
        List<Student> results = repository.searchByNameToken("John");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).contains(student1, student2);
    }

    @Test
    void testSearchByNameToken_NoResults() {
        // Given
        Student student = new Student();
        student.setId("1");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
        repository.createStudent(student);

        // When
        List<Student> results = repository.searchByNameToken("Nonexistent");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    void testSearchByNameToken_CaseInsensitive() {
        // Given
        Student student = new Student();
        student.setId("1");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
        repository.createStudent(student);

        // When
        List<Student> results = repository.searchByNameToken("john");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(student);
    }

    @Test
    void testDeleteStudent_ExistingStudent() {
        // Given
        Student student = new Student();
        student.setId("1");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
        repository.createStudent(student);

        // When
        repository.deleteStudent("1");

        // Then
        assertThat(repository.getById("1")).isNull();
        
        // Verify that we can create a new student with the same email
        Student newStudent = new Student();
        newStudent.setId("2");
        newStudent.setFullName("Jane Smith");
        newStudent.setEmail("john.doe@example.com"); // Same email as deleted student
        repository.createStudent(newStudent);
        
        // Verify that name token index is updated
        List<Student> results = repository.searchByNameToken("John");
        assertThat(results).isEmpty();
    }

    @Test
    void testDeleteStudent_NonExistentStudent() {
        // When - This should not throw an exception
        repository.deleteStudent("nonexistent");
        
        // Then - Verify repository state remains unchanged
        assertThat(repository.getById("nonexistent")).isNull();
    }
}