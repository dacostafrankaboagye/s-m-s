package repository;

import lombok.NonNull;
import model.Enrollment;
import model.EnrollmentStatus;
import model.GradeType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static model.EnrollmentStatus.DROPPED;
import static model.EnrollmentStatus.ENROLLED;

/**
 * In-memory implementation of the EnrollmentRepository interface.
 *
 * Responsibilities:
 * - Maintain student enrollments in memory using thread-safe collections.
 * - Provide quick lookups by student and by course.
 *
 * Data Structures:
 * - enrollmentsByStudent: ConcurrentHashMap mapping studentId -> CopyOnWriteArrayList of enrollments.
 *   Reason:
 *     - Frequent reads and occasional writes.
 *     - CopyOnWriteArrayList is good for read-heavy scenarios.
 *
 * - studentsByCourse: ConcurrentHashMap mapping courseCode -> Concurrent Set of student IDs.
 *   Reason:
 *     - Thread-safe.
 *     - O(1) membership checks.
 *     - Easy to add/remove student IDs when enrolling/dropping.
 */
public class InMemoryEnrollmentRepository implements EnrollmentRepository{

    /**
     * Maps student IDs to their enrollments.
     * Key: studentId, Value: list of Enrollment objects.
     * Using CopyOnWriteArrayList for safe iteration during concurrent reads.
     */
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Enrollment>> enrollmentsByStudent = new ConcurrentHashMap<>();

    /**
     * Maps course codes to sets of enrolled student IDs.
     * Key: courseCode, Value: thread-safe set of student IDs.
     */
    private final ConcurrentHashMap<String, Set<String>> studentsByCourse = new ConcurrentHashMap<>();

    @Override
    public void enroll(@NonNull String studentId, @NonNull String courseCode, @NonNull String semester) {

        Enrollment enrollment = new Enrollment(
                studentId,
                courseCode,
                semester,
                ENROLLED,
                new EnumMap<>(GradeType.class),
                new BitSet()
        );

        enrollmentsByStudent
                .computeIfAbsent(studentId, k-> new CopyOnWriteArrayList<>())
                .add(enrollment);

        studentsByCourse
                .computeIfAbsent(courseCode, k-> ConcurrentHashMap.newKeySet())
                .add(studentId);

    }

    @Override
    public void drop(@NonNull String studentId, @NonNull String courseCode, @NonNull String semester) {
        CopyOnWriteArrayList<Enrollment> enrollments = enrollmentsByStudent.get(studentId);
        if(enrollments != null){
            for(Enrollment e : enrollments){
                if(e.getCourseCode().equals(courseCode) && e.getSemester().equals(semester)){
                    e.setStatus(DROPPED);
                }
            }
        }

        Set<String> studentSet = studentsByCourse.get(courseCode);
        if (studentSet != null) {
            studentSet.remove(studentId);
        }


    }

    @Override
    public List<Enrollment> getEnrollmentsForStudent(@NonNull String studentId) {
        return enrollmentsByStudent.getOrDefault(studentId, new CopyOnWriteArrayList<>());
    }

    @Override
    public List<String> getStudentsForCourse(String courseCode) {
        Set<String> students  = studentsByCourse.getOrDefault(courseCode, ConcurrentHashMap.newKeySet());
        return new ArrayList<>(students);

    }
}
