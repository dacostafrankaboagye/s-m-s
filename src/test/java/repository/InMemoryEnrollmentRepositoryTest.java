package repository;

import model.Enrollment;
import model.EnrollmentStatus;
import model.GradeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryEnrollmentRepositoryTest {

    private EnrollmentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryEnrollmentRepository();
    }

    @Test
    void testEnroll_Success() {
        // When
        repository.enroll("S12345", "CS101", "Fall 2025");

        // Then
        List<Enrollment> enrollments = repository.getEnrollmentsForStudent("S12345");
        assertThat(enrollments).hasSize(1);
        assertThat(enrollments.getFirst().getStudentId()).isEqualTo("S12345");
        assertThat(enrollments.getFirst().getCourseCode()).isEqualTo("CS101");
        assertThat(enrollments.getFirst().getSemester()).isEqualTo("Fall 2025");
        assertThat(enrollments.getFirst().getStatus()).isEqualTo(EnrollmentStatus.ENROLLED);
        
        List<String> students = repository.getStudentsForCourse("CS101");
        assertThat(students).hasSize(1);
        assertThat(students).contains("S12345");
    }
    
    @Test
    void testEnroll_NullParameters() {
        // Then
        assertThatThrownBy(() -> repository.enroll(null, "CS101", "Fall 2025"))
                .isInstanceOf(NullPointerException.class);
                
        assertThatThrownBy(() -> repository.enroll("S12345", null, "Fall 2025"))
                .isInstanceOf(NullPointerException.class);
                
        assertThatThrownBy(() -> repository.enroll("S12345", "CS101", null))
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    void testEnroll_MultipleEnrollments() {
        // When
        repository.enroll("S12345", "CS101", "Fall 2025");
        repository.enroll("S12345", "MATH101", "Fall 2025");
        repository.enroll("S12345", "CS102", "Spring 2026");
        
        // Then
        List<Enrollment> enrollments = repository.getEnrollmentsForStudent("S12345");
        assertThat(enrollments).hasSize(3);
        
        // Verify course codes
        assertThat(enrollments).extracting(Enrollment::getCourseCode)
                .containsExactlyInAnyOrder("CS101", "MATH101", "CS102");
                
        // Verify semesters
        assertThat(enrollments).extracting(Enrollment::getSemester)
                .containsExactlyInAnyOrder("Fall 2025", "Fall 2025", "Spring 2026");
                
        // Verify student is in all courses
        assertThat(repository.getStudentsForCourse("CS101")).contains("S12345");
        assertThat(repository.getStudentsForCourse("MATH101")).contains("S12345");
        assertThat(repository.getStudentsForCourse("CS102")).contains("S12345");
    }
    
    @Test
    void testDrop_Success() {
        // Given
        repository.enroll("S12345", "CS101", "Fall 2025");
        
        // When
        repository.drop("S12345", "CS101", "Fall 2025");
        
        // Then
        List<Enrollment> enrollments = repository.getEnrollmentsForStudent("S12345");
        assertThat(enrollments).hasSize(1);
        assertThat(enrollments.getFirst().getStatus()).isEqualTo(EnrollmentStatus.DROPPED);
        
        List<String> students = repository.getStudentsForCourse("CS101");
        assertThat(students).isEmpty();
    }
    
    @Test
    void testDrop_NonExistentEnrollment() {
        // When - This should not throw an exception
        repository.drop("S12345", "CS101", "Fall 2025");
        
        // Then
        List<Enrollment> enrollments = repository.getEnrollmentsForStudent("S12345");
        assertThat(enrollments).isEmpty();
    }
    
    @Test
    void testDrop_NullParameters() {
        // Then
        assertThatThrownBy(() -> repository.drop(null, "CS101", "Fall 2025"))
                .isInstanceOf(NullPointerException.class);
                
        assertThatThrownBy(() -> repository.drop("S12345", null, "Fall 2025"))
                .isInstanceOf(NullPointerException.class);
                
        assertThatThrownBy(() -> repository.drop("S12345", "CS101", null))
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    void testGetEnrollmentsForStudent_WithEnrollments() {
        // Given
        repository.enroll("S12345", "CS101", "Fall 2025");
        repository.enroll("S12345", "MATH101", "Fall 2025");
        
        // When
        List<Enrollment> enrollments = repository.getEnrollmentsForStudent("S12345");
        
        // Then
        assertThat(enrollments).hasSize(2);
        assertThat(enrollments).extracting(Enrollment::getCourseCode)
                .containsExactlyInAnyOrder("CS101", "MATH101");
    }
    
    @Test
    void testGetEnrollmentsForStudent_NoEnrollments() {
        // When
        List<Enrollment> enrollments = repository.getEnrollmentsForStudent("S12345");
        
        // Then
        assertThat(enrollments).isEmpty();
    }
    
    @Test
    void testGetEnrollmentsForStudent_NullParameter() {
        // Then
        assertThatThrownBy(() -> repository.getEnrollmentsForStudent(null))
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    void testGetStudentsForCourse_WithEnrollments() {
        // Given
        repository.enroll("S12345", "CS101", "Fall 2025");
        repository.enroll("S67890", "CS101", "Fall 2025");
        
        // When
        List<String> students = repository.getStudentsForCourse("CS101");
        
        // Then
        assertThat(students).hasSize(2);
        assertThat(students).containsExactlyInAnyOrder("S12345", "S67890");
    }
    
    @Test
    void testGetStudentsForCourse_NoEnrollments() {
        // When
        List<String> students = repository.getStudentsForCourse("CS101");
        
        // Then
        assertThat(students).isEmpty();
    }
    
    @Test
    void testMultipleStudentsMultipleCourses() {
        // Given
        repository.enroll("S12345", "CS101", "Fall 2025");
        repository.enroll("S12345", "MATH101", "Fall 2025");
        repository.enroll("S67890", "CS101", "Fall 2025");
        repository.enroll("S67890", "PHYS101", "Fall 2025");
        
        // When
        List<String> cs101Students = repository.getStudentsForCourse("CS101");
        List<String> math101Students = repository.getStudentsForCourse("MATH101");
        List<String> phys101Students = repository.getStudentsForCourse("PHYS101");
        
        // Then
        assertThat(cs101Students).hasSize(2);
        assertThat(cs101Students).containsExactlyInAnyOrder("S12345", "S67890");
        
        assertThat(math101Students).hasSize(1);
        assertThat(math101Students).contains("S12345");
        
        assertThat(phys101Students).hasSize(1);
        assertThat(phys101Students).contains("S67890");
    }
}