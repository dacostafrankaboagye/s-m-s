package util;

import model.Enrollment;
import model.EnrollmentStatus;
import model.GradeType;

import java.util.Map;

/**
 * Utility class for GPA calculation and grade conversion.
 *
 * This class provides:
 * - Mapping of numeric grades or letter grades to GPA points.
 * - Common GPA scale (4.0).
 */
public final class GPAUtils {

    private GPAUtils() {}

    /**
     * Converts a numeric grade (0-100) to GPA on a 4.0 scale.
     */
    public static double numericToGpa(double numericGrade) {
        if (numericGrade >= 90) return 4.0;
        if (numericGrade >= 80) return 3.0;
        if (numericGrade >= 70) return 2.0;
        if (numericGrade >= 60) return 1.0;
        return 0.0;
    }

    /**
     * Computes GPA for a single course enrollment.
     * Assumes grades are numeric values.
     *
     * @param enrollment the enrollment record
     * @return GPA points for this course, or 0 if not completed
     */
    public static double computeCourseGpa(Enrollment enrollment) {
        if (enrollment.getStatus() != EnrollmentStatus.COMPLETED) {
            return 0.0; // Ignore incomplete courses
        }

        Map<GradeType, Double> grades = enrollment.getGrades();
        if (grades.isEmpty()) return 0.0;

        double total = 0;
        for (Double score : grades.values()) {
            total += score;
        }
        double average = total / grades.size();
        return numericToGpa(average);
    }
}
