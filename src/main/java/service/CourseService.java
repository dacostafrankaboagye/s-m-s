package service;

import model.Course;

import java.util.List;

/**
 * Service interface for managing courses.
 *
 * Responsibilities:
 * - Abstracts repository layer for business logic.
 * - Provides operations for creating, retrieving, and listing courses.
 */
public interface CourseService {
    /**
     * Creates a new course.
     *
     * @param course the Course object to add.
     */
    void createCourse(Course course);

    /**
     * Retrieves a course by its code.
     *
     * @param code the course code (e.g., "CS101").
     * @return the Course object, or null if not found.
     */
    Course getCourseByCode(String code);

    /**
     * Lists all courses in a department.
     *
     * @param department the department ID (e.g., "CS").
     * @return list of courses sorted by code.
     */
    List<Course> listCoursesByDepartment(String department);

    /**
     * Deletes a course by its code.
     *
     * @param code the course code.
     */
    void deleteCourse(String code);
}
