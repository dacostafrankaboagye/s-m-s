package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

class InstructorTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Instructor instructor = new Instructor();

        // Then
        assertThat(instructor.getId()).isNull();
        assertThat(instructor.getName()).isNull();
        assertThat(instructor.getCoursesTaught()).isNotNull().isEmpty();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String id = "INS001";
        String name = "John Doe";
        Set<String> coursesTaught = new HashSet<>();
        coursesTaught.add("CS101");
        coursesTaught.add("CS201");

        // When
        Instructor instructor = new Instructor(id, name, coursesTaught);

        // Then
        assertThat(instructor.getId()).isEqualTo(id);
        assertThat(instructor.getName()).isEqualTo(name);
        assertThat(instructor.getCoursesTaught()).isEqualTo(coursesTaught);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Instructor instructor = new Instructor();
        String id = "INS001";
        String name = "John Doe";
        Set<String> coursesTaught = new HashSet<>();
        coursesTaught.add("CS101");
        coursesTaught.add("CS201");

        // When
        instructor.setId(id);
        instructor.setName(name);
        instructor.setCoursesTaught(coursesTaught);

        // Then
        assertThat(instructor.getId()).isEqualTo(id);
        assertThat(instructor.getName()).isEqualTo(name);
        assertThat(instructor.getCoursesTaught()).isEqualTo(coursesTaught);
    }

    @Test
    void testCoursesTaughtCollection() {
        // Given
        Instructor instructor = new Instructor();
        Set<String> coursesTaught = instructor.getCoursesTaught();

        // When
        coursesTaught.add("CS101");
        coursesTaught.add("CS201");
        coursesTaught.add("CS101"); // Duplicate should be ignored

        // Then
        assertThat(instructor.getCoursesTaught()).hasSize(2);
        assertThat(instructor.getCoursesTaught()).contains("CS101", "CS201");
    }
}