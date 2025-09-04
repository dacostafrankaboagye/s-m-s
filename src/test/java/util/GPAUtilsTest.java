package util;

import model.Enrollment;
import model.EnrollmentStatus;
import model.GradeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GPAUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "95.0, 4.0",
        "90.0, 4.0",
        "89.9, 3.0",
        "85.0, 3.0",
        "80.0, 3.0",
        "79.9, 2.0",
        "75.0, 2.0",
        "70.0, 2.0",
        "69.9, 1.0",
        "65.0, 1.0",
        "60.0, 1.0",
        "59.9, 0.0",
        "50.0, 0.0",
        "0.0, 0.0"
    })
    void numericToGpa_shouldReturnCorrectGpaForGrade(double numericGrade, double expectedGpa) {
        assertThat(GPAUtils.numericToGpa(numericGrade)).isEqualTo(expectedGpa);
    }

    @Test
    void computeCourseGpa_shouldReturnZeroForIncompleteEnrollment() {
        Enrollment enrollment = new Enrollment("S1", "CS101", "Fall2023", EnrollmentStatus.ENROLLED, new EnumMap<>(GradeType.class), new BitSet());
        
        assertThat(GPAUtils.computeCourseGpa(enrollment)).isEqualTo(0.0);
    }

    @Test
    void computeCourseGpa_shouldReturnZeroForEmptyGrades() {
        Enrollment enrollment = new Enrollment("S1", "CS101", "Fall2023", EnrollmentStatus.ENROLLED, new EnumMap<>(GradeType.class), new BitSet());
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        
        assertThat(GPAUtils.computeCourseGpa(enrollment)).isEqualTo(0.0);
    }

    @Test
    void computeCourseGpa_shouldCalculateCorrectGpaForSingleGrade() {
        Enrollment enrollment = new Enrollment("S1", "CS101", "Fall2023", EnrollmentStatus.ENROLLED, new EnumMap<>(GradeType.class), new BitSet());
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        
        Map<GradeType, Double> grades = new EnumMap<>(GradeType.class);
        grades.put(GradeType.FINAL, 85.0);
        enrollment.setGrades(grades);
        
        assertThat(GPAUtils.computeCourseGpa(enrollment)).isEqualTo(3.0);
    }

    @Test
    void computeCourseGpa_shouldCalculateCorrectGpaForMultipleGrades() {
        Enrollment enrollment = new Enrollment("S1", "CS101", "Fall2023", EnrollmentStatus.ENROLLED, new EnumMap<>(GradeType.class), new BitSet());
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        
        Map<GradeType, Double> grades = new EnumMap<>(GradeType.class);
        grades.put(GradeType.MIDTERM, 80.0);
        grades.put(GradeType.FINAL, 90.0);
        grades.put(GradeType.ASSIGNMENT, 85.0);
        enrollment.setGrades(grades);
        
        // Average: (80 + 90 + 85) / 3 = 85.0 -> 3.0 GPA
        assertThat(GPAUtils.computeCourseGpa(enrollment)).isEqualTo(3.0);
    }
}