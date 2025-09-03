package repository;

import model.Student;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the StudentRepository interface.
 *
 * Responsibilities:
 * - Store Student objects using a thread-safe map.
 * - Provide fast lookups by ID.
 * - Provide name-based search using an inverted index.
 * - Maintain unique email constraint.
 *
 * Data Structures:
 * - studentsById: ConcurrentHashMap for thread-safe student storage.
 * - nameTokenIndex: HashMap (token -> set of student IDs) for name-based search.
 * - emailToId: HashMap for quick email-to-ID lookup.
 */
public class InMemoryStudentRepository implements StudentRepository{

    /**
     * Stores students by their unique ID.
     * Thread-safe because of concurrent access in multi-threaded environments.
     */
    private final ConcurrentHashMap<String, Student> studentsById = new ConcurrentHashMap<>();

    /**
     * Inverted index mapping lowercase name tokens to sets of student IDs.
     * Example: "john" -> { "id1", "id2" }
     */
    private final Map<String, String> emailToId = new HashMap<>();

    /**
     * Maps email addresses to student IDs for uniqueness checks and lookups.
     */
    private final Map<String, Set<String>> nameTokenIndex = new HashMap<>();


    /**
     * Creates a new student and updates all indexes.
     *
     * @param student the Student object to store.
     * @throws IllegalArgumentException if a student with the same ID or email already exists.
     */
    @Override
    public synchronized void createStudent(Student student) {
        if(student == null){ throw new IllegalArgumentException("Student cannot be null");}

        String id = student.getId();
        String email = student.getEmail();

        if (id == null || email == null) {
            throw new IllegalArgumentException("Student, id, email cannot be null");
        }

        if(studentsById.containsKey(id) || emailToId.containsKey(email)){
            throw new IllegalArgumentException("Student with this id or email already exists");
        }

        studentsById.put(id, student);
        emailToId.put(email, id);
        for(String token : tokenize(student.getFullName())){
            nameTokenIndex
                    .computeIfAbsent(token.toLowerCase(), k -> new HashSet<>())
                    .add(id);
        }


    }



    /**
     * Retrieves a student by their unique ID.
     *
     * @param id the student's unique identifier.
     * @return the Student object, or null if not found.
     */
    @Override
    public Student getById(String id) {
        return studentsById.get(id);
    }


    /**
     * Searches for students whose names contain the given token.
     * Case-insensitive search using the inverted index.
     *
     * @param token the name token to search for.
     * @return a list of matching Student objects, empty if none found.
     */
    @Override
    public List<Student> searchByNameToken(String token) {
        Set<String> studentIds = nameTokenIndex.getOrDefault(token.toLowerCase(), Collections.emptySet());
        List<Student> result = new ArrayList<>();
        for(String id : studentIds){
            Student student = studentsById.get(id);
            if(student != null){
                result.add(student);
            }
        }
        return result;
    }


    /**
     * Deletes a student by their ID and removes all associated indexes.
     *
     * @param id the student's unique identifier.
     */
    @Override
    public synchronized void deleteStudent(String id) {

        Student student = studentsById.remove(id);
        if(student == null) return;

        emailToId.remove(student.getEmail());

        for(String token : tokenize(student.getFullName())){
            Set<String> ids = nameTokenIndex.get(token.toLowerCase());
            if(ids != null){
                ids.remove(id);
                if(ids.isEmpty()){
                    nameTokenIndex.remove(token.toLowerCase());
                }
            }
        }
    }

    /**
     * Splits the full name into tokens based on whitespace.
     * Example: "John Doe" -> ["John", "Doe"]
     *
     * @param fullName the full name of the student.
     * @return a list of name tokens.
     */
    private List<String> tokenize(String fullName) {
        return Arrays.asList(fullName.split("\\s+"));
    }
}
