package model;

import lombok.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * Represents an academic department in the institution.
 *
 * <p>A department is identified by an ID and name,
 * and it maintains a sorted collection of courses it offers.</p>
 *
 * <p>Data structure choice:
 * <ul>
 *   <li>{@link TreeSet} for courses → ensures automatic alphabetical order
 *       (by course code or title depending on what is stored).</li>
 * </ul>
 * </p>
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    /** Unique identifier for the department (e.g., "CS", "MATH"). */
    private String id;

    /** Human-readable department name (e.g., "Computer Science"). */
    private String name;

    /**
     * The set of course codes offered by this department.
     *
     * <p>Stored as a {@link TreeSet} to maintain alphabetical order automatically.</p>
     */
    private Set<String> courses = new TreeSet<>();
}
