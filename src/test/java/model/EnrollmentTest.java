package model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.Map;

class EnrollmentTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Enrollment enrollment = new Enrollment();

        // Then
        assertThat(enrollment.getStudentId()).isNull();
        assertThat(enrollment.getCourseCode()).isNull();
        assertThat(enrollment.getSemester()).isNull();
        assertThat(enrollment.getStatus()).isNull();
        assertThat(enrollment.getGrades()).isNotNull().isEmpty();
        assertThat(enrollment.getAttendance()).isNotNull();
        assertThat(enrollment.getAttendance().isEmpty()).isTrue();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String studentId = "S12345";
        String courseCode = "CS101";
        String semester = "Fall 2025";
        EnrollmentStatus status = EnrollmentStatus.ENROLLED;
        Map<GradeType, Double> grades = new EnumMap<>(GradeType.class);
        grades.put(GradeType.ASSIGNMENT, 85.5);
        BitSet attendance = new BitSet();
        attendance.set(0); // Present on first day
        attendance.set(2); // Present on third day

        // When
        Enrollment enrollment = new Enrollment(studentId, courseCode, semester, status, grades, attendance);

        // Then
        assertThat(enrollment.getStudentId()).isEqualTo(studentId);
        assertThat(enrollment.getCourseCode()).isEqualTo(courseCode);
        assertThat(enrollment.getSemester()).isEqualTo(semester);
        assertThat(enrollment.getStatus()).isEqualTo(status);
        assertThat(enrollment.getGrades()).isEqualTo(grades);
        assertThat(enrollment.getAttendance()).isEqualTo(attendance);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Enrollment enrollment = new Enrollment();
        String studentId = "S12345";
        String courseCode = "CS101";
        String semester = "Fall 2025";
        EnrollmentStatus status = EnrollmentStatus.ENROLLED;
        Map<GradeType, Double> grades = new EnumMap<>(GradeType.class);
        grades.put(GradeType.ASSIGNMENT, 85.5);
        BitSet attendance = new BitSet();
        attendance.set(0); // Present on first day
        attendance.set(2); // Present on third day

        // When
        enrollment.setStudentId(studentId);
        enrollment.setCourseCode(courseCode);
        enrollment.setSemester(semester);
        enrollment.setStatus(status);
        enrollment.setGrades(grades);
        enrollment.setAttendance(attendance);

        // Then
        assertThat(enrollment.getStudentId()).isEqualTo(studentId);
        assertThat(enrollment.getCourseCode()).isEqualTo(courseCode);
        assertThat(enrollment.getSemester()).isEqualTo(semester);
        assertThat(enrollment.getStatus()).isEqualTo(status);
        assertThat(enrollment.getGrades()).isEqualTo(grades);
        assertThat(enrollment.getAttendance()).isEqualTo(attendance);
    }

    @Test
    void testGradesCollection() {
        // Given
        Enrollment enrollment = new Enrollment();
        Map<GradeType, Double> grades = enrollment.getGrades();

        // When
        grades.put(GradeType.ASSIGNMENT, 85.5);
        grades.put(GradeType.QUIZ, 90.0);
        grades.put(GradeType.MIDTERM, 78.5);

        // Then
        assertThat(enrollment.getGrades()).hasSize(3);
        assertThat(enrollment.getGrades()).containsEntry(GradeType.ASSIGNMENT, 85.5);
        assertThat(enrollment.getGrades()).containsEntry(GradeType.QUIZ, 90.0);
        assertThat(enrollment.getGrades()).containsEntry(GradeType.MIDTERM, 78.5);
    }

    @Test
    void testAttendanceBitSet() {
        // Given
        Enrollment enrollment = new Enrollment();
        BitSet attendance = enrollment.getAttendance();

        // When
        attendance.set(0); // Present on first day
        attendance.set(2); // Present on third day
        attendance.set(5); // Present on sixth day

        // Then
        assertThat(enrollment.getAttendance().get(0)).isTrue();
        assertThat(enrollment.getAttendance().get(1)).isFalse();
        assertThat(enrollment.getAttendance().get(2)).isTrue();
        assertThat(enrollment.getAttendance().get(3)).isFalse();
        assertThat(enrollment.getAttendance().get(4)).isFalse();
        assertThat(enrollment.getAttendance().get(5)).isTrue();
        
        // Check cardinality (number of bits set to true)
        assertThat(enrollment.getAttendance().cardinality()).isEqualTo(3);
    }
}