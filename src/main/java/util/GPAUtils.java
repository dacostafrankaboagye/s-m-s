package util;

/**
 * Utility class for GPA calculation and grade conversion.
 *
 * This class provides:
 * - Mapping of numeric grades or letter grades to GPA points.
 * - Common GPA scale (4.0).
 */
public final class GPAUtils {

    private GPAUtils(){}

    /**
     * Converts a numeric grade (0-100) to GPA on a 4.0 scale.
     *
     * @param numericGrade the numeric grade (0-100)
     * @return GPA value (0.0 - 4.0)
     */
    public static double numericToGpa(double numericGrade) {
        if (numericGrade >= 90) return 4.0;
        if (numericGrade >= 80) return 3.0;
        if (numericGrade >= 70) return 2.0;
        if (numericGrade >= 60) return 1.0;
        return 0.0;
    }
    /**
     * Converts a letter grade to GPA points.
     *
     * @param letterGrade the letter grade (A, B, C, D, F)
     * @return GPA value (0.0 - 4.0)
     */
    public static double letterToGpa(String letterGrade) {
        return switch (letterGrade.toUpperCase()) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0; // F or invalid
        };
    }
    
    /**
     * Converts a stored grade value (could be numeric or letter) to GPA points.
     * If the grade is numeric, it applies numeric conversion.
     * If the grade is a letter (A, B, C, D, F), it applies letter conversion.
     *
     * @param grade the grade string
     * @return GPA value
     */
    public static double gradeToPoints(String grade) {
        try {
            // Try numeric first
            double numeric = Double.parseDouble(grade);
            return numericToGpa(numeric);
        } catch (NumberFormatException e) {
            // Fallback to letter-based
            return letterToGpa(grade);
        }
    }
}
