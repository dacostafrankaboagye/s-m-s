package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.TreeSet;

class DepartmentTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Department department = new Department();

        // Then
        assertThat(department.getId()).isNull();
        assertThat(department.getName()).isNull();
        assertThat(department.getCourses()).isNotNull().isEmpty();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String id = "CS";
        String name = "Computer Science";
        Set<String> courses = new TreeSet<>();
        courses.add("CS101");
        courses.add("CS201");

        // When
        Department department = new Department(id, name, courses);

        // Then
        assertThat(department.getId()).isEqualTo(id);
        assertThat(department.getName()).isEqualTo(name);
        assertThat(department.getCourses()).isEqualTo(courses);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Department department = new Department();
        String id = "CS";
        String name = "Computer Science";
        Set<String> courses = new TreeSet<>();
        courses.add("CS101");
        courses.add("CS201");

        // When
        department.setId(id);
        department.setName(name);
        department.setCourses(courses);

        // Then
        assertThat(department.getId()).isEqualTo(id);
        assertThat(department.getName()).isEqualTo(name);
        assertThat(department.getCourses()).isEqualTo(courses);
    }

    @Test
    void testCoursesCollection() {
        // Given
        Department department = new Department();
        Set<String> courses = department.getCourses();

        // When
        courses.add("CS101");
        courses.add("CS201");
        courses.add("CS101"); // Duplicate should be ignored

        // Then
        assertThat(department.getCourses()).hasSize(2);
        assertThat(department.getCourses()).contains("CS101", "CS201");
        
        // Verify alphabetical ordering (CS101 should come before CS201)
        assertThat(department.getCourses().iterator().next()).isEqualTo("CS101");
    }
}