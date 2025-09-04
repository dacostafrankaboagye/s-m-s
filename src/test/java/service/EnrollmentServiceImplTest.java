package service;

import model.Enrollment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.EnrollmentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        enrollmentService = new EnrollmentServiceImpl(enrollmentRepository);
    }

    @Test
    void enrollStudent_ValidParameters_CallsRepository() {
        String studentId = "S001";
        String courseCode = "CS101";
        String semester = "Fall2024";

        enrollmentService.enrollStudent(studentId, courseCode, semester);

        verify(enrollmentRepository).enroll(studentId, courseCode, semester);
    }

    @Test
    void enrollStudent_NullStudentId_ThrowsException() {
        assertThatThrownBy(() -> enrollmentService.enrollStudent(null, "CS101", "Fall2024"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID, Course Code, and Semester cannot be null");
    }

    @Test
    void enrollStudent_NullCourseCode_ThrowsException() {
        assertThatThrownBy(() -> enrollmentService.enrollStudent("S001", null, "Fall2024"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID, Course Code, and Semester cannot be null");
    }

    @Test
    void enrollStudent_NullSemester_ThrowsException() {
        assertThatThrownBy(() -> enrollmentService.enrollStudent("S001", "CS101", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID, Course Code, and Semester cannot be null");
    }

    @Test
    void dropStudent_ValidParameters_CallsRepository() {
        String studentId = "S001";
        String courseCode = "CS101";
        String semester = "Fall2024";

        enrollmentService.dropStudent(studentId, courseCode, semester);

        verify(enrollmentRepository).drop(studentId, courseCode, semester);
    }

    @Test
    void dropStudent_NullStudentId_ThrowsException() {
        assertThatThrownBy(() -> enrollmentService.dropStudent(null, "CS101", "Fall2024"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID, Course Code, and Semester cannot be null");
    }

    @Test
    void dropStudent_NullCourseCode_ThrowsException() {
        assertThatThrownBy(() -> enrollmentService.dropStudent("S001", null, "Fall2024"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID, Course Code, and Semester cannot be null");
    }

    @Test
    void dropStudent_NullSemester_ThrowsException() {
        assertThatThrownBy(() -> enrollmentService.dropStudent("S001", "CS101", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Student ID, Course Code, and Semester cannot be null");
    }

    @Test
    void getEnrollmentsForStudent_ValidStudentId_ReturnsEnrollments() {
        String studentId = "S001";
        List<Enrollment> expectedEnrollments = List.of(
                Enrollment.builder().studentId(studentId).courseCode("CS101").semester("Fall2024").build(),
                Enrollment.builder().studentId(studentId).courseCode("MATH201").semester("Fall2024").build()
        );

        when(enrollmentRepository.getEnrollmentsForStudent(studentId)).thenReturn(expectedEnrollments);

        List<Enrollment> result = enrollmentService.getEnrollmentsForStudent(studentId);

        assertThat(result).isEqualTo(expectedEnrollments);
        verify(enrollmentRepository).getEnrollmentsForStudent(studentId);
    }

    @Test
    void getStudentsForCourse_ValidCourseCode_ReturnsStudentIds() {
        String courseCode = "CS101";
        List<String> expectedStudentIds = List.of("S001", "S002", "S003");

        when(enrollmentRepository.getStudentsForCourse(courseCode)).thenReturn(expectedStudentIds);

        List<String> result = enrollmentService.getStudentsForCourse(courseCode);

        assertThat(result).isEqualTo(expectedStudentIds);
        verify(enrollmentRepository).getStudentsForCourse(courseCode);
    }
}