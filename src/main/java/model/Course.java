package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a course offered in the academic system.
 *
 * <p>Each course has a unique identifier (code), a descriptive title,
 * a credit value, and an associated department. Prerequisite
 * courses and scheduled time slots.</p>
 *
 * <p>Data structures are carefully chosen for efficiency:</p>
 * <ul>
 *   <li>{@link HashSet} for prerequisites → fast membership checks.</li>
 *   <li>{@link TreeSet} for scheduled slots → automatically keeps slots sorted.</li>
 *   <li>{@link LinkedHashSet} as an alternative if preserving insertion order
 *       is more important than sorting.</li>
 * </ul>
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /** Unique course code (e.g., "CS101"). */
    private String code;

    /** Title of the course (e.g., "Introduction to Computer Science"). */
    private String title;

    /** Number of credit hours assigned to this course. */
    private int credits;

    /** The academic department offering the course (e.g., "Computer Science"). */
    private String department;


    /**
     * Set of prerequisite course codes.
     *
     * <p>Implemented as a {@link HashSet} to allow O(1) membership checks,
     * e.g., quickly verifying if a student has completed a required course.</p>
     */
    private Set<String> prerequisites = new HashSet<>();

    /**
     * Scheduled time slots for the course.
     *
     * <p>Default choice: {@link TreeSet} (ensures natural ordering of {@link TimeSlot}).
     * But if say, insertion order is relevant, try and replace with {@link LinkedHashSet}. 😊</p>
     */
    private Set<TimeSlot> scheduledSlots = new TreeSet<>();
}
