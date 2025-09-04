package service;

import lombok.AllArgsConstructor;
import model.Enrollment;
import repository.EnrollmentRepository;

import java.util.List;

/**
 * Implementation of EnrollmentService.
 *
 * Responsibilities:
 * - Delegates operations to the EnrollmentRepository.
 * - Adds validation and business logic (e.g., preventing double enrollment).
 */
@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService{

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void enrollStudent(String studentId, String courseCode, String semester) {
        if (studentId == null || courseCode == null || semester == null) {
            throw new IllegalArgumentException("Student ID, Course Code, and Semester cannot be null");
        }
        enrollmentRepository.enroll(studentId, courseCode, semester);

    }

    @Override
    public void dropStudent(String studentId, String courseCode, String semester) {
        if (studentId == null || courseCode == null || semester == null) {
            throw new IllegalArgumentException("Student ID, Course Code, and Semester cannot be null");
        }
        enrollmentRepository.drop(studentId, courseCode, semester);

    }

    @Override
    public List<Enrollment> getEnrollmentsForStudent(String studentId) {
        return enrollmentRepository.getEnrollmentsForStudent(studentId);
    }

    @Override
    public List<String> getStudentsForCourse(String courseCode) {
        return enrollmentRepository.getStudentsForCourse(courseCode);
    }
}
