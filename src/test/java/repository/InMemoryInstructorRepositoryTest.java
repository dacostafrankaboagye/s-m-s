package repository;

import model.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryInstructorRepositoryTest {

    private InstructorRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryInstructorRepository();
    }

    @Test
    void testCreateInstructor_Success() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setId("INS001");
        instructor.setName("John Doe");
        Set<String> coursesTaught = new HashSet<>();
        coursesTaught.add("CS101");
        coursesTaught.add("CS201");
        instructor.setCoursesTaught(coursesTaught);

        // When
        repository.createInstructor(instructor);

        // Then
        Instructor retrieved = repository.getById("INS001");
        assertThat(retrieved).isEqualTo(instructor);
    }

    @Test
    void testCreateInstructor_NullInstructor() {
        // Then
        assertThatThrownBy(() -> repository.createInstructor(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testCreateInstructor_NullId() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setName("John Doe");
        // ID is null

        // Then
        assertThatThrownBy(() -> repository.createInstructor(instructor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Instructor with this ID already exists or is invalid");
    }

    @Test
    void testCreateInstructor_DuplicateId() {
        // Given
        Instructor instructor1 = new Instructor();
        instructor1.setId("INS001");
        instructor1.setName("John Doe");

        Instructor instructor2 = new Instructor();
        instructor2.setId("INS001"); // Same ID
        instructor2.setName("Jane Smith");

        // When
        repository.createInstructor(instructor1);

        // Then
        assertThatThrownBy(() -> repository.createInstructor(instructor2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Instructor with this ID already exists or is invalid");
    }

    @Test
    void testGetById_ExistingInstructor() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setId("INS001");
        instructor.setName("John Doe");
        repository.createInstructor(instructor);

        // When
        Instructor retrieved = repository.getById("INS001");

        // Then
        assertThat(retrieved).isEqualTo(instructor);
    }

    @Test
    void testGetById_NonExistentInstructor() {
        // When
        Instructor retrieved = repository.getById("nonexistent");

        // Then
        assertThat(retrieved).isNull();
    }

    @Test
    void testSearchByNameToken_SingleResult() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setId("INS001");
        instructor.setName("John Doe");
        repository.createInstructor(instructor);

        // When
        List<Instructor> results = repository.searchByNameToken("John");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(instructor);
    }

    @Test
    void testSearchByNameToken_MultipleResults() {
        // Given
        Instructor instructor1 = new Instructor();
        instructor1.setId("INS001");
        instructor1.setName("John Doe");
        repository.createInstructor(instructor1);

        Instructor instructor2 = new Instructor();
        instructor2.setId("INS002");
        instructor2.setName("John Smith");
        repository.createInstructor(instructor2);

        // When
        List<Instructor> results = repository.searchByNameToken("John");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).contains(instructor1, instructor2);
    }

    @Test
    void testSearchByNameToken_NoResults() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setId("INS001");
        instructor.setName("John Doe");
        repository.createInstructor(instructor);

        // When
        List<Instructor> results = repository.searchByNameToken("Nonexistent");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    void testSearchByNameToken_CaseInsensitive() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setId("INS001");
        instructor.setName("John Doe");
        repository.createInstructor(instructor);

        // When
        List<Instructor> results = repository.searchByNameToken("john");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(instructor);
    }

    @Test
    void testDeleteInstructor_ExistingInstructor() {
        // Given
        Instructor instructor = new Instructor();
        instructor.setId("INS001");
        instructor.setName("John Doe");
        Set<String> coursesTaught = new HashSet<>();
        coursesTaught.add("CS101");
        coursesTaught.add("CS201");
        instructor.setCoursesTaught(coursesTaught);
        repository.createInstructor(instructor);

        // When
        repository.deleteInstructor("INS001");

        // Then
        assertThat(repository.getById("INS001")).isNull();
        
        // Verify that name token index is updated
        List<Instructor> results = repository.searchByNameToken("John");
        assertThat(results).isEmpty();
    }

    @Test
    void testDeleteInstructor_NonExistentInstructor() {
        // When - This should not throw an exception
        repository.deleteInstructor("nonexistent");
        
        // Then - Verify repository state remains unchanged
        assertThat(repository.getById("nonexistent")).isNull();
    }

    @Test
    void testCourseToInstructorMapping() {
        // Given
        Instructor instructor1 = new Instructor();
        instructor1.setId("INS001");
        instructor1.setName("John Doe");
        Set<String> coursesTaught1 = new HashSet<>();
        coursesTaught1.add("CS101");
        coursesTaught1.add("CS201");
        instructor1.setCoursesTaught(coursesTaught1);
        repository.createInstructor(instructor1);

        Instructor instructor2 = new Instructor();
        instructor2.setId("INS002");
        instructor2.setName("Jane Smith");
        Set<String> coursesTaught2 = new HashSet<>();
        coursesTaught2.add("CS101");
        coursesTaught2.add("MATH101");
        instructor2.setCoursesTaught(coursesTaught2);
        repository.createInstructor(instructor2);

        // When
        repository.deleteInstructor("INS001");

        // Then
        // Verify that instructor1 is deleted
        assertThat(repository.getById("INS001")).isNull();
        
        // Verify that instructor2 still exists
        assertThat(repository.getById("INS002")).isNotNull();
        
        // Verify that name token index is updated
        List<Instructor> results = repository.searchByNameToken("John");
        assertThat(results).isEmpty();
        
        List<Instructor> results2 = repository.searchByNameToken("Jane");
        assertThat(results2).hasSize(1);
        assertThat(results2.get(0)).isEqualTo(instructor2);
    }
}