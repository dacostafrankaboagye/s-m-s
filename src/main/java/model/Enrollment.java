package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    /** Unique identifier of the student. */
    private String studentId;

    /** Code identifying the course (e.g., "CS101"). */
    private String courseCode;

    /** Semester identifier (e.g., "Fall 2025"). */
    private String semester;

    /** Current enrollment status of the student. */
    private EnrollmentStatus status;

    /**
     * Grades mapped by grade type (Assignment, Quiz, Midterm, etc.).
     *
     * <p>Uses {@link EnumMap} for efficiency when {@link GradeType} enums are used as keys.
     */
    private Map<GradeType, Double> grades = new EnumMap<>(GradeType.class);

    /**
     * Attendance record for the course, where each bit represents a session day:
     * <ul>
     *   <li>1 = present</li>
     *   <li>0 = absent</li>
     * </ul>
     *
     * <p>Example: if 10 sessions have been held, then bits [0..9] indicate presence/absence
     * across those days. This structure is compact and supports fast bitwise operations.</p>
     */
    private BitSet attendance = new BitSet(); // presence/absence tracking

}
