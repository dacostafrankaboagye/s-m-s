package service;

import lombok.AllArgsConstructor;
import model.Course;
import repository.CourseRepository;

import java.util.List;

/**
 * Implementation of CourseService.
 *
 * Responsibilities:
 * - Delegates persistence to the CourseRepository.
 * - Adds business validations.
 */
@AllArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public void createCourse(Course course) {
        if (course == null || course.getCode() == null || course.getDepartment() == null) {
            throw new IllegalArgumentException("Course code and department cannot be null");
        }
        courseRepository.createCourse(course);

    }

    @Override
    public Course getCourseByCode(String code) {
        return courseRepository.getByCode(code);
    }

    @Override
    public List<Course> listCoursesByDepartment(String department) {
        return courseRepository.listByDepartment(department);
    }

    @Override
    public void deleteCourse(String code) {
        courseRepository.deleteCourse(code);
    }
}
