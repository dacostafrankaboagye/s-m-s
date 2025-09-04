package repository;

import lombok.NonNull;
import model.Instructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static util.StringUtils.tokenize;

/**
 * In-memory implementation of the {@link InstructorRepository} interface.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Store Instructor objects using thread-safe collections.</li>
 *   <li>Provide quick lookups by instructor ID.</li>
 *   <li>Support name-based search using an inverted index for tokens.</li>
 *   <li>Maintain a reverse mapping from course codes to instructor IDs for quick association checks.</li>
 * </ul>
 *
 * <p>Data Structures:
 * <ul>
 *   <li>{@link ConcurrentHashMap} for thread-safe ID-based storage.</li>
 *   <li>{@link HashMap} with {@link HashSet} for name token index (inverted index).</li>
 *   <li>{@link HashMap} with {@link HashSet} for course-to-instructors mapping.</li>
 * </ul>
 * </p>
 */
public class InMemoryInstructorRepository implements InstructorRepository{

    /**
     * Stores instructors by their unique ID.
     * Thread-safe for concurrent read/write operations.
     */
    private final ConcurrentHashMap<String, Instructor> instructorsById = new ConcurrentHashMap<>();

    /**
     * Inverted index mapping lowercase name tokens to sets of instructor IDs.
     * Example: "john" -> { "id1", "id2" }
     */
    private final Map<String, Set<String>> nameTokenIndex = new HashMap<>();

    /**
     * Reverse mapping from course codes to sets of instructor IDs teaching that course.
     * Example: "CS101" -> { "instructor1", "instructor2" }
     */
    private final Map<String, Set<String>> courseToInstructorIds = new HashMap<>();

    /**
     * Creates a new instructor and updates all indexes.
     *
     * @param instructor the Instructor object to store (must not be null).
     * @throws IllegalArgumentException if an instructor with the same ID already exists.
     */
    @Override
    public synchronized void createInstructor(@NonNull Instructor instructor) {
        String id = instructor.getId();
        if (id == null || instructorsById.containsKey(id)) {
            throw new IllegalArgumentException("Instructor with this ID already exists or is invalid");
        }

        instructorsById.put(id, instructor);

        for (String token : tokenize(instructor.getName())) {
            nameTokenIndex
                    .computeIfAbsent(token.toLowerCase(), k -> new HashSet<>())
                    .add(id);
        }
        for (String courseCode : instructor.getCoursesTaught()) {
            courseToInstructorIds
                    .computeIfAbsent(courseCode, k -> new HashSet<>())
                    .add(id);
        }

    }

    @Override
    public Instructor getById(String id) {
        return instructorsById.get(id);
    }

    @Override
    public List<Instructor> searchByNameToken(String token) {
        Set<String> instructorIds = nameTokenIndex.getOrDefault(token.toLowerCase(), Collections.emptySet());
        List<Instructor> result = new ArrayList<>();
        for (String id : instructorIds) {
            Instructor instructor = instructorsById.get(id);
            if (instructor != null) {
                result.add(instructor);
            }
        }
        return result;
    }

    @Override
    public synchronized void deleteInstructor(String id) {
        Instructor instructor = instructorsById.remove(id);
        if (instructor == null) return;

        for (String token : tokenize(instructor.getName())) {
            Set<String> ids = nameTokenIndex.get(token.toLowerCase());
            if (ids != null) {
                ids.remove(id);
                if (ids.isEmpty()) {
                    nameTokenIndex.remove(token.toLowerCase());
                }
            }
        }
        for (String courseCode : instructor.getCoursesTaught()) {
            Set<String> ids = courseToInstructorIds.get(courseCode);
            if (ids != null) {
                ids.remove(id);
                if (ids.isEmpty()) {
                    courseToInstructorIds.remove(courseCode);
                }
            }
        }

    }
}
