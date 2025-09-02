package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents an instructor within the academic system.
 *
 * <p>An instructor has a unique identifier, a name, and a set of courses they teach.
 * Courses are stored as codes (e.g., "CS101").</p>
 *
 * <p>Data structure choice:
 * <ul>
 *   <li>{@link HashSet} for courses → ensures uniqueness and provides O(1) lookup for membership checks.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    /** Unique identifier for the instructor (e.g., employee ID). */
    private String id;

    /** Full name of the instructor. */
    private String name;

    /**
     * The set of course codes taught by this instructor.
     *
     * <p>Implemented as a {@link HashSet} to avoid duplicates and allow fast membership checks.</p>
     */
    private Set<String> coursesTaught = new HashSet<>();
}
