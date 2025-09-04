package repository;

import model.Instructor;

import java.util.List;

/**
 * Repository interface for managing Instructor entities.
 *
 * Responsibilities:
 * - Create and store instructor objects.
 * - Retrieve instructors by ID.
 * - Search instructors by name.
 */
public interface InstructorRepository {

    /**
     * Repository interface for managing Instructor entities.
     *
     * Responsibilities:
     * - Create and store instructor objects.
     * - Retrieve instructors by ID.
     * - Search instructors by name.
     */
    void createInstructor(Instructor instructor);

    /**
     * Retrieves an instructor by their unique ID.
     *
     * @param id the instructor's ID
     * @return the Instructor object, or null if not found
     */
    Instructor getById(String id);

    /**
     * Searches for instructors by name token (case-insensitive).
     *
     * @param token part of the name to search for
     * @return a list of matching instructors
     */
    List<Instructor> searchByNameToken(String token);
}
