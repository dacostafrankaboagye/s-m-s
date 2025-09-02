package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class StudentTest {

    @Test
    void testDefaultConstructor() {
        // When
        Student student = new Student();

        // Then
        assertThat(student.getId()).isNull();
        assertThat(student.getFullName()).isNull();
        assertThat(student.getEmail()).isNull();
        assertThat(student.getPhone()).isNull();
        assertThat(student.getEnrolledCourses()).isNotNull().isEmpty();
        assertThat(student.getAttributes()).isNotNull().isEmpty();
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Student student = new Student();
        String id = "S12345";
        String fullName = "Asiedu John";
        String email = "asiedu.john@example.com";
        String phone = "123-456-7890";
        Set<Enrollment> enrolledCourses = new LinkedHashSet<>();
        Enrollment enrollment = new Enrollment("S12345", "CS101", "Fall 2025", EnrollmentStatus.ENROLLED, 
                                              new HashMap<>(), new java.util.BitSet());
        enrolledCourses.add(enrollment);
        Map<String, String> attributes = new HashMap<>();
        attributes.put("nationality", "Ghanaian");
        attributes.put("guardianName", "Patrick Danso");

        // When
        student.setId(id);
        student.setFullName(fullName);
        student.setEmail(email);
        student.setPhone(phone);
        student.setEnrolledCourses(enrolledCourses);
        student.setAttributes(attributes);

        // Then
        assertThat(student.getId()).isEqualTo(id);
        assertThat(student.getFullName()).isEqualTo(fullName);
        assertThat(student.getEmail()).isEqualTo(email);
        assertThat(student.getPhone()).isEqualTo(phone);
        assertThat(student.getEnrolledCourses()).isEqualTo(enrolledCourses);
        assertThat(student.getAttributes()).isEqualTo(attributes);
    }

    @Test
    void testEnrolledCoursesCollection() {
        // Given
        Student student = new Student();
        Set<Enrollment> enrolledCourses = student.getEnrolledCourses();
        Enrollment cs101Enrollment = new Enrollment("S12345", "CS101", "Fall 2025", 
                                                   EnrollmentStatus.ENROLLED, new HashMap<>(), new java.util.BitSet());
        Enrollment math101Enrollment = new Enrollment("S12345", "MATH101", "Fall 2025", 
                                                     EnrollmentStatus.ENROLLED, new HashMap<>(), new java.util.BitSet());

        // When
        enrolledCourses.add(cs101Enrollment);
        enrolledCourses.add(math101Enrollment);
        enrolledCourses.add(cs101Enrollment); // Duplicate should be ignored

        // Then
        assertThat(student.getEnrolledCourses()).hasSize(2);
        assertThat(student.getEnrolledCourses()).contains(cs101Enrollment, math101Enrollment);
        
        // Verify insertion order is preserved (CS101 should come before MATH101)
        assertThat(student.getEnrolledCourses().iterator().next()).isEqualTo(cs101Enrollment);
    }

    @Test
    void testAttributesMap() {
        // Given
        Student student = new Student();
        Map<String, String> attributes = student.getAttributes();

        // When
        attributes.put("nationality", "Canadian");
        attributes.put("guardianName", "John Smith");
        attributes.put("nationality", "American"); // Should override previous value

        // Then
        assertThat(student.getAttributes()).hasSize(2);
        assertThat(student.getAttributes()).containsEntry("nationality", "American");
        assertThat(student.getAttributes()).containsEntry("guardianName", "John Smith");
    }
}