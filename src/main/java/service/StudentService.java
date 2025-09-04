package service;

import model.Student;

import java.util.List;
import java.util.Map;

/**
 * Service layer for managing student operations.
 *
 * Responsibilities:
 * - Abstracts repository layer for business logic.
 * - Provides validation and higher-level operations.
 * - Handles attribute updates and searches.
 */
public interface StudentService {


    /**
     * Registers a new student in the system.
     *
     * @param student the Student object to register.
     */
    void registerStudent(Student student);

    /**
     * Retrieves a student by their unique ID.
     *
     * @param studentId the student's ID.
     * @return the Student object, or null if not found.
     */
    Student getStudentById(String studentId);

    /**
     * Searches for students by name token (case-insensitive).
     *
     * @param token partial or full name token.
     * @return list of matching students.
     */
    List<Student> searchStudentsByName(String token);

    /**
     * Updates a student's contact details.
     *
     * @param studentId the ID of the student.
     * @param email new email (optional).
     * @param phone new phone (optional).
     */
    void updateContact(String studentId, String email, String phone);

    /**
     * Adds or updates custom attributes for a student.
     *
     * @param studentId the student's ID.
     * @param attributes key-value pairs to add/update.
     */
    void updateAttributes(String studentId, Map<String, String> attributes);

    /**
     * Deletes a student from the system.
     *
     * @param studentId the student's ID.
     */
    void deleteStudent(String studentId);
}
