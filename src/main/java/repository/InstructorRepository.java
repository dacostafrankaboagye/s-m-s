package repository;

import model.Instructor;

import java.util.List;

/**
 * Repository interface for managing Instructor entities.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Create and store new instructors.</li>
 *   <li>Retrieve instructors by ID.</li>
 *   <li>Search instructors by name token (partial name match).</li>
 *   <li>Delete instructors by ID.</li>
 * </ul>
 *
 * <p>This abstraction allows switching between in-memory, database-backed,
 * or distributed implementations without affecting the service layer.</p>
 */
public interface InstructorRepository {

    /**
     * Creates a new instructor and stores it in the repository.
     *
     * @param instructor the Instructor object to store (must not be null).
     * @throws IllegalArgumentException if an instructor with the same ID already exists.
     */
    void createInstructor(Instructor instructor);

    /**
     * Retrieves an instructor by their unique ID.
     *
     * @param id the instructor's unique identifier.
     * @return the Instructor object, or null if not found.
     */
    Instructor getById(String id);

    /**
     * Searches for instructors whose names contain the given token.
     * The search should be case-insensitive and use a pre-built inverted index if possible.
     *
     * @param token the name token to search for (e.g., "john").
     * @return a list of matching Instructor objects, empty if none found.
     */
    List<Instructor> searchByNameToken(String token);

    /**
     * Deletes an instructor by their unique ID and removes all associated indexes.
     *
     * @param id the instructor's unique identifier.
     */
    void deleteInstructor(String id);
}
