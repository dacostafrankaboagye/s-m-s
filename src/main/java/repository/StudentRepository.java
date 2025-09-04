package repository;

import model.Student;

import java.util.List;
import java.util.Map;

/**
 * Repository interface for managing Student entities.
 *
 * Responsibilities:
 * - Add and store Student objects.
 * - Retrieve students by ID.
 * - Search students by name token.
 * - Delete students and clean up indexes.
 */
public interface StudentRepository {

    /**
     * Creates a new student and stores it in the repository.
     *
     * @param student the Student object to add.
     * @throws IllegalArgumentException if a student with the same ID or email already exists.
     */
    void createStudent(Student student);

    /**
     * Retrieves a student by their unique ID.
     *
     * @param id the student ID.
     * @return the Student object, or null if not found.
     */
    Student getById(String id);

    /**
     * Searches for students whose names contain the given token.
     *
     * @param token the name token to search for (case-insensitive).
     * @return a list of matching students, or an empty list if none found.
     */
    List<Student> searchByNameToken(String token);

    /**
     * Deletes a student by their ID and removes all associated indexes.
     *
     * @param id the student ID.
     */
    void deleteStudent(String id);
}
