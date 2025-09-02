package model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * Represents a student in the academic system.
 *
 * <p>This class holds the student's identity and contact information,
 * along with their enrolled courses and additional attributes.</p>
 *
 * <p>Uses Lombok annotations {@link Getter} and {@link Setter} to
 * automatically generate boilerplate accessor methods.</p>
 */

@Getter
@Setter
public class Student {

    /** Unique identifier for the student (e.g., UUID or institutional ID). */
    private String id;

    /** Full legal name of the student. */
    private String fullName;

    /** Email address of the student (used for communication). */
    private String email;

    /** Phone number of the student. */
    private String phone;

    /**
     * The set of courses in which the student is enrolled.
     *
     * <p>Implemented as a {@link LinkedHashSet} to:</p>
     * <ul>
     *   <li>Preserve insertion order (useful for chronological enrollment records).</li>
     *   <li>Avoid duplicates (a student cannot enroll in the same course twice).</li>
     * </ul>
     */
    private Set<Enrollment> enrolledCourses = new LinkedHashSet<>();

    /**
     * A flexible map of additional attributes for the student.
     *
     * <p>Allows storing custom metadata (e.g., "nationality", "guardianName")
     * without modifying the core model. Keys and values are both Strings.</p>
     */
    private Map<String, String> attributes = new HashMap<>(); // for flexible key/value profile fields.
}
