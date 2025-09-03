package repository;

import model.Course;

import java.util.List;

/**
 * Repository interface for managing Course entities.
 *
 * Responsibilities:
 * - Add and store Course objects.
 * - Retrieve courses by code.
 * - List courses by department.
 * - Delete courses.
 */
public interface CourseRepository {

    /**
     * Creates a new course and stores it in the repository.
     *
     * @param course the Course object to add.
     * @throws IllegalArgumentException if a course with the same code already exists.
     */
    void createCourse(Course course);

    /**
     * Retrieves a course by its unique code.
     *
     * @param code the course code (e.g., "CS101").
     * @return the Course object, or null if not found.
     */
    Course getByCode(String code);


    /**
     * Lists all courses offered by a specific department.
     *
     * @param department the department name or code.
     * @return a list of courses belonging to the given department, empty if none found.
     */
    List<Course> listByDepartment(String department);


    /**
     * Deletes a course by its unique code.
     *
     * @param code the course code.
     */
    void deleteCourse(String code);
}
