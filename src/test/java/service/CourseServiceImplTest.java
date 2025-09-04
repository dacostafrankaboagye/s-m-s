package service;

import model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CourseRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository);
    }

    @Test
    void createCourse_ValidCourse_CallsRepository() {
        Course course = Course.builder()
                .code("CS101")
                .title("Introduction to Computer Science")
                .department("Computer Science")
                .credits(3)
                .build();

        courseService.createCourse(course);

        verify(courseRepository).createCourse(course);
    }

    @Test
    void createCourse_NullCourse_ThrowsException() {
        assertThatThrownBy(() -> courseService.createCourse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Course code and department cannot be null");
    }

    @Test
    void createCourse_NullCode_ThrowsException() {
        Course course = Course.builder()
                .title("Introduction to Computer Science")
                .department("Computer Science")
                .credits(3)
                .build();

        assertThatThrownBy(() -> courseService.createCourse(course))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Course code and department cannot be null");
    }

    @Test
    void createCourse_NullDepartment_ThrowsException() {
        Course course = Course.builder()
                .code("CS101")
                .title("Introduction to Computer Science")
                .credits(3)
                .build();

        assertThatThrownBy(() -> courseService.createCourse(course))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Course code and department cannot be null");
    }

    @Test
    void getCourseByCode_ValidCode_ReturnsCourse() {
        String courseCode = "CS101";
        Course expectedCourse = Course.builder()
                .code(courseCode)
                .title("Introduction to Computer Science")
                .department("Computer Science")
                .credits(3)
                .build();

        when(courseRepository.getByCode(courseCode)).thenReturn(expectedCourse);

        Course result = courseService.getCourseByCode(courseCode);

        assertThat(result).isEqualTo(expectedCourse);
        verify(courseRepository).getByCode(courseCode);
    }

    @Test
    void listCoursesByDepartment_ValidDepartment_ReturnsCourses() {
        String department = "Computer Science";
        List<Course> expectedCourses = List.of(
                Course.builder().code("CS101").title("Intro to CS").department(department).credits(3).build(),
                Course.builder().code("CS201").title("Data Structures").department(department).credits(4).build()
        );

        when(courseRepository.listByDepartment(department)).thenReturn(expectedCourses);

        List<Course> result = courseService.listCoursesByDepartment(department);

        assertThat(result).isEqualTo(expectedCourses);
        verify(courseRepository).listByDepartment(department);
    }

}