package repository;


import model.Enrollment;

import java.util.List;

/**
 * Repository interface for managing enrollments of students in courses.
 *
 * Responsibilities:
 * - Enroll students into courses.
 * - Drop students from courses.
 * - Retrieve enrollments by student or course.
 */
public interface EnrollmentRepository {

    /**
     * Enrolls a student in a course for a given semester.
     *
     * @param studentId the unique ID of the student.
     * @param courseCode the unique code of the course.
     * @param semester the semester identifier (e.g., "Fall 2025").
     */
    void enroll(String studentId, String courseCode, String semester );

    /**
     * Drops a student from a course for a given semester.
     *
     * @param studentId the unique ID of the student.
     * @param courseCode the unique code of the course.
     * @param semester the semester identifier.
     */
    void drop(String studentId, String courseCode, String semester );

    /**
     * Retrieves all enrollments for a given student.
     *
     * @param studentId the unique ID of the student.
     * @return a list of Enrollment objects for that student, or an empty list if none found.
     */
    List<Enrollment> getEnrollmentsForStudent(String studentId);

    /**
     * Retrieves all student IDs enrolled in a specific course.
     *
     * @param courseCode the course code.
     * @return a list of student IDs enrolled in the course, or an empty list if none found.
     */
    List<String> getStudentsForCourse(String courseCode);


}
