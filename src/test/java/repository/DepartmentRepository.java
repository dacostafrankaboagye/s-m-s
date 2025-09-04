package repository;


import model.Department;

import java.util.List;

/**
 * Repository interface for managing Department entities.
 *
 * Responsibilities:
 * - Create and store department objects.
 * - Retrieve departments by ID.
 * - List all departments.
 */
public interface DepartmentRepository {

    /**
     * Creates and stores a new department.
     *
     * @param department the Department object to add
     * @throws IllegalArgumentException if the department ID already exists
     */
    void createDepartment(Department department);

    /**
     * Retrieves a department by its unique ID.
     *
     * @param id the department ID
     * @return the Department object, or null if not found
     */
    Department getById(String id);

    /**
     * Lists all departments in the system.
     *
     * @return a list of all departments
     */
    List<Department> listAll();
}
