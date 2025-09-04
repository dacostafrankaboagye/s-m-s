package repository;

import lombok.NonNull;
import model.Department;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of the DepartmentRepository interface.
 *
 * Responsibilities:
 * - Store Department objects in memory.
 * - Provide quick lookups by department ID.
 * - Allow listing all departments in sorted order.
 *
 * Data Structures:
 * - departmentsById: ConcurrentHashMap for thread-safe storage and O(1) lookups.
 * - departmentNames: TreeSet to maintain alphabetical order of department names (optional for sorting).
 */
public class InMemoryDepartmentRepository implements DepartmentRepository{

    /**
     * Maps department IDs to Department objects.
     * Thread-safe for concurrent read/write operations.
     */
    private final Map<String, Department> departmentsById = new ConcurrentHashMap<>();

    /**
     * Stores department IDs in alphabetical order for sorted listing.
     * TreeSet automatically sorts by department ID.
     */
    private final Set<String> departmentIds  = Collections.synchronizedSet(new TreeSet<>());


    /**
     * Creates a new department and updates indexes.
     *
     * @param department the Department object to store.
     * @throws IllegalArgumentException if the department is null or ID already exists.
     */
    @Override
    public synchronized void createDepartment(Department department) {
        if (department == null || department.getId() == null) {
            throw new IllegalArgumentException("Department or ID cannot be null");
        }

        String id = department.getId();
        if (departmentsById.containsKey(id)) {
            throw new IllegalArgumentException("Department with ID " + id + " already exists");
        }

        departmentsById.put(id, department);
        departmentIds.add(id);

    }


    /**
     * Retrieves a department by its unique ID.
     *
     * @param id the department ID.
     * @return the Department object, or null if not found.
     */
    @Override
    public Department getById(@NonNull String id) {
        return departmentsById.get(id);
    }

    /**
     * Lists all departments in the system.
     * Sorted by department ID (because TreeSet is used).
     *
     * @return a list of all departments in sorted order.
     */
    @Override
    public List<Department> listAll() {
        List<Department> result = new ArrayList<>();
        synchronized (departmentIds) {
            for (String id : departmentIds) {
                Department dept = departmentsById.get(id);
                if (dept != null) {
                    result.add(dept);
                }
            }
        }
        return result;
    }
}
