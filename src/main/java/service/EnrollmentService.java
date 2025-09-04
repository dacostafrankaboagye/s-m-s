package service;

import model.Enrollment;

import java.util.List;

/**
 * Service interface for managing student enrollments in courses.
 *
 * Responsibilities:
 * - Abstracts enrollment operations and validation logic.
 * - Delegates persistence to the repository layer.
 */
public interface EnrollmentService {

    /**
     * Enrolls a student in a course for a given semester.
     *
     * @param studentId the student's ID.
     * @param courseCode the course code.
     * @param semester the semester identifier (e.g., "Fall 2025").
     */
    void enrollStudent(String studentId, String courseCode, String semester);

    /**
     * Drops a student from a course.
     *
     * @param studentId the student's ID.
     * @param courseCode the course code.
     * @param semester the semester identifier.
     */
    void dropStudent(String studentId, String courseCode, String semester);

    /**
     * Retrieves all enrollments for a student.
     *
     * @param studentId the student's ID.
     * @return list of Enrollment objects.
     */
    List<Enrollment> getEnrollmentsForStudent(String studentId);

    /**
     * Retrieves all students enrolled in a given course.
     *
     * @param courseCode the course code.
     * @return list of student IDs.
     */
    List<String> getStudentsForCourse(String courseCode);
}
