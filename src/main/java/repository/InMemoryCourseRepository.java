package repository;

import model.Course;

import java.util.*;

/**
 * In-memory implementation of the CourseRepository interface.
 *
 * Responsibilities:
 * - Store Course objects by their unique course code.
 * - Maintain a mapping from department IDs to the set of course codes offered by that department.
 * - Provide fast lookups by course code.
 * - Enable listing of courses by department in alphabetical order.
 *
 * Data Structures:
 * - coursesByCode: HashMap for O(1) course retrieval by code.
 * - deptToCourseCodes: HashMap (departmentId -> set of course codes),
 *   where each set is a TreeSet for automatic alphabetical ordering of course codes.
 */

public class InMemoryCourseRepository implements CourseRepository{

    /**
     * Stores courses by their unique code.
     * Key: course code (e.g., "CS101"), Value: Course object.
     */
    private final Map<String, Course> coursesByCode = new HashMap<>(); // {code -> course}

    /**
     * Maps department IDs to sets of course codes offered by that department.
     * Each set is a TreeSet to maintain alphabetical order of course codes.
     * Example: "CS" -> { "CS101", "CS102", "CS201" }
     */
    private final Map<String, Set<String>> deptToCourseCodes = new HashMap<>();



    /**
     * Creates a new course and stores it in the repository.
     * Also updates the department-to-course mapping.
     *
     * @param course the Course object to add.
     * @throws IllegalArgumentException if a course with the same code already exists.
     */
    @Override
    public synchronized void createCourse(Course course) {
        if(course == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        String code = course.getCode();
        String departmentId = course.getDepartment();

        if(coursesByCode.containsKey(code)){
            throw new IllegalArgumentException("Course with code " + code + " already exists");
        }

        coursesByCode.put(code, course);

        deptToCourseCodes
                .computeIfAbsent(departmentId, k -> new TreeSet<>())
                .add(code);


    }


    /**
     * Retrieves a course by its unique code.
     *
     * @param code the course code (e.g., "CS101").
     * @return the Course object, or null if not found.
     */
    @Override
    public Course getByCode(String code) {
        return coursesByCode.get(code);
    }


    /**
     * Lists all courses offered by a specific department.
     * The result is sorted alphabetically by course code.
     *
     * @param department the department ID (e.g., "CS").
     * @return a list of courses in alphabetical order, empty if none found.
     */
    @Override
    public List<Course> listByDepartment(String department) {
        List<Course> result = new ArrayList<>();
        Set<String> courseCodes  = deptToCourseCodes.getOrDefault(department, Collections.emptySet());

        for(String code : courseCodes ){
            Course course = coursesByCode.get(code);
            if(course != null){
                result.add(course);
            }
        }
        return result;

    }

    /**
     * Deletes a course by its unique code and removes all associated mappings.
     *
     * @param code the course code.
     */
    @Override
    public synchronized void deleteCourse(String code) {

        Course removedCourse = coursesByCode.remove(code);
        if (removedCourse == null) return;

        String departmentId = removedCourse.getDepartment();


        Set<String> courseCodes = deptToCourseCodes.get(departmentId);
        if (courseCodes != null) {
            courseCodes.remove(code);
            if (courseCodes.isEmpty()) {
                deptToCourseCodes.remove(departmentId);
            }
        }

    }
}
