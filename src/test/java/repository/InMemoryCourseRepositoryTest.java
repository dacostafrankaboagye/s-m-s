package repository;

import model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryCourseRepositoryTest {

    private CourseRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCourseRepository();
    }

    @Test
    void testCreateCourse_Success() {
        // Given
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Computer Science");
        course.setCredits(3);
        course.setDepartment("CS");

        // When
        repository.createCourse(course);

        // Then
        Course retrieved = repository.getByCode("CS101");
        assertThat(retrieved).isEqualTo(course);
    }

    @Test
    void testCreateCourse_NullCourse() {
        // Then
        assertThatThrownBy(() -> repository.createCourse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Course cannot be null");
    }

    @Test
    void testCreateCourse_DuplicateCode() {
        // Given
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("Introduction to Computer Science");
        course1.setCredits(3);
        course1.setDepartment("CS");

        Course course2 = new Course();
        course2.setCode("CS101"); // Same code
        course2.setTitle("Advanced Computer Science");
        course2.setCredits(4);
        course2.setDepartment("CS");

        // When
        repository.createCourse(course1);

        // Then
        assertThatThrownBy(() -> repository.createCourse(course2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Course with code CS101 already exists");
    }

    @Test
    void testGetByCode_ExistingCourse() {
        // Given
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Computer Science");
        course.setCredits(3);
        course.setDepartment("CS");
        repository.createCourse(course);

        // When
        Course retrieved = repository.getByCode("CS101");

        // Then
        assertThat(retrieved).isEqualTo(course);
    }

    @Test
    void testGetByCode_NonExistentCourse() {
        // When
        Course retrieved = repository.getByCode("nonexistent");

        // Then
        assertThat(retrieved).isNull();
    }

    @Test
    void testListByDepartment_MultipleCourses() {
        // Given
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("Introduction to Computer Science");
        course1.setCredits(3);
        course1.setDepartment("CS");
        repository.createCourse(course1);

        Course course2 = new Course();
        course2.setCode("CS201");
        course2.setTitle("Data Structures");
        course2.setCredits(4);
        course2.setDepartment("CS");
        repository.createCourse(course2);

        Course course3 = new Course();
        course3.setCode("MATH101");
        course3.setTitle("Calculus I");
        course3.setCredits(3);
        course3.setDepartment("MATH");
        repository.createCourse(course3);

        // When
        List<Course> csCourses = repository.listByDepartment("CS");
        List<Course> mathCourses = repository.listByDepartment("MATH");

        // Then
        assertThat(csCourses).hasSize(2);
        assertThat(csCourses).contains(course1, course2);
        
        assertThat(mathCourses).hasSize(1);
        assertThat(mathCourses).contains(course3);
    }

    @Test
    void testListByDepartment_NoCourses() {
        // When
        List<Course> courses = repository.listByDepartment("nonexistent");

        // Then
        assertThat(courses).isEmpty();
    }

    @Test
    void testListByDepartment_AlphabeticalOrdering() {
        // Given
        Course course1 = new Course();
        course1.setCode("CS201");
        course1.setTitle("Data Structures");
        course1.setCredits(4);
        course1.setDepartment("CS");
        repository.createCourse(course1);

        Course course2 = new Course();
        course2.setCode("CS101");
        course2.setTitle("Introduction to Computer Science");
        course2.setCredits(3);
        course2.setDepartment("CS");
        repository.createCourse(course2);

        Course course3 = new Course();
        course3.setCode("CS301");
        course3.setTitle("Algorithms");
        course3.setCredits(4);
        course3.setDepartment("CS");
        repository.createCourse(course3);

        // When
        List<Course> courses = repository.listByDepartment("CS");

        // Then
        assertThat(courses).hasSize(3);
        // Verify alphabetical ordering by course code
        assertThat(courses.get(0).getCode()).isEqualTo("CS101");
        assertThat(courses.get(1).getCode()).isEqualTo("CS201");
        assertThat(courses.get(2).getCode()).isEqualTo("CS301");
    }

    @Test
    void testDeleteCourse_ExistingCourse() {
        // Given
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Computer Science");
        course.setCredits(3);
        course.setDepartment("CS");
        repository.createCourse(course);

        // When
        repository.deleteCourse("CS101");

        // Then
        assertThat(repository.getByCode("CS101")).isNull();
        
        // Verify that department-to-course mapping is updated
        assertThat(repository.listByDepartment("CS")).isEmpty();
    }

    @Test
    void testDeleteCourse_NonExistentCourse() {
        // When - This should not throw an exception
        repository.deleteCourse("nonexistent");
        
        // Then - Verify repository state remains unchanged
        assertThat(repository.getByCode("nonexistent")).isNull();
    }

    @Test
    void testDeleteCourse_MultipleCoursesSameDepartment() {
        // Given
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("Introduction to Computer Science");
        course1.setCredits(3);
        course1.setDepartment("CS");
        repository.createCourse(course1);

        Course course2 = new Course();
        course2.setCode("CS201");
        course2.setTitle("Data Structures");
        course2.setCredits(4);
        course2.setDepartment("CS");
        repository.createCourse(course2);

        // When
        repository.deleteCourse("CS101");

        // Then
        assertThat(repository.getByCode("CS101")).isNull();
        assertThat(repository.getByCode("CS201")).isNotNull();
        
        // Verify that department-to-course mapping is updated correctly
        List<Course> courses = repository.listByDepartment("CS");
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getCode()).isEqualTo("CS201");
    }

    @Test
    void testDeleteCourse_LastCourseInDepartment() {
        // Given
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Computer Science");
        course.setCredits(3);
        course.setDepartment("CS");
        repository.createCourse(course);

        // When
        repository.deleteCourse("CS101");

        // Then
        assertThat(repository.getByCode("CS101")).isNull();
        
        // Verify that department-to-course mapping is removed
        assertThat(repository.listByDepartment("CS")).isEmpty();
    }
}